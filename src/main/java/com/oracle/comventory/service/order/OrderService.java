package com.oracle.comventory.service.order;

import com.oracle.comventory.dto.orders.OrderDto;
import java.util.List;

public interface OrderService {

	
    List<OrderDto> selectOrderList(OrderDto order);
    
    OrderDto selectOrderDetail(long orderCode);

    
    
    
    int insertOrder(OrderDto order) throws Exception;
    
    int cancelOrder(long orderCode, int empNo) throws Exception;


    
    
    int approveOrder(OrderDto order)throws Exception;
    
    int rejectOrder(OrderDto order)throws Exception;
    
    int logisticsApprove(long orderCode, int apprEmpNo);
    
    int closeOrderLedger(long orderCode, int regEmpNo) throws Exception;

}

