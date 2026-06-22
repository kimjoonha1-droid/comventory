package com.oracle.comventory.dto.purchase;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PurchaseOrderDto {
	private int purchaseId;
	private int custCode;
	private int totalPrice;
	private Date requestDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date inboundDate;
	private int regEmpNo;
	private Date regDate;
	private int    status;
	private String cancelReason;
	
	//조회용
	private String search;		private String keyword;
	private String pageNum;
	private int start;			private int end;
	private String statusName;
	// Page 정보
	private String currentPage;
}
