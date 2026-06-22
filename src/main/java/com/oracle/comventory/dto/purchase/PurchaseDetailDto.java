package com.oracle.comventory.dto.purchase;

import java.util.Date;

import lombok.Data;

@Data
public class PurchaseDetailDto {
	private int    purchaseId;
	private int    productCode;
	private int    purchaseAmount;
	private int    purchaseProductPrice;
	private int    regEmpNo;
	private Date   regDate;
	
	//조회용
	private String search;		private String keyword;
	private String pageNum;
	private int start;			private int end;
	private String productName; // 조회 화면용
	private String statusName;
	// Page 정보
	private String currentPage;
}
