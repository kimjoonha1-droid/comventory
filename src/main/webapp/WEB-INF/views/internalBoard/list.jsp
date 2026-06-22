<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>직원 게시판</title>
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
        <h2 class="mb-4">직원 게시판</h2>

        <div class="mb-3 text-end">
            <a href="${pageContext.request.contextPath}/internal/write" class="btn btn-primary btn-sm text-white">
                <i class="bi bi-pencil-square me-1"></i> 게시글 등록
            </a>
        </div>
        
        <!-- 검색 폼 -->
		<form action="${pageContext.request.contextPath}/internal/list" method="get" class="d-flex gap-2 mb-3 align-items-center">
		    <select name="searchType" class="form-select w-auto">
		        <option value="all" ${dto.searchType == 'all' ? 'selected' : ''}>제목+작성자</option>
		        <option value="title" ${dto.searchType == 'title' ? 'selected' : ''}>제목</option>
		        <option value="writer" ${dto.searchType == 'writer' ? 'selected' : ''}>작성자</option>
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
		    <!-- 공지사항 고정 표시 -->
		    <c:forEach var="notice" items="${noticeList}" varStatus="nStatus">
		        <tr class="notice-row ${nStatus.index >= 3 ? 'notice-hidden' : ''}" 
		            style="${nStatus.index >= 3 ? 'display:none;' : ''}">
		            <td><span class="badge bg-danger">공지</span></td>
		            <td class="text-start">
					    <div style="display: flex; align-items: center;">
					        <a href="${pageContext.request.contextPath}/internal/detail/${notice.boardCode}" class="text-decoration-none text-dark">
					            ${notice.boardTitle.length() > 20 ? notice.boardTitle.substring(0, 20).concat('...') : notice.boardTitle}
					        </a>
					        <c:if test="${notice.replyCount > 0}">
					            <span class="badge bg-primary ms-1">${notice.replyCount}</span>
					        </c:if>
					    </div>
					</td>
		            <td>${notice.empName}</td>
		            <td><fmt:formatDate value="${notice.regDate}" pattern="yyyy-MM-dd a h:mm:ss"/></td>
		            <td>${notice.boardReadCount}</td>
		        </tr>
		    </c:forEach>
		
		    <!-- 더보기 버튼 -->
		    <c:if test="${noticeList.size() > 3}">
		        <tr id="noticeMoreBtn">
		            <td colspan="5" class="text-center">
		                <button class="btn btn-sm btn-outline-secondary" onclick="toggleNotice()">▼ 공지 더보기</button>
		            </td>
		        </tr>
		    </c:if>
		
		    <!-- 구분선 -->
		    <tr class="table-secondary">
		        <td colspan="5"></td>
		    </tr>
		
		    <!-- 일반 게시글 -->
		    <c:choose>
		        <c:when test="${empty list}">
		            <tr>
		                <td colspan="5" class="text-center py-5">
		                    <i class="bi bi-exclamation-circle me-2"></i>등록된 게시글이 없습니다.
		                </td>
		            </tr>
		        </c:when>
		        <c:otherwise>
		            <c:forEach var="board" items="${list}" varStatus="status">
		            <tr>
		                <td>${page.total - (page.currentPage - 1) * page.rowPage - status.index}</td>
		                <td class="text-start">
		                    <div style="display: flex; align-items: center;">
		                        <a href="${pageContext.request.contextPath}/internal/detail/${board.boardCode}?currentPage=${page.currentPage}&searchType=${dto.searchType}&searchKeyword=${dto.searchKeyword}&answerFilter=${dto.answerFilter}" class="text-decoration-none text-dark">
		                            ${board.boardTitle.length() > 20 ? board.boardTitle.substring(0, 20).concat('...') : board.boardTitle}
		                        </a>
		                        <c:if test="${board.replyCount > 0}">
		                            <span class="badge bg-primary ms-1">${board.replyCount}</span>
		                        </c:if>
		                    </div>
		                </td>
		                <td>${board.empName}</td>
		                <td><fmt:formatDate value="${board.regDate}" pattern="yyyy-MM-dd a h:mm:ss"/></td>
		                <td>${board.boardReadCount}</td>
		            </tr>
		            </c:forEach>
		        </c:otherwise>
		    </c:choose>
		    <script>
			function toggleNotice() {
			    var rows = document.querySelectorAll('.notice-hidden');
			    var btn = document.getElementById('noticeMoreBtn').querySelector('button');
			    var hidden = rows[0] && rows[0].style.display === 'none';
			    rows.forEach(function(row) {
			        row.style.display = hidden ? '' : 'none';
			    });
			    btn.textContent = hidden ? '▲ 공지 접기' : '▼ 공지 더보기';
			}
			</script>
		</tbody>
        </table>
        
        <!-- 페이징 -->
		<div class="d-flex justify-content-center mt-3">
		    <ul class="pagination">
		        <!-- 이전 블록 -->
		        <c:if test="${page.startPage > 1}">
		            <li class="page-item">
		                <a class="page-link" href="${pageContext.request.contextPath}/internal/list?currentPage=${page.startPage - 1}&searchType=${dto.searchType}&searchKeyword=${dto.searchKeyword}">«</a>
		            </li>
		        </c:if>
		
		        <!-- 페이지 번호 -->
		        <c:forEach var="i" begin="${page.startPage}" end="${page.endPage}">
		            <li class="page-item ${page.currentPage == i ? 'active' : ''}">
		                <a class="page-link" href="${pageContext.request.contextPath}/internal/list?currentPage=${i}&searchType=${dto.searchType}&searchKeyword=${dto.searchKeyword}">${i}</a>
		            </li>
		        </c:forEach>
		
		        <!-- 다음 블록 -->
		        <c:if test="${page.endPage < page.totalPage}">
		            <li class="page-item">
		                <a class="page-link" href="${pageContext.request.contextPath}/internal/list?currentPage=${page.endPage + 1}&searchType=${dto.searchType}&searchKeyword=${dto.searchKeyword}">»</a>
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