package com.oracle.comventory.dao.order;

import com.oracle.comventory.dto.orders.OrderDto;
import com.oracle.comventory.dto.ordersDetail.OrderDetailDto;
import com.oracle.comventory.mapper.order.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class OrderDao {

    private final OrderMapper orderMapper;

    
    public List<OrderDto> selectOrderList(OrderDto order) {
        try {
            return orderMapper.selectOrderList(order);
        } catch (Exception e) {
            throw new RuntimeException("목록 조회 오류: " + e.getMessage());
        }
    }
    
    
    public OrderDto selectOrderByCode(long orderCode) {
        try {
            return orderMapper.selectOrderByCode(orderCode);
        } catch (Exception e) {
            throw new RuntimeException("단건 조회 오류: " + e.getMessage());
        }
    }
    
    
    public List<OrderDetailDto> selectOrderDetailList(long orderCode) {
        try {
            return orderMapper.selectOrderDetailList(orderCode);
        } catch (Exception e) {
            throw new RuntimeException("상세 내역 조회 오류: " + e.getMessage());
        }
    }
    
    
    public Long selectAvailableStock(Long productCode) {
        try {
            return orderMapper.selectAvailableStock(productCode);
        } catch (Exception e) {
            throw new RuntimeException("재고 확인 오류: " + e.getMessage());
        }
    }
    
    
    public int insertOrder(OrderDto order) {
        try {
            return orderMapper.insertOrder(order);
        } catch (Exception e) {
            throw new RuntimeException("주문 저장 실패: " + e.getMessage());
        }
    }
    
    
   
    public int insertOrderDetail(OrderDetailDto detail) {
        try {
            return orderMapper.insertOrderDetail(detail);
        } catch (Exception e) {
            throw new RuntimeException("상세 품목 저장 실패: " + e.getMessage());
        }
    }
    
    
    
    public int updateOrderStatus(OrderDto order) {
        try {
            return orderMapper.updateOrderStatus(order);
        } catch (Exception e) {
            throw new RuntimeException("상태 업데이트 실패: " + e.getMessage());
        }
    }
    
    
    
    
    public Integer selectDeptMgrByEmpNo(int drafterEmpNo) {
        try {
            return orderMapper.selectDeptMgrByEmpNo(drafterEmpNo);
        } catch (Exception e) {
            throw new RuntimeException("부서장 조회 오류: " + e.getMessage());
        }
    }   
       
 
    

    public Integer selectLogisticsApprover() {
        try {
            return orderMapper.selectLogisticsApprover(7000, List.of(3, 4, 5, 6));
            
           
        } catch (Exception e) {
            throw new RuntimeException("물류팀 결재자 조회 오류: " + e.getMessage());
        }
    }
    
    
    
    public List<Integer> selectLogisticsApprovers() {
        try {
            return orderMapper.selectLogisticsApprovers(7000, List.of(3, 4, 5, 6));
        } catch (Exception e) {
            throw new RuntimeException("물류팀 결재자 목록 조회 오류: " + e.getMessage());
        }
    }

    
    
    
    public List<Map<String, Object>> selectCustomers() {
        try {
            return orderMapper.selectCustomers();
        } catch (Exception e) {
            throw new RuntimeException("거래처 목록 조회 오류: " + e.getMessage());
        }
    }

    
    
    public List<Map<String, Object>> selectProducts() {
        try {
            return orderMapper.selectProducts();
        } catch (Exception e) {
            throw new RuntimeException("제품 목록 조회 오류: " + e.getMessage());
        }
    }
    
    

    public Integer selectClosingStatus(String ymd) {
        try {
            return orderMapper.selectClosingStatus(ymd);
        } catch (Exception e) {
            throw new RuntimeException("마감 상태 조회 오류: " + e.getMessage());
        }
    }
    
    

    public int promoteApprovedToShipWait(int shipWait, int manual, int auto) {
        try {
            return orderMapper.promoteApprovedToShipWait(shipWait, manual, auto);
        } catch (Exception e) {
            throw new RuntimeException("출고대기 전환 실패: " + e.getMessage());
        }
    }
    

    public int existsBatchLog(String batchName, String execDate) {
        try {
            return orderMapper.existsBatchLog(batchName, execDate);
        } catch (Exception e) {
            throw new RuntimeException("배치 이력 조회 오류: " + e.getMessage());
        }
    }


    public int insertBatchLog(String batchName, String execDate, int affectedRows,
                              java.sql.Timestamp scheduledTime) {
        try {
            return orderMapper.insertBatchLog(batchName, execDate, affectedRows, scheduledTime);
        } catch (Exception e) {
            throw new RuntimeException("배치 이력 저장 실패: " + e.getMessage());
        }
    }

    
}