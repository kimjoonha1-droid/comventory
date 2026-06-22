<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>거래처 목록</title>

<%@ include file="../common/staticResources.jsp" %>

<style>
    .cust-list-container {
        width: 95%;
        max-width: 1700px;
        margin: 0 auto;
        background: white;
        padding: 30px;
        border-radius: 12px;
        box-shadow: 0 4px 15px rgba(0,0,0,0.1);
    }

    h2 {
        margin-bottom: 20px;
        color: #2c3e50;
    }

    .top-btn {
        margin-bottom: 20px;
    }

    .cust-btn {
        text-decoration: none;
        padding: 8px 12px;
        border-radius: 5px;
        font-size: 13px;
        color: white;
        display: inline-block;
        margin: 2px;
        border: none;
        cursor: pointer;
    }

    .btn-add {
        background-color: #3498db;
    }

    .btn-main {
        background-color: #7f8c8d;
    }

    .btn-edit {
        background-color: #f39c12;
    }

    .btn-active {
        background-color: #27ae60;
    }

    .btn-stop {
        background-color: #8e44ad;
    }

    .btn-inactive {
        background-color: #e74c3c;
    }

    .btn-reset {
        background-color: #95a5a6;
    }

    .search-form {
        margin-bottom: 25px;
        display: flex;
        gap: 10px;
        align-items: center;
        flex-wrap: wrap;
    }

    .search-form select,
    .search-form input {
        padding: 8px 12px;
        border: 1px solid #ccc;
        border-radius: 6px;
        font-size: 14px;
        height: 38px;
        box-sizing: border-box;
    }

    .search-form input {
        width: 250px;
    }

    table {
        width: 100%;
        border-collapse: collapse;
    }

    th, td {
        padding: 11px;
        text-align: center;
        border-bottom: 1px solid #ddd;
        font-size: 14px;
    }

    th {
        background-color: #2c3e50;
        color: white;
    }

    tr:hover {
        background-color: #f1f1f1;
    }

    .empty {
        text-align: center;
        padding: 35px;
        color: #888;
        font-weight: bold;
        background-color: #fafafa;
    }

    .paging {
        margin-top: 30px;
        text-align: center;
    }

    .paging a {
        display: inline-block;
        margin: 0 4px;
        padding: 8px 13px;
        border-radius: 6px;
        background-color: #ecf0f1;
        color: #2c3e50;
        text-decoration: none;
        font-weight: bold;
        font-size: 14px;
    }

    .paging a:hover {
        background-color: #3498db;
        color: white;
    }

    .paging .active {
        background-color: #3498db;
        color: white;
    }
</style>
</head>

<body>

<c:if test="${not empty msg}">
    <script>
        alert("${msg}");
    </script>
</c:if>

<%@ include file="../common/header.jsp" %>

