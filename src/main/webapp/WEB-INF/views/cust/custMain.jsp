<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>거래처 관리 메인</title>

<%@ include file="../common/staticResources.jsp" %>

<style>

	.cust-main-container {
	    width: 900px;
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

	.cust-menu-card {
	    width: 250px;
	    padding: 30px 20px;
	    border-radius: 10px;
	    background-color: #eef3f8;
	    transition: 0.2s;
	}
	
	.cust-menu-card:hover {
	    transform: translateY(-5px);
	    background-color: #dfeaf5;
	}
	
	.cust-menu-card h3 {
	    color: #34495e;
	    margin-bottom: 20px;
	}
	
	.cust-menu-card a {
	    display: inline-block;
	    text-decoration: none;
	    background-color: #3498db;
	    color: white;
	    padding: 10px 25px;
	    border-radius: 6px;
	    font-weight: bold;
	}

    .cust-menu-card a:hover {
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
	        <div class="cust-main-container mx-auto">
	
	            <h1>거래처 관리</h1>
	            <p class="subtitle">
	                거래처 등록, 조회, 수정, 삭제를 관리하는 화면입니다.
	            </p>
	
	            <div class="menu-box">
	
	                <div class="cust-menu-card">
	                    <h3>거래처 등록</h3>
	                    <a href="/cust/custSaveForm">등록하기</a>
	                </div>
	
	                <div class="cust-menu-card">
	                    <h3>거래처 목록</h3>
	                    <a href="/cust/custList">목록보기</a>
	                </div>
	
	            </div>

        	</div>
		</main>
    </div>

</div>

<%@ include file="../common/footer.jsp" %>

</body>
</html>