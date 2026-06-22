<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의 등록</title>
<jsp:include page="/WEB-INF/views/common/staticResources.jsp"/>
</head>
<body class="d-flex flex-column min-vh-100">

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div id="wrapper">
    <jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>
    <div id="page-content-wrapper">
        <div class="container p-4">
        <h2 class="mb-4">문의 등록</h2>

        <form action="${pageContext.request.contextPath}/board/write" method="post">
			<!-- <input type="hidden" name="regEmpNo" value="1001"> -->
			<input type="hidden" name="regEmpNo" value="${loginUser.emp_no}">
			
			
            <div class="mb-3">
                <label class="form-label fw-bold">제목</label>
                <input type="text" name="boardTitle" class="form-control" placeholder="제목을 입력하세요" required>
            </div>

            <div class="mb-3">
                <label class="form-label fw-bold">내용</label>
                <textarea name="boardContents" class="form-control" rows="10" placeholder="내용을 입력하세요" required></textarea>
            </div>

            <div class="d-flex gap-2 justify-content-end">
                <a href="${pageContext.request.contextPath}/board/list" class="btn btn-secondary">
                    <i class="bi bi-arrow-left me-1"></i> 목록으로
                </a>
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-check-lg me-1"></i> 등록
                </button>
            </div>
        </form>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>