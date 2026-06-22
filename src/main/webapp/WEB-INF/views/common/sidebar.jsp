<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${sessionScope.loginUser.user_access != 800 && sessionScope.loginUser.user_access != 900}">
	<div class="bg-white border-end" id="sidebar-wrapper">
		<div class="sidebar-heading border-bottom bg-light p-3 fw-bold">메뉴</div>
		<div class="list-group list-group-flush">
			
	
			<a href="${pageContext.request.contextPath}/"
			    class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
			    <span>메인 페이지</span>
			</a>
			
			<a href="#boardSubmenu" data-bs-toggle="collapse"
			    aria-expanded="false"
			    class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
			    <span>게시판</span> <span class="arrow-icon">▼</span>
			</a>
			
			<div class="collapse" id="boardSubmenu">
			    <div class="list-group list-group-flush">
			        <a href="${pageContext.request.contextPath}/board/list"
			            class="list-group-item list-group-item-action ps-4 submenu-item">
			            └ 문의 게시판 </a>
			        <a href="${pageContext.request.contextPath}/internal/list"
			            class="list-group-item list-group-item-action ps-4 submenu-item">
			            └ 직원 게시판 </a>
			    </div>
			</div>
			
			<a href="#bomSubmenu" data-bs-toggle="collapse"
				aria-expanded="false"
				class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
				<span>BOM 관리</span> <span class="arrow-icon">▼</span>
			</a>
			
			<div class="collapse" id="bomSubmenu">
				<div class="list-group list-group-flush">
					<a href="${pageContext.request.contextPath}/bom/list"
					   class="list-group-item list-group-item-action ps-4 submenu-item">
					   └ BOM 목록
					</a>
				</div>
			</div>
			
			<a href="#deptSubmenu" data-bs-toggle="collapse"
				aria-expanded="false"
				class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
				<span>부서 관리</span> <span class="arrow-icon">▼</span>
			</a>
			
			<div class="collapse" id="deptSubmenu">
				<div class="list-group list-group-flush">
					<a href="${pageContext.request.contextPath}/dept/deptList"
						class="list-group-item list-group-item-action ps-4 submenu-item">
						└ 부서 목록 </a>
				</div>
			</div>
			
			<a href="#empSubmenu" data-bs-toggle="collapse"
				aria-expanded="false"
				class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
				<span>사원 관리</span> <span class="arrow-icon">▼</span>
			</a>
			
			<div class="collapse" id="empSubmenu">
				<div class="list-group list-group-flush">
					<a href="${pageContext.request.contextPath}/emp/empMain"
						class="list-group-item list-group-item-action ps-4 submenu-item">
						└ 사원 목록 </a>
					<a href="${pageContext.request.contextPath}/emp/empInsert"
						class="list-group-item list-group-item-action ps-4 submenu-item">
						└ 사원 등록 </a>
				</div>
			</div>
					
			<a href="${pageContext.request.contextPath}/cust/custMain"
			    class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
			    <span>거래처 관리</span>
			</a>
			
			<a href="${pageContext.request.contextPath}/product/productMain"
			    class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
			    <span>제품 관리</span>
			</a>
			
			<a href="#inventorySubmenu" data-bs-toggle="collapse"
				aria-expanded="false"
				class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
				<span>재고 관리</span> <span class="arrow-icon">▼</span>
			</a>
			
			<div class="collapse" id="inventorySubmenu">
				<div class="list-group list-group-flush">
					<a href="${pageContext.request.contextPath}/inventory/currentStockView"
					   class="list-group-item list-group-item-action ps-4 submenu-item">
					    └ 현재 재고 조회
					</a>
					
					<a href="${pageContext.request.contextPath}/inventory/stockListView"
					   class="list-group-item list-group-item-action ps-4 submenu-item">
					    └ 월별 재고 조회
					</a>
					
					<a href="${pageContext.request.contextPath}/inventory/physicalInventoryView"
					   class="list-group-item list-group-item-action ps-4 submenu-item">
					    └ 실사재고 현황 및 처리
					</a>
					
					<a href="${pageContext.request.contextPath}/inventory/inventoryAdjustmentView"
					   class="list-group-item list-group-item-action ps-4 submenu-item">
					    └ 제품 수불처리/조회
					</a>
				</div>
			</div>
			
			<a href="#orderSubmenu" data-bs-toggle="collapse"
				aria-expanded="false"
				class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
				<span>판매/수주 관리</span> <span class="arrow-icon">▼</span>
			</a>
			
			<div class="collapse" id="orderSubmenu">
				<div class="list-group list-group-flush">
					<a href="${pageContext.request.contextPath}/order/orderMain"
						class="list-group-item list-group-item-action ps-4 submenu-item">
						└ 수주/기안 조회 및 승인 </a>
				</div>
			</div>
			
			<a href="#purchaseSubmenu" data-bs-toggle="collapse"
				aria-expanded="false"
				class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
				<span>구매 관리</span> <span class="arrow-icon">▼</span>
			</a>
			
			<div class="collapse" id="purchaseSubmenu">
				<div class="list-group list-group-flush">
					<a href="${pageContext.request.contextPath}/purchase/list"
						class="list-group-item list-group-item-action ps-4 submenu-item">
						└ 구매/발주 목록 </a>
				</div>
			</div>
	
			
			<a href="#productionSubmenu" data-bs-toggle="collapse"
			   aria-expanded="false"
			   class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
			    <span>생산 관리</span> <span class="arrow-icon">▼</span>
			</a>
			
			<div class="collapse" id="productionSubmenu">
			    <div class="list-group list-group-flush">
			        <a href="${pageContext.request.contextPath}/production/list"
			           class="list-group-item list-group-item-action ps-4 submenu-item">
			           └ 생산 목록
			        </a>
			    </div>
			</div>
			
		</div>
	</div>
</c:if>