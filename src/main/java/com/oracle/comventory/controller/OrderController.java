package com.oracle.comventory.controller;

import com.oracle.comventory.dao.order.OrderDao;
import com.oracle.comventory.dto.emp.EmpDto;
import com.oracle.comventory.dto.orders.OrderDto;
import com.oracle.comventory.service.order.OrderService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//========== JSP 페이지 반환 ==========
@Controller
@RequiredArgsConstructor
class OrderPageController {
 
 @GetMapping("/orderList")
 public String orderListPage() {
     return "orderList";
 }
 
 // orderMain 
 @GetMapping("/order/orderMain")
 public String orderMainPage(HttpSession session, Model model) {

     EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");
     
     if (loginUser == null) {
         return "redirect:/login";
     }
     
     Integer empNo = loginUser.getEmp_no();         // ← getter 이름은 실제 EmpDto 필드명에 맞춰주세요
     Integer deptCode = loginUser.getDept_code();   // ← 마찬가지로 EmpDto 필드명 확인 필요
     
     System.out.println("✅ 로그인 사용자: empNo=" + empNo + ", deptCode=" + deptCode);
     
     model.addAttribute("currentEmpNo", empNo);
     model.addAttribute("currentDeptCode", deptCode);
     
     return "order/orderMain";
 	}
 }


@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderDao orderDao; // jsp떄문에 추가 (리액트변경시 삭제)

    

    @GetMapping
    public ResponseEntity<?> getOrderList(
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "custCode", required = false) Integer custCode,
            @RequestParam(value = "status", required = false) Integer[] status,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {

        try {
            OrderDto searchParam = new OrderDto();
            searchParam.setStartDate(startDate);
            searchParam.setEndDate(endDate);
            searchParam.setCustCodeParam(custCode); 
            searchParam.setStatusList(status);
            searchParam.setSearchKeyword(searchKeyword);

            List<OrderDto> orderList = orderService.selectOrderList(searchParam);
            return ResponseEntity.ok(orderList);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorBody("수주 목록 조회 실패: " + e.getMessage()));
        }
    }
    
    
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    // 🔥 jsp때문에 새로 추가: 물류팀 결재자 조회 API (리액트변경시 삭제)
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    @GetMapping("/logistics-approvers")
    public ResponseEntity<?> getLogisticsApprovers() {
        try {
            List<Integer> approvers = orderDao.selectLogisticsApprovers();
            Map<String, Object> result = new HashMap<>();
            result.put("approvers", approvers);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorBody("물류팀 결재자 조회 실패: " + e.getMessage()));
        }
    }

    
    
    
 
    @GetMapping("/{orderCode}")
    public ResponseEntity<?> getOrderDetail(@PathVariable("orderCode") long orderCode) {
        try {
            OrderDto order = orderService.selectOrderDetail(orderCode);
            if (order == null) {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody("해당 주문을 찾을 수 없습니다."));
            }
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody("조회 실패:" + e.getMessage()));
        }
    }

    
    
  
    @PostMapping
    public ResponseEntity<?> insertOrder(@RequestBody OrderDto orderDto) {
        try {
            int result = orderService.insertOrder(orderDto);
            Map<String, Object> resultBody = new HashMap<>();
            resultBody.put("orderCode", orderDto.getOrder_code());
            resultBody.put("message", "수주 기안 완료");
            return ResponseEntity.status(HttpStatus.CREATED).body(resultBody);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody("등록 실패: " + e.getMessage()));
        }
    }

    
    
    @PutMapping("/{orderCode}/cancel")
    public ResponseEntity<?> cancelOrder(
            @PathVariable("orderCode") long orderCode,
            @RequestBody Map<String, Integer> body) {
        try {
            Integer empNo = body.get("empNo");
            if (empNo == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody("기안 취소 실패: empNo가 누락되었습니다."));
            }
            int result = orderService.cancelOrder(orderCode, empNo);
            return ResponseEntity.ok(successBody("기안 취소 완료", result));
        } catch (Exception e) {
        	String msg = e.getMessage();
        	if (msg.contains("본인만")) {
        		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorBody("기안 취소 실패: "+msg));
        	}
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody("기안 취소 실패: "+msg));
        }
    }

    
    
    
    @PutMapping("/{orderCode}/approve")
    public ResponseEntity<?> approveOrder(@PathVariable("orderCode") long orderCode, @RequestBody OrderDto orderDto) {
        try {
            orderDto.setOrder_code(orderCode);
            int result = orderService.approveOrder(orderDto);
            return ResponseEntity.ok(successBody("승인 처리 완료", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody("승인 실패: " + e.getMessage()));
        }
    }

    
    
   
    @PutMapping("/{orderCode}/reject")
    public ResponseEntity<?> rejectOrder(@PathVariable("orderCode") long orderCode, @RequestBody OrderDto orderDto) {
        try {
            orderDto.setOrder_code(orderCode);
            int result = orderService.rejectOrder(orderDto);
            return ResponseEntity.ok(successBody("반려 처리 완료", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody("반려 실패: " + e.getMessage()));
        }
    }

    
    
   
    @PutMapping("/{orderCode}/logistics")
    public ResponseEntity<?> logisticsApprove(
            @PathVariable("orderCode") long orderCode, 
            @RequestBody Map<String, Integer> body) {
        try {
            Integer apprEmpNo = body.get("apprEmpNo");
            if (apprEmpNo == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody("물류 승인 실패: apprEmpNo가 누락되었습니다."));
            }
            int result = orderService.logisticsApprove(orderCode, apprEmpNo);
            return ResponseEntity.ok(successBody("물류 승인 완료", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody("물류 승인 실패: " + e.getMessage()));
        }
    }

    
    
    
    @PutMapping("/{orderCode}/close")
    public ResponseEntity<?> closeOrderLedger(
            @PathVariable("orderCode") long orderCode,
            @RequestBody Map<String, Integer> body) {
        try {
            Integer regEmpNo = body.get("regEmpNo");
            if (regEmpNo == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody("마감 실패: regEmpNo가 누락되었습니다."));
            }
            int result = orderService.closeOrderLedger(orderCode, regEmpNo);
            return ResponseEntity.ok(successBody("수불 마감 완료", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody("마감 실패: " + e.getMessage()));
        }
    }

    
    
    
    @GetMapping("/customers")
    public ResponseEntity<?> getCustomers() {
        try {
            List<Map<String, Object>> customers = orderDao.selectCustomers();
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorBody("거래처 목록 조회 실패: " + e.getMessage()));
        }
    }

    
    
    
    
    @GetMapping("/products")
    public ResponseEntity<?> getProducts() {
        try {
            List<Map<String, Object>> products = orderDao.selectProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorBody("제품 목록 조회 실패: " + e.getMessage()));
        }
    }

    
    private Map<String, Object> errorBody(String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("success", false);
        body.put("message", message);
        return body;
    }

    private Map<String, Object> successBody(String message, int affectedRows) {
        Map<String, Object> body = new HashMap<>();
        body.put("success", true);
        body.put("message", message);
        body.put("affectedRows", affectedRows);
        return body;
    }
} 
