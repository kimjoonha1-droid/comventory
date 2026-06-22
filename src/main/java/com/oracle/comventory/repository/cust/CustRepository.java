package com.oracle.comventory.repository.cust;

import java.util.List;

import com.oracle.comventory.domain.cust.Cust;
import com.oracle.comventory.dto.custTable.CustTableDto;

public interface CustRepository {

    Cust save(Cust cust);                        // 등록

    Long totalCount(CustTableDto custDto);       // 전체 개수

    List<CustTableDto> findAll();                // 전체 목록

    List<CustTableDto> findPagingCust(CustTableDto custTableDto); // 페이징 목록

    Cust findById(Long cust_code);               // 상세 조회
 
    void changeStatus(Long cust_code, Integer cust_del_status); 	// 상태 변경

    Cust update(Cust cust);                      // 수정
    
    Long findNextCustCode();
}