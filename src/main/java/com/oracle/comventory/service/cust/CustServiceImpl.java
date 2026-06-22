package com.oracle.comventory.service.cust;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.comventory.domain.cust.Cust;
import com.oracle.comventory.dto.custTable.CustTableDto;
import com.oracle.comventory.repository.cust.CustRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class CustServiceImpl implements CustService {

    private final CustRepository custRepository;

    @Override
    public Cust save(Cust cust) {
        log.info("CustServiceImpl save start cust -> {}", cust);
        return custRepository.save(cust);
    }

    @Override
    public Long totalCount(CustTableDto custDto) {

        return custRepository.totalCount(custDto);
    }

    @Override
    public List<CustTableDto> findAll() {
        log.info("CustServiceImpl findAll start");
        return custRepository.findAll();
    }

    @Override
    public List<CustTableDto> findPagingCust(CustTableDto custTableDto) {
        log.info("CustServiceImpl findPagingCust start custTableDto -> {}", custTableDto);
        return custRepository.findPagingCust(custTableDto);
    }

    @Override
    public Cust findById(Long cust_code) {
        log.info("CustServiceImpl findById start cust_code -> {}", cust_code);
        return custRepository.findById(cust_code);
    }

    @Override
    @Transactional
    public void changeStatus(Long cust_code, Integer cust_del_status) {

        Cust cust = custRepository.findById(cust_code);

        if(cust != null) {
            cust.changeCustDelStatus(cust_del_status);
        }
    }

    @Override
    public Cust update(Cust cust) {
        log.info("CustServiceImpl update start cust -> {}", cust);
        return custRepository.update(cust);
    }

 // 다음 거래처 코드 조회
    @Override
    public Long findNextCustCode() {

        log.info("CustServiceImpl findNextCustCode start");

        return custRepository.findNextCustCode();
    }
    
}