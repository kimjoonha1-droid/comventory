<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>사원 메인</title>
    <%@ include file="../common/staticResources.jsp" %>
	<style>
	    .emp-thumb {
	        width: 44px;
	        height: 44px;
	        object-fit: cover;
	        border-radius: 6px;
	    }
	
	    .emp-table th {
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
				    <h4 class="fw-bold mb-0">사원 목록</h4>
				
				    <a href="${pageContext.request.contextPath}/emp/empInsert"
				       class="btn btn-primary btn-sm">
				        신규등록
				    </a>
				</div>

				<div class="card shadow-sm mb-4 border-0">
				    <div class="card-body">
				        <form action="${pageContext.request.contextPath}/emp/empMain" method="get">
							<input type="hidden"
							       id="empType"
							       name="empType"
							       value="${empty param.empType ? 'internal' : param.empType}">
							<div class="btn-group btn-group-sm mb-3" role="group" aria-label="사원 구분">
							    <button type="submit"
							            class="btn ${empty param.empType || param.empType == 'internal' ? 'btn-dark' : 'btn-outline-dark'}"
							            onclick="document.getElementById('empType').value='internal';">
							        내부사원
							    </button>
							
							    <button type="submit"
							            class="btn ${param.empType == 'sales' ? 'btn-dark' : 'btn-outline-dark'}"
							            onclick="document.getElementById('empType').value='sales';">
							        판매처사원
							    </button>
							
							    <button type="submit"
							            class="btn ${param.empType == 'purchase' ? 'btn-dark' : 'btn-outline-dark'}"
							            onclick="document.getElementById('empType').value='purchase';">
							        구매처사원
							    </button>
							</div>
				            <div class="row g-3">
				                <div class="col-md-10">
				                    <div class="input-group input-group-sm">
				                        <span class="input-group-text bg-white border-end-0">
				                            <i class="bi bi-search"></i>
				                        </span>
				                        <input type="text"
				                               name="searchKeyword"
				                               class="form-control border-start-0"
				                               placeholder="사번, 이름, 부서, 직급으로 검색..."
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
				        <table class="table table-hover align-middle mb-0 text-center emp-table">
				            <thead>
				                <tr>
				                    <th style="width: 70px;">사번</th>
				                    <th>아이디</th>
				                    <th>이름</th>
				                    <th>부서</th>
				                    <th>직급</th>
				                    <th>이메일</th>
				                    <th>전화번호</th>
				                    <th>이미지</th>
				                </tr>
				            </thead>
				            <tbody>
				                <c:choose>
				                    <c:when test="${not empty empList}">
				                        <c:forEach var="emp" items="${empList}">
											<tr style="cursor: pointer;"
											    onclick="location.href='${pageContext.request.contextPath}/emp/empDetail?empNo=${emp.empNo}'">
												<td class="fw-bold">${emp.empNo}</td>
				                                <td>${emp.empId}</td>
				                                <td class="fw-bold">${emp.empName}</td>
				                                <td>${emp.deptName}</td>
				                                <td>
				                                    <c:choose>
				                                        <c:when test="${emp.empGrade == 1}"><span class="badge bg-light text-dark border">대표</span></c:when>
				                                        <c:when test="${emp.empGrade == 2}"><span class="badge bg-light text-dark border">이사</span></c:when>
				                                        <c:when test="${emp.empGrade == 3}"><span class="badge bg-light text-dark border">부장</span></c:when>
				                                        <c:when test="${emp.empGrade == 4}"><span class="badge bg-light text-dark border">차장</span></c:when>
				                                        <c:when test="${emp.empGrade == 5}"><span class="badge bg-light text-dark border">과장</span></c:when>
				                                        <c:when test="${emp.empGrade == 6}"><span class="badge bg-light text-dark border">대리</span></c:when>
				                                        <c:when test="${emp.empGrade == 7}"><span class="badge bg-light text-dark border">주임</span></c:when>
				                                        <c:when test="${emp.empGrade == 8}"><span class="badge bg-light text-dark border">사원/비서</span></c:when>
				                                        <c:when test="${emp.empGrade == 9}"><span class="badge bg-light text-dark border">외부직원</span></c:when>
				                                        <c:otherwise><span class="badge bg-light text-dark border">${emp.empGrade}</span></c:otherwise>
				                                    </c:choose>
				                                </td>
				                                <td class="text-secondary">
				                                    <c:out value="${emp.empEmail}" default="미등록"/>
				                                </td>
				                                <td>${emp.empTel}</td>
				                                <td>
				                                    <c:choose>
				                                        <c:when test="${not empty emp.empPic}">
				                                            <img src="${pageContext.request.contextPath}/upload/emp/${emp.empPic}"
				                                                 class="emp-thumb"
				                                                 alt="">
				                                        </c:when>
				                                        <c:otherwise>
				                                            <span class="badge bg-light text-dark border">없음</span>
				                                        </c:otherwise>
				                                    </c:choose>
				                                </td>
				                            </tr>
				                        </c:forEach>
				                    </c:when>
				                    <c:otherwise>
				                        <tr>
				                            <td colspan="8" class="py-5 text-muted">
				                                <i class="bi bi-exclamation-circle d-block mb-2 fs-3"></i>
				                                조회된 사원 내역이 없습니다.
				                            </td>
				                        </tr>
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
	                                    <a class="page-link" href="${pageContext.request.contextPath}/emp/empMain?currentPage=${i}&searchKeyword=${param.searchKeyword}&empType=${empty param.empType ? 'internal' : param.empType}">
	                                        이전
	                                    </a>
	                                </li>
	                            </c:if>
	                    
	                            <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
	                                <li class="page-item ${page.currentPage == i ? 'active' : ''}">
	                                    <a class="page-link" href="${pageContext.request.contextPath}/emp/empMain?currentPage=${i}&searchKeyword=${param.searchKeyword}">
	                                        ${i}
	                                    </a>
	                                </li>
	                            </c:forEach>
	                    
	                            <c:if test="${page.endPage < page.totalPage}">
	                                <li class="page-item">
	                                    <a class="page-link" href="${pageContext.request.contextPath}/emp/empMain?currentPage=${page.startPage + page.pageBlock}&searchKeyword=${param.searchKeyword}">
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

