package com.oracle.comventory.service.dept;

import com.oracle.comventory.dao.dept.DeptDao;
import com.oracle.comventory.dto.dept.DeptDto;
import com.oracle.comventory.dto.emp.EmpDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService {

    private final DeptDao deptDao;

    @Override
    public List<DeptDto> getDeptList() {
        return deptDao.findAll();
    }

	@Override
	public List<DeptDto> getDeptList(String searchKeyword) {
		return deptDao.findAll(searchKeyword);
	}
    
	@Override
	public int getTotalDept(DeptDto deptDto) {
	    return deptDao.getTotalDept(deptDto);
	}

	@Override
	public List<DeptDto> getDeptPageList(DeptDto deptDto) {
	    return deptDao.findPageList(deptDto);
	}
	
    @Override
    public DeptDto getDeptDetail(int deptCode) {
        return deptDao.findById(deptCode)
                .orElseThrow(() -> new IllegalArgumentException("부서를 찾을 수 없습니다. deptCode=" + deptCode));
    }

    @Override
    public void saveDept(DeptDto deptDto) {
        deptDao.save(deptDto);
    }

    @Override
    public void updateDept(DeptDto deptDto) {
        deptDao.update(deptDto);
    }

    @Override
    public void deleteDept(int deptCode) {
        deptDao.delete(deptCode);
    }

    @Override
    public int getNextDeptCode() {
        Integer nextDeptCode = deptDao.findNextDeptCode();

        if (nextDeptCode == null) {
            throw new IllegalStateException("등록 가능한 부서코드가 없습니다.");
        }

        return nextDeptCode;
    }

    @Override
    public List<EmpDto> getDeptManagerList() {
        return deptDao.findDeptManagerList();
    }
}
