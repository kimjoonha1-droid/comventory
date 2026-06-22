<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>생산 수정</title>
    <%@ include file="../common/staticResources.jsp" %>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div id="wrapper">
    <%@ include file="../common/sidebar.jsp" %>
    <div id="page-content-wrapper">
        <main class="content p-4">
            <h2 class="mb-4">생산 수정</h2>
            <form action="${pageContext.request.contextPath}/production/update"
                  method="post">

                <!-- 생산번호 -->
                <input type="hidden"
                       name="productionCode"
                       value="${production.productionCode}">

                <!-- 등록자 -->
                <input type="hidden"
                       name="regEmpNo"
                       value="${production.regEmpNo}">

                <div class="card shadow-sm">
                    <div class="card-header bg-dark text-white">
                        생산 정보 수정
                    </div>
                    <div class="card-body">

                        <!-- 제품 선택 -->
                        <div class="mb-3">

                            <label class="form-label">
                                생산 제품
                            </label>
                            <select name="productCode"
                                    class="form-select"
                                    required>

                                <c:forEach var="product"
                                           items="${finishedProductionList}">
                                    <option value="${product.productCode}"
                                        <c:if test="${product.productCode == production.productCode}">
                                            selected
                                        </c:if>>
                                        ${product.productCode}
                                        -
                                        ${product.productName}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <!-- 생산 수량 -->
                        <div class="mb-3">

                            <label class="form-label">
                                생산 수량
                            </label>

                            <input type="number"
                                   name="productionQty"
                                   class="form-control"
                                   value="${production.productionQty}"
                                   min="1"
                                   required>
                        </div>
                        
                        <!-- 완료 예정일 -->
                        <div class="mb-3">
						    <label class="form-label">
						        완료 예정일
						    </label>
						
						    <input type="date"
							       name="completeDate"
							       class="form-control"
							       min="${today}"
							       value="${production.completeDate.substring(0,4)}-${production.completeDate.substring(4,6)}-${production.completeDate.substring(6,8)}">
						</div>

                        <!-- 생산 상태 -->
                        <c:if test="${isAdmin}">
							    <div class="mb-3">						
							        <label class="form-label">
							            발주 상태
							        </label>
							
							        <select name="productionStatus"
							                id="statusSelect"
							                class="form-select">
							
							            <option value="1"
							                <c:if test="${production.productionStatus == 1}">
							                    selected
							                </c:if>>
							                대기
							            </option>
							
							            <option value="2"
							                <c:if test="${production.productionStatus == 2}">
							                    selected
							                </c:if>>
							                취소
							            </option>
							
							            <option value="3"
							                <c:if test="${production.productionStatus == 3}">
							                    selected
							                </c:if>>
							                보류
							            </option>
							
							            <option value="5"
							                <c:if test="${production.productionStatus == 5}">
							                    selected
							                </c:if>>
							                완료
							            </option>
							            
							            <option value="7"
										    <c:if test="${production.productionStatus == 7}">
										        selected
										    </c:if>>
										    수불마감
										</option>
							        </select>
							    </div>
							</c:if>
							<c:if test="${!isAdmin}">
							    <input type="hidden"
							           name="productionStatus"
							           value="${production.productionStatus}">
							</c:if>
							<div class="mb-3" id="cancelReasonBox" style="display:none;">
							    <label class="form-label">취소/보류 사유</label>
							    <textarea name="cancelReason"
							              class="form-control">${production.cancelReason}</textarea>
							</div>
					</div>
				</div>
                <div class="mt-4">
                    <button type="submit"
                            class="btn btn-primary">
                        수정 완료
                    </button>

                    <a href="${pageContext.request.contextPath}/production/detail?productionCode=${production.productionCode}"
                       class="btn btn-secondary">
                        취소
                    </a>
                </div>
            </form>
        </main>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>

<script>
    const statusSelect = document.getElementById("statusSelect");
    const cancelReasonBox = document.getElementById("cancelReasonBox");

    if (statusSelect) {
        function toggleReasonBox() {
            cancelReasonBox.style.display =
                (statusSelect.value == "3" || statusSelect.value == "2")
                ? "block"
                : "none";
        }

        statusSelect.addEventListener("change", toggleReasonBox);
        toggleReasonBox();
    }
</script>

</body>
</html>