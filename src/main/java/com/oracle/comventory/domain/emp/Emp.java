package com.oracle.comventory.domain.emp;

import java.util.Date;
import com.oracle.comventory.dto.emp.EmpDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Emp {

    private int     empNo;
    private String  empName;
    private String  empId;
    private String  empPassword;
    private String  empTel;
    private int     empGrade;
    private Integer empSal;
    private String  empEmail;
    private String  empPic;
    private int     userAccess;
    private int     deptCode;
    
    private String deptName;
    
    private Date    empJoinDate;
    private boolean deleted;
    private int     regEmpNo;
    private Date    regDate;

    // ==============================
    // 비즈니스 메서드
    // ==============================

    public boolean isActive()      { return !this.deleted; }
    public boolean isAdmin()       { return this.empGrade == 1; }
    public boolean hasFullAccess() { return this.userAccess == 1; }

    // ==============================
    // DTO → Entity 변환 (정적 팩토리)
    // ==============================
    public static Emp fromDto(EmpDto dto) {
        return Emp.builder()
                .empNo(dto.getEmp_no())
                .empName(dto.getEmp_name())
                .empId(dto.getEmp_id())
                .empPassword(dto.getEmp_password())
                .empTel(dto.getEmp_tel())
                .empGrade(dto.getEmp_grade())
                .empSal(dto.getEmp_sal())
                .empEmail(dto.getEmp_email())
                .empPic(dto.getEmp_pic())
                .userAccess(dto.getUser_access())
                .deptCode(dto.getDept_code())
                .deptName(dto.getDept_name())
                .empJoinDate(dto.getEmp_join_date())
                .deleted(dto.getEmp_del_status() == 1)
                .regEmpNo(dto.getReg_emp_no())
                .regDate(dto.getReg_date())
                .build();
    }

    // ==============================
    // Entity → DTO 변환
    // ==============================
    public EmpDto toDto() {
        EmpDto dto = new EmpDto();
        dto.setEmp_no(this.empNo);
        dto.setEmp_name(this.empName);
        dto.setEmp_id(this.empId);
        dto.setEmp_password(this.empPassword);
        dto.setEmp_tel(this.empTel);
        dto.setEmp_grade(this.empGrade);
        dto.setEmp_sal(this.empSal);
        dto.setEmp_email(this.empEmail);
        dto.setEmp_pic(this.empPic);
        dto.setUser_access(this.userAccess);
        dto.setDept_code(this.deptCode);
        dto.setEmp_join_date(this.empJoinDate);
        dto.setEmp_del_status(this.deleted ? 1 : 0);
        dto.setReg_emp_no(this.regEmpNo);
        dto.setReg_date(this.regDate);
        return dto;
    }
}

