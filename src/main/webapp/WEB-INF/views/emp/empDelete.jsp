<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>사원 삭제</title>
    <%@ include file="../common/staticResources.jsp" %>
</head>
<body>
    <%@ include file="../common/header.jsp" %>

    <div id="wrapper">
        <%@ include file="../common/sidebar.jsp" %>

        <div id="page-content-wrapper">
        
            <main class="content p-4">
			    <h5>emp 삭제</h5>
			    <form action="/empDelete" method="post">
			        <label for="empId">사원 ID:</label>
			        <input type="text" id="empId" name="empId" required>
			        <button type="submit">삭제</button>
			    </form>
			</main>            
        </div>
    </div>

    <%@ include file="../common/footer.jsp" %>
</body>
</html>
