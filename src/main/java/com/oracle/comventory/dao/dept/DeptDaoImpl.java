package com.oracle.comventory.dao.dept;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.comventory.dto.dept.DeptDto;
import com.oracle.comventory.dto.emp.EmpDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeptDaoImpl implements DeptDao {

    private final SqlSession sqlSession;
    private static final String NAMESPACE = "com.oracle.comventory.dao.dept.DeptMapper.";

    @Override
    public List<DeptDto> findAll() {
        System.out.println("DeptDaoImpl deptSelect Start...");

        List<DeptDto> deptList = sqlSession.selectList(NAMESPACE + "findAll");

        System.out.println("DeptDaoImpl deptSelect deptList.size()-->" + deptList.size());

        return deptList;
    }

    @Override
    public List<DeptDto> findAll(String searchKeyword) {
        return sqlSession.selectList(NAMESPACE + "findAll", searchKeyword);
    }
    
    @Override
    public int getTotalDept(DeptDto deptDto) {
        return sqlSession.selectOne(NAMESPACE + "getTotalDept", deptDto);
    }

    @Override
    public List<DeptDto> findPageList(DeptDto deptDto) {
        return sqlSession.selectList(NAMESPACE + "findPageList", deptDto);
    }
    
    @Override
    public Optional<DeptDto> findById(int deptCode) {
        return Optional.ofNullable(sqlSession.selectOne(NAMESPACE + "findById", deptCode));
    }

	@Override
	public void save(DeptDto deptDto) {
	       //sqlSession.insert(NAMESPACE + "save", deptDto);
		sqlSession.insert(NAMESPACE + "save", deptDto);
	}

	@Override
	public void update(DeptDto deptDto) {
        //sqlSession.update(NAMESPACE + "update", deptDto);
		sqlSession.update(NAMESPACE + "update", deptDto);

	}

	@Override
	public void delete(int deptCode) {
        //sqlSession.delete(NAMESPACE + "delete", deptCode);
		sqlSession.update(NAMESPACE + "delete", deptCode);

	}

	@Override
	public Integer findNextDeptCode() {
	    return sqlSession.selectOne(NAMESPACE + "findNextDeptCode");
	}

	@Override
	public List<EmpDto> findDeptManagerList() {
	    return sqlSession.selectList(NAMESPACE + "findDeptManagerList");
	}
}