<div id="wrapper">

    <%@ include file="../common/sidebar.jsp" %>

    <div id="page-content-wrapper">
        <main class="content p-4">

        <div class="cust-list-container">

            <h2>거래처 목록</h2>

            <div class="top-btn">

			    <c:if test="${canManageCust}">
			        <a href="/cust/custSaveForm" class="cust-btn btn-add">
			            + 거래처 등록
			        </a>
			    </c:if>
			
			    <a href="/cust/custMain" class="cust-btn btn-main">
			        메인
			    </a>
			
			</div>

            <form action="/cust/custList" method="get" class="search-form">

                <select name="searchType">
                    <option value="name" ${searchType == 'name' ? 'selected' : ''}>
                        거래처명
                    </option>

                    <option value="ceo" ${searchType == 'ceo' ? 'selected' : ''}>
                        대표자명
                    </option>

                    <option value="business" ${searchType == 'business' ? 'selected' : ''}>
                        사업자번호
                    </option>

                    <option value="tel" ${searchType == 'tel' ? 'selected' : ''}>
                        전화번호
                    </option>
                </select>

                <input type="text"
                       name="keyword"
                       value="${keyword}"
                       placeholder="검색어 입력">

                <select name="custType">
                    <option value="">전체유형</option>

                    <option value="1" ${custType == 1 ? 'selected' : ''}>
                        판매처
                    </option>

                    <option value="2" ${custType == 2 ? 'selected' : ''}>
                        구매처
                    </option>
                </select>

                <button type="submit" class="cust-btn btn-add">
                    검색
                </button>

                <a href="/cust/custList" class="cust-btn btn-reset">
                    초기화
                </a>

            </form>

            <table>
                <thead>
                    <tr>
                        <th>코드</th>
                        <th>거래처명</th>
                        <th>유형</th>
                        <th>사업자번호</th>
                        <th>대표자</th>
                        <th>전화번호</th>
                        <th>이메일</th>
                        <th>주소</th>
                        <th>사용상태</th>
                        <th>관리</th>
                    </tr>
                </thead>

                <tbody>

                    <c:choose>

                        <c:when test="${empty custList}">
                            <tr>
                                <td colspan="10" class="empty">
                                    검색된 거래처가 없습니다.
                                </td>
                            </tr>
                        </c:when>

                        <c:otherwise>

                            <c:forEach var="c" items="${custList}">

                                <tr>
                                    <td>${c.custCode}</td>
                                    <td>${c.custName}</td>
                                    <td>${c.custTypeName}</td>
                                    <td>${c.businessNo}</td>
                                    <td>${c.ceoName}</td>
                                    <td>${c.custTel}</td>
                                    <td>${c.custEmail}</td>
                                    <td>${c.custAddress}</td>
                                    <td>${c.custDelStatusName}</td>

                                    <td>

									    <c:if test="${canManageCust}">
									
									        <a href="/cust/custUpdateForm?cust_code=${c.custCode}"
									           class="cust-btn btn-edit">
									            수정
									        </a>
									
									        <c:if test="${c.custDelStatus != 1}">
									            <a href="/cust/active?cust_code=${c.custCode}"
									               class="cust-btn btn-active"
									               onclick="return confirm('사용 처리하시겠습니까?');">
									                사용
									            </a>
									        </c:if>
									
									        <c:if test="${c.custDelStatus != 2}">
									            <a href="/cust/stop?cust_code=${c.custCode}"
									               class="cust-btn btn-stop"
									               onclick="return confirm('거래정지 처리하시겠습니까?');">
									                거래정지
									            </a>
									        </c:if>
									
									        <c:if test="${c.custDelStatus != 9}">
									            <a href="/cust/inactive?cust_code=${c.custCode}"
									               class="cust-btn btn-inactive"
									               onclick="return confirm('사용중지 처리하시겠습니까?');">
									                사용중지
									            </a>
									        </c:if>
									
									    </c:if>
									
									</td>
                                </tr>

                            </c:forEach>

                        </c:otherwise>

                    </c:choose>

                </tbody>
            </table>

            <c:if test="${not empty custList}">

                <div class="paging">

                    <c:if test="${startPage > 1}">
                        <a href="/cust/custList?currentPage=${startPage - 1}&keyword=${keyword}&searchType=${searchType}&custType=${custType}">
                            이전
                        </a>
                    </c:if>

                    <c:forEach var="i" begin="${startPage}" end="${endPage}">
                        <a href="/cust/custList?currentPage=${i}&keyword=${keyword}&searchType=${searchType}&custType=${custType}"
                           class="${currentPage == i ? 'active' : ''}">
                            ${i}
                        </a>
                    </c:forEach>

                    <c:if test="${endPage < pageCount}">
                        <a href="/cust/custList?currentPage=${endPage + 1}&keyword=${keyword}&searchType=${searchType}&custType=${custType}">
                            다음
                        </a>
                    </c:if>

                </div>

            </c:if>

        </div>

        </main>
    </div>

</div>

<%@ include file="../common/footer.jsp" %>

</body>
</html>
