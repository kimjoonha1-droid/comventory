package com.oracle.comventory.service.bom;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.comventory.dao.bom.BomDao;
import com.oracle.comventory.dto.bom.BomDto;
import com.oracle.comventory.dto.product.ProductDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BomServiceImpl implements BomService {

    private final BomDao bda;

    @Override
    public int totalBom() {
        return bda.totalBom();
    }

    @Override
    public List<BomDto> bomList(BomDto bom) {
        return bda.bomList(bom);
    }

    @Override
    public List<BomDto> bomDetailList(int productCode) {
        return bda.bomDetailList(productCode);
    }

    @Override
    public List<BomDto> finishedProductList() {
        return bda.finishedProductList();
    }

    @Override
    public List<BomDto> partList() {
        return bda.partList();
    }

    @Override
    public int insertBom(BomDto bom) {
        return bda.insertBom(bom);
    }

    @Override
    public int deleteBom(int productCode, int productWon) {
        return bda.deleteBom(productCode, productWon);
    }

    @Override
    public BomDto bomOne(int productCode, int productWon) {
        return bda.bomOne(productCode, productWon);
    }

    @Override
    public int updateBom(BomDto bom) {
        return bda.updateBom(bom);
    }

	@Override
	public List<ProductDto> finishedProductListForAdd(int productCode) {
		return bda.finishedProductListForAdd(productCode);
	}
}