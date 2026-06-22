package com.oracle.comventory.mapper.order;

import com.oracle.comventory.dto.orders.OrderDto;
import com.oracle.comventory.dto.ordersDetail.OrderDetailDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    List<OrderDto> selectOrderList(OrderDto order);

    OrderDto selectOrderByCode(long orderCode);

    List<OrderDetailDto> selectOrderDetailList(long orderCode);

    Long selectAvailableStock(Long productCode);

    int insertOrder(OrderDto order);

    int insertOrderDetail(OrderDetailDto detail);

    int updateOrderStatus(OrderDto order);

    Integer selectDeptMgrByEmpNo(int drafterEmpNo);

    Integer selectLogisticsApprover(
        @Param("deptCode") int deptCode, 
        @Param("grades") List<Integer> grades
    );

    List<Integer> selectLogisticsApprovers(
        @Param("deptCode") int deptCode, 
        @Param("grades") List<Integer> grades
    );

    List<Map<String, Object>> selectCustomers();

    List<Map<String, Object>> selectProducts();


    Integer selectClosingStatus(String ymd);


    int promoteApprovedToShipWait(
        @Param("shipWait") int shipWait,
        @Param("manual")   int manual,
        @Param("auto")     int auto
    );

    int existsBatchLog(@Param("batchName") String batchName,
            @Param("execDate") String execDate);

    int insertBatchLog(@Param("batchName") String batchName,
            @Param("execDate") String execDate,
            @Param("affectedRows") int affectedRows,
            @Param("scheduledTime") java.sql.Timestamp scheduledTime);

}