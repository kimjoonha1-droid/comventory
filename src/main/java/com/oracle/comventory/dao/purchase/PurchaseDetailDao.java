package com.oracle.comventory.dao.purchase;

import java.util.List;

import com.oracle.comventory.dto.purchase.PurchaseDetailDto;
import com.oracle.comventory.dto.purchase.PurchaseOrderDto;

public interface PurchaseDetailDao {

	int 						totalPde();
	List<PurchaseDetailDto> 	pDetailList(PurchaseDetailDto pde);
	List<PurchaseDetailDto> 	purchaseItemList(int purchaseId);
	int insertPurchaseDetail	(PurchaseDetailDto detailDto);
	int deletePurchaseDetail	(int purchaseId);
	int updatePurchaseDetail	(PurchaseDetailDto detailDto);
	

}
