<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark border-bottom shadow-sm">
    <div class="container-fluid">
        <!-- 1. 사이드바 온오프 버튼 (로고 왼쪽에 배치) -->
		<c:if test="${sessionScope.loginUser.user_access != 800 && sessionScope.loginUser.user_access != 900}">
		    <button class="btn btn-outline-light me-3" id="menu-toggle" onclick="toggleSidebar()">
		        <i class="bi bi-list"></i>
		    </button>
		</c:if>

        <!-- 2. 시스템 로고 -->
		<a class="navbar-brand fw-bold me-auto"
		   href="${pageContext.request.contextPath}${sessionScope.loginUser.user_access == 800 || sessionScope.loginUser.user_access == 900 ? '/board/list' : '/'}">
            <i class="bi bi-cpu-fill me-2"></i> COMVENTORY
        </a>

        <!-- 3. 사용자 정보 및 로그아웃 -->
        <div class="d-flex align-items-center">
            <span class="navbar-text text-white me-3 d-none d-md-inline">
                <i class="bi bi-person-circle"></i>
                <c:choose>
                    <c:when test="${not empty sessionScope.loginUser}">
                        ${sessionScope.loginUser.emp_name}님
                    </c:when>
                    <c:otherwise>
                        비회원님
                    </c:otherwise>
                </c:choose>
            </span>

            <form action="${pageContext.request.contextPath}/logout" method="post" class="m-0">
                <button class="btn btn-outline-light btn-sm" type="submit">로그아웃</button>
            </form>
        </div>

    </div>
</nav>