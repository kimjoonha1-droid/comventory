package com.oracle.comventory.service.purchase;

import java.util.List;

import com.oracle.comventory.dto.custTable.CustTableDto;
import com.oracle.comventory.dto.product.ProductDto;
import com.oracle.comventory.dto.purchase.PurchaseDetailDto;
import com.oracle.comventory.dto.purchase.PurchaseOrderDto;

public interface PurchaseService {
	
	int 						totalPde();
	int 						totalPor();
	List<PurchaseDetailDto> 	pdeList(PurchaseDetailDto pDetail);
	List<PurchaseOrderDto> 		porList(PurchaseOrderDto pOrder);
	PurchaseOrderDto 			purchaseDetail(int purchaseId);
	List<PurchaseDetailDto> 	purchaseItemList(int purchaseId);
	List<ProductDto> 			partList();
	int insertPurchase			(PurchaseOrderDto orderDto, PurchaseDetailDto detailDto);
	List<CustTableDto> 			custList();
	int deletePurchase(int purchaseId);
	int updatePurchase(PurchaseOrderDto orderDto, PurchaseDetailDto detailDto);
}
