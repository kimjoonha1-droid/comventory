<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>생산 등록</title>
    <%@ include file="../common/staticResources.jsp" %>
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    <div id="wrapper">
        <%@ include file="../common/sidebar.jsp" %>
        <div id="page-content-wrapper">
            <main class="content p-4">
                <h2 class="mb-4">생산 등록</h2>
                <form action="${pageContext.request.contextPath}/production/insert"
                      method="post">
                    <div class="mb-3">
                        <label class="form-label">생산 제품</label>
                        <select name="productCode" class="form-select" required>
                            <option value="">생산할 제품을 선택하세요</option>

                            <c:forEach var="product" items="${finishedProductionList}">
                                <option value="${product.productCode}">
                                    ${product.productCode} - ${product.productName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">생산 수량</label>
                        <input type="number"
                               name="productionQty"
                               class="form-control"
                               min="1"
                               required>
                    </div>
                    <div class="mb-3">
					    <label class="form-label">
					        완료 예정일
					    </label>
					
					    <input type="date"
						       name="completeDate"
						       class="form-control"
						       value="${today}"
						       min="${today}">
					</div>
                    <!-- 로그인 연동 전 임시 등록자 -->
                    <input type="hidden" name="regEmpNo" value="1001">

                    <button type="submit" class="btn btn-primary">
                        등록
                    </button>
                    <a href="${pageContext.request.contextPath}/production/list"
                       class="btn btn-secondary">
                        목록
                    </a>
                </form>
            </main>
        </div>
    </div>

    <%@ include file="../common/footer.jsp" %>
</body>
</html>