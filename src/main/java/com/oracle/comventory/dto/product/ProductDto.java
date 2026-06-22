package com.oracle.comventory.dto.product;

import com.oracle.comventory.domain.product.Product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {

    private Long    productCode;
    private String  productName;
    private Integer productCategory;
    private Integer productStatus;
    private Integer productPrice;
    private Integer productProperQty;
    private Integer productDelStatus;
    private Long    regEmpNo;  
    private String  regDate;

    // 코드명 출력용
    private String productCategoryName;
    private String productDelStatusName;
    
    // BOM Modal 용
    private String productWonName;    
    private Integer needQty;
    
    //정렬용
    private String sortType;

    // 조회용 (검색 + 페이징)
    private String pageNum;
    private int    start;
    private int    end;
    private String currentPage;
    
    // 검색용
    private String searchType;
    private String keyword;
    private Integer searchCategory;
    private Integer searchDelStatus;

    // Entity → DTO 변환 생성자
    public ProductDto(Product product) {

        this.productCode      = product.getProduct_code();
        this.productName      = product.getProduct_name();
        this.productCategory  = product.getProduct_category();
        this.productStatus    = product.getProduct_status();
        this.productPrice     = product.getProduct_price();
        this.productProperQty = product.getProduct_proper_qty();
        this.productDelStatus = product.getProduct_del_status();
        this.regEmpNo         = product.getReg_emp_no();

        // regDate String 변환
        if(product.getReg_date() != null) {
            this.regDate = product.getReg_date().toString();
        }
    }

    // 목록 조회용 생성자
    public ProductDto(Product product,
                      String productCategoryName,
                      String productDelStatusName) {

        this(product);

        this.productCategoryName  = productCategoryName;
        this.productDelStatusName = productDelStatusName;
    }

}