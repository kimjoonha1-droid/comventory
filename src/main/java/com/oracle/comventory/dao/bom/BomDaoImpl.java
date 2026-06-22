package com.oracle.comventory.dao.bom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.comventory.dto.bom.BomDto;
import com.oracle.comventory.dto.product.ProductDto;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BomDaoImpl implements BomDao {

    private final SqlSession session;

    @Override
    public int totalBom() {
        return session.selectOne("totalBom");
    }

    @Override
    public List<BomDto> bomList(BomDto bom) {
        return session.selectList("bomList", bom);
    }

    @Override
    public List<BomDto> bomDetailList(int productCode) {
        return session.selectList("bomDetailList", productCode);
    }

    @Override
    public List<BomDto> finishedProductList() {
        return session.selectList("bomFinishedProductList");
    }

    @Override
    public List<BomDto> partList() {
        return session.selectList("bomPartList");
    }

    @Override
    public int insertBom(BomDto bom) {
        return session.insert("insertBom", bom);
    }

    @Override
    public int deleteBom(int productCode, int productWon) {
        Map<String, Object> map = new HashMap<>();
        map.put("productCode", productCode);
        map.put("productWon", productWon);

        return session.delete("deleteBom", map);
    }

    @Override
    public BomDto bomOne(int productCode, int productWon) {
        Map<String, Object> map = new HashMap<>();
        map.put("productCode", productCode);
        map.put("productWon", productWon);

        return session.selectOne("bomOne", map);
    }

    @Override
    public int updateBom(BomDto bom) {
        return session.update("updateBom", bom);
    }

    @Override
    public List<ProductDto> finishedProductListForAdd(int productCode) {

        return session.selectList(
                "bomFinishedProductListForAdd",
                productCode);
    }
}