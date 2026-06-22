package com.oracle.comventory.dto.ordersDetail; 

import java.util.Date;
import lombok.Data; 

@Data
public class OrderDetailDto {

    
    private long order_code;             
    private long product_code;           
    private long orders_drafter_empno;   
    private Integer article_code;        
    private long order_amount;           
    private long order_unit_price;       
    private long order_amt_price;        
    private long reg_emp_no;            
    private Date purchase_reg_date;      


    private String product_name;         
    private String product_category_name;
    private String drafter_name;         
    private String article_name;         

    

    public void calculateTotal() {
    	if (this.order_amount > 0 && this.order_unit_price > 0) {
            this.order_amt_price = this.order_amount * this.order_unit_price;
        }
    }
}