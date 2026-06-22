package com.oracle.comventory.repository.emp;

import java.util.List;
import java.util.Optional;

import java.util.HashMap;
import java.util.Map;


import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;


import com.oracle.comventory.dto.emp.EmpDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmpRepositoryImpl implements EmpRepository {

    private final SqlSession sqlSession;

    private static final String NAMESPACE = "com.oracle.comventory.dao.emp.EmpMapper.";
    
    @Override
    public List<EmpDto> findAll() {
    	System.out.println("EmpRepositoryImpl findAll Start...");

    	List<EmpDto> empList = sqlSession.selectList(NAMESPACE + "findAll");
    	System.out.println("EmpRepositoryImpl findAll empList->"+empList);
    	
     //   return sqlSession.selectList(NAMESPACE + "findAll");
    	return empList;
    }
    
    @Override
    public int getTotalEmp(EmpDto empDto) {
    	return sqlSession.selectOne(NAMESPACE + "getTotalEmp", empDto);
    }

    @Override
    public List<EmpDto> findPageList(EmpDto empDto) {
        return sqlSession.selectList(NAMESPACE + "findPageList", empDto);
    }

    
    @Override
    public Optional<EmpDto> findByEmpNo(int empNo) {
        EmpDto dto = sqlSession.selectOne(NAMESPACE + "findByEmpNo", empNo);
        return Optional.ofNullable(dto);
    }

    @Override
    public Optional<EmpDto> findByEmpId(String empId) {
        EmpDto dto = sqlSession.selectOne(NAMESPACE + "findByEmpId", empId);
        return Optional.ofNullable(dto);
    }
    
    @Override
    public Optional<EmpDto> findLoginUser(String empId, String empPassword) {
    	
        Map<String, String> param = new HashMap<>();
        param.put("empId", empId);
        param.put("empPassword", empPassword);
        sqlSession.selectOne(NAMESPACE + "findLoginUser", param);

        EmpDto dto = sqlSession.selectOne(NAMESPACE + "findLoginUser", param);
        return Optional.ofNullable(dto);
    }
	@Override
	public int checkEmpIdDuplicate(String empId) {
	    return sqlSession.selectOne(NAMESPACE + "checkEmpIdDuplicate", empId);
	}

	@Override
	public Optional<EmpDto> findLoginUser1(String empId, String empPassword) {

		
		
		return Optional.empty();
	}
	
	@Override
	public int findNextEmpNoInRange(int startEmpNo, int endEmpNo) {
	    Map<String, Integer> param = new HashMap<>();
	    param.put("startEmpNo", startEmpNo);
	    param.put("endEmpNo", endEmpNo);

	    return sqlSession.selectOne(NAMESPACE + "findNextEmpNoInRange", param);
	}
	
    @Override
    public int insert(EmpDto dto) {
        return sqlSession.insert(NAMESPACE + "insert", dto);
    }

    @Override
    public int update(EmpDto dto) {
        return sqlSession.update(NAMESPACE + "update", dto);
    }

    @Override
    public int delete(int empNo) {
        return sqlSession.update(NAMESPACE + "delete", empNo);
    }


//	@Override
//	public int checkEmpIdDuplicate(String empId) {
//	    return sqlSession.selectOne(NAMESPACE + "checkEmpIdDuplicate", empId);
//	}
//
//	@Override
//	public Optional<EmpDto> findLoginUser1(String empId, String empPassword) {
//		// TODO Auto-generated method stub
//		return Optional.empty();
//	}


}