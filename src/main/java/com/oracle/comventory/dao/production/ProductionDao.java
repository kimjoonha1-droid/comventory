package com.oracle.comventory.dao.production;

import java.util.List;

import com.oracle.comventory.dto.product.ProductDto;
import com.oracle.comventory.dto.production.ProductionDto;

public interface ProductionDao {

	int 					totalProduction();
	List<ProductionDto> 	productionList(ProductionDto productionDto);
	int insertProduction	(ProductionDto productionDto);
	List<ProductDto> 		finishedProductionList();
	int deletePurchase		(int purchaseId);
	ProductionDto 			productionDetail(int productionCode);
	int updateProduction	(ProductionDto production);
	int deleteProduction	(int productionCode);
}
