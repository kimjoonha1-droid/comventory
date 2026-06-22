package com.oracle.comventory.dao.purchase;

import java.util.List;

import com.oracle.comventory.dto.custTable.CustTableDto;
import com.oracle.comventory.dto.product.ProductDto;
import com.oracle.comventory.dto.purchase.PurchaseOrderDto;

public interface PurchaseOrderDao {
	int 					totalPor();
	List<PurchaseOrderDto> 	pOrderList(PurchaseOrderDto por);
	PurchaseOrderDto 		purchaseDetail(int purchaseId);
	List<ProductDto> 		partList();
	int insertPurchase		(PurchaseOrderDto orderDto);
	List<CustTableDto> 		custList();
	int deletePurchase		(int purchaseId);
	int updatePurchase		(PurchaseOrderDto orderDto);

}
