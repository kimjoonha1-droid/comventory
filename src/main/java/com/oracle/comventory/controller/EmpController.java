package com.oracle.comventory.controller;

import java.util.List;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

import com.oracle.comventory.domain.emp.Emp;
import com.oracle.comventory.dto.emp.EmpDto;
import com.oracle.comventory.service.emp.EmpService;

import jakarta.servlet.http.HttpSession;

import com.oracle.comventory.dto.dept.DeptDto;
import com.oracle.comventory.service.dept.DeptService;

import com.oracle.comventory.service.Paging;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/emp")
@RequiredArgsConstructor
public class EmpController {
	
	@Value("${com.oracle.oBootPersonal01.upload.path}")
	private String uploadPath;
	
    private final EmpService empService;
    
    private final DeptService deptService;

    private static final String TEL_PATTERN = "^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$";


    // 전체 조회
    @GetMapping("/empList")
    public String getAllEmps(Model model) {
    	System.out.println("/emp/empList Start...");
        List<Emp> empList = empService.getAllEmps();
        System.out.println(" controller /emp/empList empList->"+empList);
        model.addAttribute("empList", empList);
        return "emp/empList";
    }
    
    // 단건 조회
    @GetMapping("/empDetail")
    public String empDetail(@RequestParam("empNo") int empNo, Model model) {
        Emp emp = empService.getEmpByEmpNo(empNo);
        DeptDto dept = deptService.getDeptDetail(emp.getDeptCode());

        model.addAttribute("emp", emp);
        model.addAttribute("dept", dept);

        return "emp/empDetail";
    }

    // ID로 조회
    @GetMapping("/empSearch")
    public String getEmpById(@RequestParam("empId") String empId, Model model) {
        Emp emp = empService.getEmpById(empId);
        model.addAttribute("emp", emp);
        return "emp/empDetail";
    }
    
    @GetMapping("/empMain")
    public String empMain(
            EmpDto empDto,
            @RequestParam(value = "currentPage", required = false) String currentPage,
            Model model) {

        if (empDto.getEmpType() == null || empDto.getEmpType().isBlank()) {
            empDto.setEmpType("internal");
        }

        int totalEmp = empService.getTotalEmp(empDto);

        Paging page = new Paging(totalEmp, currentPage);
        empDto.setStart(page.getStart());
        empDto.setEnd(page.getEnd());

        List<Emp> empList = empService.getEmpPageList(empDto);

        model.addAttribute("empList", empList);
        model.addAttribute("page", page);

        return "emp/empMain";
    }


    // ID 중복 체크 (AJAX)
    @GetMapping("/checkEmpId")
    @ResponseBody
    public Map<String, Object> checkEmpId(@RequestParam("empId") String empId) {
        Map<String, Object> result = new HashMap<>();
        result.put("available", empService.isEmpIdAvailable(empId));
        return result;
    }
    
    @GetMapping("/nextEmpNo")
    @ResponseBody
    public Map<String, Object> nextEmpNo(
            @RequestParam("deptCode") int deptCode,
            @RequestParam("userAccess") int userAccess) {

        Map<String, Object> result = new HashMap<>();

        try {
            int nextEmpNo = empService.getNextEmpNo(deptCode, userAccess);
            result.put("success", true);
            result.put("empNo", nextEmpNo);
        } catch (IllegalStateException e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }

        return result;
    }

    // 등록 폼
    @GetMapping("/empInsert")
    public String insertEmpForm(Model model) {
        List<DeptDto> deptList = deptService.getDeptList();

        Map<Integer, String> empGradeList = new LinkedHashMap<>();
        empGradeList.put(1, "대표");
        empGradeList.put(2, "이사");
        empGradeList.put(3, "부장");
        empGradeList.put(4, "차장");
        empGradeList.put(5, "과장");
        empGradeList.put(6, "대리");
        empGradeList.put(7, "주임");
        empGradeList.put(8, "사원/비서");
        empGradeList.put(9, "없음(외부직원)");
        
        Map<Integer, String> userAccessList = new LinkedHashMap<>();
        userAccessList.put(1, "admin");
        userAccessList.put(2, "관리자");
        userAccessList.put(3, "일반사원");
        userAccessList.put(800, "판매처직원");
        userAccessList.put(900, "구매처직원");

        model.addAttribute("userAccessList", userAccessList);
        model.addAttribute("deptList", deptList);
        model.addAttribute("empGradeList", empGradeList);

        return "emp/empInsert";
    }


