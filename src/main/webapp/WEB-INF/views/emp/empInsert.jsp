<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>사원 등록</title>
    <%@ include file="../common/staticResources.jsp" %>

    <style>
		.emp-insert-card {
		    max-width: 900px;
		    margin: 0 auto;
		}
		
		.emp-insert-form {
		    max-width: none;
		}
        .emp-insert-form .form-label {
            font-size: 14px;
            font-weight: 600;
            color: #495057;
            margin-bottom: 6px;
        }

        .emp-insert-form .form-control,
        .emp-insert-form .form-select {
            font-size: 14px;
        }

        .emp-form-actions {
            border-top: 1px solid #e9ecef;
            padding-top: 16px;
            margin-top: 8px;
        }

		#idCheckMsg {
		    display: block;
		    margin-top: 4px;
		    font-size: 13px;
		}
    </style>
</head>
<body>
    <%@ include file="../common/header.jsp" %>

    <div id="wrapper">
        <%@ include file="../common/sidebar.jsp" %>

        <div id="page-content-wrapper">
            <main class="content p-4">
                <div class="emp-insert-card">
				    <div class="d-flex justify-content-between align-items-center mb-4">
	                    <h4 class="fw-bold mb-0">사원 등록</h4>
	                    <a href="${pageContext.request.contextPath}/emp/empMain"
	                       class="btn btn-outline-secondary btn-sm">
	                        목록
	                    </a>
	                </div>
                </div>

                <div class="card shadow-sm border-0 emp-insert-card">
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/emp/empInsert"
                              method="post"
                              enctype="multipart/form-data"
                              class="emp-insert-form">

                            <c:if test="${not empty empInsertError}">
                                <div class="alert alert-danger">
                                    ${empInsertError}
                                </div>
                            </c:if>

                            <div class="row g-3">
								<div class="col-md-12">
								    <label for="emp_no" class="form-label">사원번호</label>
									<input type="number"
									       id="emp_no"
									       name="emp_no"
									       class="form-control bg-light"
									       placeholder="부서와 접근권한 선택 시 자동 생성됩니다"
									       readonly
									       required>
								</div>
								
								<div class="col-md-6">
								    <label for="emp_id" class="form-label">ID</label>
								    <input type="text"
								           id="emp_id"
								           name="emp_id"
								           class="form-control"
								           required>
								    <span id="idCheckMsg"></span>
								</div>
								
								<div class="col-md-6">
								    <label for="emp_password" class="form-label">비밀번호</label>
								    <input type="password"
								           id="emp_password"
								           name="emp_password"
								           class="form-control"
								           required>
								</div>
								
								<div class="col-md-6">
								    <label for="emp_name" class="form-label">이름</label>
								    <input type="text"
								           id="emp_name"
								           name="emp_name"
								           class="form-control"
								           required>
								</div>
								
								<div class="col-md-6">
								    <label for="dept_code" class="form-label">부서</label>
								    <select id="dept_code"
								            name="dept_code"
								            class="form-select"
								            required>
								        <option value="">부서 선택</option>
										<c:forEach var="dept" items="${deptList}">
										    <option value="${dept.dept_code}">
										        ${dept.dept_name}(${dept.dept_code})
										    </option>
										</c:forEach>
								    </select>
								</div>
								
								<div class="col-md-6">
								    <label for="emp_grade" class="form-label">직급</label>
								    <select id="emp_grade"
								            name="emp_grade"
								            class="form-select"
								            required>
								        <option value="">직급 선택</option>
								        <c:forEach var="grade" items="${empGradeList}">
								            <option value="${grade.key}">
								                ${grade.value}(${grade.key})
								            </option>
								        </c:forEach>
								    </select>
								</div>
								
								<div class="col-md-6">
								    <label for="emp_sal" class="form-label">연봉</label>
								    <input type="number"
								           id="emp_sal"
								           name="emp_sal"
								           class="form-control"
								           min="0"
								           step="1">
								</div>
								
								<div class="col-md-12">
								    <label for="user_access" class="form-label">접근권한</label>
								    <select id="user_access"
								            name="user_access"
								            class="form-select"
								            required>
										<option value="">접근권한 선택</option>
										<c:forEach var="access" items="${userAccessList}">
										    <option value="${access.key}" ${access.key == 3 ? 'selected' : ''}>
										        ${access.value}(${access.key})
										    </option>
										</c:forEach>
								    </select>
								</div>
								
								<div class="col-md-6">
								    <label for="emp_email" class="form-label">이메일</label>
								    <input type="text"
								           id="emp_email"
								           name="emp_email"
								           class="form-control">
								</div>
								
								<div class="col-md-6">
								    <label for="emp_tel" class="form-label">연락처</label>
								    <input type="text"
								           id="emp_tel"
								           name="emp_tel"
								           class="form-control"
								           placeholder="000-0000-0000"
								           pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}"
								           title="전화번호는 000-0000-0000 형식으로 입력하세요. 예: 010-1234-5678"
								           inputmode="numeric"
								           autocomplete="tel"
								           required>
								</div>
								
								<div class="col-md-12">
								    <label for="empPicFile" class="form-label">이미지파일</label>
								    <input type="file"
								           id="empPicFile"
								           name="empPicFile"
								           class="form-control"
								           accept="image/*">
								</div>
							</div>
                            <div class="emp-form-actions d-flex justify-content-end gap-2">
                                <a href="${pageContext.request.contextPath}/emp/empMain"
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
            </main>
        </div>
    </div>

    <%@ include file="../common/footer.jsp" %>

    <script>
        let idCheckTimer;
        let idAvailable = false;

        $(function() {
            $('#emp_id').on('keyup', function() {
                clearTimeout(idCheckTimer);
                const empId = $(this).val().trim();

                if (empId === '') {
                    $('#idCheckMsg').text('').css('color', '');
                    idAvailable = false;
                    return;
                }

                idCheckTimer = setTimeout(function() {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/emp/checkEmpId',
                        type: 'get',
                        data: { empId: empId },
                        dataType: 'json',
                        success: function(data) {
                            if (data.available) {
                                $('#idCheckMsg').text('입력 가능합니다.').css('color', 'green');
                                idAvailable = true;
                            } else {
                                $('#idCheckMsg').text('이미 사용 중인 id입니다.').css('color', 'red');
                                idAvailable = false;
                                $('#emp_id').val('');
                                $('#emp_id').focus();
                            }
                        },
                        error: function() {
                            $('#idCheckMsg').text('중복 확인 중 오류가 발생했습니다.').css('color', 'red');
                            idAvailable = false;
                        }
                    });
                }, 300);
            });
            function loadNextEmpNo() {
                const deptCode = $('#dept_code').val();
                const userAccess = $('#user_access').val();

                if (!deptCode || !userAccess) {
                    $('#emp_no').val('');
                    return;
                }

                $.ajax({
                    url: '${pageContext.request.contextPath}/emp/nextEmpNo',
                    type: 'get',
                    data: {
                        deptCode: deptCode,
                        userAccess: userAccess
                    },
                    dataType: 'json',
                    success: function(data) {
                        if (data.success) {
                            $('#emp_no').val(data.empNo);
                        } else {
                            $('#emp_no').val('');
                            alert(data.message);
                        }
                    },
                    error: function() {
                        $('#emp_no').val('');
                        alert('사원번호 생성 중 오류가 발생했습니다.');
                    }
                });
            }

            $('#dept_code, #user_access').on('change', loadNextEmpNo);
            $('form').on('submit', function(e) {
                const empId = $('#emp_id').val().trim();

                if (empId === '' || !idAvailable) {
                    e.preventDefault();
                    $('#emp_id').focus();
                    return false;
                }
            });
        });
    </script>
</body>
</html>