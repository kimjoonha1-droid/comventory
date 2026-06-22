package com.oracle.comventory.dto.emp;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

@Data
public class EmpDto {
	private int    emp_no;
	private String emp_name;
	private String emp_id;
	private String emp_password;
	private String emp_tel;
	private int    emp_grade;
	private Integer emp_sal;
	private String emp_email;
	private String emp_pic;
	private int    user_access;
	private int    dept_code;
	
	private String dept_name;
	
	private Date   emp_join_date;
	private int    emp_del_status;
	private int    reg_emp_no;
	private Date   reg_date;
	
	private String searchKeyword;
	
	private String empType;
	
	private int start;
	private int end;
	
	public void setReg_date(String regDate) {
		// TODO Auto-generated method stub
		
	}
	public void setEmp_join_date(String empJoinDate) {
		// TODO Auto-generated method stub
		
	}
	public void setEmp_join_date(Timestamp valueOf) {
		// TODO Auto-generated method stub
		
	}
	public void setReg_date(Timestamp valueOf) {
		// TODO Auto-generated method stub
		
	}
	public void setEmp_join_date(Date empJoinDate) {
	    this.emp_join_date = empJoinDate;
	}
	public void setReg_date(Date regDate) {
	    this.reg_date = regDate;
	}

	public void setEmp_sal(Integer empSal) {
	    this.emp_sal = empSal;
	}
}
