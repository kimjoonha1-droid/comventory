package com.oracle.comventory.configuration;

import com.oracle.comventory.dto.emp.EmpDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;

public class InventoryAccessInterceptor implements HandlerInterceptor {

    private static final Set<Integer> CURRENT_STOCK_DEPTS =
            Set.of(1000, 3000, 5000, 5500, 6000, 7000);

    private static final Set<Integer> STOCK_LIST_DEPTS =
            Set.of(1000, 3000, 5500, 6000, 7000);

    private static final Set<Integer> PHYSICAL_INVENTORY_DEPTS =
            Set.of(1000, 3000, 7000);

    private static final Set<Integer> ADJUSTMENT_DEPTS =
            Set.of(1000, 7000);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false);
        if (session == null) return true;

        EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");
        if (loginUser == null) return true;

        if (loginUser.getUser_access() == 1) {
            return true;
        }

        String contextPath = request.getContextPath();
        String path = request.getRequestURI().substring(contextPath.length());
        int deptCode = loginUser.getDept_code();

        if (path.equals("/inventory/currentStockView")) {
            return allow(CURRENT_STOCK_DEPTS, deptCode, response, contextPath);
        }

        if (path.equals("/inventory/stockListView")) {
            return allow(STOCK_LIST_DEPTS, deptCode, response, contextPath);
        }

        if (path.equals("/inventory/physicalInventoryView")) {
            return allow(PHYSICAL_INVENTORY_DEPTS, deptCode, response, contextPath);
        }

        if (path.equals("/inventory/inventoryAdjustmentView")) {
            return allow(ADJUSTMENT_DEPTS, deptCode, response, contextPath);
        }

        return true;
    }

    private boolean allow(Set<Integer> allowedDepts,
                          int deptCode,
                          HttpServletResponse response,
                          String contextPath) throws Exception {

        if (allowedDepts.contains(deptCode)) {
            return true;
        }

        response.sendRedirect(contextPath + "/accessDenied");
        return false;
    }
}