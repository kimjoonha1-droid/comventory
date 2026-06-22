<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>제품 관리 메인</title>

<%@ include file="../common/staticResources.jsp" %>

<style>

    .product-main-container {
        width: 900px;
        background: white;
        padding: 40px;
        border-radius: 12px;
        box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        text-align: center;
    }

    h1 {
        color: #2c3e50;
        margin-bottom: 10px;
    }

    .subtitle {
        color: #777;
        margin-bottom: 40px;
    }

    .menu-box {
        display: flex;
        justify-content: center;
        gap: 30px;
    }

    .product-menu-card {
        width: 250px;
        padding: 30px 20px;
        border-radius: 10px;
        background-color: #eef3f8;
        transition: 0.2s;
    }

    .product-menu-card:hover {
        transform: translateY(-5px);
        background-color: #dfeaf5;
    }

    .product-menu-card h3 {
        color: #34495e;
        margin-bottom: 20px;
    }

    .product-menu-card a {
        display: inline-block;
        text-decoration: none;
        background-color: #3498db;
        color: white;
        padding: 10px 25px;
        border-radius: 6px;
        font-weight: bold;
    }

    .product-menu-card a:hover {
        background-color: #2980b9;
    }
</style>
</head>

<body>

<%@ include file="../common/header.jsp" %>

<div id="wrapper">

    <%@ include file="../common/sidebar.jsp" %>

	<div id="page-content-wrapper">
	    <main class="content p-4">
	        <div class="product-main-container mx-auto">
	
	            <h1>제품 관리</h1>
	
	            <p class="subtitle">
	                제품 등록, 조회, 수정, 삭제를 관리하는 화면입니다.
	            </p>
	
	            <div class="menu-box">
	
	                <div class="product-menu-card">
	                    <h3>제품 등록</h3>
	                    <a href="/product/saveForm">등록하기</a>
	                </div>
	
	                <div class="product-menu-card">
	                    <h3>제품 목록</h3>
	                    <a href="/product/list">목록보기</a>
	                </div>
	
	            </div>

        	</div>
		</main>
    </div>

</div>

<%@ include file="../common/footer.jsp" %>

</body>
</html>