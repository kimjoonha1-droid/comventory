package com.oracle.comventory.controller;

import com.oracle.comventory.dto.dept.DeptDto;
import com.oracle.comventory.dto.emp.EmpDto;
import com.oracle.comventory.service.dept.DeptService;

import jakarta.servlet.http.HttpSession;

import com.oracle.comventory.service.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/dept")
public class DeptController {

    private final DeptService deptService;

    // ==============================
    // 부서 목록 조회
    // ==============================
    @GetMapping("/deptList")
    public String list(
            DeptDto deptDto,
            @RequestParam(value = "currentPage", required = false) String currentPage,
            Model model) {

        int totalDept = deptService.getTotalDept(deptDto);

        Paging page = new Paging(totalDept, currentPage);
        deptDto.setStart(page.getStart());
        deptDto.setEnd(page.getEnd());

        List<DeptDto> deptList = deptService.getDeptPageList(deptDto);

        model.addAttribute("deptList", deptList);
        model.addAttribute("page", page);

        return "dept/deptList";
    }

    // ==============================
    // 부서 상세 조회
    // ==============================
    @GetMapping("/detail")
    public String detail(@RequestParam("dept_code") int deptCode, Model model) {
    	System.out.println("1.DeptController detail Start...");
        DeptDto deptDto = ((DeptService) deptService).getDeptDetail(deptCode);
        model.addAttribute("deptDto", deptDto);
        return "dept/deptDetail"; // JSP 파일명
    }

    // ==============================
    // 부서 등록 폼
    // ==============================
    @GetMapping("/registerForm")
    public String registerForm(Model model) {
        model.addAttribute("nextDeptCode", deptService.getNextDeptCode());
        model.addAttribute("managerList", deptService.getDeptManagerList());

        return "dept/deptInform";
    }

    // ==============================
    // 부서 등록 처리
    // ==============================
    @PostMapping("/saveDept")
    public String saveDept(@ModelAttribute DeptDto deptDto, HttpSession session) {
        EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/login";
        }

        deptDto.setDept_code(deptService.getNextDeptCode());
        deptDto.setReg_emp_no(loginUser.getEmp_no());
        deptService.saveDept(deptDto);

        return "redirect:/dept/deptList";
    }
    
    // ==============================
    // 부서 수정 폼
    // ==============================
    @GetMapping("/modifyForm")
    public String modifyForm(@RequestParam("dept_code") int deptCode, Model model) {
        DeptDto deptDto = deptService.getDeptDetail(deptCode);
        model.addAttribute("deptDto", deptDto);
        return "dept/modifyForm"; // JSP 파일명
    }

    // ==============================
    // 부서 수정 처리
    // ==============================
    @PostMapping("/updateDept")
    public String updateDept(@ModelAttribute DeptDto deptDto) {
        deptService.updateDept(deptDto);
        return "redirect:/dept/detail?dept_code=" + deptDto.getDept_code();
    }
	
	 // ==============================
	 // 부서 삭제 처리
	 // ==============================
	 @PostMapping("/deleteDept")
	 public String deleteDept(@RequestParam("dept_code") int deptCode) {
	     deptService.deleteDept(deptCode);
	     return "redirect:/dept/deptList";
	 }

}
