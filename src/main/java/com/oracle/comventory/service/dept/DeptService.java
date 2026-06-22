package com.oracle.comventory.service.dept;

import com.oracle.comventory.dto.dept.DeptDto;
import com.oracle.comventory.dto.emp.EmpDto;

import java.util.List;

public interface DeptService {

    // 부서 목록 조회
    List<DeptDto> getDeptList();

    List<DeptDto> getDeptList(String searchKeyword);
    
    int getTotalDept(DeptDto deptDto);

    List<DeptDto> getDeptPageList(DeptDto deptDto);
    
    // 부서 상세 조회
    DeptDto getDeptDetail(int deptCode);

    // 부서 등록
    void saveDept(DeptDto deptDto);

    // 부서 수정
    void updateDept(DeptDto deptDto);

    // 부서 삭제
    void deleteDept(int deptCode);
    
    int getNextDeptCode();

    List<EmpDto> getDeptManagerList();
}
