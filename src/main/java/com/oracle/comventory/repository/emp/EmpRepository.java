package com.oracle.comventory.repository.emp;

import java.util.List;
import java.util.Optional;

import com.oracle.comventory.domain.emp.Emp;
import com.oracle.comventory.dto.emp.EmpDto;

public interface EmpRepository {

    // 전체 조회
    List<EmpDto> findAll();
    
    int getTotalEmp(EmpDto empDto);

    List<EmpDto> findPageList(EmpDto empDto);
    
    // 단건 조회
    Optional<EmpDto> findByEmpNo(int empNo);

    // ID로 조회
    Optional<EmpDto> findByEmpId(String empId);
   
    //
    Optional<EmpDto> findLoginUser1(String empId, String empPassword);

    // ID 중복 체크
    int checkEmpIdDuplicate(String empId);

    int findNextEmpNoInRange(int startEmpNo, int endEmpNo);

    // 등록
    int insert(EmpDto dto);

    // 수정
    int update(EmpDto dto);

    // 삭제 (논리 삭제)
    int delete(int empNo);

	Optional<EmpDto> findLoginUser(String empId, String empPassword);

//	int checkEmpIdDuplicate1(String empId);
//
//	Optional<EmpDto> findLoginUser11(String empId, String empPassword);

}