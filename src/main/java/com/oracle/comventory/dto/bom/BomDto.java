package com.oracle.comventory.dto.bom;

import java.util.Date;

import lombok.Data;

@Data
public class BomDto {
    private int productCode;
    private String productName;

    private int productWon;
    private String productWonName;

    private int needQty;
    private int regEmpNo;
    private Date regDate;

    // 목록용
    private int partCount;
    private int totalNeedQty;

    // 조회/페이징용
    private String search;
    private String keyword;
    private String pageNum;
    private int start;
    private int end;
    private String currentPage;
}