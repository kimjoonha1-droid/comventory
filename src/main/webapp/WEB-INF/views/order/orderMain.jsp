<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>수주 기안 조회 및 승인</title>

    <%@ include file="../common/staticResources.jsp" %>
    
    <style>
        .filter-section {
            background: white;
            padding: 20px 30px;
            border-radius: 8px;
            margin-bottom: 20px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
        }

        .filter-row {
            display: flex;
            gap: 15px;
            align-items: center;
            flex-wrap: wrap;
        }

        .filter-group {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .filter-group label {
            font-size: 14px;
            font-weight: 500;
            color: #555;
        }

        .filter-input {
            padding: 8px 12px;
            border: 1px solid #d0d0d0;
            border-radius: 4px;
            font-size: 14px;
        }

        .search-box {
            flex: 1;
            min-width: 300px;
            position: relative;
        }

        .search-box input {
            width: 100%;
            padding: 8px 12px 8px 35px;
            border: 1px solid #d0d0d0;
            border-radius: 4px;
            font-size: 14px;
        }

        .search-icon {
            position: absolute;
            left: 12px;
            top: 50%;
            transform: translateY(-50%);
            color: #999;
        }

        .btn-search {
            background-color: #1976d2;
            color: white;
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }

        .btn-search:hover {
            background-color: #1565c0;
        }

        .alert-badge {
            color: #d32f2f;
            font-size: 13px;
            font-weight: 600;
            margin-left: auto;
        }

        .table-container {
            background: white;
            border-radius: 8px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.1);
            overflow-x: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        thead {
            background-color: #fafafa;
        }

        th {
            padding: 14px 12px;
            text-align: center;
            font-size: 13px;
            font-weight: 600;
            color: #555;
            border-bottom: 2px solid #e0e0e0;
            white-space: nowrap;
        }

        td {
            padding: 14px 12px;
            text-align: center;
            font-size: 13px;
            border-bottom: 1px solid #f0f0f0;
            color: #333;
        }

        tbody tr:hover {
            background-color: #f8f9fa;
        }

        .status-badge {
            display: inline-block;
            padding: 5px 12px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: 600;
        }

        .status-draft {
            background-color: #fff3cd;
            color: #856404;
        }

        .status-approved {
            background-color: #d1ecf1;
            color: #0c5460;
        }

        .status-rejected {
            background-color: #f8d7da;
            color: #721c24;
        }

        .status-wait {
            background-color: #d4edda;
            color: #155724;
        }

        .btn-action {
            padding: 6px 14px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 12px;
            font-weight: 500;
            margin: 0 2px;
            border: none;
        }

        .btn-approve {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }

        .btn-approve:hover {
            background-color: #c3e6cb;
            color: #155724;
        }

        .btn-reject {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }

        .btn-reject:hover {
            background-color: #f5c6cb;
            color: #721c24;
        }

        .history-cell {
            text-align: left;
            font-size: 12px;
            color: #666;
        }

        .reject-reason {
            color: #dc3545;
            font-weight: 600;
        }

        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #999;
        }

        .toast {
            position: fixed;
            top: 80px;
            right: 30px;
            padding: 15px 20px;
            border-radius: 6px;
            font-size: 14px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
            z-index: 1000;
            display: none;
        }

        .toast.success {
            background-color: #d4edda;
            color: #155724;
        }

        .toast.error {
            background-color: #f8d7da;
            color: #721c24;
        }

        .btn-register {
            position: absolute;
            top: 20px;
            right: 30px;
            background-color: #1976d2;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 600;
            box-shadow: 0 2px 4px rgba(0,0,0,0.2);
        }

        .btn-register:hover {
            background-color: #1565c0;
        }

        .modal-overlay {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 9999;
            justify-content: center;
            align-items: center;
        }

        .modal-overlay.active {
            display: flex;
        }

        .modal-container {
            background: white;
            border-radius: 8px;
            width: 90%;
            max-width: 900px;
            max-height: 90vh;
            overflow-y: auto;
            box-shadow: 0 4px 20px rgba(0,0,0,0.3);
        }

        .modal-header {
            padding: 20px 30px;
            border-bottom: 1px solid #e0e0e0;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .modal-title {
            font-size: 20px;
            font-weight: 700;
            color: #1a1a1a;
        }

        .btn-close {
            background: none;
            border: none;
            font-size: 24px;
            cursor: pointer;
            color: #999;
            padding: 0;
            width: 30px;
            height: 30px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .btn-close:hover {
            color: #333;
        }

        .modal-body {
            padding: 30px;
        }

        .form-row {
            display: flex;
            gap: 20px;
            margin-bottom: 20px;
        }

        .form-group {
            flex: 1;
            display: flex;
            flex-direction: column;
        }

        .form-group label {
            font-size: 14px;
            font-weight: 600;
            color: #333;
            margin-bottom: 8px;
        }

        .form-group input,
        .form-group select {
            padding: 10px 12px;
            border: 1px solid #d0d0d0;
            border-radius: 4px;
            font-size: 14px;
        }

        .form-group input:focus,
        .form-group select:focus {
            outline: none;
            border-color: #1976d2;
        }

        .modal-footer {
            padding: 20px 30px;
            border-top: 1px solid #e0e0e0;
            display: flex;
            gap: 10px;
            justify-content: flex-end;
        }

        .btn-modal {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 600;
        }

        .btn-submit {
            background-color: #1976d2;
            color: white;
        }

        .btn-submit:hover {
            background-color: #1565c0;
        }

        .btn-cancel {
            background-color: #6c757d;
            color: white;
        }

        .btn-cancel:hover {
            background-color: #5a6268;
        }

        .reject-tooltip {
            position: relative;
            cursor: help;
            display: inline-block;
        }

        .reject-tooltip .tooltip-text {
            visibility: hidden;
            width: max-content;
            max-width: 300px;
            width: max-content;
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            text-align: left;
            border-radius: 6px;
            padding: 10px 12px;
            position: absolute;
            z-index: 1000;
            bottom: 125%;
            left: 50%;
            transform: translateX(-50%);
            opacity: 0;
            transition: opacity 0.3s;
            font-size: 12px;
            line-height: 1.5;
            box-shadow: 0 3px 10px rgba(0,0,0,0.25);
            white-space: nowrap;
            word-break: break-word;
        }

        .reject-tooltip .tooltip-text::after {
            content: "";
            position: absolute;
            top: 100%;
            left: 50%;
            transform: translateX(-50%);
            margin-left: -5px;
            border-width: 5px;
            border-style: solid;
            border-color: #f8d7da transparent transparent transparent;
        }

        .reject-tooltip:hover .tooltip-text {
            visibility: visible;
            opacity: 1;
        }
    </style>
</head>

<body>
    <%@ include file="../common/header.jsp" %>

    <div id="wrapper">
        <%@ include file="../common/sidebar.jsp" %>

        <div id="page-content-wrapper">
            <main class="content p-4" style="position: relative;">
                <h5 class="mb-4">수주 기안 조회 및 승인</h5>

                <button type="button" class="btn-register" onclick="openRegisterModal()">수주 등록</button>

                <div class="filter-section">
                    <div class="filter-row">
                        <div class="filter-group">
                            <label>날짜 선택</label>
                            <input type="month" id="searchMonth" class="filter-input" value="2026-05" onchange="loadOrders()">
                        </div>

                        <div class="search-box">
                            <span class="search-icon">🔍</span>
                            <input type="text" id="searchKeyword" placeholder="고객명 또는 기안자로 검색..."
                                   onkeypress="if(event.key === 'Enter') loadOrders()">
                        </div>

                        <button type="button" class="btn-search" onclick="loadOrders()">검색</button>

                        <div class="alert-badge">🔴 <span id="draftLabel">결재 대기</span>:
                            <span id="draftCount">0</span><span id="draftUnit">건</span>
                        </div>
                    </div>
                </div>

                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <!-- <th>No.</th> -->
                                <th>수주일자</th>
                                <th>주문서번호</th>
                                <th>거래처</th>
                                <th>품목</th>
                                <th>수량</th>
                                <th>단위</th>
                                <th style="text-align: right;">금액</th>
                                <th>기안자</th>
                                <th>최종승인자</th>
                                <th>결재상태</th>
                                <th>히스토리</th>
                                <th id="actionColHeader">처리</th>
                            </tr>
                        </thead>
                        <tbody id="orderTableBody">
                            <tr>
                                <td colspan="13">
                                    <div class="empty-state">
                                        <div>⏳</div>
                                        <div>데이터를 불러오는 중...</div>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </main>
        </div>
    </div>

    <div id="toast" class="toast"></div>

    <!-- 수주 등록 모달 -->
    <div id="registerModal" class="modal-overlay">
        <div class="modal-container">
            <div class="modal-header">
                <h2 class="modal-title">수주서 등록</h2>
                <button type="button" class="btn-close" onclick="closeRegisterModal()">&times;</button>
            </div>
            <div class="modal-body">
                <form id="registerForm">
                    <!-- 날짜/거래처 -->
                    <div class="form-row">
                        <div class="form-group">
                            <label>수주일자 <span style="color: red;">*</span></label>
                            <input type="date" id="orderDate" name="orderDate" required min="">
                        </div>
                        <div class="form-group">
                            <label>거래처 선택 <span style="color: red;">*</span></label>
                            <select id="custCode" name="custCode" required>
                                <option value="">거래처를 선택하세요</option>
                            </select>
                        </div>
                    </div>

                    <!-- 제품 선택 -->
                    <div class="form-row">
                        <div class="form-group">
                            <label>제품 선택 <span style="color: red;">*</span></label>
                            <select id="productCode" name="productCode" required onchange="updateProductInfo()">
                                <option value="">제품을 선택하세요</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>단가</label>
                            <input type="text" id="unitPrice" name="unitPrice" readonly style="background-color: #f5f5f5;">
                        </div>
                    </div>

                    <!-- 수량 -->
                    <div class="form-row">
                        <div class="form-group">
                            <label>수량 <span style="color: red;">*</span></label>
                            <input type="number" id="quantity" name="quantity" placeholder="수량 입력" min="1" required>
                        </div>
                        <div class="form-group">
                            <label>합계금액</label>
                            <input type="text" id="totalPrice" name="totalPrice" readonly style="background-color: #f5f5f5;">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn-modal btn-cancel" onclick="closeRegisterModal()">취소</button>
                <button type="button" class="btn-modal btn-submit" onclick="submitRegister()">등록 완료</button>
            </div>
        </div>
    </div>

    <%@ include file="../common/footer.jsp" %>

    <script>
        const CURRENT_USER_EMPNO = ${currentEmpNo};  
        let LOGISTICS_APPROVER_LIST = [];
        let PRODUCT_LIST = []; 

        window.onload = function() {

            loadLogisticsApprover().then(() => loadOrders());

            loadCustomers();
            loadProducts(); 

            const quantityInput = document.getElementById('quantity');
            if (quantityInput) {
                quantityInput.addEventListener('input', calculateTotal);
            }
        };

        function showToast(message, type) {
            const toast = document.getElementById('toast');
            toast.textContent = message;
            toast.className = 'toast ' + type;
            toast.style.display = 'block';
            setTimeout(() => toast.style.display = 'none', 3000);
        }

        function loadLogisticsApprover() {
            return fetch('/api/orders/logistics-approvers')
                .then(response => response.json())
                .then(data => {
                    LOGISTICS_APPROVER_LIST = data.approvers || [];
                    console.log('물류팀 결재자 목록:', LOGISTICS_APPROVER_LIST);
                })
                .catch(error => {
                    console.error('물류팀 결재자 조회 실패:', error);
                });
        }

        function loadCustomers() {
            fetch('/api/orders/customers')
                .then(response => response.json())
                .then(data => {
                    const select = document.getElementById('custCode');
                    data.forEach(cust => {
                        const option = document.createElement('option');
                        option.value = cust.CUST_CODE;
                        option.textContent = cust.CUST_NAME;
                        select.appendChild(option);
                    });
                    console.log('✅ 거래처 목록 로드 완료:', data.length + '개');
                })
                .catch(error => {
                    console.error('❌ 거래처 목록 조회 실패:', error);
                });
        }

        function loadProducts() {
            fetch('/api/orders/products')
                .then(response => response.json())
                .then(data => {
                    PRODUCT_LIST = data;
                    const select = document.getElementById('productCode');
                    data.forEach(prod => {
                        const option = document.createElement('option');
                        option.value = prod.PRODUCT_CODE;
                        option.textContent = prod.PRODUCT_NAME + ' (' + prod.PRODUCT_PRICE.toLocaleString() + '원)';
                        select.appendChild(option);
                    });
                    console.log('✅ 제품 목록 로드 완료:', data.length + '개');
                })
                .catch(error => {
                    console.error('❌ 제품 목록 조회 실패:', error);
                });
        }

        function updateProductInfo() {
            const productCode = parseInt(document.getElementById('productCode').value);
            const product = PRODUCT_LIST.find(p => p.PRODUCT_CODE === productCode);

            if (product) {
                document.getElementById('unitPrice').value = product.PRODUCT_PRICE.toLocaleString() + '원';
                calculateTotal();
            } else {
                document.getElementById('unitPrice').value = '';
                document.getElementById('totalPrice').value = '';
            }
        }

        function calculateTotal() {
            const productCode = parseInt(document.getElementById('productCode').value);
            const product = PRODUCT_LIST.find(p => p.PRODUCT_CODE === productCode);
            const quantity = parseInt(document.getElementById('quantity').value) || 0;

            if (product && quantity > 0) {
                const total = product.PRODUCT_PRICE * quantity;
                document.getElementById('totalPrice').value = total.toLocaleString() + '원';
            } else {
                document.getElementById('totalPrice').value = '';
            }
        }

        function loadOrders() {
            const searchMonth = document.getElementById('searchMonth').value;
            const searchKeyword = document.getElementById('searchKeyword').value.trim();
            let params = new URLSearchParams();

            if (searchMonth) {
                const [year, month] = searchMonth.split('-');
                params.append('startDate', year + '-' + month + '-01');
                const lastDay = new Date(year, month, 0).getDate();
                params.append('endDate', year + '-' + month + '-' + String(lastDay).padStart(2, '0'));
            }

            if (searchKeyword) {
                params.append('searchKeyword', searchKeyword);
            }

            fetch('/api/orders?' + params.toString())
                .then(response => response.json())
                .then(data => {
                    console.log('🔥 서버에서 받은 데이터:', data);
                    if (!Array.isArray(data)) {
                        console.error('응답이 배열이 아닙니다:', data);
                        showToast('✗ ' + (data.message || '데이터 형식 오류'), 'error');
                        displayOrders([]);  
                        updateDraftCount([]);
                        return;
                    }

                    displayOrders(data);
                    updateDraftCount(data);
                })
                .catch(error => {
                    console.error('Error:', error);
                    showToast('✗ 데이터 조회 실패', 'error');
                });
        }


        function hasActionForRow(order) {

            if (order.drafter_empno === CURRENT_USER_EMPNO && order.eapp_status === 110) return true;

            if (order.appr_empno === CURRENT_USER_EMPNO && order.eapp_status === 110) return true;

            return false;
        }

        function displayOrders(orders) {
            const tbody = document.getElementById('orderTableBody');
            const actionColHeader = document.getElementById('actionColHeader');


            const showActionCol = orders && orders.some(o => hasActionForRow(o));
            actionColHeader.style.display = showActionCol ? '' : 'none';


            const colCount = showActionCol ? 12 : 11;

            if (!orders || orders.length === 0) {
                tbody.innerHTML = '<tr><td colspan="' + colCount + '"><div class="empty-state"><div>ℹ️</div><div>조회된 주문 내역이 없습니다.</div></div></td></tr>';
                return;
            }

            let html = '';
            orders.forEach((order, index) => {
                const statusInfo = getStatusInfo(order.eapp_status);
                const firstDetail = order.orderDetails && order.orderDetails[0];

                html += '<tr>';
                html += '<td>' + formatDate(order.order_drafting_date) + '</td>';
                html += '<td>' + order.order_code + '</td>';
                html += '<td>' + (order.cust_name || '-') + '</td>';
                html += '<td>' + (firstDetail ? firstDetail.product_name : '-') + '</td>';
                html += '<td>' + (firstDetail ? firstDetail.order_amount : '-') + '</td>';
                html += '<td>' + (firstDetail ? 'EA' : '-') + '</td>';
                html += '<td style="text-align: right; white-space: nowrap;">' + formatNumber(order.order_sum_price) + '원</td>';
                html += '<td>' + (order.drafter_name || '-') + '</td>';
                html += '<td>' + (order.appr_name || '-') + '</td>';


                html += '<td>';
                if ((order.eapp_status === 230 || order.eapp_status === 330) && order.order_refuse) {
                    html += '<div class="reject-tooltip">';
                    html += '<span class="status-badge ' + statusInfo.class + '">' + statusInfo.text + '</span>';
                    html += '<span class="tooltip-text">사유: ' + order.order_refuse + '</span>';
                    html += '</div>';
                } else {
                    html += '<span class="status-badge ' + statusInfo.class + '">' + statusInfo.text + '</span>';
                }
                html += '</td>';


				html += '<td class="history-cell">';
				if (order.order_drafting_date) {
				    html += formatDateTime(order.order_drafting_date) + ' 기안';
				}
				if (order.approval_timestamp) {
				    html += '<br>' + formatDateTime(order.approval_timestamp) + ' 승인';
				}
				if (order.order_rejected_date) {
				    html += '<br>' + formatDateTime(order.order_rejected_date) + ' 반려';
				}
				if (order.order_cancelled_date) {
				    html += '<br>' + formatDateTime(order.order_cancelled_date) + ' 회수';
				}
				html += '</td>';


                if (showActionCol) {
                    html += '<td>';
                    if (order.drafter_empno === CURRENT_USER_EMPNO && order.eapp_status === 110) {

                        html += '<button class="btn-action btn-reject" onclick="cancelOrder(' + order.order_code + ')">회수</button>';
                    }
                    else if (order.appr_empno === CURRENT_USER_EMPNO && order.eapp_status === 110) {

                        html += '<button class="btn-action btn-approve" onclick="approveOrder(' + order.order_code + ')">승인</button>';
                        html += '<button class="btn-action btn-reject" onclick="rejectOrder(' + order.order_code + ')">반려</button>';
                    }

                    html += '</td>';
                }

                html += '</tr>';
            });

            tbody.innerHTML = html;
        }

        function updateDraftCount(orders) {
            const isApprover = LOGISTICS_APPROVER_LIST.includes(CURRENT_USER_EMPNO);
            let count;

            if (isApprover) {

                count = orders.filter(o =>
                    o.eapp_status === 110 && o.appr_empno === CURRENT_USER_EMPNO
                ).length;
            } else {

                count = orders.filter(o =>
                    o.eapp_status === 110 && o.drafter_empno === CURRENT_USER_EMPNO
                ).length;
            }

            document.getElementById('draftCount').textContent = count;


            const label = document.getElementById('draftLabel');
            if (label) {
                label.textContent = isApprover ? '결재 대기 건수' : '미결';
            }
        }

        function getStatusInfo(status) {
            const map = {
                110: { text: '기안요청',  class: 'status-draft' },
                140: { text: '회수',      class: 'status-rejected' },
                210: { text: '검토진행',  class: 'status-wait' },       
                220: { text: '검토승인',  class: 'status-approved' },   
                230: { text: '검토반려',  class: 'status-rejected' },  
                310: { text: '물류대기',  class: 'status-wait' },       
                320: { text: '승인',      class: 'status-approved' },  
                325: { text: '자동승인',  class: 'status-approved' },  
                330: { text: '반려',      class: 'status-rejected' },   
                410: { text: '출고대기',  class: 'status-wait' },
                420: { text: '출고완료',  class: 'status-approved' },   
                710: { text: '수불마감',  class: 'status-approved' }
            };
            return map[status] || { text: '알 수 없음', class: '' };
        }

        function formatDate(dateStr) {
            if (!dateStr) return '-';
            const d = new Date(dateStr);
            return d.getFullYear() + '-' +
                   String(d.getMonth() + 1).padStart(2, '0') + '-' +
                   String(d.getDate()).padStart(2, '0');
        }

        function formatDateTime(dateStr) {
            if (!dateStr) return '-';
            const d = new Date(dateStr);
            return d.getFullYear() + '-' +
                   String(d.getMonth() + 1).padStart(2, '0') + '-' +
                   String(d.getDate()).padStart(2, '0') + ' ' +
                   String(d.getHours()).padStart(2, '0') + ':' +
                   String(d.getMinutes()).padStart(2, '0') + ':' +
                   String(d.getSeconds()).padStart(2, '0');
        }

        function formatNumber(num) {
            return num ? num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',') : '0';
        }

        function approveOrder(orderCode) {
            if (!confirm('승인하시겠습니까?')) return;

            fetch('/api/orders/' + orderCode + '/approve', {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ requester_empno: CURRENT_USER_EMPNO })
            })
            .then(r => r.json())
            .then(data => {
                if (data.success) {
                    showToast('✓ 승인 완료', 'success');
                    setTimeout(() => loadOrders(), 500);  
                } else {
                    showToast('✗ ' + data.message, 'error');
                }
            })
            .catch(error => {
                console.error('에러 발생:', error);
                showToast('✗ 서버 오류', 'error');
            });
        }

        function rejectOrder(orderCode) {
            const reason = prompt('반려 사유:');
            if (!reason || !reason.trim()) {
                alert('반려 사유는 필수입니다.');
                return;
            }

            fetch('/api/orders/' + orderCode + '/reject', {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    requester_empno: CURRENT_USER_EMPNO,
                    order_refuse: reason
                })
            })
            .then(r => r.json())
            .then(data => {
                if (data.success) {
                    showToast('✓ 반려 완료', 'success');
                    setTimeout(() => loadOrders(), 500);
                } else {
                    showToast('✗ ' + data.message, 'error');
                }
            })
            .catch(error => {
                console.error('에러 발생:', error);
                showToast('✗ 서버 오류', 'error');
            });
        }

        function cancelOrder(orderCode) {
            if (!confirm('회수하시겠습니까?')) return;

            fetch('/api/orders/' + orderCode + '/cancel', {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ empNo: CURRENT_USER_EMPNO })
            })
            .then(r => r.json())
            .then(data => {
                if (data.success) {
                    showToast('✓ 회수 완료', 'success');
                    setTimeout(() => loadOrders(), 500);
                } else {
                    showToast('✗ ' + data.message, 'error');
                }
            })
            .catch(error => {
                console.error('에러 발생:', error);
                showToast('✗ 서버 오류', 'error');
            });
        }

        function openRegisterModal() {
            document.getElementById('registerModal').classList.add('active');
            const today = new Date().toISOString().split('T')[0];
            document.getElementById('orderDate').value = today;
            document.getElementById('orderDate').setAttribute('min', today);
        }

        function closeRegisterModal() {
            document.getElementById('registerModal').classList.remove('active');
            document.getElementById('registerForm').reset();
            document.getElementById('unitPrice').value = '';
            document.getElementById('totalPrice').value = '';
        }

        function submitRegister() {
            const form = document.getElementById('registerForm');

            if (!form.checkValidity()) {
                alert('필수 항목을 모두 입력해주세요.');
                return;
            }

            const productCode = parseInt(document.getElementById('productCode').value);
            const product = PRODUCT_LIST.find(p => p.PRODUCT_CODE  === productCode);

            const requestData = {
                cust_code: parseInt(document.getElementById('custCode').value),
                drafter_empno: CURRENT_USER_EMPNO,
                reg_emp_no: CURRENT_USER_EMPNO,
                orderDetails: [
                    {
                        product_code: productCode,
                        orders_drafter_empno: CURRENT_USER_EMPNO,
                        order_amount: parseInt(document.getElementById('quantity').value),
                        order_unit_price: product.PRODUCT_PRICE,
                        reg_emp_no: CURRENT_USER_EMPNO
                    }
                ]
            };

            console.log('등록 데이터:', requestData);

            fetch('/api/orders', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(requestData)
            })
            .then(response => response.json())
            .then(data => {
                if (data.orderCode) {
                    showToast('✓ 수주 등록 완료 (주문번호: ' + data.orderCode + ')', 'success');
                    closeRegisterModal();
                    loadOrders();
                } else {
                    showToast('✗ 등록 실패: ' + (data.message || '알 수 없는 오류'), 'error');
                }
            })
            .catch(error => {
                console.error('등록 오류:', error);
                showToast('✗ 서버 오류가 발생했습니다.', 'error');
            });
        }

        document.getElementById('registerModal').addEventListener('click', function(e) {
            if (e.target === this) {
                closeRegisterModal();
            }
        });
    </script>
</body>
</html>