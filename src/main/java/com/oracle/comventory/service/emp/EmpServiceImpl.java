package com.oracle.comventory.service.emp;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.oracle.comventory.domain.emp.Emp;
import com.oracle.comventory.dto.emp.EmpDto;
import com.oracle.comventory.repository.emp.EmpRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public   class EmpServiceImpl implements EmpService {

    private final EmpRepository empRepository;

    // ==============================
    // 전체 조회
    // ==============================
    @Override
    public List<Emp> getAllEmps() {
    	 
    	System.out.println("/emp/empList EmpServiceImpl Start...");
    	
    	
        return empRepository.findAll()
                .stream()
                .map(Emp::fromDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public int getTotalEmp(EmpDto empDto) {
        return empRepository.getTotalEmp(empDto);
    }

    @Override
    public List<Emp> getEmpPageList(EmpDto empDto) {
        return empRepository.findPageList(empDto)
                .stream()
                .map(Emp::fromDto)
                .collect(Collectors.toList());
    }

    
    // ==============================
    // 단건 조회
    // ==============================
    @Override
    public Emp getEmpByNo(int empNo) {
        return empRepository.findByEmpNo(empNo)
                .map(Emp::fromDto)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사원입니다. empNo: " + empNo));
    }

    // ==============================
    // ID로 조회
    // ==============================
    @Override
    public Emp getEmpById(String empId) {
        return empRepository.findByEmpId(empId)
                .map(Emp::fromDto)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사원입니다. empId: " + empId));
    }
    
    @Override
    public boolean isEmpIdAvailable(String empId) {
        return empRepository.findByEmpId(empId).isEmpty();
  //      return empRepository.checkEmpIdDuplicate(empId) == 0;
    }

    // ==============================
    // 등록
    // ==============================
    @Override
    public int insertEmp(EmpDto dto) {
        return empRepository.insert(dto);
    }

    // ==============================
    // 수정
    // ==============================
    @Override
    public int updateEmp(EmpDto dto) {
        return empRepository.update(dto);
    }
 // ==============================
    // 삭제 (논리 삭제)
    // ==============================
    @Override
    public int deleteEmp(int empNo) {
        return empRepository.delete(empNo);
    }

    // ==============================
    // DTO → Entity 변환
    // ==============================
    private Emp toEntity(EmpDto dto) {
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
                .empJoinDate(dto.getEmp_join_date())
                .deleted(dto.getEmp_del_status() == 1)
                .regEmpNo(dto.getReg_emp_no())
                .regDate(dto.getReg_date())
                .build();
    }

    @Override
    public Emp getEmpByEmpNo(int empNo) {
        return empRepository.findByEmpNo(empNo)
                .map(Emp::fromDto)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사원입니다. empNo: " + empNo));
    }
    
    @Override
    public int getNextEmpNo(int deptCode, int userAccess) {
        int startEmpNo;
        int endEmpNo;

        if (userAccess == 800) {
            startEmpNo = 800001;
            endEmpNo = 899999;
        } else if (userAccess == 900) {
            startEmpNo = 900001;
            endEmpNo = 999999;
        } else if (deptCode == 9000) {
            startEmpNo = 9001;
            endEmpNo = 9299;
        } else if (deptCode == 9300) {
            startEmpNo = 9301;
            endEmpNo = 9499;
        } else if (deptCode == 9500) {
            startEmpNo = 9501;
            endEmpNo = 9999;
        } else {
            startEmpNo = deptCode + 1;
            endEmpNo = deptCode + 499;
        }

        int nextEmpNo = empRepository.findNextEmpNoInRange(startEmpNo, endEmpNo);

        if (nextEmpNo > endEmpNo) {
            throw new IllegalStateException("해당 범위에 등록 가능한 사원번호가 없습니다.");
        }

        return nextEmpNo;
    }
    
    @Override
    public EmpDto login(String empId, String empPassword) {
        return empRepository.findLoginUser(empId, empPassword).orElse(null);
    }

}