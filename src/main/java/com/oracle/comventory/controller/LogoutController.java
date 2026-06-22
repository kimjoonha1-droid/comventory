package com.oracle.comventory.controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 기존 세션 가져오기 (없으면 null)
        if (session != null) {
            session.invalidate();  // 세션 무효화 -> 로그아웃 처리
        }
        return "redirect:/login";  // 로그아웃 후 로그인 페이지로 이동
    }
}
