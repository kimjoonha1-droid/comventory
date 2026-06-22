<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt"
    uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>제품 목록</title>

<%@ include file="../common/staticResources.jsp" %>

<style>
    .product-list-container {
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

    .product-btn {
        text-decoration: none;
        padding: 8px 15px;
        border-radius: 5px;
        font-size: 14px;
        color: white;
        display: inline-block;
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

    .btn-delete {
        background-color: #e74c3c;
    }

    .btn-detail {
        background-color: #16a085;
        border: none;
        cursor: pointer;
        margin-left: 6px;
        padding: 5px 9px;
        font-size: 12px;
        color: white;
        border-radius: 5px;
    }

    .btn-search {
        background-color: #2c3e50;
        border: none;
        cursor: pointer;
    }

    .btn-reset {
        background-color: #95a5a6;
    }

    .search-box {
        margin-bottom: 20px;
        padding: 15px;
        background-color: #f8f9fa;
        border-radius: 8px;
        display: flex;
        gap: 10px;
        align-items: center;
        flex-wrap: wrap;
    }

    .search-box input,
    .search-box select {
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 5px;
        height: 38px;
        box-sizing: border-box;
    }

    .search-box input {
        width: 220px;
    }

    table {
        width: 100%;
        border-collapse: collapse;
    }

    th,
    td {
        padding: 12px;
        text-align: center;
        border-bottom: 1px solid #ddd;
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

    th:nth-child(4),
    td:nth-child(4) {
        width: 90px;
        white-space: nowrap;
    }

    mark {
        background-color: #fff3cd;
        color: #c0392b;
        padding: 2px 4px;
        border-radius: 4px;
        font-weight: bold;
    }

    .modal {
        display: none;
        position: fixed;
        z-index: 9999;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0,0,0,0.45);
    }

    .modal-content {
        background-color: white;
        margin: 4% auto;
        padding: 25px;
        width: 780px;
        border-radius: 12px;
        box-shadow: 0 5px 20px rgba(0,0,0,0.3);
    }

    .modal-close {
        float: right;
        font-size: 30px;
        font-weight: bold;
        cursor: pointer;
    }

    .bom-table {
        width: 100%;
        margin-top: 20px;
        border-collapse: collapse;
    }

    .bom-table th {
        background-color: #2c3e50;
        color: white;
        padding: 10px;
        white-space: nowrap;
    }

    .bom-table td {
        padding: 10px;
        border-bottom: 1px solid #ddd;
    }

    .bom-category {
        display: inline-block;
        background-color: #ecf0f1;
        color: #2c3e50;
        padding: 4px 10px;
        border-radius: 30px;
        font-size: 12px;
        font-weight: bold;
    }

    .bom-name {
        text-align: left;
        max-width: 320px;
        word-break: break-word;
        line-height: 1.5;
    }

    .bom-qty {
        font-weight: bold;
        color: #16a085;
    }

    .loading-text,
    .empty-text,
    .error-text {
        text-align: center;
        padding: 25px;
        font-weight: bold;
    }

    .loading-text {
        color: #3498db;
    }

    .empty-text {
        color: #7f8c8d;
    }

    .error-text {
        color: #e74c3c;
    }

    .modal-content h3 {
        margin-bottom: 20px;
        color: #2c3e50;
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

        <div class="product-list-container">

            <h2>제품 목록</h2>

            <div class="top-btn">

                <c:if test="${canManageProduct}">
				    <a href="/product/saveForm" class="product-btn btn-add">
				        + 제품 등록
				    </a>
				</c:if>
                <a href="/product/productMain" class="product-btn btn-main">
                    메인
                </a>

            </div>

            <form action="/product/list" method="get" class="search-box">

                <input type="text"
                       name="keyword"
                       placeholder="제품명 검색"
                       value="${productDto.keyword}">

                <select name="searchCategory">

                    <option value="0">카테고리 전체</option>

                    <option value="1" ${productDto.searchCategory == 1 ? 'selected' : ''}>CPU</option>
                    <option value="2" ${productDto.searchCategory == 2 ? 'selected' : ''}>RAM</option>
                    <option value="3" ${productDto.searchCategory == 3 ? 'selected' : ''}>SSD</option>
                    <option value="4" ${productDto.searchCategory == 4 ? 'selected' : ''}>HDD</option>
                    <option value="5" ${productDto.searchCategory == 5 ? 'selected' : ''}>GPU</option>
                    <option value="6" ${productDto.searchCategory == 6 ? 'selected' : ''}>파워</option>
                    <option value="7" ${productDto.searchCategory == 7 ? 'selected' : ''}>메인보드</option>
                    <option value="8" ${productDto.searchCategory == 8 ? 'selected' : ''}>사운드카드</option>
                    <option value="9" ${productDto.searchCategory == 9 ? 'selected' : ''}>쿨러</option>
                    <option value="10" ${productDto.searchCategory == 10 ? 'selected' : ''}>ODD</option>

                    <option value="101" ${productDto.searchCategory == 101 ? 'selected' : ''}>케이스</option>
                    <option value="102" ${productDto.searchCategory == 102 ? 'selected' : ''}>모니터</option>
                    <option value="103" ${productDto.searchCategory == 103 ? 'selected' : ''}>마우스</option>

                    <option value="201" ${productDto.searchCategory == 201 ? 'selected' : ''}>스피커</option>
                    <option value="202" ${productDto.searchCategory == 202 ? 'selected' : ''}>헤드셋</option>
                    <option value="203" ${productDto.searchCategory == 203 ? 'selected' : ''}>이어폰</option>
                    <option value="204" ${productDto.searchCategory == 204 ? 'selected' : ''}>마이크</option>

                    <option value="701" ${productDto.searchCategory == 701 ? 'selected' : ''}>노트북1</option>
                    <option value="702" ${productDto.searchCategory == 702 ? 'selected' : ''}>노트북2</option>
                    <option value="703" ${productDto.searchCategory == 703 ? 'selected' : ''}>노트북3</option>

                    <option value="801" ${productDto.searchCategory == 801 ? 'selected' : ''}>사무용1</option>
                    <option value="802" ${productDto.searchCategory == 802 ? 'selected' : ''}>사무용2</option>
                    <option value="803" ${productDto.searchCategory == 803 ? 'selected' : ''}>사무용3</option>

                    <option value="901" ${productDto.searchCategory == 901 ? 'selected' : ''}>게이밍1</option>
                    <option value="902" ${productDto.searchCategory == 902 ? 'selected' : ''}>게이밍2</option>
                    <option value="903" ${productDto.searchCategory == 903 ? 'selected' : ''}>게이밍3</option>

                </select>

                <select name="searchDelStatus">

                    <option value="0">사용상태 전체</option>

                    <option value="1" ${productDto.searchDelStatus == 1 ? 'selected' : ''}>
                        사용
                    </option>

                    <option value="3" ${productDto.searchDelStatus == 3 ? 'selected' : ''}>
                        대체예정
                    </option>

                </select>

                <select name="sortType" onchange="this.form.submit()">

                    <option value="">기본 정렬</option>

                    <option value="priceAsc"
                        ${productDto.sortType == 'priceAsc' ? 'selected' : ''}>
                        가격 낮은순
                    </option>

                    <option value="priceDesc"
                        ${productDto.sortType == 'priceDesc' ? 'selected' : ''}>
                        가격 높은순
                    </option>

                    <option value="codeDesc"
                        ${productDto.sortType == 'codeDesc' ? 'selected' : ''}>
                        최신 등록순
                    </option>

                </select>

                <div style="display:flex; align-items:center; width:100%;">

                    <div style="display:flex; gap:10px;">

                        <button type="submit" class="product-btn btn-search">
                            검색
                        </button>

                        <a href="/product/list" class="product-btn btn-reset">
                            초기화
                        </a>

                    </div>

                    <div style="margin-left:auto; display:flex; gap:8px;">

                        <a href="/product/list?keyword=${productDto.keyword}&searchCategory=${productDto.searchCategory}&searchDelStatus=${productDto.searchDelStatus}&sortType=${productDto.sortType}"
                           class="product-btn"
                           style="background-color:#7f8c8d;">
                            전체
                        </a>

                        <a href="/product/list?keyword=${productDto.keyword}&searchCategory=${productDto.searchCategory}&searchDelStatus=${productDto.searchDelStatus}&productStatus=1&sortType=${productDto.sortType}"
                           class="product-btn"
                           style="background-color:#3498db;">
                            완제품
                        </a>

                        <a href="/product/list?keyword=${productDto.keyword}&searchCategory=${productDto.searchCategory}&searchDelStatus=${productDto.searchDelStatus}&productStatus=0&sortType=${productDto.sortType}"
                           class="product-btn"
                           style="background-color:#27ae60;">
                            부품
                        </a>

                    </div>

                </div>

            </form>

            <table>

                <thead>
                    <tr>
                        <th>코드</th>
                        <th>제품명</th>
                        <th>카테<br>고리</th>
                        <th>상태</th>
                        <th>가격</th>
                        <th>적정<br>수량</th>
                        <th>사용<br>상태</th>
                        <th>관리</th>
                    </tr>
                </thead>

                <tbody>

                    <c:choose>

                        <c:when test="${empty productList}">

                            <tr>
                                <td colspan="8" class="empty">
                                    검색된 제품이 없습니다.
                                </td>
                            </tr>

                        </c:when>

                        <c:otherwise>

                            <c:forEach var="p" items="${productList}">

                                <tr>

                                    <td>${p.productCode}</td>

                                    <td>
                                        <span class="product-name-text">${p.productName}</span>

                                        <c:if test="${p.productStatus == 1}">
                                            <button type="button"
                                                    class="product-btn btn-detail"
                                                    onclick="openBomModal('${p.productCode}', '${p.productName}')">
                                                상세정보
                                            </button>
                                        </c:if>
                                    </td>

                                    <td>${p.productCategoryName}</td>

                                    <td>
                                        <c:choose>
                                            <c:when test="${p.productStatus == 0}">
                                                부품
                                            </c:when>
                                            <c:when test="${p.productStatus == 1}">
                                                완제품
                                            </c:when>
                                            <c:otherwise>
                                                -
                                            </c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td>
                                        <fmt:formatNumber value="${p.productPrice}" pattern="#,###" />원
                                    </td>

                                    <td>
                                        <fmt:formatNumber value="${p.productProperQty}" pattern="#,###" />
                                    </td>

                                    <td>${p.productDelStatusName}</td>

                                    <td>

                                        <c:if test="${canManageProduct}">

										    <a href="/product/updateForm?product_code=${p.productCode}"
										       class="product-btn btn-edit">
										        수정
										    </a>
										
										    <a href="/product/delete?product_code=${p.productCode}"
										       class="product-btn btn-delete"
										       onclick="return confirm('단종 처리하시겠습니까?');">
										        삭제
										    </a>
										
										</c:if>

                                    </td>

                                </tr>

                            </c:forEach>

                        </c:otherwise>

                    </c:choose>

                </tbody>

            </table>

            <c:if test="${not empty productList}">

                <div class="paging">

                    <c:if test="${startPage > 1}">
                        <a href="/product/list?currentPage=${startPage - 1}&keyword=${productDto.keyword}&searchCategory=${productDto.searchCategory}&searchDelStatus=${productDto.searchDelStatus}&productStatus=${productDto.productStatus}&sortType=${productDto.sortType}">
                            이전
                        </a>
                    </c:if>

                    <c:forEach var="i" begin="${startPage}" end="${endPage}">

                        <a href="/product/list?currentPage=${i}&keyword=${productDto.keyword}&searchCategory=${productDto.searchCategory}&searchDelStatus=${productDto.searchDelStatus}&productStatus=${productDto.productStatus}&sortType=${productDto.sortType}"
                           class="${currentPage == i ? 'active' : ''}">
                            ${i}
                        </a>

                    </c:forEach>

                    <c:if test="${endPage < pageCount}">
                        <a href="/product/list?currentPage=${endPage + 1}&keyword=${productDto.keyword}&searchCategory=${productDto.searchCategory}&searchDelStatus=${productDto.searchDelStatus}&productStatus=${productDto.productStatus}&sortType=${productDto.sortType}">
                            다음
                        </a>
                    </c:if>

                </div>

            </c:if>

        </div>

        </main>
    </div>

</div>

<div id="bomModal" class="modal">

    <div class="modal-content">

        <span class="modal-close" onclick="closeBomModal()">&times;</span>

        <h3 id="bomModalTitle">BOM 상세정보</h3>

        <table class="bom-table">

            <thead>
                <tr>
                    <th>부품<br>분류</th>
                    <th>부품명</th>
                    <th>필요<br>수량</th>
                </tr>
            </thead>

            <tbody id="bomModalBody">
            </tbody>

        </table>

    </div>

</div>

<%@ include file="../common/footer.jsp" %>

<script>
    function openBomModal(productCode, productName) {

        document.getElementById("bomModalTitle").innerText =
            productName + " BOM";

        document.getElementById("bomModalBody").innerHTML =
            "<tr><td colspan='3' class='loading-text'>BOM 정보를 불러오는 중입니다...</td></tr>";

        fetch('/product/bomDetail?productCode=' + productCode)
            .then(response => response.json())
            .then(data => {

                let html = "";

                if (data.length === 0) {

                    html =
                        "<tr><td colspan='3' class='empty-text'>등록된 부품 정보가 없습니다.</td></tr>";

                } else {

                    data.forEach(function(item) {

                        html += "<tr>";

                        html +=
                            "<td>" +
                            "<span class='bom-category'>" +
                            (item.productCategoryName || "-") +
                            "</span>" +
                            "</td>";

                        html +=
                            "<td class='bom-name'>" +
                            (item.productWonName || "-") +
                            "</td>";

                        html +=
                            "<td class='bom-qty'>" +
                            (item.needQty || 0) +
                            "개</td>";

                        html += "</tr>";
                    });

                }

                document.getElementById("bomModalBody").innerHTML = html;

            })
            .catch(error => {

                document.getElementById("bomModalBody").innerHTML =
                    "<tr><td colspan='3' class='error-text'>BOM 정보를 불러오지 못했습니다.</td></tr>";

            });

        document.getElementById("bomModal").style.display = "block";
    }

    function closeBomModal() {
        document.getElementById("bomModal").style.display = "none";
    }

    window.onclick = function(event) {

        const modal = document.getElementById("bomModal");

        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    function escapeRegex(text) {
        return text.replace(/[-\/\\^$*+?.()|[\]{}]/g, "\\$&");
    }

    const keyword = "${productDto.keyword}";

    if (keyword && keyword.trim() !== "") {

        const regex = new RegExp("(" + escapeRegex(keyword.trim()) + ")", "gi");
        const productNames = document.querySelectorAll(".product-name-text");

        productNames.forEach(function(span) {

            const originalText = span.textContent;

            span.innerHTML = originalText.replace(regex, "<mark>$1</mark>");

        });
    }
</script>

</body>
</html>
