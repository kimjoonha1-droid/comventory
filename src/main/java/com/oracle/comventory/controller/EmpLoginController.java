package com.oracle.comventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oracle.comventory.dto.emp.EmpDto;
import com.oracle.comventory.service.emp.EmpService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmpLoginController {

    private final EmpService empService;

    @GetMapping("/login")
    public String loginForm() {
        return "emp/login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("empId") String empId,
            @RequestParam("empPassword") String empPassword,
            HttpSession session,
            Model model) {

        EmpDto loginUser = empService.login(empId, empPassword);

        if (loginUser == null) {
            model.addAttribute("loginError", "ID 또는 비밀번호가 일치하지 않습니다.");
            return "emp/login";
        }

        // 검증할 session 
        session.setAttribute("loginUser", loginUser);

        if (loginUser.getUser_access() == 800 || loginUser.getUser_access() == 900) {
            return "redirect:/board/list";
        }

        return "redirect:/";
    }

	/*
	 * @GetMapping("/logout") 
	 * public String logoutGet(HttpSession session) {
	 * session.invalidate(); 
	 * return "redirect:/login"; }
	 * 
	 * @PostMapping("/logout") 
	 * public String logoutPost(HttpSession session) {
	 * session.invalidate(); 
	 * return "redirect:/login"; }
	 */

}
