package com.oracle.comventory.service.closingService;

public interface ClosingService {
	boolean isTrueClosed(String closingYmd);
	boolean isTempClosed(String closingYmd);
	boolean isBeforeTrueClosed(String ymd);
}
