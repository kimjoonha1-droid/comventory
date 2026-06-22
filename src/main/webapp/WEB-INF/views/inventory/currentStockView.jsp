<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>현재 재고 조회</title>
    <%@ include file="../common/staticResources.jsp" %>
</head>
<body>
    <%@ include file="../common/header.jsp" %>

    <div id="wrapper">
        <%@ include file="../common/sidebar.jsp" %>

        <div id="page-content-wrapper">
            <main class="content p-4">
                <h4 class="fw-bold mb-4">현재 재고 조회</h4>

                <!-- 1. 현재 재고 요약 바 -->
                <div class="card shadow-sm mb-3 border-0 stock-summary-card">
                    <div class="card-body py-3">
                        <div class="row align-items-center">
                            <div class="col-md-4 border-end stock-summary-item">
                                <div class="d-flex align-items-center justify-content-center">
                                    <span class="stock-summary-label me-3">재고 현황</span>
                                    <select name="itemType" form="currentStockSearchForm" class="form-select form-select-sm w-auto bg-light border-0">
                                        <option value="ALL" ${param.itemType == 'ALL' || empty param.itemType ? 'selected' : ''}>원재료/완제품 전체</option>
                                        <option value="RAW" ${param.itemType == 'RAW' ? 'selected' : ''}>원재료</option>
                                        <option value="PRODUCT" ${param.itemType == 'PRODUCT' ? 'selected' : ''}>완제품</option>
                                    </select>
                                </div>
                            </div>

                            <div class="col-md-5 border-end stock-summary-item">
                                <div class="d-flex align-items-center justify-content-center text-center">
                                    <span class="text-muted me-2">최근 기준</span>
                                    <span class="fw-bold">
                                        ${latestClosingYm != null ? latestClosingYm : '가마감/마감 데이터 없음'}
                                    </span>
                                </div>
                            </div>

                            <div class="col-md-3 stock-summary-item text-center">
                                <div class="text-danger fw-bold">
                                    <i class="bi bi-exclamation-triangle-fill me-1"></i>
                                    재고 부족 품목 <span class="fs-5">${lackCount != null ? lackCount : 0}</span>개
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 2. 상세 검색 필터 영역 -->
                <div class="card shadow-sm mb-4 border-0 stock-filter-box">
                    <div class="card-body">
                        <form id="currentStockSearchForm" action="${pageContext.request.contextPath}/inventory/currentStockView" method="get">
                            <div class="row g-3">
                                <div class="col-md-5">
                                    <div class="input-group input-group-sm">
                                        <span class="input-group-text bg-white border-end-0">
                                            <i class="bi bi-search"></i>
                                        </span>
                                        <input type="text"
                                               name="searchKeyword"
                                               class="form-control border-start-0"
                                               placeholder="제품명 또는 제품 코드로 검색..."
                                               value="${param.searchKeyword}">
                                    </div>
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

                                <div class="col-md-3">
                                    <select name="inventoryStatus" class="form-select form-select-sm">
                                        <option value="">적정재고 상태 전체</option>
                                        <option value="NORMAL" ${param.inventoryStatus == 'NORMAL' ? 'selected' : ''}>정상</option>
                                        <option value="LACK" ${param.inventoryStatus == 'LACK' ? 'selected' : ''}>부족</option>
                                    </select>
                                </div>

                                <div class="col-md-1">
                                    <button type="submit" class="btn btn-dark btn-sm w-100">검색</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <!-- 3. 현재 재고 목록 테이블 -->
                <div class="card shadow-sm border-0">
                    <div class="table-responsive">
                        <table class="table table-hover align-middle mb-0 text-center stock-table">
                            <thead>
                                <tr>
                                    <th style="width: 60px;">#</th>
                                    <th>제품코드</th>
                                    <th>제품명</th>
                                    <th>카테고리</th>
                                    <th>현재재고</th>
                                    <th>적정재고</th>
                                    <th>적정재고상태</th>
                                    <th>최근반영일</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${not empty currentStockList}">
                                        <c:forEach items="${currentStockList}" var="stock" varStatus="status">
                                            <tr>
                                                <td>${page.start + status.index}</td>
                                                <td class="stock-code">${stock.product_code}</td>
                                                <td>${stock.product_name}</td>
                                                <td>
                                                    <span class="badge bg-light text-dark border">
                                                        ${stock.category_name}
                                                    </span>
                                                </td>
                                                <td class="stock-qty">${stock.qty}</td>
                                                <td class="text-muted">${stock.proper_qty}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${stock.qty < stock.proper_qty}">
                                                            <span class="badge rounded-pill bg-danger badge-pill-custom">부족</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge rounded-pill bg-success badge-pill-custom">정상</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td class="text-secondary">
                                                    <fmt:formatDate value="${stock.reg_date}" pattern="yyyy-MM-dd" />
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="8" class="py-5 text-muted">
                                                <i class="bi bi-exclamation-circle d-block mb-2 fs-3"></i>
                                                조회된 현재 재고 내역이 없습니다.
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
				                    <c:url var="pageUrl" value="/inventory/currentStockView">
				                        <c:param name="pageNum" value="${pageNo}" />
				                        <c:param name="itemType" value="${param.itemType}" />
				                        <c:param name="searchKeyword" value="${param.searchKeyword}" />
				                        <c:param name="category" value="${param.category}" />
				                        <c:param name="inventoryStatus" value="${param.inventoryStatus}" />
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
    <%@ include file="../common/footer.jsp" %>
</body>
</html>
