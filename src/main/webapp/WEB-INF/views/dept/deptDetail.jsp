<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>부서 상세 조회</title>
    <%@ include file="../common/staticResources.jsp" %>
	<style>
	    .dept-detail-card {
	        max-width: 900px;
	        margin: 0 auto;
	    }
	
	    .dept-info-label {
	        color: #6c757d;
	        font-size: 13px;
	        margin-bottom: 4px;
	    }
	
	    .dept-info-value {
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
                <div class="dept-detail-card">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h4 class="fw-bold mb-0">부서 상세</h4>

                        <a href="${pageContext.request.contextPath}/dept/deptList"
                           class="btn btn-outline-secondary btn-sm">
                            목록
                        </a>
                    </div>

                    <div class="card shadow-sm border-0">
                        <div class="card-body p-4">
                            <div class="row g-3">
                                <div class="col-md-6">
                                    <div class="dept-info-label">부서번호</div>
                                    <div class="dept-info-value">${deptDto.dept_code}</div>
                                </div>

                                <div class="col-md-6">
                                    <div class="dept-info-label">부서이름</div>
                                    <div class="dept-info-value">${deptDto.dept_name}</div>
                                </div>

                                <div class="col-md-6">
                                    <div class="dept-info-label">대표 전화</div>
                                    <div class="dept-info-value">
                                        <c:out value="${deptDto.dept_tel}" default="미등록"/>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="dept-info-label">위치</div>
                                    <div class="dept-info-value">
                                        <c:out value="${deptDto.dept_loc}" default="미등록"/>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="dept-info-label">부서장</div>
                                    <div class="dept-info-value">
                                        <c:out value="${empty deptDto.dept_mgr_display ? deptDto.dept_mgr : deptDto.dept_mgr_display}" default="미등록"/>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="dept-info-label">부서 사용상태</div>
                                    <div class="dept-info-value">
                                        <c:choose>
                                            <c:when test="${deptDto.dept_gubun == 0}">사용</c:when>
                                            <c:otherwise>미사용</c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <div class="dept-info-label">등록일</div>
                                    <div class="dept-info-value">${deptDto.str_in_date}</div>
                                </div>
                            </div>

                            <div class="d-flex justify-content-end gap-2 mt-4 pt-3 border-top">
                                <a class="btn btn-primary"
                                   href="${pageContext.request.contextPath}/dept/modifyForm?dept_code=${deptDto.dept_code}">
                                    수정
                                </a>

								<button type="button"
								        class="btn btn-outline-danger"
								        data-bs-toggle="modal"
								        data-bs-target="#deleteDeptModal">
								    삭제
								</button>
                            </div>
                        </div>
                    </div>
                </div>
				<div class="modal fade" id="deleteDeptModal" tabindex="-1" aria-labelledby="deleteDeptModalLabel" aria-hidden="true">
				    <div class="modal-dialog modal-dialog-centered">
				        <div class="modal-content">
				            <form action="${pageContext.request.contextPath}/dept/deleteDept" method="post">
				                <div class="modal-header">
				                    <h5 class="modal-title" id="deleteDeptModalLabel">부서 삭제</h5>
				                    <button type="button"
				                            class="btn-close"
				                            data-bs-dismiss="modal"
				                            aria-label="닫기"></button>
				                </div>
				
				                <div class="modal-body">
				                    <p class="mb-2">
				                        정말 <strong>${deptDto.dept_name}</strong> 부서를 삭제하시겠습니까?
				                    </p>
				                    <p class="text-muted small mb-0">
				                        삭제 시 부서 목록에서 조회되지 않습니다.
				                    </p>
				
				                    <input type="hidden" name="dept_code" value="${deptDto.dept_code}">
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
