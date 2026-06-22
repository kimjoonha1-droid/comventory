package com.oracle.comventory.dao.bom;

import java.util.List;

import com.oracle.comventory.dto.bom.BomDto;
import com.oracle.comventory.dto.product.ProductDto;

public interface BomDao {
    int totalBom();

    List<BomDto> bomList(BomDto bom);
    List<BomDto> bomDetailList(int productCode);

    List<BomDto> finishedProductList();
    List<ProductDto> finishedProductListForAdd(int productCode);
    List<BomDto> partList();

    int insertBom(BomDto bom);
    int deleteBom(int productCode, int productWon);

    BomDto bomOne(int productCode, int productWon);
    int updateBom(BomDto bom);
	
}