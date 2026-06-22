<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

				    <h2>생산 목록</h2>
	                <c:if test="${isProductionDept}">
					    <a href="${pageContext.request.contextPath}/production/insertForm"
					       class="btn btn-primary">
					        생산 등록
					    </a>
					</c:if>
				</div>
			<div class="table-responsive">
			    <table class="table table-bordered table-hover align-middle">
			        <thead class="table-dark">
			            <tr>
			                <th>생산번호</th>
			                <th>제품코드</th>
			                <th>제품명</th>
			                <th>생산수량</th>
			                <th>생산상태</th>
			                <th>완료일</th>
			                <th>등록자</th>
			                <th>등록일</th>
			            </tr>
			        </thead>
			
			        <tbody>
			            <c:forEach var="production" items="${productionList}">
			                <tr onclick="location.href='${pageContext.request.contextPath}/production/detail?productionCode=${production.productionCode}'"
    							style="cursor:pointer;">
			                    <td>${production.productionCode}</td>
			                    <td>${production.productCode}</td>
			                    <td>${production.productName}</td>
			                    <td>${production.productionQty}</td>
			                    <td>${production.productionStatusName}</td>
			                    <td>
								    ${production.completeDate.substring(0,4)}-${production.completeDate.substring(4,6)}-${production.completeDate.substring(6,8)}
								</td>
			                    <td>${production.regEmpNo}</td>
			                    <td>
			                        <fmt:formatDate value="${production.regDate}" pattern="yyyy-MM-dd"/>
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
