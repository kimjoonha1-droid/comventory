package com.oracle.comventory.service.purchase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.comventory.dao.purchase.PurchaseDetailDao;
import com.oracle.comventory.dao.purchase.PurchaseOrderDao;
import com.oracle.comventory.dto.custTable.CustTableDto;
import com.oracle.comventory.dto.product.ProductDto;
import com.oracle.comventory.dto.purchase.PurchaseDetailDto;
import com.oracle.comventory.dto.purchase.PurchaseOrderDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

	private final PurchaseDetailDao	pde;
	private final PurchaseOrderDao	por;
	
	@Override
	public int totalPde() {
		int totPdeCnt = pde.totalPde();
		return totPdeCnt;
	}
	
	@Override
	public int totalPor() {
		int totPorCnt = por.totalPor();
		return totPorCnt;
	}

	@Override
	public List<PurchaseDetailDto> pdeList(PurchaseDetailDto pDetail) {
	    return pde.pDetailList(pDetail);
	}
	
	@Override
	public List<PurchaseOrderDto> porList(PurchaseOrderDto pOrder) {
	    return por.pOrderList(pOrder);
	}

	@Override
	public PurchaseOrderDto purchaseDetail(int purchaseId) {
	    return por.purchaseDetail(purchaseId);
	}

	@Override
	public List<PurchaseDetailDto> purchaseItemList(int purchaseId) {
	    return pde.purchaseItemList(purchaseId);
	}

	@Override
	public int insertPurchase(PurchaseOrderDto orderDto,
	                          PurchaseDetailDto detailDto) {

	    int result = por.insertPurchase(orderDto);
	    pde.insertPurchaseDetail(detailDto);

	    return result;
	}
	
	@Override
	public List<ProductDto> partList() {
		return por.partList();
	}

	@Override
	public List<CustTableDto> custList() {
		return por.custList();
	}

	@Override
	public int deletePurchase(int purchaseId) {
	    pde.deletePurchaseDetail(purchaseId);
	    return por.deletePurchase(purchaseId);
	}

	@Override
	public int updatePurchase(PurchaseOrderDto orderDto,
	                          PurchaseDetailDto detailDto) {

	    // 발주 기본정보 수정
	    por.updatePurchase(orderDto);

	    // 상세정보 수정
	    return pde.updatePurchaseDetail(detailDto);
	}
	
	

}
