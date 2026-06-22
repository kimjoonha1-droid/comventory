<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>구매/발주 수정</title>

    <%@ include file="../common/staticResources.jsp" %>
</head>
<body>
    <%@ include file="../common/header.jsp" %>

    <div id="wrapper">
        <%@ include file="../common/sidebar.jsp" %>

        <div id="page-content-wrapper">
            <main class="content p-4">

                <h2 class="mb-4">구매/발주 수정</h2>

                <form action="${pageContext.request.contextPath}/purchase/update"
                      method="post">

                    <input type="hidden" name="purchaseId" value="${purchase.purchaseId}">
                    <input type="hidden" name="regEmpNo" value="${purchase.regEmpNo}">

                    <div class="card shadow-sm mb-4">
                        <div class="card-header bg-dark text-white">
                            발주 기본 정보
                        </div>

                        <div class="card-body">

                            <div class="mb-3">
                                <label class="form-label">발주번호</label>
                                <input type="text"
                                       class="form-control"
                                       value="${purchase.purchaseId}"
                                       readonly>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">거래처번호</label>
                                <select name="custCode" class="form-select" required>
								    <option value="">거래처를 선택하세요</option>
								
								    <c:forEach var="cust" items="${custList}">
								        <option value="${cust.custCode}"
								            <c:if test="${cust.custCode == purchase.custCode}">
								                selected
								            </c:if>>
								            ${cust.custCode} - ${cust.custName}
								        </option>
								    </c:forEach>
								</select>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">입고 예정일</label>
                                <input type="date"
								       name="inboundDate"
								       class="form-control"
								       value="<fmt:formatDate value='${purchase.inboundDate}' pattern='yyyy-MM-dd'/>"
								       min="${today}">
                            </div>

                            <div class="mb-3">
                                <label class="form-label">총금액</label>
                                <input type="number"
                                       name="totalPrice"
                                       id="totalPriceInput"
                                       class="form-control"
                                       value="${purchase.totalPrice}"
                                       readonly>
                            </div>
                            <c:if test="${isAdmin}">
							    <div class="mb-3">						
							        <label class="form-label">
							            발주 상태
							        </label>
							
							        <select name="status"
							                id="statusSelect"
							                class="form-select">
							
							            <option value="1"
							                <c:if test="${purchase.status == 1}">
							                    selected
							                </c:if>>
							                신청
							            </option>
							
							            <option value="3"
							                <c:if test="${purchase.status == 3}">
							                    selected
							                </c:if>>
							                취소
							            </option>
							
							            <option value="5"
							                <c:if test="${purchase.status == 5}">
							                    selected
							                </c:if>>
							                입고완료
							            </option>
							
							            <option value="7"
							                <c:if test="${purchase.status == 7}">
							                    selected
							                </c:if>>
							                수불마감
							            </option>
							        </select>
							    </div>
							
							    <!-- 취소사유 -->
							    <div class="mb-3"
							         id="cancelReasonBox"
							         style="display:none;">
							
							        <label class="form-label">
							            취소 사유
							        </label>
							        <textarea name="cancelReason"
          										class="form-control">${purchase.cancelReason}</textarea>
							    </div>
							</c:if>
							<c:if test="${!isAdmin}">
							    <input type="hidden"
							           name="status"
							           value="${purchase.status}">
							</c:if>
						</div>
                    </div>

                    <div class="card shadow-sm mb-4">
                        <div class="card-header bg-dark text-white">
                            발주 품목 정보
                        </div>
                        <div class="card-body">
                            <c:forEach var="detail" items="${detailList}">

							    <!-- 제품 선택 -->
							    <div class="mb-3">
							        <label class="form-label">제품</label>
							
							        <select name="productCode"
							                id="productSelect"
							                class="form-select"
							                required>
							
							            <option value="">품목을 선택하세요</option>
							
							            <c:forEach var="part" items="${partList}">
							                <option value="${part.productCode}"
							                        data-price="${part.productPrice}"
							                        <c:if test="${part.productCode == detail.productCode}">
							                            selected
							                        </c:if>>
							
							                    ${part.productCode} - ${part.productName}
							
							                </option>
							            </c:forEach>
							        </select>
							    </div>
							
							    <!-- 수량 -->
							    <div class="mb-3">
							        <label class="form-label">구매수량</label>
							
							        <input type="number"
							               name="purchaseAmount"
							               id="amountInput"
							               class="form-control"
							               value="${detail.purchaseAmount}">
							    </div>
							
							    <!-- 단가 -->
							    <div class="mb-3">
							        <label class="form-label">단가</label>
							
							        <input type="number"
							               name="purchaseProductPrice"
							               id="priceInput"
							               class="form-control"
							               value="${detail.purchaseProductPrice}"
							               readonly>
							    </div>
							</c:forEach>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary">
                        수정 완료
                    </button>

                    <a href="${pageContext.request.contextPath}/purchase/detail?purchaseId=${purchase.purchaseId}"
                       class="btn btn-secondary">
                        취소
                    </a>

                </form>

            </main>
        </div>
    </div>

    <%@ include file="../common/footer.jsp" %>

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
	
	    amountInput.addEventListener("input", calculateTotalPrice);
	    
	    function toggleCancelReason(){
		        const cancelReasonBox =
		            document.getElementById("cancelReasonBox");
		        cancelReasonBox.style.display =
		            statusSelect.value == "3"
		            ? "block"
		            : "none";
		    }
		    if(statusSelect){
		        statusSelect.addEventListener(
		            "change",
		            toggleCancelReason
		        );
		        toggleCancelReason();
		    }
	</script>
</body>
</html>