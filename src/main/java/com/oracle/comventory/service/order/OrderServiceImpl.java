package com.oracle.comventory.service.order;

import com.oracle.comventory.dao.order.OrderDao;
import com.oracle.comventory.dto.orders.OrderDto;
import com.oracle.comventory.dto.ordersDetail.OrderDetailDto;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {



    private static final int STATUS_DRAFT_REQUEST   = 110; 
    private static final int STATUS_DRAFT_WITHDRAW  = 140; 
    @SuppressWarnings("unused")
    private static final int STATUS_REVIEW_PROGRESS = 210;
    @SuppressWarnings("unused")
    private static final int STATUS_REVIEW_APPROVE  = 220; 
    @SuppressWarnings("unused")
    private static final int STATUS_REVIEW_REJECT   = 230;
    @SuppressWarnings("unused")
    private static final int STATUS_APPROVAL_WAIT   = 310;
    private static final int STATUS_APPROVAL_MANUAL = 320; 
    private static final int STATUS_APPROVAL_AUTO   = 325; 
    private static final int STATUS_APPROVAL_REJECT = 330; 
    private static final int STATUS_SHIP_WAIT       = 410;
    @SuppressWarnings("unused")
    private static final int STATUS_SHIP_COMPLETE   = 420; 
    private static final int STATUS_LEDGER_CLOSED   = 710; 

    private static final LocalTime CUTOFF_TIME    = LocalTime.of(14, 0);
    private static final LocalTime NEXT_BIZ_START = LocalTime.of(8, 0); 

    private static final String BATCH_PROMOTE_SHIP_WAIT = "PROMOTE_SHIP_WAIT";

    
    private final OrderDao orderDao;

    @Override
    public List<OrderDto> selectOrderList(OrderDto order) {
        List<OrderDto> orderList = orderDao.selectOrderList(order);
        for (OrderDto o : orderList) {
            o.setOrderDetails(orderDao.selectOrderDetailList(o.getOrder_code()));
        }
        return orderList;
    }

    @Override
    public OrderDto selectOrderDetail(long orderCode) {
        OrderDto order = orderDao.selectOrderByCode(orderCode);
        if (order != null) {
            order.setOrderDetails(orderDao.selectOrderDetailList(orderCode));
        }
        return order;
    }


    @Override
    @Transactional
    public int insertOrder(OrderDto order) throws Exception {
        validateAndCalculate(order);

        System.out.println("========================================");
        System.out.println("🔍 [insertOrder 진입]");
        for (OrderDetailDto d : order.getOrderDetails()) {
            Long stock = orderDao.selectAvailableStock(d.getProduct_code());
            System.out.println("  - 제품:" + d.getProduct_code()
                    + " | 요청:" + d.getOrder_amount()
                    + " | 재고:" + stock
                    + " | 자동승인:" + (stock != null && d.getOrder_amount() * 2 <= stock));
        }

        boolean isStockRich = checkStockRich(order.getOrderDetails());
        System.out.println("🎯 isStockRich 결과: " + isStockRich);
        System.out.println("========================================");

        if (isStockRich) {
            order.setEapp_status(STATUS_APPROVAL_AUTO);
            order.setApproval_timestamp(new java.sql.Timestamp(System.currentTimeMillis())); 
            setConfirmedDate(order);
        } else {
            order.setEapp_status(STATUS_DRAFT_REQUEST);
        }

        Integer logisticsApprover = orderDao.selectLogisticsApprover();
        if (logisticsApprover == null) {
            throw new Exception("물류팀 결재 가능한 담당자를 찾을 수 없습니다.");
        }
        order.setAppr_empno(logisticsApprover);

        int result = orderDao.insertOrder(order);
        saveOrderDetails(order);
        return result;
    }


    @Override
    @Transactional
    public int cancelOrder(long orderCode, int empNo) throws Exception {
        OrderDto current = getValidatedOrder(orderCode);
        if (!Integer.valueOf(empNo).equals(current.getDrafter_empno()))
            throw new Exception("기안자 본인만 가능합니다.");
        if (!Integer.valueOf(STATUS_DRAFT_REQUEST).equals(current.getEapp_status()))
            throw new Exception("기안요청 상태에서만 취소 가능합니다.");
        current.setEapp_status(STATUS_DRAFT_WITHDRAW);
        current.setOrder_cancelled_date(new java.sql.Timestamp(System.currentTimeMillis()));
        return orderDao.updateOrderStatus(current);
    }


    @Override
    @Transactional
    public int approveOrder(OrderDto order) throws Exception {
        OrderDto current = getValidatedOrder(order.getOrder_code());

        if (!Integer.valueOf(STATUS_DRAFT_REQUEST).equals(current.getEapp_status())) {
            throw new Exception("기안요청 상태에서만 승인할 수 있습니다.");
        }
        if (order.getRequester_empno() == null) {
            throw new Exception("요청자 사원번호가 누락되었습니다.");
        }
        if (!order.getRequester_empno().equals(current.getAppr_empno())) {
            throw new Exception("승인 권한이 없습니다. 지정된 결재자만 승인할 수 있습니다.");
        }
        order.setEapp_status(STATUS_APPROVAL_MANUAL); 
        order.setApproval_timestamp(new java.sql.Timestamp(System.currentTimeMillis())); // ← 이 줄 추가
        setConfirmedDate(order);
        return orderDao.updateOrderStatus(order);
    }


    @Override
    @Transactional
    public int rejectOrder(OrderDto order) throws Exception {
        OrderDto current = getValidatedOrder(order.getOrder_code());

        if (!Integer.valueOf(STATUS_DRAFT_REQUEST).equals(current.getEapp_status())) {
            throw new Exception("기안요청 상태에서만 반려할 수 있습니다.");
        }
        if (order.getRequester_empno() == null) {
            throw new Exception("요청자 사원번호가 누락되었습니다.");
        }
        if (!order.getRequester_empno().equals(current.getAppr_empno())) {
            throw new Exception("반려 권한이 없습니다.");
        }
        if (order.getOrder_refuse() == null || order.getOrder_refuse().trim().isEmpty()) {
            throw new Exception("반려 사유를 입력해야 합니다.");
        }

        order.setEapp_status(STATUS_APPROVAL_REJECT); // 330
        order.setOrder_rejected_date(new java.sql.Timestamp(System.currentTimeMillis()));
        return orderDao.updateOrderStatus(order);
    }


    @Override
    @Transactional
    public int logisticsApprove(long orderCode, int apprEmpNo) {
        throw new UnsupportedOperationException(
            "검토 단계는 현재 미구현입니다. (210/220/230 자리 예약만 되어 있음)");
    }


    @Override
    @Transactional
    public int closeOrderLedger(long orderCode, int regEmpNo) throws Exception {
        OrderDto order = getValidatedOrder(orderCode);

        Integer status = order.getEapp_status();
        if (!Integer.valueOf(STATUS_SHIP_WAIT).equals(status)
                && !Integer.valueOf(STATUS_APPROVAL_MANUAL).equals(status)
                && !Integer.valueOf(STATUS_APPROVAL_AUTO).equals(status)) {
            throw new Exception("출고대기/승인/자동승인 상태만 마감 가능합니다.");
        }

        order.setEapp_status(STATUS_LEDGER_CLOSED); // 710
        order.setReg_emp_no(regEmpNo);
        return orderDao.updateOrderStatus(order);
    }


 @Scheduled(cron = "0 0 8 * * MON-FRI")
 @Transactional
 public void promoteToShipWaitScheduled() {
     LocalDateTime scheduled = LocalDate.now().atTime(8, 0);
     promoteToShipWaitInternal(java.sql.Timestamp.valueOf(scheduled));
 }

 @PostConstruct
 public void runMissedBatchOnStartup() {
     LocalDateTime now = LocalDateTime.now();
     if (isBusinessDay(now.toLocalDate()) && now.toLocalTime().isAfter(LocalTime.of(8, 0))) {
         System.out.println("🔄 [서버시작] 08:00 이후 시작 감지 - 보정 배치 실행");
         promoteToShipWaitInternal(null); // 보정 실행은 예정시각 null
     } else {
         System.out.println("ℹ️  [서버시작] 08:00 이전 또는 휴일 - 누락 배치 없음");
     }
 }


 @Transactional
 public void promoteToShipWaitInternal(java.sql.Timestamp scheduledTime) {
     LocalDate today = LocalDate.now();
     String todayStr = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

     if (orderDao.existsBatchLog(BATCH_PROMOTE_SHIP_WAIT, todayStr) > 0) {
         System.out.println("⏭️  [배치] 오늘 이미 실행됨 - SKIP (" + todayStr + ")");
         return;
     }

     if (!isBusinessDay(today)) {
         System.out.println("⏭️  [배치] 오늘은 영업일 아님 - SKIP");
         return;
     }

     int updated = orderDao.promoteApprovedToShipWait(STATUS_SHIP_WAIT,
             STATUS_APPROVAL_MANUAL, STATUS_APPROVAL_AUTO);


     orderDao.insertBatchLog(BATCH_PROMOTE_SHIP_WAIT, todayStr, updated, scheduledTime);

     String type = (scheduledTime != null) ? "정시" : "보정";
     System.out.println("✅ [배치 " + type + "] 출고대기 전환 완료: " + updated + "건");
 }
    

    private boolean checkStockRich(List<OrderDetailDto> details) {
        for (OrderDetailDto detail : details) {
            Long stock = orderDao.selectAvailableStock(detail.getProduct_code());
            if (stock == null) return false;
            if (detail.getOrder_amount() * 2 > stock) return false;
        }
        return true;
    }


    
    private void setConfirmedDate(OrderDto order) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime confirmedDate;

        if (now.toLocalTime().isBefore(CUTOFF_TIME) && isBusinessDay(now.toLocalDate())) {
            confirmedDate = now;
        } else {
            LocalDate nextDate = now.toLocalDate().plusDays(1);
            int safety = 0;
            while (!isBusinessDay(nextDate) && safety++ < 30) {
                nextDate = nextDate.plusDays(1);
            }
            confirmedDate = nextDate.atTime(NEXT_BIZ_START);
        }

        order.setOrder_confirmed_date(java.sql.Timestamp.valueOf(confirmedDate));
    }


    private boolean isBusinessDay(LocalDate date) {
        DayOfWeek dow = date.getDayOfWeek();
        if (dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY) return false;

        String ymd = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Integer closingStatus = orderDao.selectClosingStatus(ymd);
        if (closingStatus != null && closingStatus == 2) return false;

        return true;
    }

    private void validateAndCalculate(OrderDto order) throws Exception {
        if (order.getOrderDetails() == null || order.getOrderDetails().isEmpty())
            throw new Exception("품목이 없습니다.");

        long total = 0;
        for (OrderDetailDto d : order.getOrderDetails()) {
            d.calculateTotal();
            total += d.getOrder_amt_price();
        }
        order.setOrder_sum_price(total);
    }

    private void saveOrderDetails(OrderDto order) {
        for (OrderDetailDto detail : order.getOrderDetails()) {
            detail.setOrder_code(order.getOrder_code());
            orderDao.insertOrderDetail(detail);
        }
    }

    private OrderDto getValidatedOrder(long code) {
        OrderDto order = orderDao.selectOrderByCode(code);
        if (order == null) throw new RuntimeException("주문을 찾을 수 없습니다.");
        return order;
    }
}