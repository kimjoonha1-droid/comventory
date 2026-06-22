package com.oracle.comventory.configuration;

import com.oracle.comventory.dto.emp.EmpDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class HrAccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        if (session == null) return true;

        EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");
        if (loginUser == null) return true;

        boolean isAdmin = loginUser.getUser_access() == 1;
        boolean isHrDept = loginUser.getDept_code() == 2000;

        if (isAdmin || isHrDept) {
            return true;
        }

        response.sendRedirect(request.getContextPath() + "/accessDenied");
        return false;
    }
}