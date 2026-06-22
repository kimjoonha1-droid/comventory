package com.oracle.comventory.dto.orders;

import java.util.Date;
import java.util.List;

import com.oracle.comventory.dto.ordersDetail.OrderDetailDto;

import lombok.Data;

@Data
public class OrderDto {
     
    private long    order_code;
    private int    cust_code;
    private Date   order_drafting_date;
    private java.sql.Timestamp order_confirmed_date;
    private java.sql.Timestamp order_rejected_date;
    private java.sql.Timestamp order_cancelled_date; 
    private java.sql.Timestamp approval_timestamp;
    private long   order_sum_price;
    private Integer    drafter_empno;
    private Date   draft_timestamp;
    private Integer    appr_empno;
    private Date   appr_timestamp;
    private String doc_cc;
    private Integer eapp_status;
    private String eapp_note;
    private String eapp_specific_matters;
    private String eapp_pfa;
    private String order_refuse;
    private String eapp_comment_appr_emp_name;
    private String eapp_comment_drafter_emp_name;
    private int    reg_emp_no;
    private Date   purchase_reg_date;

    
    
    
    private String cust_name;
    private String drafter_name;
    private String appr_name;
    private String status_name;
    
    
    private List<OrderDetailDto> orderDetails;

    
    private Integer requester_empno;    
    
   
    private String startDate;
    private String endDate;
    private Integer custCodeParam;
    private Integer[] statusList;
    private String searchKeyword;

}