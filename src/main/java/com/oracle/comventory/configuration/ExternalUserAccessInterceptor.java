package com.oracle.comventory.configuration;

import com.oracle.comventory.dto.emp.EmpDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class ExternalUserAccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        if (session == null) return true;

        EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");
        if (loginUser == null) return true;

        int userAccess = loginUser.getUser_access();

        if (userAccess != 800 && userAccess != 900) {
            return true;
        }

        String contextPath = request.getContextPath();
        String uri = request.getRequestURI();
        String path = uri.substring(contextPath.length());

        if (path.startsWith("/board")) {
            return true;
        }

        if (path.equals("/")) {
            response.sendRedirect(contextPath + "/board/list");
            return false;
        }

        response.sendRedirect(contextPath + "/accessDenied");
        return false;
    }
}