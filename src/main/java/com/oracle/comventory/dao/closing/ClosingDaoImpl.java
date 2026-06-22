package com.oracle.comventory.dao.closing;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ClosingDaoImpl implements ClosingDao {

	private final SqlSession session;

    @Override
    public int isTrueClosed(String closingYmd) {

        return session.selectOne("closing.isTrueClosed",closingYmd);
    }

    @Override
    public boolean isTempClosed(String closingYmd) {
        return session.selectOne("closing.isTempClosed", closingYmd);
    }

    @Override
    public boolean isBeforeTrueClosed(String ymd) {
        return session.selectOne("closing.isBeforeTrueClosed", ymd);
    }

}
