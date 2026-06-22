<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>BOM 등록</title>
    <%@ include file="../common/staticResources.jsp" %>
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    <div id="wrapper">
        <%@ include file="../common/sidebar.jsp" %>

        <div id="page-content-wrapper">
            <main class="content p-4">
                <h2 class="mb-4">BOM 등록 페이지</h2>
                <form action="${pageContext.request.contextPath}/bom/insert"
                      method="post">
                    <div class="mb-3">
                        <label class="form-label">완제품</label>
                        <select name="productCode" class="form-select" required>
						    <option value="">완제품을 선택하세요</option>
						
						    <c:forEach var="product" items="${finishedProductList}">
						        <option value="${product.productCode}"
						            <c:if test="${product.productCode == selectedProductCode}">
						                selected
						            </c:if>>
						            ${product.productCode} - ${product.productName}
						        </option>
						    </c:forEach>
						</select>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">부품</label>
                        <select name="productWon" class="form-select" required>
                            <option value="">부품을 선택하세요</option>
                            <c:forEach var="part" items="${partList}">
                                <option value="${part.productWon}">
                                    ${part.productWon} - ${part.productWonName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">필요수량</label>
                        <input type="number"
                               name="needQty"
                               class="form-control"
                               min="1"
                               required>
                    </div>

                    <!-- 로그인 기능 붙기 전 임시 등록자 -->
                    <input type="hidden" name="regEmpNo" value="1001">

                    <button type="submit" class="btn btn-primary">
                        등록
                    </button>

                    <a href="${pageContext.request.contextPath}/bom/list"
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