package com.oracle.comventory.service.product;

import java.util.List;

import com.oracle.comventory.domain.product.Product;
import com.oracle.comventory.dto.product.ProductDto;

public interface ProductService {

    Product productSave(Product product);

    Long productTotalCount(ProductDto productDto);

    List<ProductDto> findAllProduct(ProductDto productDto);

    List<ProductDto> findPagingProduct(ProductDto productDto);

    Product findById(Long product_code);

    void deleteById(Long product_code);

    Product update(Product product);
    
    List<ProductDto> findBomDetail(Long productCode);		//모달
    
    Long findNextProductCode();
}