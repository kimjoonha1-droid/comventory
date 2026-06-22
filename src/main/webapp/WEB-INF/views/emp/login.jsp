<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 화면</title>
<style>
    body {
        margin: 0;
        font-family: Arial, sans-serif;
        color: #4b5563;
        background: #fff;
    }

    .top-bar {
        height: 67px;
        background: #2f69b1;
    }

    .login-wrap {
        min-height: calc(100vh - 67px);
        display: flex;
        align-items: center;
        justify-content: center;
        padding-bottom: 80px;
    }

    .login-box {
        width: 650px;
        padding: 28px 30px 36px;
        border: 1px solid #cfd6e2;
        border-radius: 4px;
        box-shadow: 0 3px 0 #d9dde5;
        box-sizing: border-box;
    }

    .login-title {
        margin: 0 0 18px;
        padding-bottom: 14px;
        border-bottom: 1px solid #dde3ec;
        text-align: center;
        font-size: 38px;
        font-weight: 700;
        color: #4b5563;
    }

    .form-row {
        display: flex;
        align-items: center;
        margin-bottom: 14px;
    }

    .form-row label {
        width: 125px;
        padding-right: 24px;
        text-align: right;
        font-size: 22px;
        font-weight: 700;
    }

    .form-row input {
        flex: 1;
        height: 52px;
        padding: 0 16px;
        border: 1px solid #c6cfdd;
        border-radius: 4px;
        font-size: 22px;
        box-sizing: border-box;
    }

    .btn-row {
        display: flex;
        justify-content: center;
        gap: 14px;
        margin-top: 32px;
    }

    .btn-row button,
    .btn-row a {
        width: 152px;
        height: 58px;
        border: 0;
        border-radius: 5px;
        color: white;
        font-size: 26px;
        font-weight: 700;
        text-align: center;
        line-height: 58px;
        text-decoration: none;
        cursor: pointer;
    }

    .btn-login { background: #1f65b8; }
    .btn-join { background: #43a83f; }
    .btn-cancel { background: #566171; }

    .error-msg {
        margin: 12px 0 0 150px;
        color: #d93025;
        font-size: 15px;
    }
</style>
</head>
<body>
    <div class="top-bar"></div>

    <div class="login-wrap">
        <form class="login-box" action="${pageContext.request.contextPath}/login" method="post">
            <h1 class="login-title">로그인 화면</h1>

            <div class="form-row">
                <label for="empId">ID</label>
                <input type="text" id="empId" name="empId" placeholder="ID 입력" required>
            </div>

            <div class="form-row">
                <label for="empPassword">Password</label>
                <input type="password" id="empPassword" name="empPassword" placeholder="비밀번호 입력" required>
            </div>

            <c:if test="${not empty loginError}">
                <div class="error-msg">${loginError}</div>
            </c:if>

            <div class="btn-row">
                <button type="submit" class="btn-login">로그인</button>
                <button type="reset" class="btn-cancel">다시입력</button>
            </div>
        </form>
    </div>
</body>
</html>
