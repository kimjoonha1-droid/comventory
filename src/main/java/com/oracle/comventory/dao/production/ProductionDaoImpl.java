package com.oracle.comventory.dao.production;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.comventory.dto.product.ProductDto;
import com.oracle.comventory.dto.production.ProductionDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductionDaoImpl implements ProductionDao {

    private final SqlSession session;

    @Override
    public int totalProduction() {
        int totalProductionCount = 0;

        try {
            totalProductionCount =
                session.selectOne("productionTotal");
            System.out.println("ProductionDaoImpl totalProduction start...");
        } catch(Exception e) {
            System.out.println("ProductionDaoImpl totalProduction ERROR"+e.getMessage());
        }

        return totalProductionCount;
    }

    @Override
    public List<ProductionDto> productionList(ProductionDto productionDto) {
    	List<ProductionDto> prodList = null;
    	System.out.println("ProductionDaoImpl productionList start...");
    	try {
    		prodList = session.selectList("productionList", productionDto);
			System.out.println("ProductionDaoImpl productionList prodList.size()-> "+prodList.size());
		} catch (Exception e) {
			System.out.println("ProductionDaoImpl productionList e.getMessage()-> "+e.getMessage());
		}
		return prodList;
    }

    @Override
    public int insertProduction(ProductionDto productionDto) {
        return session.insert("insertProduction", productionDto);
    }

    @Override
    public List<ProductDto> finishedProductionList() {
        return session.selectList("finishedProductionList");
    }
    
    @Override
    public int deletePurchase(int purchaseId) {
        return session.delete("deletePurchase", purchaseId);
    }
    
    @Override
    public ProductionDto productionDetail(int productionCode) {
        return session.selectOne("productionDetail", productionCode);
    }

	@Override
	public int updateProduction(ProductionDto production) {
		return session.update("updateProduction", production);
	}

	@Override
	public int deleteProduction(int productionCode) {
		return session.delete("deleteProduction", productionCode);
	}
}
