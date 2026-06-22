<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의 게시판</title>
<jsp:include page="/WEB-INF/views/common/staticResources.jsp"/>
<style>
    a:visited {
        color: purple !important;
    }
    .navbar-brand:visited {
        color: white !important;
    }
    .btn-primary:visited {
        color: white !important;
    }
    .page-link:visited {
    	color: inherit !important;
	}
	.page-item.active .page-link {
	    color: white !important;
	}
</style>
</head>
<body class="d-flex flex-column min-vh-100">

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div id="wrapper">
    <jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>
    <div id="page-content-wrapper">
        <div class="container p-4">
        <h2 class="mb-4">문의 게시판</h2>

        <div class="mb-3 text-end">
            <a href="${pageContext.request.contextPath}/board/write" class="btn btn-primary btn-sm text-white">
                <i class="bi bi-pencil-square me-1"></i> 문의 등록
            </a>
        </div>
        
        <!-- 검색 폼 -->
		<form action="${pageContext.request.contextPath}/board/list" method="get" class="d-flex gap-2 mb-3 align-items-center">
		    <select name="searchType" class="form-select w-auto">
		        <option value="all" ${dto.searchType == 'all' ? 'selected' : ''}>제목+작성자</option>
		        <option value="title" ${dto.searchType == 'title' ? 'selected' : ''}>제목</option>
		        <option value="writer" ${dto.searchType == 'writer' ? 'selected' : ''}>작성자</option>
		    </select>
		    <select name="answerFilter" class="form-select w-auto">
			    <option value="">전체</option>
			    <option value="done" ${dto.answerFilter == 'done' ? 'selected' : ''}>답변완료</option>
			    <option value="wait" ${dto.answerFilter == 'wait' ? 'selected' : ''}>미답변</option>
			</select>
		    <input type="text" name="searchKeyword" class="form-control" value="${dto.searchKeyword}" placeholder="검색어를 입력하세요">
		    <button type="submit" class="btn btn-primary text-white" style="white-space: nowrap;">검색</button>
		</form>

        <table class="table table-bordered table-hover text-center align-middle">
            <thead class="table-dark">
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>등록일</th>
                    <th>조회수</th>
                </tr>
            </thead>
            <tbody>
			    <c:choose>
			        <c:when test="${empty list}">
			            <tr>
			                <td colspan="5" class="text-center py-5">
			                    <i class="bi bi-exclamation-circle me-2"></i>등록된 문의가 없습니다.
			                </td>
			            </tr>
			        </c:when>
			        <c:otherwise>
			            <c:forEach var="board" items="${list}" varStatus="status">
			            <tr>
			                <td>${page.total - (page.currentPage - 1) * page.rowPage - status.index}</td>
			                <td class="text-start">
							    <div style="display: flex; align-items: center;">
							        <a href="${pageContext.request.contextPath}/board/detail/${board.boardCode}?currentPage=${page.currentPage}&searchType=${dto.searchType}&searchKeyword=${dto.searchKeyword}&answerFilter=${dto.answerFilter}" class="text-decoration-none text-dark">
							            ${board.boardTitle.length() > 20 ? board.boardTitle.substring(0, 20).concat('...') : board.boardTitle}
							        </a>
							        <c:choose>
							            <c:when test="${board.answerCount > 0}">
							                <span class="badge bg-success ms-1">답변완료</span>
							            </c:when>
							            <c:otherwise>
							                <span class="badge bg-secondary ms-1">미답변</span>
							            </c:otherwise>
							        </c:choose>
							    </div>
							</td>
			                <td>${board.empName}</td>
			                <td><fmt:formatDate value="${board.regDate}" pattern="yyyy-MM-dd a h:mm:ss"/></td>
			                <td>${board.boardReadCount}</td>
			            </tr>
			            </c:forEach>
			        </c:otherwise>
			    </c:choose>
			</tbody>
        </table>
        
        <!-- 페이징 -->
		<div class="d-flex justify-content-center mt-3">
		    <ul class="pagination">
		        <!-- 이전 블록 -->
		        <c:if test="${page.startPage > 1}">
		            <li class="page-item">
		                <a class="page-link" href="${pageContext.request.contextPath}/board/list?currentPage=${page.startPage - 1}&searchType=${dto.searchType}&searchKeyword=${dto.searchKeyword}">«</a>
		            </li>
		        </c:if>
		
		        <!-- 페이지 번호 -->
		        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
		            <li class="page-item ${page.currentPage == i ? 'active' : ''}">
		                <a class="page-link" href="${pageContext.request.contextPath}/board/list?currentPage=${i}&searchType=${dto.searchType}&searchKeyword=${dto.searchKeyword}">${i}</a>
		            </li>
		        </c:forEach>
		
		        <!-- 다음 블록 -->
		        <c:if test="${page.endPage < page.totalPage}">
		            <li class="page-item">
		                <a class="page-link" href="${pageContext.request.contextPath}/board/list?currentPage=${page.endPage + 1}&searchType=${dto.searchType}&searchKeyword=${dto.searchKeyword}">»</a>
		            </li>
		        </c:if>
		    </ul>
		</div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>