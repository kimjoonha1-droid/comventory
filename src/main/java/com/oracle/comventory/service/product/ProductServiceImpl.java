package com.oracle.comventory.service.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.comventory.domain.product.Product;
import com.oracle.comventory.dto.product.ProductDto;
import com.oracle.comventory.repository.product.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    // 등록
    @Override
    public Product productSave(Product product) {

        log.info("ProductServiceImpl productSave start product -> {}", product);

        return productRepository.productSave(product);
    }

 // 전체 개수 - 검색 포함
    @Override
    public Long productTotalCount(ProductDto productDto) {

        log.info("ProductServiceImpl productTotalCount start");

        return productRepository.productTotalCount(productDto);
    }

    // 전체 목록 - 검색 + 페이징 포함
    @Override
    public List<ProductDto> findAllProduct(ProductDto productDto) {

        log.info("ProductServiceImpl findAllProduct start");

        return productRepository.findAllProduct(productDto);
    }
    // 상세 조회
    @Override
    public Product findById(Long product_code) {

        log.info("ProductServiceImpl findById start product_code -> {}", product_code);

        return productRepository.findById(product_code);
    }

    // 논리 삭제
    @Override
    public void deleteById(Long product_code) {

        log.info("ProductServiceImpl deleteById start product_code -> {}", product_code);

        productRepository.deleteById(product_code);
    }

    // 수정
    @Override
    public Product update(Product product) {

        log.info("ProductServiceImpl update start product -> {}", product);

        return productRepository.update(product);
    }

    @Override
    public List<ProductDto> findPagingProduct(ProductDto productDto) {
        return productRepository.findPagingProduct(productDto);
    }
    
    @Override
    public List<ProductDto> findBomDetail(Long productCode) {

        return productRepository.findBomDetail(productCode);
    }

 // 다음 제품 코드 조회
    @Override
    public Long findNextProductCode() {

        log.info("ProductServiceImpl findNextProductCode start");

        return productRepository.findNextProductCode();
    }
}