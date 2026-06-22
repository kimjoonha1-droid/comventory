package com.oracle.comventory.dto.dept;

import java.util.Date;

public class DeptDto {
    private int dept_code;              // 부서 번호
    private String dept_name;           // 부서 이름
    private String dept_tel;            // 부서 대표 전화
    private String dept_loc;            // 부서 위치
    private String dept_mgr; 			// 부서장 dept_code
    
    // 부서장 표시용
    private String dept_mgr_display;    // 부서장 표시용: 이름(사원번호)
    
    private String dept_gubun;          // 부서 구분
    private Date in_date;               // 등록일 (원본 Date)
    private String str_in_date;         // 등록일 (문자열 포맷)
    
    private int reg_emp_no;
    private Date reg_date;
    
    // 검색, 페이징용
    private String searchKeyword;
    private int start;
    private int end;
    
    // 기본 생성자
    public DeptDto() {}

    // 모든 필드를 포함한 생성자
    public DeptDto(int dept_code, String dept_name, String dept_tel, String dept_loc,
            String dept_mgr, String dept_gubun, Date in_date, String str_in_date) {
    	this.dept_code = dept_code;
        this.dept_name = dept_name;
        this.dept_tel = dept_tel;
        this.dept_loc = dept_loc;
        this.dept_mgr = dept_mgr;
        this.dept_gubun = dept_gubun;
        this.in_date = in_date;
        this.str_in_date = str_in_date;
    }

    // Getter & Setter
    public int getDept_code() {
        return dept_code;
    }

    public void setDept_code(int dept_code) {
        this.dept_code = dept_code;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public String getDept_tel() {
        return dept_tel;
    }

    public void setDept_tel(String dept_tel) {
        this.dept_tel = dept_tel;
    }

    public String getDept_loc() {
        return dept_loc;
    }

    public void setDept_loc(String dept_loc) {
        this.dept_loc = dept_loc;
    }

    public String getDept_mgr() {
        return dept_mgr;
    }

    public void setDept_mgr(String dept_mgr) {
        this.dept_mgr = dept_mgr;
    }

    public String getDept_mgr_display() {
        return dept_mgr_display;
    }

    public void setDept_mgr_display(String dept_mgr_display) {
        this.dept_mgr_display = dept_mgr_display;
    }
    
    public String getDept_gubun() {
        return dept_gubun;
    }

    public void setDept_gubun(String dept_gubun) {
        this.dept_gubun = dept_gubun;
    }

    public Date getIn_date() {
        return in_date;
    }

    public void setIn_date(Date in_date) {
        this.in_date = in_date;
    }

    public String getStr_in_date() {
        return str_in_date;
    }

    public void setStr_in_date(String str_in_date) {
        this.str_in_date = str_in_date;
    }

    @Override
    public String toString() {
        return "DeptDto{" +
                "dept_code=" + dept_code +
                ", dept_name='" + dept_name + '\'' +
                ", dept_tel='" + dept_tel + '\'' +
                ", dept_loc='" + dept_loc + '\'' +
                ", dept_mgr='" + dept_mgr + '\'' +
                ", dept_mgr_display='" + dept_mgr_display + '\'' +
                ", dept_gubun='" + dept_gubun + '\'' +
                ", in_date=" + in_date +
                ", str_in_date='" + str_in_date + '\'' +
                '}';
    }

	public String getDept_loc1() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDept_del_status() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getReg_emp_no() {
	    return reg_emp_no;
	}

	public Date getReg_date() {
	    return reg_date;
	}

	public void setDept_del_status(Object object) {
		// TODO Auto-generated method stub
		
	}

	public void setReg_emp_no(int reg_emp_no) {
	    this.reg_emp_no = reg_emp_no;
	}

	public void setReg_date(Date reg_date) {
	    this.reg_date = reg_date;
	}
	
	public String getSearchKeyword() {
	    return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
	    this.searchKeyword = searchKeyword;
	}

	public int getStart() {
	    return start;
	}

	public void setStart(int start) {
	    this.start = start;
	}

	public int getEnd() {
	    return end;
	}

	public void setEnd(int end) {
	    this.end = end;
	}
}
