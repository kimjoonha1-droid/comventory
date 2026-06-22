<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    
    <%@ include file="../common/staticResources.jsp" %>
</head>
<body>
    <%@ include file="../common/header.jsp" %>

    <div id="wrapper">
        <%@ include file="../common/sidebar.jsp" %>
        
        <div id="page-content-wrapper">
            <main class="content p-4">
            	<div class="d-flex justify-content-between align-items-center mb-3">
	                <h2 class="mb-4">구매/발주 목록</h2>
	                <c:if test="${isPurchaseDept}">
					    <a href="${pageContext.request.contextPath}/purchase/insertForm"
					       class="btn btn-primary">
					        구매 등록
					    </a>
					</c:if>
				</div>
			<div class="table-responsive">
			    <table class="table table-bordered table-hover align-middle">
			        <thead class="table-dark">
			            <tr>
                             <th>발주번호</th>
                             <th>거래처번호</th>
                             <th>상태</th>
                             <th>신청일</th>
                             <th>총금액</th>
                             <th>입고일</th>
                             <th>등록자</th>
                             <th>등록일</th>
                        </tr>
			        </thead>
			
			        <tbody>
			            <c:forEach var="purchase" items="${purchaseList}">
                                <tr onclick="location.href='${pageContext.request.contextPath}/purchase/detail?purchaseId=${purchase.purchaseId}'"
    								style="cursor:pointer;">
                                    <td>${purchase.purchaseId}</td>
                                    <td>${purchase.custCode}</td>
                                    <td>${purchase.statusName}</td>
                                    <td>
                                        <fmt:formatDate value="${purchase.requestDate}" pattern="yyyy-MM-dd"/>
                                    </td>
                                    <td>${purchase.totalPrice}</td>
                                    <td>
                                        <fmt:formatDate value="${purchase.inboundDate}" pattern="yyyy-MM-dd"/>
                                    </td>
                                    <td>${purchase.regEmpNo}</td>
                                    <td>
                                        <fmt:formatDate value="${purchase.regDate}" pattern="yyyy-MM-dd"/>
                                    </td>
                                </tr>
                            </c:forEach>
			        </tbody>
			    </table>
			</div>
            </main>
        </div>
    </div>
    <%@ include file="../common/footer.jsp" %>
</body>
</html>