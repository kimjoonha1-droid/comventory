<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 1. 외부 라이브러리 (부트스트랩) -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">

<!-- 2. 내 커스텀 CSS (src/main/resources/static/css/style.css) -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

<!-- 3. 외부 라이브러리 JS (부트스트랩 번들) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<!-- 4. 내 커스텀 JS (src/main/resources/static/js/ 하위 파일들) -->
<script src="${pageContext.request.contextPath}/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/js/test.js"></script>