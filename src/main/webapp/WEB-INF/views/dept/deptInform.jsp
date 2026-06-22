<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../common/staticResources.jsp" %>
<style>
    .dept-insert-card {
        max-width: 900px;
        margin: 0 auto;
    }

    .dept-insert-form .form-label {
        font-size: 14px;
        font-weight: 600;
        color: #495057;
        margin-bottom: 6px;
    }

    .dept-insert-form .form-control,
    .dept-insert-form .form-select {
        font-size: 14px;
    }

    .dept-form-actions {
        border-top: 1px solid #e9ecef;
        padding-top: 16px;
        margin-top: 20px;
    }
</style>
</head>
<body>
    <%@ include file="../common/header.jsp" %>

    <div id="wrapper">
        <%@ include file="../common/sidebar.jsp" %>

        <div id="page-content-wrapper">
            <main class="content p-4">
                <div class="dept-insert-card">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h4 class="fw-bold mb-0">부서 등록</h4>

                        <a href="${pageContext.request.contextPath}/dept/deptList"
                           class="btn btn-outline-secondary btn-sm">
                            목록
                        </a>
                    </div>

                    <div class="card shadow-sm border-0">
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/dept/saveDept"
                                  method="post"
                                  class="dept-insert-form">

                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label for="dept_code" class="form-label">부서코드</label>
										<input type="number"
										       id="dept_code"
										       name="dept_code"
										       class="form-control bg-light"
										       value="${nextDeptCode}"
										       readonly
										       required>
                                    </div>

                                    <div class="col-md-6">
                                        <label for="dept_name" class="form-label">부서명</label>
                                        <input type="text"
                                               id="dept_name"
                                               name="dept_name"
                                               class="form-control"
                                               required>
                                    </div>

                                    <div class="col-md-6">
                                        <label for="dept_tel" class="form-label">부서 대표전화</label>
                                        <input type="text"
                                               id="dept_tel"
                                               name="dept_tel"
                                               class="form-control"
                                               placeholder="02-000-0000">
                                    </div>

                                    <div class="col-md-6">
                                        <label for="dept_loc" class="form-label">부서 위치</label>
                                        <input type="text"
                                               id="dept_loc"
                                               name="dept_loc"
                                               class="form-control">
                                    </div>

									<div class="col-md-12">
										<label for="dept_mgr" class="form-label">부서장</label>
										<select id="dept_mgr"
										        name="dept_mgr"
									            class="form-select"
									            required>
									        <option value="">부서장 선택</option>
									        <c:forEach items="${managerList}" var="manager">
									            <option value="${manager.emp_no}">
									                ${manager.emp_name} (${manager.emp_no})
									            </option>
									        </c:forEach>
									    </select>
									</div>
                                </div>

                                <div class="dept-form-actions d-flex justify-content-end gap-2">
                                    <a href="${pageContext.request.contextPath}/dept/deptList"
                                       class="btn btn-outline-secondary">
                                        취소
                                    </a>

                                    <button type="submit" class="btn btn-primary">
                                        등록
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