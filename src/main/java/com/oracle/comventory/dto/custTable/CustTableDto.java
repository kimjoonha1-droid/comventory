package com.oracle.comventory.dto.custTable;

import com.oracle.comventory.domain.cust.Cust;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustTableDto {

    private Long custCode;
    private String custName;
    private Integer custType;
    private String businessNo;
    private String ceoName;
    private String custTel;
    private String custEmail;
    private String custAddress;
    private Integer custDelStatus;
    private Long regEmpNo;
    private String regDate;

    // 코드명 출력용
    private String custTypeName;
    private String custDelStatusName;

    // 조회용
    private String pageNum;
    private int start;
    private int end;
    private String currentPage;
    
    //검색
    private String keyword;
    private String searchType;
    

    // Entity → DTO 변환 생성자
    public CustTableDto(Cust cust) {

        this.custCode = cust.getCust_code();
        this.custName = cust.getCust_name();
        this.custType = cust.getCust_type();
        this.businessNo = cust.getBusiness_no();
        this.ceoName = cust.getCeo_name();
        this.custTel = cust.getCust_tel();
        this.custEmail = cust.getCust_email();
        this.custAddress = cust.getCust_address();
        this.custDelStatus = cust.getCust_del_status();
        this.regEmpNo = cust.getReg_emp_no();

        if (cust.getReg_date() != null) {
            this.regDate = cust.getReg_date().toString();
        }
    }

    // 목록 조회용 생성자
    public CustTableDto(Cust cust,
                        String custTypeName,
                        String custDelStatusName) {

        this(cust);

        this.custTypeName = custTypeName;
        this.custDelStatusName = custDelStatusName;
    }
}