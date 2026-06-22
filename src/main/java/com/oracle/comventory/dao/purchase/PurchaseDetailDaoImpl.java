package com.oracle.comventory.dao.purchase;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.comventory.dto.purchase.PurchaseDetailDto;
import com.oracle.comventory.dto.purchase.PurchaseOrderDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PurchaseDetailDaoImpl implements PurchaseDetailDao {
	
	private final SqlSession session;

	@Override
	public int totalPde() {
		int totPdeCount = 0;
		System.out.println("PurchaseDetailDao totalPde start...");
		
		try {
			totPdeCount = session.selectOne("totalPde");
			System.out.println("PurchaseDetailDao totalPde totPdeCount-> "+totPdeCount);
		} catch (Exception e) {
			System.out.println("PurchaseDetailDao totalPde ERROR"+e.getMessage());
		}
		
		return totPdeCount;
	}

	@Override
	public List<PurchaseDetailDto> pDetailList(PurchaseDetailDto pde) {
		List<PurchaseDetailDto> pDetailList = null;
		System.out.println("PurchaseDetailDto pDetailList start...");

		try {
			pDetailList = session.selectList("pDetailList", pde);
			System.out.println("PurchaseDetailDto pDetailList pDetailList.size()-> "+pDetailList.size());
		} catch (Exception e) {
			System.out.println("PurchaseDetailDto pDetailList e.getMessage()-> "+e.getMessage());
		}
		return pDetailList;
	}

	@Override
	public List<PurchaseDetailDto> purchaseItemList(int purchaseId) {
	    return session.selectList("purchaseItemList", purchaseId);
	}
	
	@Override
	public int insertPurchaseDetail(PurchaseDetailDto detailDto) {

	    return session.insert(
	        "insertPurchaseDetail",
	        detailDto
	    );
	}
	
	@Override
	public int deletePurchaseDetail(int purchaseId) {
	    return session.delete("deletePurchaseDetail", purchaseId);
	}

	@Override
	public int updatePurchaseDetail(PurchaseDetailDto detailDto) {
		return session.update("updatePurchaseDetail",detailDto);
	}
	
}
