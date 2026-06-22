<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt"
           uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>생산 상세</title>
    <%@ include file="../common/staticResources.jsp" %>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div id="wrapper">
    <%@ include file="../common/sidebar.jsp" %>
    <div id="page-content-wrapper">
        <main class="content p-4">
            <div class="d-flex justify-content-between mb-4">
                <h2>생산 상세</h2>
                <div>
                	<c:if test="${isProductionDept && !isClosed && production.productionStatus == 1}">
					    <a href="${pageContext.request.contextPath}/production/updateForm?productionCode=${production.productionCode}"
					       class="btn btn-warning">
					        수정
					    </a>
					</c:if>
					<c:if test="${isProductionDept && isAdmin && !isClosed && production.productionStatus == 1}">
					    <a href="${pageContext.request.contextPath}/production/delete?productionCode=${production.productionCode}"
					       class="btn btn-danger"
					       onclick="return confirm('정말 삭제하시겠습니까?')">
					        삭제
					    </a>
					</c:if>
                    
                    <a href="${pageContext.request.contextPath}/production/list"
					   class="btn btn-secondary">
					    목록으로
					</a>
                </div>
            </div>
            <div class="card shadow-sm">
                <div class="card-header bg-dark text-white">
                    생산 정보
                </div>
                <div class="card-body">
                    <table class="table">
                        <tr>
                            <th>생산번호</th>
                            <td>${production.productionCode}</td>
                        </tr>

                        <tr>
                            <th>제품코드</th>
                            <td>${production.productCode}</td>
                        </tr>

                        <tr>
                            <th>제품명</th>
                            <td>${production.productName}</td>
                        </tr>

                        <tr>
                            <th>생산수량</th>
                            <td>${production.productionQty}</td>
                        </tr>

                        <tr>
                            <th>생산상태</th>
                            <td>${production.productionStatusName}</td>
                        </tr>

                        <tr>
                            <th>완료예정일</th>
                            <td>
							    ${production.completeDate.substring(0,4)}-${production.completeDate.substring(4,6)}-${production.completeDate.substring(6,8)}
							</td>
                        </tr>

                        <tr>
                            <th>등록자</th>
                            <td>${production.regEmpNo}</td>
                        </tr>

                        <tr>
                            <th>등록일</th>
                            <td>
                                <fmt:formatDate
                                    value="${production.regDate}"
                                    pattern="yyyy-MM-dd"/>
                            </td>
                        </tr>
                       	<c:if test="${production.productionStatus == 2
						           || production.productionStatus == 3}">
						    <tr>
						        <th>
						            <c:choose>
						                <c:when test="${production.productionStatus == 2}">
						                    취소 사유
						                </c:when>
						                <c:when test="${production.productionStatus == 3}">
						                    보류 사유
						                </c:when>
						            </c:choose>
						        </th>
						
						        <td colspan="3">
						            <textarea class="form-control bg-light"
						                      style="min-height:90px; resize:none; text-align:left;"
						                      readonly>${production.cancelReason}</textarea>
						        </td>
						    </tr>
						</c:if>
                    </table>
                </div>
            </div>
        </main>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>

</body>
</html>