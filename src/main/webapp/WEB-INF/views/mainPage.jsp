<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>메인 대시보드</title>
    
    <%@ include file="common/staticResources.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
	<style>
	    .dashboard-page {
	        max-width: 1180px;
	        margin: 0 auto;
	    }
	
	    .dashboard-header {
	        display: flex;
	        justify-content: space-between;
	        align-items: center;
	        margin-bottom: 18px;
	    }
	
	    .summary-grid {
	        display: grid;
	        grid-template-columns: repeat(4, minmax(0, 1fr));
	        gap: 12px;
	        margin-bottom: 12px;
	    }
	
	    .summary-card,
	    .chart-panel,
	    .shortage-panel {
	        background: #fff;
	        border: 1px solid #d8dee8;
	        border-radius: 8px;
	        box-shadow: 0 2px 8px rgba(15, 23, 42, 0.04);
	    }
	
	    .summary-card {
	        min-height: 132px;
	        padding: 18px;
	        display: flex;
	        flex-direction: column;
	        justify-content: space-between;
	    }
	
	    .summary-top {
	        display: flex;
	        justify-content: space-between;
	        align-items: center;
	        font-size: 16px;
	        font-weight: 700;
	        color: #111827;
	    }
	
	    .summary-top i {
	        font-size: 22px;
	    }
	
	    .text-purple {
	        color: #7c3aed;
	    }
	
	    .summary-value {
	        margin-top: 24px;
	        font-size: 26px;
	        font-weight: 800;
	        color: #111827;
	        line-height: 1;
	    }
	
	    .summary-value span {
	        font-size: 14px;
	        font-weight: 700;
	    }
	
	    .summary-sub {
	        margin-top: 8px;
	        color: #374151;
	        font-size: 14px;
	    }
	
	    .dashboard-bottom {
	        display: grid;
	        grid-template-columns: minmax(0, 2fr) minmax(280px, 1fr);
	        gap: 12px;
	    }
	
	    .chart-panel,
	    .shortage-panel {
	        padding: 16px;
	    }
	
	    .chart-panel h5,
	    .shortage-panel h5 {
	        margin: 0 0 10px;
	        font-size: 17px;
	        font-weight: 800;
	    }
	
	    .chart-wrap {
	        position: relative;
	        height: 250px;
	    }
	
	    .shortage-panel {
	        display: flex;
	        flex-direction: column;
	        gap: 8px;
	    }
	
	    .shortage-item {
	        display: flex;
	        justify-content: space-between;
	        align-items: center;
	        gap: 12px;
	        padding: 10px 12px;
	        border: 1px solid #e5e7eb;
	        border-radius: 6px;
	    }
	
	    .shortage-item div {
	        display: flex;
	        flex-direction: column;
	        gap: 2px;
	    }
	
	    .shortage-item div:last-child {
	        text-align: right;
	    }
	
	    .shortage-item strong {
	        color: #111827;
	        font-size: 14px;
	    }
	
	    .shortage-item span {
	        color: #4b5563;
	        font-size: 13px;
	    }
	
	    @media (max-width: 992px) {
	        .summary-grid {
	            grid-template-columns: repeat(2, minmax(0, 1fr));
	        }
	
	        .dashboard-bottom {
	            grid-template-columns: 1fr;
	        }
	    }
	
	    @media (max-width: 576px) {
	        .dashboard-header {
	            align-items: flex-start;
	            flex-direction: column;
	            gap: 6px;
	        }
	
	        .summary-grid {
	            grid-template-columns: 1fr;
	        }
	    }
	</style>

</head>
<body>
    <%@ include file="common/header.jsp" %>

    <div id="wrapper">
        <%@ include file="common/sidebar.jsp" %>
        
        <div id="page-content-wrapper">
			<main class="content p-4">
			    <div class="dashboard-header">
			        <h4 class="fw-bold mb-0">메인 대시보드</h4>
			    </div>
			
			    <div class="summary-grid">
			        <div class="summary-card">
			            <div class="summary-top">
			                <span>금일 총 입고량</span>
			                <i class="bi bi-arrow-down-square-fill text-primary"></i>
			            </div>
						<div class="summary-value">${summary.todayInQty} <span>EA</span></div>
						<div class="summary-sub">전일 대비 ${summary.inDiff >= 0 ? '+' : ''}${summary.inDiff} EA</div>
			        </div>
			
			        <div class="summary-card">
			            <div class="summary-top">
			                <span>금일 총 출고량</span>
			                <i class="bi bi-arrow-up-square-fill text-warning"></i>
			            </div>
						<div class="summary-value">${summary.todayOutQty} <span>EA</span></div>
						<div class="summary-sub">전일 대비 ${summary.outDiff >= 0 ? '+' : ''}${summary.outDiff} EA</div>
			        </div>
			
			        <div class="summary-card">
			            <div class="summary-top">
			                <span>재고 부족 품목</span>
			                <i class="bi bi-triangle-fill text-danger"></i>
			            </div>
			            <div class="summary-value">${summary.lackCount} <span>EA</span></div>
			            <div class="summary-sub">즉시 발주 검토 필요</div>
			        </div>
			
			        <div class="summary-card">
			            <div class="summary-top">
			                <span>전체 관리 품목</span>
			                <i class="bi bi-square-fill text-purple"></i>
			            </div>
						<div class="summary-value">${summary.totalProductCount} <span>EA</span></div>
						<div class="summary-sub">정상: ${summary.normalCount} · 부족: ${summary.lackCount}</div>
			        </div>
			    </div>
			
			    <div class="dashboard-bottom">
			        <section class="chart-panel">
			            <h5>최근 7일 입출고 현황</h5>
			            <div class="chart-wrap">
			                <canvas id="inoutChart"></canvas>
			            </div>
			        </section>
			
			        <section class="shortage-panel">
			            <h5>재고 부족 품목</h5>
			
						<c:forEach var="item" items="${shortageItems}">
						    <div class="shortage-item">
						        <div>
						            <strong>${item.product_name}</strong>
						            <span>ITM-${item.product_code}</span>
						        </div>
						        <div>
						            <strong>${item.qty} EA</strong>
						            <span>적정재고 ${item.proper_qty}</span>
						        </div>
						    </div>
						</c:forEach>
			        </section>
			    </div>
			</main>

        </div>
    </div>

    <%@ include file="common/footer.jsp" %>
	<script>
	    const ctx = document.getElementById('inoutChart');
	
	    new Chart(ctx, {
	        type: 'bar',
	        data: {
	        	labels: ${chartLabelsJson},
	            datasets: [
	            	{
	            	    label: '입고',
	            	    data: ${chartInQtyJson},
	            	    backgroundColor: '#3b7ddd',
	            	    maxBarThickness: 32,
	            	    categoryPercentage: 0.58,
	            	    barPercentage: 0.72
	            	},
	            	{
	            	    label: '출고',
	            	    data: ${chartOutQtyJson},
	            	    backgroundColor: '#f59f00',
	            	    maxBarThickness: 32,
	            	    categoryPercentage: 0.58,
	            	    barPercentage: 0.72
	            	}

	            ]
	        },
	        options: {
	            responsive: true,
	            maintainAspectRatio: false,
	            plugins: {
	                legend: {
	                    position: 'bottom'
	                }
	            },
	            scales: {
	                y: {
	                    beginAtZero: true
	                }
	            }
	        }
	    });
	</script>
</body>
</html>