<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문의 상세</title>
<jsp:include page="/WEB-INF/views/common/staticResources.jsp"/>
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<div id="wrapper">
    <jsp:include page="/WEB-INF/views/common/sidebar.jsp"/>

    <div id="page-content-wrapper">
        <div class="container p-4">
            <h2 class="mb-4">게시글 상세</h2>

            <!-- 원글 -->
            <c:forEach var="board" items="${boardList}">
                <c:if test="${board.boardRefLvl == 0}">
                    <div class="card mb-4">
                        <div class="card-header bg-dark text-white fw-bold">
                            ${board.boardTitle}
                        </div>
                        <div class="card-body">
                            <div class="row mb-0.5">
                                <div class="col-md-3 fw-bold">작성자</div>
                                <div class="col-md-9">${board.empName}</div>
                            </div>
                            <div class="row mb-0.5">
                                <div class="col-md-3 fw-bold">등록일</div>
                                <div class="col-md-9"><fmt:formatDate value="${board.regDate}" pattern="yyyy-MM-dd a h:mm:ss"/></div>
                            </div>
                            <div class="row mb-0.5">
                                <div class="col-md-3 fw-bold">조회수</div>
                                <div class="col-md-9">${board.boardReadCount}</div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-md-3 fw-bold">내용</div>
                                <div class="col-md-9">${board.boardContents}</div>
                            </div>
                        </div>
                    </div>
                </c:if>
            </c:forEach>

			<!-- 답변 목록 -->
			<c:forEach var="board" items="${boardList}">
			    <c:if test="${board.boardRefLvl == 1}">
			        <div class="card mb-2 border-primary">
			            <div class="card-header bg-primary text-white fw-bold">
			                <i class="bi bi-chat-dots me-1"></i> 답변 - ${board.empName}
			            </div>
			            <div class="card-body">
			                <div class="row mb-0.5">
			                    <div class="col-md-3 fw-bold">등록일</div>
			                    <div class="col-md-9"><fmt:formatDate value="${board.regDate}" pattern="yyyy-MM-dd a h:mm:ss"/></div>
			                </div>
			                <hr>
			                <div class="row">
			                    <div class="col-md-3 fw-bold">내용</div>
			                    <div class="col-md-9">${board.boardContents}</div>
			                </div>
			
			                <!-- 해당 답변의 대댓글 목록 -->
			                <c:forEach var="reply" items="${boardList}">
			                    <c:if test="${reply.boardRefLvl == 2 && reply.boardRef == board.boardRef}">
			                        <div class="card mb-3 mt-3 border-primary ms-4">
			                            <div class="card-header bg-primary text-white fw-bold">
			                                <i class="bi bi-chat-dots me-1"></i> 대댓글 - ${reply.empName}
			                            </div>
			                            <div class="card-body">
			                                <div class="row mb-0.5">
			                                    <div class="col-md-3 fw-bold">등록일</div>
			                                    <div class="col-md-9"><fmt:formatDate value="${reply.regDate}" pattern="yyyy-MM-dd a h:mm:ss"/></div>
			                                </div>
			                                <hr>
			                                <div class="row">
			                                    <div class="col-md-3 fw-bold">내용</div>
			                                    <div class="col-md-9">${reply.boardContents}</div>
			                                </div>
			                            </div>
			                        </div>
			                    </c:if>
			                </c:forEach>
			
			                <!-- 대댓글 버튼 -->
			                <c:if test="${loginUser != null && (loginUser.user_access == 1 || loginUser.user_access == 2 || loginUser.user_access == 4 || loginUser.user_access == 5 || loginUser.user_access == 6 || loginUser.user_access == 9 || loginUser.emp_no == writerEmpNo)}">
			                    <div class="text-end mt-2">
			                        <button class="btn btn-sm btn-outline-primary" onclick="toggleReplyForm('replyForm_${board.boardCode}')">
			                            <i class="bi bi-reply me-1"></i> 대댓글 달기
			                        </button>
			                    </div>
			                    <div id="replyForm_${board.boardCode}" style="display:none;" class="mt-2">
			                        <form action="${pageContext.request.contextPath}/internal/reply" method="post">
			                            <input type="hidden" name="boardRef" value="${board.boardRef}">
			                            <input type="hidden" name="boardCode" value="${originBoardCode}">
			                            <input type="hidden" name="boardTitle" value="${board.boardTitle}">
			                            <div class="mb-3">
			                                <textarea name="boardContents" class="form-control" rows="3" placeholder="대댓글을 입력하세요" required></textarea>
			                            </div>
			                            <div class="text-end">
			                                <button type="button" class="btn btn-sm btn-secondary" onclick="toggleReplyForm('replyForm_${board.boardCode}')">취소</button>
			                                <button type="submit" class="btn btn-sm btn-primary">
			                                    <i class="bi bi-reply me-1"></i> 대댓글 등록
			                                </button>
			                            </div>
			                        </form>
			                    </div>
			                </c:if>
			
			            </div>
			        </div>
			    </c:if>
			</c:forEach>

            <!-- 답변 등록 폼 - 로그인한 사람만 -->
            <c:if test="${loginUser != null && loginUser.user_access != 800 && loginUser.user_access != 900}">
			    <div class="card mb-4 border-secondary">
			        <div class="card-header bg-secondary text-white fw-bold">
			            <i class="bi bi-reply me-1"></i> 답변 등록
			        </div>
                    <div class="card-body">
                        <c:forEach var="board" items="${boardList}">
                            <c:if test="${board.boardRefLvl == 0}">
                                <form action="${pageContext.request.contextPath}/internal/answer" method="post">
                                    <input type="hidden" name="boardRef" value="${board.boardRef}">
                                    <input type="hidden" name="boardCode" value="${board.boardCode}">
                                    <input type="hidden" name="boardTitle" value="${board.boardTitle}">
                                    <div class="mb-3">
                                        <textarea name="boardContents" class="form-control" rows="3" placeholder="답변 내용을 입력하세요" required></textarea>
                                    </div>
                                    <div class="text-end">
                                        <button type="submit" class="btn btn-primary">
                                            <i class="bi bi-check-lg me-1"></i> 답변 등록
                                        </button>
                                    </div>
                                </form>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </c:if>

            <div class="text-start">
                <a href="${pageContext.request.contextPath}/internal/list?currentPage=${param.currentPage}&searchType=${param.searchType}&searchKeyword=${param.searchKeyword}&answerFilter=${param.answerFilter}" class="btn btn-secondary">
                    <i class="bi bi-arrow-left me-1"></i> 목록으로
                </a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
function toggleReplyForm(id) {
    var form = document.getElementById(id);
    form.style.display = form.style.display === 'none' ? '' : 'none';
}
</script>
</body>
</html>