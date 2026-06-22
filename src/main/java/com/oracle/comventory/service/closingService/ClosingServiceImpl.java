package com.oracle.comventory.service.closingService;

import org.springframework.stereotype.Service;

import com.oracle.comventory.dao.closing.ClosingDao;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClosingServiceImpl implements ClosingService {

    private final ClosingDao closingDao;

    @Override
    public boolean isTrueClosed(String closingYmd) {

        return closingDao.isTrueClosed(closingYmd) > 0;
    }

    @Override
    public boolean isTempClosed(String closingYmd) {
        return closingDao.isTempClosed(closingYmd);
    }

    @Override
    public boolean isBeforeTrueClosed(String ymd) {
        return closingDao.isBeforeTrueClosed(ymd);
    }
}
