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

                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2>BOM 상세</h2>
                    <c:if test="${isProductionDept}">
					    <a href="${pageContext.request.contextPath}/bom/insertForm?productCode=${bomDetailList[0].productCode}"
					       class="btn btn-primary">
					        부품 추가
					    </a>
					</c:if>
                    <a href="${pageContext.request.contextPath}/bom/list"
                       class="btn btn-secondary">
                        목록으로
                    </a>
                </div>
				<div class="card shadow-sm mb-3">
				    <div class="card-header bg-dark text-white">
				        완제품 정보
				    </div>
				    <div class="card-body">
				        <table class="table mb-0">
				            <tr>
				                <th style="width: 180px;">완제품 코드</th>
				                <td>${bomDetailList[0].productCode}</td>
				            </tr>
				            <tr>
				                <th>완제품명</th>
				                <td>${bomDetailList[0].productName}</td>
				            </tr>
				        </table>
				    </div>
				</div>
                <div class="table-responsive">
                    <table class="table table-bordered table-hover align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th>부품코드</th>
                                <th>부품명</th>
                                <th>필요수량</th>
                                <th>등록자</th>
                                <th>등록일</th>
                                <th>관리</th>
                            </tr>
                        </thead>

                        <tbody>
                            <c:forEach var="bom" items="${bomDetailList}">
                                <tr>
                                  
                                    <td>${bom.productWon}</td>
                                    <td>${bom.productWonName}</td>
                                    <td>${bom.needQty}</td>
                                    <td>${bom.regEmpNo}</td>
                                    <td>
                                        <fmt:formatDate value="${bom.regDate}" pattern="yyyy-MM-dd"/>
                                    </td>
                                    <td>
                                    	<c:if test="${isProductionDept && isAdmin}">
	                                    	<a href="${pageContext.request.contextPath}/bom/updateForm?productCode=${bom.productCode}&productWon=${bom.productWon}"
											   class="btn btn-warning btn-sm">
											    수정
											</a>
										</c:if>
									    <c:if test="${isProductionDept && isAdmin}">
										    <a href="${pageContext.request.contextPath}/bom/delete?productCode=${bom.productCode}&productWon=${bom.productWon}"
										       class="btn btn-danger btn-sm"
										       onclick="return confirm('이 부품을 BOM에서 삭제하시겠습니까?')">
										        삭제
										    </a>
										</c:if>
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