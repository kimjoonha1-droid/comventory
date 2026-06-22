package com.oracle.comventory.dao.purchase;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.comventory.dto.bom.BomDto;
import com.oracle.comventory.dto.custTable.CustTableDto;
import com.oracle.comventory.dto.product.ProductDto;
import com.oracle.comventory.dto.purchase.PurchaseOrderDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PurchaseOrderDaoImpl implements PurchaseOrderDao {

	private final SqlSession session;

	@Override
	public int totalPor() {
		int totPorCount = 0;
		System.out.println("PurchaseOrderDao totalPor start...");
		
		try {
			totPorCount = session.selectOne("totalPor");
			System.out.println("PurchaseOrderDao totalPor totPorCount-> "+totPorCount);
		} catch (Exception e) {
			System.out.println("PurchaseOrderDao totalPor ERROR"+e.getMessage());
		}
		
		return totPorCount;
	}

	@Override
	public List<PurchaseOrderDto> pOrderList(PurchaseOrderDto por) {
		List<PurchaseOrderDto> pOrderList = null;
		System.out.println("BomDaoImpl bomList start...");

		try {
			pOrderList = session.selectList("pOrderList", por);
			System.out.println("BomDaoImpl bomList empList.size()-> "+pOrderList.size());
		} catch (Exception e) {
			System.out.println("BomDaoImpl bomList e.getMessage()-> "+e.getMessage());
		}
		return pOrderList;
	}

	@Override
	public PurchaseOrderDto purchaseDetail(int purchaseId) {
	    return session.selectOne("purchaseDetail", purchaseId);
	}

	@Override
	public List<ProductDto> partList() {
		// TODO Auto-generated method stub
		return session.selectList("purchasePartList");
	}

	@Override
	public int insertPurchase(PurchaseOrderDto orderDto) {
		return session.insert("insertPurchase", orderDto);
	}

	@Override
	public List<CustTableDto> custList() {
	    return session.selectList("purchaseCustList");
	}

	@Override
	public int deletePurchase(int purchaseId) {
	    return session.delete("deletePurchase", purchaseId);
	}

	@Override
	public int updatePurchase(PurchaseOrderDto orderDto) {
		return session.update("updatePurchase", orderDto);
	}
}
