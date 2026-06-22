package com.oracle.comventory.dto.realItems;

import java.util.Date;

import lombok.Data;

@Data
public class RealItemsDto {
	private String real_ymd;
	private int    product_code;
	private int    change_qty;
	private String change_reason;
	private int    confirm_status;
	private int    reg_emp_no;
	private Date   reg_date;
	
	// 조회용
	private String product_name;
	private String category_name;
	private int    warehouse_qty;

}
