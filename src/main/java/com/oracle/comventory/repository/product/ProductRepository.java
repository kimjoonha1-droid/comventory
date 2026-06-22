package com.oracle.comventory.repository.product;

import java.util.List;

import com.oracle.comventory.domain.product.Product;
import com.oracle.comventory.dto.product.ProductDto;

public interface ProductRepository {

    Product productSave(Product product);          // 등록

    Long productTotalCount(ProductDto productDto);			// 전체 개수

    List<ProductDto> findAllProduct(ProductDto productDto);     // 전체 목록

    List<ProductDto> findPagingProduct(ProductDto productDto); // 페이징 목록

    Product findById(Long product_code);           // 상세

    void deleteById(Long product_code);            // 논리 삭제

    Product update(Product product);               // 수정
    
    List<ProductDto> findBomDetail(Long productCode);	//모달 
    
    Long findNextProductCode();
}