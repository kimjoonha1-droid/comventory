package com.oracle.comventory.dto.closingDay;

import java.util.Date;

import lombok.Data;

@Data
public class ClosingDayDto {
	private String closing_ymd;
	private int    closing_status;
	private int    reg_emp_no;
	private Date   reg_date;
	
}
