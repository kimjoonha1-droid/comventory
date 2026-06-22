package com.oracle.comventory.dao.dept;

import java.util.List;
import java.util.Optional;

import com.oracle.comventory.dto.dept.DeptDto;
import com.oracle.comventory.dto.emp.EmpDto;

public interface DeptDao {
    // 전체 부서 목록 조회
    List<DeptDto> findAll();

    List<DeptDto> findAll(String searchKeyword);
    
    int getTotalDept(DeptDto deptDto);

    List<DeptDto> findPageList(DeptDto deptDto);
    
    // 특정 부서 조회
    Optional<DeptDto> findById(int deptCode);

    // 부서 등록
    void save(DeptDto deptDto);

    // 부서 수정
    void update(DeptDto deptDto);

    // 부서 삭제
    void delete(int deptCode);
    
    Integer findNextDeptCode();

    List<EmpDto> findDeptManagerList();
}
