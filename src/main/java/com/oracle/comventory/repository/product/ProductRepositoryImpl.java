package com.oracle.comventory.repository.product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.oracle.comventory.domain.product.Product;
import com.oracle.comventory.dto.product.ProductDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final EntityManager em;

 // 등록
    @Override
    public Product productSave(Product product) {

        if (product.getProduct_del_status() == null) {
            product.changeProductDelStatus(1);
        }

        em.persist(product);

        // 월 재고 자동 생성
        String sql = """
            INSERT INTO CLOSING_MONTH (
                CLOSING_YM,
                CLOSING_TYPE,
                PRODUCT_CODE,
                QTY,
                REG_EMP_NO,
                REG_DATE
            ) VALUES (
                TO_CHAR(SYSDATE, 'YYYYMM'),
                0,
                :productCode,
                0,
                :regEmpNo,
                SYSDATE
            )
            """;

        em.createNativeQuery(sql)
          .setParameter("productCode", product.getProduct_code())
          .setParameter("regEmpNo", product.getReg_emp_no())
          .executeUpdate();

        return product;
    }

 // 전체 개수 - 검색 포함
    @Override
    public Long productTotalCount(ProductDto productDto) {

        String jpql =
                "select count(p) " +
                "from Product p " +
                "where p.product_del_status in (1, 3) ";

        String keyword = productDto.getKeyword();
        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        boolean isNumber = hasKeyword && keyword.trim().matches("\\d+");

        if (hasKeyword) {
        	jpql += "and (lower(p.product_name) like lower(:keyword) ";

            if (isNumber) {
                jpql += "or p.product_code = :productCode ";
            }

            jpql += ") ";
        }

        if (productDto.getSearchCategory() != null && productDto.getSearchCategory() != 0) {
            jpql += "and p.product_category = :searchCategory ";
        }

        if (productDto.getSearchDelStatus() != null && productDto.getSearchDelStatus() != 0) {
            jpql += "and p.product_del_status = :searchDelStatus ";
        }
        // 완제품 / 부품 검색
        if (productDto.getProductStatus() != null) {

            jpql += "and p.product_status = :productStatus ";
        }

        TypedQuery<Long> query = em.createQuery(jpql, Long.class);

        if (hasKeyword) {
            query.setParameter("keyword", "%" + keyword.trim() + "%");

            if (isNumber) {
                query.setParameter("productCode", Long.parseLong(keyword.trim()));
            }
        }

        if (productDto.getSearchCategory() != null && productDto.getSearchCategory() != 0) {
            query.setParameter("searchCategory", productDto.getSearchCategory());
        }

        if (productDto.getSearchDelStatus() != null && productDto.getSearchDelStatus() != 0) {
            query.setParameter("searchDelStatus", productDto.getSearchDelStatus());
        }
        if (productDto.getProductStatus() != null) {

            query.setParameter(
                    "productStatus",
                    productDto.getProductStatus());
        }

        return query.getSingleResult();
    }

    @Override
    public List<ProductDto> findAllProduct(ProductDto productDto) {

        String sql =
                "select * from ( " +
                "    select rownum rn, a.* from ( " +
                "        select p.product_code, " +
                "               p.product_name, " +
                "               p.product_category, " +
                "               p.product_status, " +
                "               p.product_price, " +
                "               p.product_proper_qty, " +
                "               p.product_del_status, " +
                "               p.reg_emp_no, " +
                "               to_char(p.reg_date, 'YYYY-MM-DD') as reg_date, " +
                "               c.code_contents as product_category_name, " +
                "               s.code_contents as product_del_status_name " +
                "        from product p " +
                "        left join status_type c " +
                "          on c.bcode = 230 " +
                "         and c.mcode = p.product_category " +
                "        left join status_type s " +
                "          on s.bcode = 240 " +
                "         and s.mcode = p.product_del_status " +
                "        where p.product_del_status in (1, 3) ";

        String keyword = productDto.getKeyword();
        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        boolean isNumber = hasKeyword && keyword.trim().matches("\\d+");

        if (hasKeyword) {
        	sql += " and (lower(p.product_name) like lower(:keyword) ";

            if (isNumber) {
                sql += " or p.product_code = :productCode ";
            }

            sql += ") ";
        }

        if (productDto.getSearchCategory() != null && productDto.getSearchCategory() != 0) {
            sql += " and p.product_category = :searchCategory ";
        }
        // 완제품 / 부품 검색
        if (productDto.getProductStatus() != null) {

            sql += " and p.product_status = :productStatus ";
        }

        if (productDto.getSearchDelStatus() != null && productDto.getSearchDelStatus() != 0) {
            sql += " and p.product_del_status = :searchDelStatus ";
        }

        if ("priceAsc".equals(productDto.getSortType())) {

            sql += " order by p.product_price asc ";

        } else if ("priceDesc".equals(productDto.getSortType())) {

            sql += " order by p.product_price desc ";

        } else if ("codeDesc".equals(productDto.getSortType())) {

            sql += " order by p.product_code desc ";

        } else {

            sql += " order by p.product_code ";

        }

        sql +=
                "    ) a " +
                "    where rownum <= :end " +
                ") " +
                "where rn >= :start ";

        Query query = em.createNativeQuery(sql);

        if (hasKeyword) {
            query.setParameter("keyword", "%" + keyword.trim() + "%");

            if (isNumber) {
                query.setParameter("productCode", Long.parseLong(keyword.trim()));
            }
        }

        if (productDto.getSearchCategory() != null && productDto.getSearchCategory() != 0) {
            query.setParameter("searchCategory", productDto.getSearchCategory());
        }
        if (productDto.getProductStatus() != null) {

            query.setParameter(
                    "productStatus",
                    productDto.getProductStatus());
        }

        if (productDto.getSearchDelStatus() != null && productDto.getSearchDelStatus() != 0) {
            query.setParameter("searchDelStatus", productDto.getSearchDelStatus());
        }

        query.setParameter("start", productDto.getStart());
        query.setParameter("end", productDto.getEnd());

        List<Object[]> resultList = query.getResultList();

        List<ProductDto> productList = new ArrayList<>();

        for (Object[] row : resultList) {

            ProductDto dto = new ProductDto();

            dto.setProductCode(((Number) row[1]).longValue());
            dto.setProductName((String) row[2]);
            dto.setProductCategory(row[3] == null ? null : ((Number) row[3]).intValue());
            dto.setProductStatus(row[4] == null ? null : ((Number) row[4]).intValue());
            dto.setProductPrice(row[5] == null ? null : ((Number) row[5]).intValue());
            dto.setProductProperQty(row[6] == null ? null : ((Number) row[6]).intValue());
            dto.setProductDelStatus(row[7] == null ? null : ((Number) row[7]).intValue());
            dto.setRegEmpNo(row[8] == null ? null : ((Number) row[8]).longValue());
            dto.setRegDate((String) row[9]);
            dto.setProductCategoryName((String) row[10]);
            dto.setProductDelStatusName((String) row[11]);

            productList.add(dto);
        }

        return productList;
    }

    // 상세 조회
    @Override
    public Product findById(Long product_code) {
        return em.find(Product.class, product_code);
    }

    // 논리 삭제: 단종 처리
    @Override
    public void deleteById(Long product_code) {

        Product product = em.find(Product.class, product_code);

        if (product != null) {
            product.changeProductDelStatus(2);
        }
    }

    // 수정
    @Override
    public Product update(Product product) {

        Product findProduct = em.find(Product.class, product.getProduct_code());

        if (findProduct != null) {
            findProduct.changeProductName(product.getProduct_name());
            findProduct.changeProductCategory(product.getProduct_category());
            findProduct.changeProductStatus(product.getProduct_status());
            findProduct.changeProductPrice(product.getProduct_price());
            findProduct.changeProductProperQty(product.getProduct_proper_qty());
            findProduct.changeProductDelStatus(product.getProduct_del_status());
        }

        return findProduct;
    }

    // 페이징 목록 - Oracle 11g ROWNUM 방식
    @Override
    public List<ProductDto> findPagingProduct(ProductDto productDto) {

        String sql =
                "SELECT * " +
                "FROM ( " +
                "    SELECT ROWNUM rn, a.* " +
                "    FROM ( " +
                "        SELECT p.* " +
                "        FROM PRODUCT p " +
                "        WHERE p.PRODUCT_DEL_STATUS IN (1, 3) " +
                "        ORDER BY p.PRODUCT_CODE DESC " +
                "    ) a " +
                ") " +
                "WHERE rn BETWEEN ?1 AND ?2";

        Query query = em.createNativeQuery(sql, Product.class);

        query.setParameter(1, productDto.getStart());
        query.setParameter(2, productDto.getEnd());

        List<Product> productList = query.getResultList();

        return productList.stream()
                .map(product -> {

                    String productCategoryName = em.createQuery(
                            "select s.codeContents " +
                            "from StatusType s " +
                            "where s.bcode = 230 " +
                            "and s.mcode = :mcode",
                            String.class
                    )
                    .setParameter("mcode", product.getProduct_category())
                    .getSingleResult();

                    String productDelStatusName = em.createQuery(
                            "select s.codeContents " +
                            "from StatusType s " +
                            "where s.bcode = 240 " +
                            "and s.mcode = :mcode",
                            String.class
                    )
                    .setParameter("mcode", product.getProduct_del_status())
                    .getSingleResult();

                    return new ProductDto(
                            product,
                            productCategoryName,
                            productDelStatusName
                    );
                })
                .collect(Collectors.toList());
    }
    @Override
    public List<ProductDto> findBomDetail(Long productCode) {

        String sql = """
            SELECT
                bp.product_code,
                bp.product_name,
                st.code_contents,
                b.need_qty
            FROM BOM b
            JOIN PRODUCT bp
                ON b.product_won = bp.product_code
            JOIN STATUS_TYPE st
                ON bp.product_category = st.mcode
            WHERE st.bcode = 230
              AND b.product_code = :productCode
            ORDER BY bp.product_code
            """;

        Query query = em.createNativeQuery(sql);

        query.setParameter("productCode", productCode);

        List<Object[]> resultList = query.getResultList();

        List<ProductDto> bomList = new ArrayList<>();

        for(Object[] obj : resultList) {

            ProductDto dto = new ProductDto();

            dto.setProductCode(
                    ((Number)obj[0]).longValue());

            dto.setProductWonName(
                    (String)obj[1]);

            dto.setProductCategoryName(
                    (String)obj[2]);

            dto.setNeedQty(
                    ((Number)obj[3]).intValue());

            bomList.add(dto);
        }

        return bomList;
    }

 // 다음 제품 코드 조회
    @Override
    public Long findNextProductCode() {

        String sql = """
            SELECT NVL(MAX(PRODUCT_CODE), 0) + 1
            FROM PRODUCT
            """;

        Number result = (Number) em.createNativeQuery(sql)
                                  .getSingleResult();

        return result.longValue();
    }
}