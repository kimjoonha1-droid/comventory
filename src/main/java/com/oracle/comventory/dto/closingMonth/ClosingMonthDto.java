package com.oracle.comventory.dto.closingMonth;

import java.util.Date;

import lombok.Data;

@Data
public class ClosingMonthDto {
	private String closing_ym;
	private int    closing_type;
	private int    product_code;
	private int    qty;
	private int    reg_emp_no;
	private Date   reg_date;
}
