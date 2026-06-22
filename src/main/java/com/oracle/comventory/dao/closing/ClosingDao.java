package com.oracle.comventory.dao.closing;

public interface ClosingDao {

	int isTrueClosed(String closingYmd);
	boolean isTempClosed(String closingYmd);
	boolean isBeforeTrueClosed(String ymd);
}
