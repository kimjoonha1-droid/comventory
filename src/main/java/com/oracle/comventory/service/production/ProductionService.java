package com.oracle.comventory.service.production;

import java.util.List;

import com.oracle.comventory.dto.product.ProductDto;
import com.oracle.comventory.dto.production.ProductionDto;

public interface ProductionService {

	List<ProductionDto> 	productionList(ProductionDto productionDto);
	int 					totalProduction();
	int 					insertProduction(ProductionDto productionDto);
	List<ProductDto> 		finishedProductionList();
	ProductionDto productionDetail(int productionCode);
	int 					updateProduction(ProductionDto production);
	int 					deleteProduction(int productionCode);

}
