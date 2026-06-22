<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>제품 수불처리/조회</title>
    <%@ include file="../common/staticResources.jsp" %>
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    <c:set var="canInventoryProcess"
       value="${sessionScope.loginUser.dept_code == 7000 && sessionScope.loginUser.user_access == 2}" />
    <div id="wrapper">
        <%@ include file="../common/sidebar.jsp" %>
        <div id="page-content-wrapper">
            <main class="content p-4">
                <h4 class="fw-bold mb-4">제품 수불처리/조회</h4>
                <div class="container-fluid px-0">
				    <div class="row g-3">
				        <div class="col-md-4">
				            <div class="card shadow-sm border-0 h-100">
				                <div class="card-body d-flex flex-column justify-content-between">
				                    <div>
				                        <div class="text-muted small mb-2">오늘 수불상태</div>
				                        <h5 class="fw-bold mb-3">${currentYmText}</h5>
				
				                        <c:choose>
				                            <c:when test="${latestClosingStatus == 'READY'}">
				                                <span class="badge bg-danger fs-6">마감 미처리</span>
				                            </c:when>
				                            <c:when test="${latestClosingStatus == 'TEMP_CLOSED'}">
				                                <span class="badge bg-primary fs-6">가마감 처리</span>
				                            </c:when>
				                            <c:otherwise>
				                                <span class="badge bg-dark fs-6">마감 처리</span>
				                            </c:otherwise>
				                        </c:choose>
				                    </div>
				
									<div class="d-flex gap-2 mt-4">
									    <c:choose>
									        <c:when test="${canInventoryProcess}">
									            <c:choose>
									                <c:when test="${latestClosingStatus == 'READY'}">
									                    <form action="${pageContext.request.contextPath}/inventory/processAdjustmentClosing" method="post" class="mb-0">
									                        <input type="hidden" name="closingStatus" value="1">
									                        <button type="submit" class="btn btn-danger btn-sm">가마감</button>
									                    </form>
									                    <form action="${pageContext.request.contextPath}/inventory/processAdjustmentClosing" method="post" class="mb-0">
									                        <input type="hidden" name="closingStatus" value="2">
									                        <button type="submit" class="btn btn-danger btn-sm">마감</button>
									                    </form>
									                </c:when>
									
									                <c:when test="${latestClosingStatus == 'TEMP_CLOSED'}">
									                    <form action="${pageContext.request.contextPath}/inventory/processAdjustmentClosing" method="post" class="mb-0">
									                        <input type="hidden" name="closingStatus" value="2">
									                        <button type="submit" class="btn btn-danger btn-sm">마감</button>
									                    </form>
									                    <form action="${pageContext.request.contextPath}/inventory/processAdjustmentClosing" method="post" class="mb-0">
									                        <input type="hidden" name="closingStatus" value="0">
									                        <button type="submit" class="btn btn-outline-secondary btn-sm">가마감 취소</button>
									                    </form>
									                </c:when>
									
									                <c:otherwise>
									                    <button type="button" class="btn btn-secondary btn-sm" disabled>마감완료</button>
									                </c:otherwise>
									            </c:choose>
									        </c:when>
									
									        <c:otherwise>
									            <button type="button" class="btn btn-outline-secondary btn-sm" disabled>
									                처리권한 없음
									            </button>
									        </c:otherwise>
									    </c:choose>
									</div>
				                </div>
				            </div>
				        </div>
				
				        <div class="col-md-8">
				            <div class="card shadow-sm border-0 h-100">
				                <div class="card-header bg-white py-3 border-bottom">
				                    <h6 class="mb-0 fw-bold">최근 수불처리 내역</h6>
				                </div>
				
				                <div class="table-responsive">
				                    <table class="table align-middle mb-0 text-center stock-table">
				                        <thead class="table-light">
				                            <tr>
				                                <th>수불년월일</th>
				                                <th>수불처리 결과</th>
				                            </tr>
				                        </thead>
				                        <tbody>
				                            <c:choose>
				                                <c:when test="${not empty adjList}">
				                                    <c:forEach items="${adjList}" var="adj">
				                                        <tr>
				                                            <td class="fw-bold">
				                                                ${fn:substring(adj.closing_ymd, 0, 4)}-${fn:substring(adj.closing_ymd, 4, 6)}-${fn:substring(adj.closing_ymd, 6, 8)}
				                                            </td>
				                                            <td>
				                                                <c:choose>
				                                                    <c:when test="${adj.closing_status == 0}">
				                                                        <span class="badge bg-danger">마감 미처리</span>
				                                                    </c:when>
				                                                    <c:when test="${adj.closing_status == 1}">
				                                                        <span class="badge bg-primary">가마감 처리</span>
				                                                    </c:when>
				                                                    <c:when test="${adj.closing_status == 2}">
				                                                        <span class="badge bg-dark">마감 처리</span>
				                                                    </c:when>
				                                                    <c:otherwise>
				                                                        <span class="badge bg-secondary">${adj.closing_status}</span>
				                                                    </c:otherwise>
				                                                </c:choose>
				                                            </td>
				                                        </tr>
				                                    </c:forEach>
				                                </c:when>
				                                <c:otherwise>
				                                    <tr>
				                                        <td colspan="2" class="py-5 text-muted">
				                                            <i class="bi bi-exclamation-circle d-block mb-2 fs-3"></i>
				                                            조회된 수불처리 내역이 없습니다.
				                                        </td>
				                                    </tr>
				                                </c:otherwise>
				                            </c:choose>
				                        </tbody>
				                    </table>
				                </div>
				            </div>
				        </div>
				    </div>
				</div>


            </main>
        </div>
    </div>
    <%@ include file="../common/footer.jsp" %>
</body>
</html>