    // 등록 처리
    @PostMapping("/empInsert")
    public String insertEmp(EmpDto dto,
            @RequestParam(value = "empPicFile", required = false) MultipartFile empPicFile,
            RedirectAttributes redirectAttributes,
            HttpSession session) {

        if (dto.getEmp_tel() == null || !dto.getEmp_tel().matches(TEL_PATTERN)) {
            redirectAttributes.addFlashAttribute(
                    "empInsertError",
                    "전화번호는 000-0000-0000 형식으로 입력하세요. 예: 010-1234-5678"
            );
            return "redirect:/emp/empInsert";
        }
        
        EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/login";
        }

        dto.setReg_emp_no(loginUser.getEmp_no());
        
        int nextEmpNo = empService.getNextEmpNo(dto.getDept_code(), dto.getUser_access());
        dto.setEmp_no(nextEmpNo);
        
        try {
            if (empPicFile != null && !empPicFile.isEmpty()) {
                String originalFileName = empPicFile.getOriginalFilename();
                String saveFileName = UUID.randomUUID().toString() + "_" + originalFileName;

                Path empUploadPath = Paths.get(uploadPath, "emp")
                        .toAbsolutePath()
                        .normalize();

                Files.createDirectories(empUploadPath);

                Path savePath = empUploadPath.resolve(saveFileName);
                Files.copy(empPicFile.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);

                dto.setEmp_pic(saveFileName);
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("empInsertError", "이미지 파일 업로드 중 오류가 발생했습니다.");
            return "redirect:/emp/empInsert";
        }

        empService.insertEmp(dto);
        return "redirect:/emp/empMain";
    }


    // 수정 폼
//    @PostMapping("/empUpdate")
//    public String updateEmp(@RequestParam("empNo") int empNo, EmpDto dto) {
//        // dto 안에 empNo가 세팅되도록 보장
//        dto.setEmp_no(empNo);
//
//        empService.updateEmp(dto);
//        return "redirect:/emp/empList";
//    }    
//
    @GetMapping("/empUpdate")
    public String updateEmpForm(@RequestParam("empNo") int empNo, Model model) {
        Emp emp = empService.getEmpByEmpNo(empNo);
        List<DeptDto> deptList = deptService.getDeptList();

        Map<Integer, String> empGradeList = new LinkedHashMap<>();
        empGradeList.put(1, "대표");
        empGradeList.put(2, "이사");
        empGradeList.put(3, "부장");
        empGradeList.put(4, "차장");
        empGradeList.put(5, "과장");
        empGradeList.put(6, "대리");
        empGradeList.put(7, "주임");
        empGradeList.put(8, "사원/비서");
        empGradeList.put(9, "없음(외부직원)");

        model.addAttribute("emp", emp);
        model.addAttribute("deptList", deptList);
        model.addAttribute("empGradeList", empGradeList);

        return "emp/empUpdate";
    }


    // 수정 처리
    @PostMapping("/empUpdate")
    public String updateEmp(
            EmpDto dto,
            @RequestParam(value = "empPicFile", required = false) MultipartFile empPicFile,
            RedirectAttributes redirectAttributes) {

        if (dto.getEmp_tel() == null || !dto.getEmp_tel().matches(TEL_PATTERN)) {
            redirectAttributes.addFlashAttribute(
                    "empUpdateError",
                    "전화번호는 010-1234-5678 형식으로 입력하세요."
            );
            return "redirect:/emp/empUpdate?empNo=" + dto.getEmp_no();
        }

        try {
            if (empPicFile != null && !empPicFile.isEmpty()) {
                String originalFileName = empPicFile.getOriginalFilename();
                String saveFileName = UUID.randomUUID().toString() + "_" + originalFileName;

                Path empUploadPath = Paths.get(uploadPath, "emp")
                        .toAbsolutePath()
                        .normalize();

                Files.createDirectories(empUploadPath);

                Path savePath = empUploadPath.resolve(saveFileName);
                Files.copy(empPicFile.getInputStream(), savePath, StandardCopyOption.REPLACE_EXISTING);

                dto.setEmp_pic(saveFileName);
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("empUpdateError", "이미지 파일 업로드 중 오류가 발생했습니다.");
            return "redirect:/emp/empUpdate?empNo=" + dto.getEmp_no();
        }

        empService.updateEmp(dto);
        return "redirect:/emp/empMain";
    }

    // 삭제 화면 보여주기
    @GetMapping("/empDelete")
    public String showDeleteForm() {
        return "emp/empDelete"; // empDelete.jsp 반환
    }
	 // 삭제 처리 (논리 삭제)
    @PostMapping("/empDelete")
    public String deleteEmp(@RequestParam("empNo") int empNo) {
        empService.deleteEmp(empNo);
        return "redirect:/emp/empMain";
    }

}

