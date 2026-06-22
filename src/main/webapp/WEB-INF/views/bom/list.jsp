<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%@ include file="../common/staticResources.jsp" %>
    <style>
		.table {
		    width: 100%;
		}
		
		/* 추가 */
		tbody tr:hover {
		    background-color: #f2f2f2;
		}
		</style>
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    <div id="wrapper">
        <%@ include file="../common/sidebar.jsp" %>
        <div id="page-content-wrapper">
            <main class="content p-4">
                <div class="d-flex justify-content-between align-items-center mb-3">
				    <h2>BOM 목록</h2>
				    <c:if test="${isProductionDept}">
					    <a href="${pageContext.request.contextPath}/bom/insertForm"
					       class="btn btn-primary">
					        BOM 등록
					    </a>
					</c:if>
				</div>
			<div class="table-responsive">
			<table class="table table-bordered table-hover align-middle">
			    <thead class="table-dark">
			        <tr>
			            <th>제품코드</th>
						<th>제품명</th>
						<th>등록 부품 수</th>
						<th>총 필요수량</th>
						<th>등록자</th>
						<th>등록일</th>
			        </tr>
			    </thead>
			    
			    <tbody>
			        <c:forEach var="bom" items="${bomList}">
			            <tr onclick="location.href='/bom/detail?productCode=${bom.productCode}'"
    						style="cursor:pointer;">
    						
			                <td>${bom.productCode}</td>
							<td>${bom.productName}</td>
							<td>${bom.partCount}</td>
							<td>${bom.totalNeedQty}</td>
							<td>${bom.regEmpNo}</td>
							<td>
							    <fmt:formatDate value="${bom.regDate}" pattern="yyyy-MM-dd"/>
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