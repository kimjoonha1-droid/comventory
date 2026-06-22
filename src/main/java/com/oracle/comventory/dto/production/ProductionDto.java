package com.oracle.comventory.dto.production;

import java.util.Date;

import lombok.Data;

@Data
public class ProductionDto {
    private int productionCode;
    private int productCode;
    private String productName;

    private int productionQty;
    private int productionStatus;
    private String productionStatusName;
    private String cancelReason;

    private String completeDate;
    private int regEmpNo;
    private Date regDate;

    // 조회용
    private String search;
    private String keyword;
    private String pageNum;
    private int start;
    private int end;

    // Page 정보
    private String currentPage;
}