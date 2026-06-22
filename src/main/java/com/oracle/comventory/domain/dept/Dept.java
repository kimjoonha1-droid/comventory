package com.oracle.comventory.domain.dept;

import java.util.Date;
import com.oracle.comventory.dto.dept.DeptDto;
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
public class Dept {

    private int     deptCode;          // 부서 번호
    private String  deptName;          // 부서 이름
    private String  deptTel;           // 부서 대표 전화
    private String  deptLoc;           // 부서 위치
    private String  deptMgr;		   // 부서장 이름
    private String  deptGubun;         // 부서 구분
    private Date    inDate;            // 등록일 (Date)
    private boolean deleted;           // 삭제 여부
    private int     regEmpNo;          // 등록자 사번
    private Date    regDate;           // 등록일시

    // ==============================
    // 비즈니스 메서드
    // ==============================
    public boolean isActive() {
        return !this.deleted;
    }

    public boolean isHeadOffice() {
        return "본사".equalsIgnoreCase(this.deptGubun);
    }

    // ==============================
    // DTO → Entity 변환 (정적 팩토리)
    // ==============================
    public static Dept fromDto(DeptDto dto) {
        return Dept.builder()
                .deptCode(dto.getDept_code())
                .deptName(dto.getDept_name())
                .deptTel(dto.getDept_tel())
                .deptLoc(dto.getDept_loc())
                .deptMgr(dto.getDept_mgr())
                .deptGubun(dto.getDept_gubun())
                .inDate(dto.getIn_date())
                .deleted("Y".equalsIgnoreCase(dto.getDept_del_status()))
                .regEmpNo(dto.getReg_emp_no())
                .regDate(dto.getReg_date())
                .build();
    }

    // ==============================
    // Entity → DTO 변환
    // ==============================
    public DeptDto toDto() {
        DeptDto dto = new DeptDto();
        dto.setDept_code(this.deptCode);
        dto.setDept_name(this.deptName);
        dto.setDept_tel(this.deptTel);
        dto.setDept_loc(this.deptLoc);
        dto.setDept_mgr(this.deptMgr);
        dto.setDept_gubun(this.deptGubun);
        dto.setIn_date(this.inDate);
        dto.setDept_del_status(this.deleted ? "Y" : "N");
        dto.setReg_emp_no(this.regEmpNo);
        dto.setReg_date(this.regDate);
        return dto;
    }
}
