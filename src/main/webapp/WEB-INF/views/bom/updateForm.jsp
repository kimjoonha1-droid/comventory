<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>BOM 상세</title>
    <%@ include file="../common/staticResources.jsp" %>
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    <div id="wrapper">
        <%@ include file="../common/sidebar.jsp" %>
        <div id="page-content-wrapper">
            <main class="content p-4">
			    <h2 class="mb-4">BOM 수정</h2>
			    <div class="card shadow-sm">
			        <div class="card-header bg-dark text-white">
			            BOM 수정
			        </div>
			        <div class="card-body">
			            <form action="${pageContext.request.contextPath}/bom/update"
			                  method="post">
			
			                <!-- 완제품코드 -->
			                <div class="mb-3">
			                    <label class="form-label">완제품코드</label>
			
			                    <input type="text"
			                           class="form-control"
			                           value="${bom.productCode}"
			                           readonly>
			
			                    <input type="hidden"
			                           name="productCode"
			                           value="${bom.productCode}">
			                </div>
			
			                <!-- 부품코드 -->
			                <div class="mb-3">
			                    <label class="form-label">부품코드</label>
			
			                    <input type="text"
			                           class="form-control"
			                           value="${bom.productWon}"
			                           readonly>
			                           
			                    <input type="hidden"
			                           name="productWon"
			                           value="${bom.productWon}">
			                </div>
			
			                <!-- 필요수량 -->
			                <div class="mb-4">
			                    <label class="form-label">필요수량</label>
			                    <input type="number"
			                           name="needQty"
			                           class="form-control"
			                           value="${bom.needQty}"
			                           min="1"
			                           required>
			                </div>
			
			                <button type="submit"
			                        class="btn btn-primary">
			                    수정 완료
			                </button>
			
			                <a href="${pageContext.request.contextPath}/bom/detail?productCode=${bom.productCode}"
			                   class="btn btn-secondary">
			                    취소
			                </a>
			            </form>
			        </div>
			    </div>
			</main>
        </div>
    </div>

    <%@ include file="../common/footer.jsp" %>
</body>
</html>