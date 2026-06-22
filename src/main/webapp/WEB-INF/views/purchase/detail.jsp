<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>구매/발주 상세</title>
<%@ include file="../common/staticResources.jsp"%>
<style>
    .info-table th {
        width: 180px;
        background-color: #f8f9fa;
    }

    .card {
        border-radius: 12px;
    }

    .detail-title {
        font-weight: bold;
        margin-bottom: 20px;
    }

    .purchase-id {
        color: #0d6efd;
        font-weight: bold;
    }
</style>

</head>
<body>
<%@ include file="../common/header.jsp"%>
<div id="wrapper">
    <%@ include file="../common/sidebar.jsp"%>
    <div id="page-content-wrapper">
        <main class="content p-4">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="detail-title">구매/발주 상세</h2>
                <div>
			        <!-- 수정 -->
			        <c:if test="${isPurchaseDept && !isClosed && purchase.status == 1}">
					    <a href="${pageContext.request.contextPath}/purchase/updateForm?purchaseId=${purchase.purchaseId}"
					       class="btn btn-warning">
					        수정
					    </a>
					</c:if>
					
					<c:if test="${isPurchaseDept && isAdmin && !isClosed && (purchase.status == 1 || purchase.status == 3)}">
					    <a href="${pageContext.request.contextPath}/purchase/delete?purchaseId=${purchase.purchaseId}"
					       class="btn btn-danger"
					       onclick="return confirm('삭제하시겠습니까?')">
					        삭제
					    </a>
					</c:if>
			        <a href="${pageContext.request.contextPath}/purchase/list"
	                   class="btn btn-secondary">
	                    목록으로
	                </a>
			    </div>
            </div>
            <!-- 발주 기본 정보 -->
            <div class="card shadow-sm mb-4">
                <div class="card-header bg-dark text-white">
                    발주 기본 정보
                </div>
                <div class="card-body">
                    <table class="table table-bordered align-middle info-table">
                        <tbody>
                            <tr>
                                <th>발주번호</th>
                                <td class="purchase-id">
                                    ${purchase.purchaseId}
                                </td>
                                <th>거래처번호</th>
                                <td>
                                    ${purchase.custCode}
                                </td>
                            </tr>
                            <tr>
                                <th>상태</th>
                                <td>
                                    ${purchase.statusName}
                                </td>
                                <th>총금액</th>
                                <td>
                                    ${purchase.totalPrice}
                                </td>
                            </tr>
                            <tr>
                                <th>신청일</th>
                                <td>
                                    <fmt:formatDate
                                        value="${purchase.requestDate}"
                                        pattern="yyyy-MM-dd"/>
                                </td>
                                <th>입고일</th>
                                <td>
                                    <fmt:formatDate
                                        value="${purchase.inboundDate}"
                                        pattern="yyyy-MM-dd"/>
                                </td>
                            </tr>
                            <tr>
                                <th>등록자</th>
                                <td>
                                    ${purchase.regEmpNo}
                                </td>
                                <th>등록일</th>
                                <td>
                                    <fmt:formatDate
                                        value="${purchase.regDate}"
                                        pattern="yyyy-MM-dd"/>
                                </td>
                            </tr>
                            <c:if test="${purchase.status == 3}">
                            	<tr>
							        <th>취소 사유</th>
							        <td colspan="5">
								        <textarea class="form-control bg-light"
								             style="min-height:90px; resize:none;
								             		text-align:left;" readonly="readonly">			
								            ${purchase.cancelReason}							
								        </textarea>
							        </td>				
							    </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- 발주 상세 품목 -->
            <div class="card shadow-sm">
                <div class="card-header bg-dark text-white">
                    발주 품목 정보
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered table-hover align-middle">
                            <thead class="table-dark">
                                <tr>
                                    <th>제품코드</th>
                                    <th>제품명</th>
                                    <th>수량</th>
                                    <th>단가</th>
                                    <th>등록자</th>
                                    <th>등록일</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="detail"
                                           items="${detailList}">
                                    <tr>
                                        <td>
                                            ${detail.productCode}
                                        </td>
                                        <td>
                                            ${detail.productName}
                                        </td>
                                        <td>
                                            ${detail.purchaseAmount}
                                        </td>
                                        <td>
                                            ${detail.purchaseProductPrice}
                                        </td>
                                        <td>
                                            ${detail.regEmpNo}
                                        </td>
                                        <td>
                                            <fmt:formatDate
                                                value="${detail.regDate}"
                                                pattern="yyyy-MM-dd"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </main>
    </div>
</div>
<%@ include file="../common/footer.jsp"%>
</body>
</html>