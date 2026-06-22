<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>거래처 수정</title>

<%@ include file="../common/staticResources.jsp" %>

<style>
    .cust-form-container {
        width: 650px;
        background: white;
        padding: 35px;
        border-radius: 12px;
        box-shadow: 0 4px 15px rgba(0,0,0,0.1);
    }

    h2 {
        color: #2c3e50;
        text-align: center;
        margin-bottom: 30px;
    }

    .form-group {
        margin-bottom: 18px;
    }

    .cust-form label {
        display: block;
        margin-bottom: 7px;
        font-weight: bold;
        color: #34495e;
    }

    .cust-form input,
    .cust-form select {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 6px;
        box-sizing: border-box;
    }

    .readonly {
        background-color: #eee;
    }

    .help-text {
        font-size: 12px;
        color: #888;
        margin-top: 5px;
    }

    .btn-area {
        text-align: center;
        margin-top: 30px;
    }

    .cust-form button,
    .cust-form .btn-link {
        text-decoration: none;
        border: none;
        padding: 10px 22px;
        border-radius: 6px;
        color: white;
        font-size: 14px;
        cursor: pointer;
        display: inline-block;
    }

    .btn-update {
        background-color: #f39c12;
    }

    .btn-update:hover {
        background-color: #d68910;
    }

    .btn-list {
        background-color: #7f8c8d;
    }

    .btn-list:hover {
        background-color: #636e72;
    }
</style>
</head>

<body>

<%@ include file="../common/header.jsp" %>

<div id="wrapper">

    <%@ include file="../common/sidebar.jsp" %>

    <div id="page-content-wrapper">
        <main class="content p-4">

        <div class="cust-form-container mx-auto">

            <h2>거래처 수정</h2>

            <form action="/cust/update" method="post" class="cust-form">

                <div class="form-group">
                    <label>거래처 코드</label>
                    <input type="number"
                           name="custCode"
                           value="${cust.cust_code}"
                           readonly
                           class="readonly">
                </div>

                <div class="form-group">
                    <label>거래처명</label>
                    <input type="text"
                           name="custName"
                           value="${cust.cust_name}"
                           required
                           maxlength="100"
                           placeholder="거래처명을 입력하세요">
                </div>

                <div class="form-group">
                    <label>거래처 유형</label>

                    <select name="custType" required>
                        <c:forEach var="code" items="${custTypeList}">
                            <c:if test="${code.mcode != 999}">
                                <option value="${code.mcode}"
                                    ${cust.cust_type == code.mcode ? 'selected' : ''}>
                                    ${code.code_contents}
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label>사업자 번호</label>
                    <input type="text"
                           name="businessNo"
                           value="${cust.business_no}"
                           required
                           maxlength="20"
                           pattern="[0-9]{3}-[0-9]{2}-[0-9]{5}"
                           placeholder="000-00-00000">
                    <div class="help-text">예: 123-45-67890</div>
                </div>

                <div class="form-group">
                    <label>대표자명</label>
                    <input type="text"
                           name="ceoName"
                           value="${cust.ceo_name}"
                           required
                           maxlength="50"
                           placeholder="대표자명을 입력하세요">
                </div>

                <div class="form-group">
                    <label>전화번호</label>
                    <input type="text"
					       name="custTel"
					       value="${cust.cust_tel}"
					       required
					       maxlength="30"
					       pattern="^[0-9-]{9,15}$"
					       placeholder="전화번호 입력">
                    <div class="help-text">예: 010-1234-5678</div>
                </div>

                <div class="form-group">
                    <label>이메일</label>
                    <input type="email"
                           name="custEmail"
                           value="${cust.cust_email}"
                           required
                           maxlength="100"
                           placeholder="example@email.com">
                </div>

                <div class="form-group">
                    <label>주소</label>
                    <input type="text"
                           name="custAddress"
                           value="${cust.cust_address}"
                           required
                           maxlength="200"
                           placeholder="주소를 입력하세요">
                </div>

                <div class="form-group">
                    <label>사용 상태</label>

                    <select name="custDelStatus" required>
                        <c:forEach var="code" items="${custStatusList}">
                            <c:if test="${code.mcode != 999}">
                                <option value="${code.mcode}"
                                    ${cust.cust_del_status == code.mcode ? 'selected' : ''}>
                                    ${code.code_contents}
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
				    <label>등록 사원 번호</label>
				
				    <input type="text"
				           value="${cust.reg_emp_no}"
				           readonly>
				
				    <div class="help-text">
				        등록 사원 번호는 수정할 수 없습니다.
				    </div>
				</div>

                <div class="btn-area">

                    <button type="submit" class="btn-update">
                        수정
                    </button>

                    <a href="/cust/custList" class="btn-link btn-list">
                        목록
                    </a>

                </div>

            </form>

        </div>

        </main>
    </div>

</div>

<%@ include file="../common/footer.jsp" %>

</body>
</html>
