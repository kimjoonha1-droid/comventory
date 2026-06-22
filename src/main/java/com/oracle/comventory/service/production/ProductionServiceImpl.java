package com.oracle.comventory.service.production;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.comventory.dao.production.ProductionDao;
import com.oracle.comventory.dto.product.ProductDto;
import com.oracle.comventory.dto.production.ProductionDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductionServiceImpl implements ProductionService {

    private final ProductionDao productionDao;

    @Override
    public int totalProduction() {
        return productionDao.totalProduction();
    }

    @Override
    public List<ProductionDto> productionList(ProductionDto productionDto) {
        return productionDao.productionList(productionDto);
    }

    @Override
    public int insertProduction(ProductionDto productionDto) {
        return productionDao.insertProduction(productionDto);
    }

    @Override
    public List<ProductDto> finishedProductionList() {
        return productionDao.finishedProductionList();
    }

    @Override
    public ProductionDto productionDetail(int productionCode) {
        return productionDao.productionDetail(productionCode);
    }

	@Override
	public int updateProduction(ProductionDto production) {
		// TODO Auto-generated method stub
		return productionDao.updateProduction(production);
	}

	@Override
	public int deleteProduction(int productionCode) {
		// TODO Auto-generated method stub
		return productionDao.deleteProduction(productionCode);
	}
}
