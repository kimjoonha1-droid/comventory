package com.oracle.comventory.service.emp;

import java.util.List;
import com.oracle.comventory.domain.emp.Emp;
import com.oracle.comventory.dto.emp.EmpDto;

public interface EmpService {

    // 전체 조회
    List<Emp> getAllEmps();
    
    int getTotalEmp(EmpDto empDto);

    List<Emp> getEmpPageList(EmpDto empDto);
    
    // 단건 조회
    Emp getEmpByNo(int empNo);

    // ID로 조회
    Emp getEmpById(String empId);

    // 등록
    int insertEmp(EmpDto dto);

    // 수정
    int updateEmp(EmpDto dto);
    
    // 삭제 (논리 삭제)
    int deleteEmp(int empNo);

	Emp getEmpByEmpNo(int empNo);
	
	//
	EmpDto login(String empId, String empPassword);

	boolean isEmpIdAvailable(String empId);

	int getNextEmpNo(int deptCode, int userAccess);



}


