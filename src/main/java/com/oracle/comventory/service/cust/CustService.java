package com.oracle.comventory.service.cust;

import java.util.List;

import com.oracle.comventory.domain.cust.Cust;
import com.oracle.comventory.dto.custTable.CustTableDto;

public interface CustService {

    Cust save(Cust cust);

    Long totalCount(CustTableDto custDto);

    List<CustTableDto> findAll();

    List<CustTableDto> findPagingCust(CustTableDto custTableDto);

    Cust findById(Long cust_code);

    void changeStatus(Long cust_code, Integer cust_del_status);

    Cust update(Cust cust);
    
    Long findNextCustCode();
}