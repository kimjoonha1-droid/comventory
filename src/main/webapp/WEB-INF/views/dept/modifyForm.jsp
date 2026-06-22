<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
       
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../common/staticResources.jsp" %>
 <!-- 부트스트랩 CSS CDN 링크 -->
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
 <!-- jQuery와 jQuery UI 라이브러리 추가  달력 적용 위해-->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
<!-- Bootstrap Icons 추가 -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
 
	<style>
	    .dept-update-card {
	        max-width: 900px;
	        margin: 0 auto;
	    }
	
	    .dept-update-form .form-label {
	        font-size: 14px;
	        font-weight: 600;
	        color: #495057;
	        margin-bottom: 6px;
	    }
	
	    .dept-form-actions {
	        border-top: 1px solid #e9ecef;
	        padding-top: 16px;
	        margin-top: 20px;
	    }
	</style>
    
    <!-- Datepicker 초기화 스크립트 -->
    <script type="text/javascript">
       // datepicker Logic 
	    $(function() {
	        // Datepicker 설정
	        $(".datepicker").datepicker({
	            dateFormat: 'yy-mm-dd',
	            changeMonth: true,
	            changeYear: true,
	            showOtherMonths: true,
	            selectOtherMonths: true,
	            dayNamesMin: ['일', '월', '화', '수', '목', '금', '토'],
	            monthNamesShort: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	            monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	            yearSuffix: '년',
	            showMonthAfterYear: true
	        });
	        
	        // LocalDateTime 형식을 datepicker 형식으로 변환
	        var inDateValue = "${deptDto.in_date}";
	        if(inDateValue) {
	            var datePart = inDateValue.split('T')[0];
	            $("#inDate").val(datePart);
	        }
	        
	        // 캘린더 버튼 클릭 시 datepicker 표시
	        $(".calendar-btn").click(function() {
	            $(this).closest('.input-group').find('.datepicker').datepicker("show");
	        });
	    });
    </script>
</head>
<body>
    <%@ include file="../common/header.jsp" %>

    <div id="wrapper">
        <%@ include file="../common/sidebar.jsp" %>

        <div id="page-content-wrapper">
            <main class="content p-4">
                <div class="dept-update-card">
                    <div class="d-flex justify-content-between align-items-center mb-4">
                        <h4 class="fw-bold mb-0">부서 수정</h4>

                        <a href="${pageContext.request.contextPath}/dept/detail?dept_code=${deptDto.dept_code}"
                           class="btn btn-outline-secondary btn-sm">
                            상세
                        </a>
                    </div>

                    <div class="card shadow-sm border-0">
                        <div class="card-body">
                            <form action="${pageContext.request.contextPath}/dept/updateDept"
                                  method="post"
                                  class="dept-update-form">

                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <label for="deptCode" class="form-label">부서코드</label>
                                        <input type="text"
                                               id="deptCode"
                                               name="dept_code"
                                               class="form-control"
                                               value="${deptDto.dept_code}"
                                               readonly>
                                    </div>

                                    <div class="col-md-6">
                                        <label for="deptName" class="form-label">부서명</label>
                                        <input type="text"
                                               id="deptName"
                                               name="dept_name"
                                               class="form-control"
                                               value="${deptDto.dept_name}"
                                               required>
                                    </div>

                                    <div class="col-md-6">
                                        <label for="deptTel" class="form-label">부서 대표전화</label>
                                        <input type="text"
                                               id="deptTel"
                                               name="dept_tel"
                                               class="form-control"
                                               value="${deptDto.dept_tel}">
                                    </div>

                                    <div class="col-md-6">
                                        <label for="deptLoc" class="form-label">부서 위치</label>
                                        <input type="text"
                                               id="deptLoc"
                                               name="dept_loc"
                                               class="form-control"
                                               value="${deptDto.dept_loc}">
                                    </div>

                                    <div class="col-md-6">
										<label for="dept_mgr" class="form-label">부서장</label>
										<input type="text"
										       id="dept_mgr"
										       name="dept_mgr"
										       class="form-control"
										       value="${deptDto.dept_mgr}">
                                    </div>

                                    <div class="col-md-6">
                                        <label for="deptGubun" class="form-label">부서 사용상태</label>
                                        <select id="deptGubun"
                                                name="dept_gubun"
                                                class="form-select"
                                                required>
                                            <option value="0" <c:if test="${deptDto.dept_gubun == 0}">selected</c:if>>사용</option>
                                            <option value="1" <c:if test="${deptDto.dept_gubun == 1}">selected</c:if>>미사용</option>
                                        </select>
                                    </div>
                                </div>

                                <div class="dept-form-actions d-flex justify-content-end gap-2">
                                    <a href="${pageContext.request.contextPath}/dept/detail?dept_code=${deptDto.dept_code}"
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