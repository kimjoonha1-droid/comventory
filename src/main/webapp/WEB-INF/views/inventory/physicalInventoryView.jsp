<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>실사재고 현황 및 처리</title>
    <%@ include file="../common/staticResources.jsp" %>
</head>
<body>
    <%@ include file="../common/header.jsp" %>
    <c:set var="canInventoryProcess"
       value="${sessionScope.loginUser.dept_code == 7000 && sessionScope.loginUser.user_access == 2}" />
    <div id="wrapper">
        <%@ include file="../common/sidebar.jsp" %>
        <div id="page-content-wrapper">
            <main class="content p-4">
                <h4 class="fw-bold mb-4">실사재고 현황 및 처리</h4>
				
				<c:if test="${param.error == 'duplicate'}">
				    <div class="alert alert-warning alert-dismissible fade show" role="alert">
				        이미 오늘 등록된 실사재고입니다.
				        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="닫기"></button>
				    </div>
				</c:if>
				<form id="physicalSearchForm"
			      action="${pageContext.request.contextPath}/inventory/physicalInventoryView"
			      method="get">
	                <!-- 1. 재고 현황 요약 바 -->
	                <div class="card shadow-sm mb-3 border-0 stock-summary-card">
	                    <div class="card-body py-3">
	                        <div class="row align-items-center">
	                            <div class="col-md-6 border-end stock-summary-item">
	                                <div class="d-flex align-items-center justify-content-center">
	                                    <span class="stock-summary-label me-3">재고 현황</span>
										<select name="itemType" class="form-select form-select-sm w-auto bg-light border-0">
										    <option value="ALL" ${param.itemType == 'ALL' || empty param.itemType ? 'selected' : ''}>원재료/완제품 전체</option>
										    <option value="RAW" ${param.itemType == 'RAW' ? 'selected' : ''}>원재료</option>
										    <option value="PRODUCT" ${param.itemType == 'PRODUCT' ? 'selected' : ''}>완제품</option>
										</select>
	                                </div>
	                            </div>
	                            <div class="col-md-6 stock-summary-item">
	                                <div class="d-flex align-items-center justify-content-center">
	                                    <label class="me-2 text-muted">년월 선택</label>
										<input type="month"
										       name="searchYm"
										       class="form-control form-control-sm w-auto"
										       value="${searchYmValue}">
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	
	                <!-- 2. 필터 영역 -->
	                <div class="card shadow-sm mb-4 border-0 stock-filter-box">
	                    <div class="card-body">
	                        <div class="row g-3 align-items-center">
	                            <div class="col-md-5">
									<input type="text"
									       name="searchKeyword"
									       class="form-control form-control-sm"
									       placeholder="제품명 또는 제품 코드로 검색..."
									       value="${param.searchKeyword}">
	                            </div>
	                            <div class="col-md-3">
									<select name="category" class="form-select form-select-sm">
									    <option value="">카테고리 선택</option>
										<c:forEach items="${categories}" var="cat">
										    <option value="${cat.mcode}" ${param.category == cat.mcode ? 'selected' : ''}>
										        ${cat.code_contents}
										    </option>
										</c:forEach>
									</select>
	                            </div>
	                            <div class="col-md-2">
									<button type="submit" class="btn btn-dark btn-sm w-100">조회</button>
	                            </div>
	                            <div class="col-md-2 text-end">
	                                <button type="button" class="btn btn-primary btn-sm w-100" data-bs-toggle="modal" data-bs-target="#realItemsCreateModal">
	                                    실사재고 등록
	                                </button>
	                            </div>
	                        </div>
	                    </div>
					</div>
				</form>
                <!-- 3. 실사 테이블 -->
                <div class="card shadow-sm border-0">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle mb-0 text-center stock-table">
	                        <thead>
	                            <tr>
	                            	<th style="width: 60px;">#</th>
	                                <th>년월일</th>
	                                <th>제품코드</th>
	                                <th>제품명</th>
	                                <th>제품 카테고리</th>
	                                <th>창고재고</th>
	                                <th>변동수량</th>
	                                <th>변동사유</th>
	                                <th>승인상태</th>
	                                <th>등록사원코드</th>
	                                <th>등록일</th>
	                            </tr>
	                        </thead>
	                        <tbody>
	                            <c:choose>
	                                <c:when test="${not empty physicalList}">
	                                    <c:forEach items="${physicalList}" var="item" varStatus="status">
	                                        <tr>
        										<td>${page.start + status.index}</td>
	                                            <td>
	                                                <span class="fw-bold">${item.real_ymd}</span>
	                                                <button type="button"
	                                                        class="btn btn-outline-secondary btn-sm ms-2"
	                                                        data-bs-toggle="modal"
	                                                        data-bs-target="#realYmdEditModal"
	                                                        data-real-ymd="${item.real_ymd}"
	                                                        data-product-code="${item.product_code}">
	                                                    수정
	                                                </button>
	                                            </td>
	                                            <td class="stock-code">${item.product_code}</td>
	                                            <td>${item.product_name}</td>
	                                            <td>${item.category_name}</td>
	                                            <td class="fw-bold">${item.warehouse_qty}</td>
	                                            <td>${item.change_qty}</td>
	                                            <td>${item.change_reason}</td>
												<td>
												    <c:choose>
														<c:when test="${item.confirm_status == 1}">
														    <div class="d-flex justify-content-center align-items-center gap-2">
														        <span class="badge bg-secondary">승인대기</span>
														
																<c:choose>
																    <c:when test="${canInventoryProcess}">
																        <form action="${pageContext.request.contextPath}/inventory/approvePhysicalInventory" method="post" class="mb-0">
																            <input type="hidden" name="real_ymd" value="${item.real_ymd}">
																            <input type="hidden" name="product_code" value="${item.product_code}">
																            <input type="hidden" name="searchYm" value="${searchYmValue}">
																            <button type="submit" class="btn btn-danger btn-sm">승인처리</button>
																        </form>
																    </c:when>
																    <c:otherwise>
																        <button type="button" class="btn btn-outline-secondary btn-sm" disabled>승인권한 없음</button>
																    </c:otherwise>
																</c:choose>
														    </div>
														</c:when>
												        <c:when test="${item.confirm_status == 2}">
												            <span class="badge bg-primary">승인</span>
												        </c:when>
												        <c:when test="${item.confirm_status == 7}">
												            <span class="badge bg-dark">마감</span>
												        </c:when>
												        <c:otherwise>
												            <span class="badge bg-light text-dark border">${item.confirm_status}</span>
												        </c:otherwise>
												    </c:choose>
												</td>
	                                            <td>${item.reg_emp_no}</td>
	                                            <td class="text-secondary small">
	                                                <fmt:formatDate value="${item.reg_date}" pattern="yyyy-MM-dd" />
	                                            </td>
	                                        </tr>
	                                    </c:forEach>
	                                </c:when>
	                                <c:otherwise>
	                                    <tr>
	                                        <td colspan="11" class="py-5 text-muted">
	                                            <i class="bi bi-exclamation-circle d-block mb-2 fs-3"></i>
	                                            조회된 실사재고 내역이 없습니다.
	                                        </td>
	                                    </tr>
	                                </c:otherwise>
	                            </c:choose>
	                        </tbody>
	                    </table>
                    </div>
                </div>
                <c:if test="${page.totalPage > 1}">
                    <nav class="mt-3">
                        <ul class="pagination pagination-sm justify-content-center">
                            <c:forEach begin="${page.startPage}" end="${page.endPage}" var="pageNo">
                                <li class="page-item ${page.currentPage == pageNo ? 'active' : ''}">
                                    <c:url var="pageUrl" value="/inventory/physicalInventoryView">
                                        <c:param name="pageNum" value="${pageNo}" />
                                        <c:param name="itemType" value="${param.itemType}" />
                                        <c:param name="searchYm" value="${searchYmValue}" />
                                        <c:param name="searchKeyword" value="${param.searchKeyword}" />
                                        <c:param name="category" value="${param.category}" />
                                    </c:url>
                                    <a class="page-link" href="${pageUrl}">${pageNo}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </c:if>
            </main>
        </div>
    </div>

    <!-- 실사재고 등록 모달 -->
    <div class="modal fade" id="realItemsCreateModal" tabindex="-1" aria-labelledby="realItemsCreateModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/inventory/createPhysicalInventory" method="post">
                	<input type="hidden" name="searchYm" value="${searchYmValue}">
                    <div class="modal-header">
                        <h5 class="modal-title" id="realItemsCreateModalLabel">실사재고 등록</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫기"></button>
                    </div>
                    <div class="modal-body">
                        <div class="mb-3">
                            <label for="productCode" class="form-label">제품코드</label>
                            <select id="productCode" name="product_code" class="form-select form-select-sm" required>
                                <option value="">제품코드 선택</option>
                                <c:forEach items="${productList}" var="product">
                                    <option value="${product.product_code}" data-warehouse-qty="${product.warehouse_qty}">
                                        ${product.product_code} - ${product.product_name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="warehouseQty" class="form-label">창고재고</label>
                            <input type="number" id="warehouseQty" name="warehouse_qty" class="form-control form-control-sm" readonly>
                        </div>
                        <div class="mb-3">
                            <label for="changeQty" class="form-label">변동수량</label>
                            <input type="number" id="changeQty" name="change_qty" class="form-control form-control-sm" required>
                        </div>
                        <div class="mb-3">
                            <label for="changeReason" class="form-label">변동사유</label>
                            <textarea id="changeReason" name="change_reason" class="form-control form-control-sm" rows="3" required></textarea>
                        </div>
                        <div class="mb-0">
                            <label for="regEmpNo" class="form-label">등록사원코드</label>
                            <input type="number" id="regEmpNo" name="reg_emp_no" class="form-control form-control-sm" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline-secondary btn-sm" data-bs-dismiss="modal">취소</button>
                        <button type="submit" class="btn btn-primary btn-sm">등록</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- 실사재고 년월일 수정 모달 -->
    <div class="modal fade" id="realYmdEditModal" tabindex="-1" aria-labelledby="realYmdEditModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <form action="${pageContext.request.contextPath}/inventory/updatePhysicalInventoryDate" method="post">
                	<input type="hidden" name="searchYm" value="${searchYmValue}">
                    <div class="modal-header">
                        <h5 class="modal-title" id="realYmdEditModalLabel">실사재고 일자 수정</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫기"></button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" id="editOriginalRealYmd" name="original_real_ymd">
                        <input type="hidden" id="editProductCode" name="product_code">

                        <div class="mb-0">
                            <label for="editRealYmd" class="form-label">년월일</label>
                            <input type="date" id="editRealYmd" name="real_ymd" class="form-control form-control-sm" required>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-outline-secondary btn-sm" data-bs-dismiss="modal">취소</button>
                        <button type="submit" class="btn btn-primary btn-sm">수정</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <%@ include file="../common/footer.jsp" %>

    <script>
        const productCodeSelect = document.getElementById('productCode');
        const warehouseQtyInput = document.getElementById('warehouseQty');

        if (productCodeSelect && warehouseQtyInput) {
            productCodeSelect.addEventListener('change', function () {
                const selectedOption = this.options[this.selectedIndex];
                warehouseQtyInput.value = selectedOption.dataset.warehouseQty || '';
            });
        }

        const realYmdEditModal = document.getElementById('realYmdEditModal');

        if (realYmdEditModal) {
            realYmdEditModal.addEventListener('show.bs.modal', function (event) {
                const button = event.relatedTarget;
                const realYmd = button.getAttribute('data-real-ymd');
                const productCode = button.getAttribute('data-product-code');
                const dateValue = realYmd && realYmd.length === 8
                        ? realYmd.substring(0, 4) + '-' + realYmd.substring(4, 6) + '-' + realYmd.substring(6, 8)
                        : realYmd;

                document.getElementById('editOriginalRealYmd').value = realYmd || '';
                document.getElementById('editProductCode').value = productCode || '';
                document.getElementById('editRealYmd').value = dateValue || '';
            });
        }
    </script>
</body>
</html>
