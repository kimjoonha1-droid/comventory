<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>구매/발주 등록</title>
    <%@ include file="../common/staticResources.jsp" %>
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    <div id="wrapper">
        <%@ include file="../common/sidebar.jsp" %>
        <div id="page-content-wrapper">
            <main class="content p-4">
                <h2 class="mb-4">구매/발주 등록</h2>
                <form action="${pageContext.request.contextPath}/purchase/insert"
                      method="post">
                    <!-- 거래처 -->
                    <select name="custCode" class="form-select" required>
					    <option value="">거래처를 선택하세요</option>
					    <c:forEach var="cust" items="${custList}">
					        <option value="${cust.custCode}">
					            ${cust.custCode} - ${cust.custName}
					        </option>
					    </c:forEach>
					</select>
                    <!-- 구매 부품 -->
                    <div class="mb-3">
                        <label class="form-label">구매 부품</label>
                        <select name="productCode"
						        id="productSelect"
						        class="form-select"
						        required>
						    <option value="">
						        부품을 선택하세요
						    </option>
						    <c:forEach var="part" items="${partList}">
						        <option value="${part.productCode}"
						                data-price="${part.productPrice}">
						            ${part.productCode} - ${part.productName}
						        </option>
						    </c:forEach>
						</select>
                    </div>
                    <!-- 구매 수량 -->
                    <div class="mb-3">
                        <label class="form-label">구매 수량</label>
                        <input type="number"
                               name="purchaseAmount"
                               id="amountInput"
                               class="form-control"
                               min="1"
                               required>
                    </div>
                    <!-- 구매 단가 -->
                    <div class="mb-3">
                        <label class="form-label">구매 단가</label>

                        <input type="number"
                               name="purchaseProductPrice"
                               id="priceInput"
                               class="form-control"
                               required>
                    </div>
                    <!-- 총금액 -->
                    <div class="mb-3">
                        <label class="form-label">총 금액</label>

                        <input type="number"
                               name="totalPrice"
                               id="totalPriceInput"
                               class="form-control"
                               required>
                    </div>
                    <!-- 입고 예정일 -->
                    <div class="mb-3">
                        <label class="form-label">입고 예정일</label>
                        <input type="date"
						       name="inboundDate"
						       class="form-control"
						       value="${today}"
						       min="${today}">
                    </div>
                    <!-- 등록자 -->
                    <input type="hidden"
                           name="regEmpNo"
                           value="1001">

                    <button type="submit"
                            class="btn btn-primary">
                        등록
                    </button>
                    <a href="${pageContext.request.contextPath}/purchase/list"
                       class="btn btn-secondary">
                        목록
                    </a>
                </form>
                <script>
				    const productSelect = document.getElementById("productSelect");
				    const priceInput = document.getElementById("priceInput");
				    const amountInput = document.getElementById("amountInput");
				    const totalPriceInput = document.getElementById("totalPriceInput");
				
				    function calculateTotalPrice() {
				        const price = Number(priceInput.value || 0);
				        const amount = Number(amountInput.value || 0);
				
				        totalPriceInput.value = price * amount;
				    }
				
				    productSelect.addEventListener("change", function () {
				        const selectedOption = this.options[this.selectedIndex];
				        const price = selectedOption.getAttribute("data-price");
				
				        priceInput.value = price || "";
				        calculateTotalPrice();
				    });
				
				    amountInput.addEventListener("input", function () {
				        calculateTotalPrice();
				    });
				</script>
            </main>
        </div>
    </div>
    <%@ include file="../common/footer.jsp" %>
</body>
</html>