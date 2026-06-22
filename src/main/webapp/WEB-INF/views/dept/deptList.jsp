<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>부서 목록</title>
    <%@ include file="../common/staticResources.jsp" %>
    <style>
	    .dept-table th {
	        white-space: nowrap;
	    }
	</style>
</head>
<body>
    <%@ include file="../common/header.jsp" %>

    <div id="wrapper">
        <%@ include file="../common/sidebar.jsp" %>

        <div id="page-content-wrapper">
            <main class="content p-4">
				<div class="d-flex justify-content-between align-items-center mb-4">
				    <h4 class="fw-bold mb-0">부서 목록</h4>
				
				    <a href="${pageContext.request.contextPath}/dept/registerForm"
				       class="btn btn-primary btn-sm">
				        신규등록
				    </a>
				</div>
				
				<div class="card shadow-sm mb-4 border-0">
				    <div class="card-body">
				        <form action="${pageContext.request.contextPath}/dept/deptList" method="get">
				            <div class="row g-3">
				                <div class="col-md-10">
				                    <div class="input-group input-group-sm">
				                        <span class="input-group-text bg-white border-end-0">
				                            <i class="bi bi-search"></i>
				                        </span>
				                        <input type="text"
				                               name="searchKeyword"
				                               class="form-control border-start-0"
				                               placeholder="부서번호 또는 부서이름으로 검색..."
				                               value="${param.searchKeyword}">
				                    </div>
				                </div>
				
				                <div class="col-md-2">
				                    <button type="submit" class="btn btn-dark btn-sm w-100">검색</button>
				                </div>
				            </div>
				        </form>
				    </div>
				</div>
				
				<div class="card shadow-sm border-0">
				    <div class="table-responsive">
				        <table class="table table-hover align-middle mb-0 text-center dept-table">
				            <thead>
				                <tr>
				                    <th>부서 번호</th>
				                    <th>부서 이름</th>
				                    <th>대표 전화</th>
				                    <th>위치</th>
				                    <th>부서장</th>
				                    <th>부서 사용상태</th>
				                    <th>등록일</th>
				                </tr>
				            </thead>
				            <tbody>
				                <c:choose>
				                    <c:when test="${empty deptList}">
				                        <tr>
				                            <td colspan="7" class="py-5 text-muted">
				                                <i class="bi bi-exclamation-circle d-block mb-2 fs-3"></i>
				                                조회된 부서가 없습니다.
				                            </td>
				                        </tr>
				                    </c:when>
				                    <c:otherwise>
				                        <c:forEach var="dept" items="${deptList}">
				                            <tr style="cursor: pointer;"
				                                onclick="location.href='${pageContext.request.contextPath}/dept/detail?dept_code=${dept.dept_code}'">
				                                <td class="fw-bold">${dept.dept_code}</td>
				                                <td class="fw-bold">${dept.dept_name}</td>
				                                <td>${dept.dept_tel}</td>
				                                <td>${dept.dept_loc}</td>
				                                <td>${empty dept.dept_mgr_display ? dept.dept_mgr : dept.dept_mgr_display}</td>
				                                <td>
				                                    <c:choose>
				                                        <c:when test="${dept.dept_gubun == 0}">
				                                            <span class="badge bg-light text-dark border">사용</span>
				                                        </c:when>
				                                        <c:otherwise>
				                                            <span class="badge bg-light text-dark border">미사용</span>
				                                        </c:otherwise>
				                                    </c:choose>
				                                </td>
				                                <td class="text-secondary">${dept.str_in_date}</td>
				                            </tr>
				                        </c:forEach>
				                    </c:otherwise>
				                </c:choose>
				            </tbody>
				        </table>
				    </div>
				</div>
				<c:if test="${page.totalPage > 1}">
				    <nav class="mt-3">
				        <ul class="pagination pagination-sm justify-content-center">
				            <c:if test="${page.startPage > page.pageBlock}">
				                <li class="page-item">
				                    <a class="page-link"
				                       href="${pageContext.request.contextPath}/dept/deptList?currentPage=${page.startPage - page.pageBlock}&searchKeyword=${param.searchKeyword}">
				                        이전
				                    </a>
				                </li>
				            </c:if>
				
				            <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
				                <li class="page-item ${page.currentPage == i ? 'active' : ''}">
				                    <a class="page-link"
				                       href="${pageContext.request.contextPath}/dept/deptList?currentPage=${i}&searchKeyword=${param.searchKeyword}">
				                        ${i}
				                    </a>
				                </li>
				            </c:forEach>
				
				            <c:if test="${page.endPage < page.totalPage}">
				                <li class="page-item">
				                    <a class="page-link"
				                       href="${pageContext.request.contextPath}/dept/deptList?currentPage=${page.startPage + page.pageBlock}&searchKeyword=${param.searchKeyword}">
				                        다음
				                    </a>
				                </li>
				            </c:if>
				        </ul>
				    </nav>
				</c:if>
            </main>
        </div>
    </div>

    <%@ include file="../common/footer.jsp" %>
</body>

</html>
