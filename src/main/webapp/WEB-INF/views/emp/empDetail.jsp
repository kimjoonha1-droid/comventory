<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>사원 상세</title>
    <%@ include file="../common/staticResources.jsp" %>
    <style>
	    .emp-detail-card {
	        max-width: 980px;
	        margin: 0 auto;
	    }
	
	    .emp-profile-img {
	        width: 160px;
	        height: 160px;
	        object-fit: cover;
	        border-radius: 10px;
	        border: 1px solid #dee2e6;
	        background: #f8f9fa;
	    }
	
	    .emp-info-label {
	        color: #6c757d;
	        font-size: 13px;
	        margin-bottom: 4px;
	    }
	
	    .emp-info-value {
	        font-weight: 600;
	        min-height: 24px;
	    }
	</style>
</head>
<body>
    <%@ include file="../common/header.jsp" %>

    <div id="wrapper">
        <%@ include file="../common/sidebar.jsp" %>

        <div id="page-content-wrapper">
            <main class="content p-4">
				<div class="emp-detail-card">
				    <div class="d-flex justify-content-between align-items-center mb-4">
				        <h4 class="fw-bold mb-0">사원 상세</h4>
				
				        <a href="${pageContext.request.contextPath}/emp/empMain"
				           class="btn btn-outline-secondary btn-sm">
				            목록
				        </a>
				    </div>
				
				    <div class="card shadow-sm border-0">
				        <div class="card-body p-4">
				            <div class="row g-4">
				                <div class="col-md-3 text-center">
				                    <c:choose>
				                        <c:when test="${not empty emp.empPic}">
				                            <img src="${pageContext.request.contextPath}/upload/emp/${emp.empPic}"
				                                 class="emp-profile-img"
				                                 alt="">
				                        </c:when>
				                        <c:otherwise>
				                            <div class="emp-profile-img d-flex align-items-center justify-content-center text-muted">
				                                이미지 없음
				                            </div>
				                        </c:otherwise>
				                    </c:choose>
				                </div>
				
				                <div class="col-md-9">
				                    <div class="row g-3">
				                        <div class="col-md-6">
				                            <div class="emp-info-label">사원번호</div>
				                            <div class="emp-info-value">${emp.empNo}</div>
				                        </div>
				
				                        <div class="col-md-6">
				                            <div class="emp-info-label">사원이름</div>
				                            <div class="emp-info-value">${emp.empName}</div>
				                        </div>
				
				                        <div class="col-md-6">
				                            <div class="emp-info-label">ID</div>
				                            <div class="emp-info-value">${emp.empId}</div>
				                        </div>
				
				                        <div class="col-md-6">
				                            <div class="emp-info-label">전화번호</div>
				                            <div class="emp-info-value">${emp.empTel}</div>
				                        </div>
				
				                        <div class="col-md-6">
				                            <div class="emp-info-label">직급</div>
				                            <div class="emp-info-value">
				                                <c:choose>
				                                    <c:when test="${emp.empGrade == 1}">대표</c:when>
				                                    <c:when test="${emp.empGrade == 2}">이사</c:when>
				                                    <c:when test="${emp.empGrade == 3}">부장</c:when>
				                                    <c:when test="${emp.empGrade == 4}">차장</c:when>
				                                    <c:when test="${emp.empGrade == 5}">과장</c:when>
				                                    <c:when test="${emp.empGrade == 6}">대리</c:when>
				                                    <c:when test="${emp.empGrade == 7}">주임</c:when>
				                                    <c:when test="${emp.empGrade == 8}">사원/비서</c:when>
				                                    <c:otherwise>${emp.empGrade}</c:otherwise>
				                                </c:choose>
				                            </div>
				                        </div>
				
				                        <div class="col-md-6">
				                            <div class="emp-info-label">연봉</div>
				                            <div class="emp-info-value">
											    <c:choose>
											        <c:when test="${empty emp.empSal}">-</c:when>
											        <c:otherwise>${emp.empSal}</c:otherwise>
											    </c:choose>
											</div>
				                        </div>
				
				                        <div class="col-md-6">
				                            <div class="emp-info-label">이메일</div>
				                            <div class="emp-info-value">
				                                <c:out value="${emp.empEmail}" default="미등록"/>
				                            </div>
				                        </div>
				
				                        <div class="col-md-6">
				                            <div class="emp-info-label">부서</div>
				                            <div class="emp-info-value">${dept.dept_name}</div>
				                        </div>
				
				                        <div class="col-md-6">
				                            <div class="emp-info-label">입사일</div>
				                            <div class="emp-info-value">
				                                <fmt:formatDate value="${emp.empJoinDate}" pattern="yyyy-MM-dd"/>
				                            </div>
				                        </div>
				
				                        <div class="col-md-6">
				                            <div class="emp-info-label">퇴사여부</div>
				                            <div class="emp-info-value">
				                                <c:choose>
				                                    <c:when test="${emp.active}">재직중</c:when>
				                                    <c:otherwise>퇴사</c:otherwise>
				                                </c:choose>
				                            </div>
				                        </div>
				                    </div>
				                </div>
				            </div>
				
				            <div class="d-flex justify-content-end gap-2 mt-4 pt-3 border-top">
				                <a href="${pageContext.request.contextPath}/emp/empUpdate?empNo=${emp.empNo}"
				                   class="btn btn-primary">
				                    정보 수정
				                </a>
				
				                <button type="button"
				                        class="btn btn-outline-danger"
				                        data-bs-toggle="modal"
				                        data-bs-target="#deleteEmpModal">
				                    삭제
				                </button>
				            </div>
				        </div>
				    </div>
				</div>
				<div class="modal fade" id="deleteEmpModal" tabindex="-1" aria-labelledby="deleteEmpModalLabel" aria-hidden="true">
				    <div class="modal-dialog modal-dialog-centered">
				        <div class="modal-content">
				            <form action="${pageContext.request.contextPath}/emp/empDelete" method="post">
				                <div class="modal-header">
				                    <h5 class="modal-title" id="deleteEmpModalLabel">사원 삭제</h5>
				                    <button type="button"
				                            class="btn-close"
				                            data-bs-dismiss="modal"
				                            aria-label="닫기"></button>
				                </div>
				
				                <div class="modal-body">
				                    <p class="mb-2">
				                        <strong>${emp.empName}</strong> 사원을 삭제하시겠습니까?
				                    </p>
				                    <p class="text-muted small mb-0">
				                        삭제 시 사원 목록에서 조회되지 않습니다.
				                    </p>
				
				                    <input type="hidden" name="empNo" value="${emp.empNo}">
				                </div>
				
				                <div class="modal-footer">
				                    <button type="button"
				                            class="btn btn-outline-secondary"
				                            data-bs-dismiss="modal">
				                        취소
				                    </button>
				                    <button type="submit" class="btn btn-danger">
				                        삭제
				                    </button>
				                </div>
				            </form>
				        </div>
				    </div>
				</div>
			</main>

        </div>
    </div>

    <%@ include file="../common/footer.jsp" %>
</body>
</html>
