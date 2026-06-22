<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>제품 수정</title>

<%@ include file="../common/staticResources.jsp" %>

<style>
    .product-form-container {
        width: 600px;
        background: white;
        padding: 35px;
        border-radius: 12px;
        box-shadow: 0 4px 15px rgba(0,0,0,0.1);
    }

    h2 {
        color: #2c3e50;
        text-align: center;
        margin-bottom: 30px;
    }

    .form-group {
        margin-bottom: 18px;
    }

    .product-form label {
        display: block;
        margin-bottom: 7px;
        font-weight: bold;
        color: #34495e;
    }

    .product-form input,
    .product-form select {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 6px;
        box-sizing: border-box;
    }

    .readonly {
        background-color: #eee;
    }

    .help-text {
        font-size: 12px;
        color: #888;
        margin-top: 5px;
    }

    .btn-area {
        text-align: center;
        margin-top: 30px;
    }

    .product-form button,
    .product-form .btn-link {
        text-decoration: none;
        border: none;
        padding: 10px 22px;
        border-radius: 6px;
        color: white;
        font-size: 14px;
        cursor: pointer;
        display: inline-block;
    }

    .btn-update {
        background-color: #f39c12;
    }

    .btn-update:hover {
        background-color: #d68910;
    }

    .btn-list {
        background-color: #7f8c8d;
    }

    .btn-list:hover {
        background-color: #636e72;
    }
</style>
</head>

<body>

<%@ include file="../common/header.jsp" %>

<div id="wrapper">

    <%@ include file="../common/sidebar.jsp" %>

    <div id="page-content-wrapper">
        <main class="content p-4">

        <div class="product-form-container mx-auto">

            <h2>제품 수정</h2>

            <form action="/product/update" method="post" class="product-form">

                <div class="form-group">
                    <label>제품 코드</label>

                    <input type="number"
                           name="productCode"
                           value="${product.product_code}"
                           readonly
                           class="readonly">
                </div>

                <div class="form-group">
                    <label>제품명</label>

                    <input type="text"
                           name="productName"
                           value="${product.product_name}"
                           required
                           maxlength="100"
                           placeholder="제품명을 입력하세요">
                </div>

                <div class="form-group">
                    <label>카테고리</label>

                    <select name="productCategory" required>
                        <c:forEach var="code" items="${productCategoryList}">
                            <c:if test="${code.mcode != 999}">
                                <option value="${code.mcode}"
                                    ${product.product_category == code.mcode ? 'selected' : ''}>
                                    ${code.code_contents}
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>제품 상태</label>

                    <select name="productStatus" required>
                        <option value="0"
                            ${product.product_status == 0 ? 'selected' : ''}>
                            부품
                        </option>

                        <option value="1"
                            ${product.product_status == 1 ? 'selected' : ''}>
                            완제품
                        </option>
                    </select>
                </div>

                <div class="form-group">
                    <label>제품 가격</label>

                    <input type="number"
                           name="productPrice"
                           value="${product.product_price}"
                           required
                           min="0"
                           max="999999999"
                           step="1"
                           placeholder="제품 가격을 입력하세요">
                    <div class="help-text">0 이상 숫자만 입력</div>
                </div>

                <div class="form-group">
                    <label>적정 수량</label>

                    <input type="number"
                           name="productProperQty"
                           value="${product.product_proper_qty}"
                           required
                           min="0"
                           max="9999999"
                           step="1"
                           placeholder="적정 수량을 입력하세요">
                    <div class="help-text">0 이상 숫자만 입력</div>
                </div>

                <div class="form-group">
                    <label>사용 상태</label>

                    <select name="productDelStatus" required>
                        <c:forEach var="code" items="${productStatusList}">
                            <c:if test="${code.mcode != 999}">
                                <option value="${code.mcode}"
                                    ${product.product_del_status == code.mcode ? 'selected' : ''}>
                                    ${code.code_contents}
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
				    <label>등록 사원 번호</label>
				
				    <input type="text"
				           value="${product.reg_emp_no}"
				           readonly>
				
				    <div class="help-text">
				        등록 사원 번호는 수정할 수 없습니다.
				    </div>
				</div>

                <div class="btn-area">

                    <button type="submit" class="btn-update">
                        수정
                    </button>

                    <a href="/product/list" class="btn-link btn-list">
                        목록
                    </a>

                </div>

            </form>

        </div>

        </main>
    </div>

</div>

<%@ include file="../common/footer.jsp" %>

</body>
</html>
