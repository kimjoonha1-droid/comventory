<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>사원 수정</title>
    <%@ include file="../common/staticResources.jsp" %>
	<style>
	    .emp-update-card {
	        max-width: 900px;
	        margin: 0 auto;
	    }
	
	    .emp-update-form .form-label {
	        font-size: 14px;
	        font-weight: 600;
	        color: #495057;
	        margin-bottom: 6px;
	    }
	
	    .emp-profile-preview {
	        width: 140px;
	        height: 140px;
	        object-fit: cover;
	        border-radius: 10px;
	        border: 1px solid #dee2e6;
	        background: #f8f9fa;
	    }
	
	    .emp-form-actions {
	        border-top: 1px solid #e9ecef;
	        padding-top: 16px;
	        margin-top: 8px;
	    }
	</style>
</head>
<body>
    <%@ include file="../common/header.jsp" %>

    <div id="wrapper">
        <%@ include file="../common/sidebar.jsp" %>

        <div id="page-content-wrapper">
            <main class="content p-4">
				<div class="emp-update-card">
				    <div class="d-flex justify-content-between align-items-center mb-4">
				        <h4 class="fw-bold mb-0">사원 수정</h4>
				
				        <a href="${pageContext.request.contextPath}/emp/empDetail?empNo=${emp.empNo}"
				           class="btn btn-outline-secondary btn-sm">
				            뒤로가기
				        </a>
				    </div>
				
				    <div class="card shadow-sm border-0">
				        <div class="card-body">
				            <form action="${pageContext.request.contextPath}/emp/empUpdate"
				                  method="post"
				                  enctype="multipart/form-data"
				                  class="emp-update-form">
				
				                <input type="hidden" name="emp_no" value="${emp.empNo}">
				                <input type="hidden" name="emp_pic" value="${emp.empPic}">
				
								<div class="row g-3">
								    <div class="col-md-6">
								        <label for="empName" class="form-label">이름</label>
								        <input type="text"
								               id="empName"
								               name="emp_name"
								               class="form-control"
								               value="${emp.empName}"
								               required>
								    </div>
								
								    <div class="col-md-6">
								        <label for="empSal" class="form-label">연봉</label>
								        <input type="number"
								               id="empSal"
								               name="emp_sal"
								               class="form-control"
								               value="${empty emp.empSal ? '' : emp.empSal}"
								               min="0"
								               step="1"
								               required>
								    </div>
								
								    <div class="col-md-6">
								        <label for="deptCode" class="form-label">부서</label>
								        <select id="deptCode" name="dept_code" class="form-select" required>
								            <option value="">부서 선택</option>
								            <c:forEach var="dept" items="${deptList}">
								                <option value="${dept.dept_code}"
								                    <c:if test="${dept.dept_code == emp.deptCode}">selected</c:if>>
								                    ${dept.dept_name} (${dept.dept_code})
								                </option>
								            </c:forEach>
								        </select>
								    </div>
								
								    <div class="col-md-6">
								        <label for="empGrade" class="form-label">직급</label>
								        <select id="empGrade" name="emp_grade" class="form-select" required>
								            <option value="">직급 선택</option>
								            <c:forEach var="grade" items="${empGradeList}">
								                <option value="${grade.key}"
								                    <c:if test="${grade.key == emp.empGrade}">selected</c:if>>
								                    ${grade.value}(${grade.key})
								                </option>
								            </c:forEach>
								        </select>
								    </div>
								
								    <div class="col-md-6">
								        <label for="empEmail" class="form-label">이메일</label>
								        <input type="email"
								               id="empEmail"
								               name="emp_email"
								               class="form-control"
								               value="${emp.empEmail}">
								    </div>
								
								    <div class="col-md-6">
								        <label for="empTel" class="form-label">연락처</label>
								        <input type="text"
								               id="empTel"
								               name="emp_tel"
								               class="form-control"
								               value="${emp.empTel}"
								               pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}"
								               title="전화번호는 000-0000-0000 형식으로 입력하세요."
								               required>
								    </div>
								
								    <div class="col-md-6">
								        <label for="empDelStatus" class="form-label">퇴직여부</label>
								        <select id="empDelStatus" name="emp_del_status" class="form-select" required>
								            <option value="0" ${emp.active ? 'selected' : ''}>재직중</option>
								            <option value="1" ${!emp.active ? 'selected' : ''}>퇴사</option>
								        </select>
								    </div>
								
								    <div class="col-md-12">
								        <label class="form-label">현재 이미지</label>
								        <div class="mb-3">
								            <c:choose>
								                <c:when test="${not empty emp.empPic}">
								                    <img src="${pageContext.request.contextPath}/upload/emp/${emp.empPic}"
								                         class="emp-profile-preview"
								                         alt="">
								                </c:when>
								                <c:otherwise>
								                    <div class="emp-profile-preview d-flex align-items-center justify-content-center text-muted">
								                        이미지 없음
								                    </div>
								                </c:otherwise>
								            </c:choose>
								        </div>
								
								        <label for="empPicFile" class="form-label">이미지 교체</label>
								        <input type="file"
								               id="empPicFile"
								               name="empPicFile"
								               class="form-control"
								               accept="image/*">
								    </div>
								</div>
								
								<div class="emp-form-actions d-flex justify-content-end gap-2">
								    <a href="${pageContext.request.contextPath}/emp/empDetail?empNo=${emp.empNo}"
								       class="btn btn-outline-secondary">
								        취소
								    </a>
								
								    <button type="submit" class="btn btn-primary">
								        수정
								    </button>
								</div>
				            </form>
				        </div>
				    </div>
				</div>

            </main>
        </div>
    </div>

    <%@ include file="../common/footer.jsp" %>
</body>
</html>
