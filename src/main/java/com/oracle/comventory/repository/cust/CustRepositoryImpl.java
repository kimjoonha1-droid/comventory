package com.oracle.comventory.repository.cust;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.oracle.comventory.domain.cust.Cust;
import com.oracle.comventory.dto.custTable.CustTableDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustRepositoryImpl implements CustRepository {

    private final EntityManager em;

    // 등록
    @Override
    public Cust save(Cust cust) {

        if (cust.getCust_del_status() == null) {
            cust.changeCustDelStatus(1);
        }

        em.persist(cust);

        return cust;
    }

 // 전체 개수
    @Override
    public Long totalCount(CustTableDto custDto) {

        StringBuilder jpql = new StringBuilder();

        jpql.append("""
            SELECT COUNT(c)
            FROM Cust c
            WHERE 1=1
        """);

        // 거래처 유형 검색
        if (custDto.getCustType() != null) {

            jpql.append("""
                AND c.cust_type = :custType
            """);
        }

        // 키워드 검색
        if (custDto.getKeyword() != null
                && !custDto.getKeyword().trim().isEmpty()) {

            switch (custDto.getSearchType()) {

                case "name":

                    jpql.append("""
                        AND c.cust_name LIKE :keyword
                    """);

                    break;

                case "ceo":

                    jpql.append("""
                        AND c.ceo_name LIKE :keyword
                    """);

                    break;

                case "business":

                    jpql.append("""
                        AND c.business_no LIKE :keyword
                    """);

                    break;

                case "tel":

                    jpql.append("""
                        AND c.cust_tel LIKE :keyword
                    """);

                    break;
            }
        }

        Query query =
                em.createQuery(jpql.toString());

        // 거래처 유형
        if (custDto.getCustType() != null) {

            query.setParameter(
                    "custType",
                    custDto.getCustType());
        }

        // 키워드
        if (custDto.getKeyword() != null
                && !custDto.getKeyword().trim().isEmpty()) {

            query.setParameter(
                    "keyword",
                    "%" + custDto.getKeyword() + "%");
        }

        return (Long) query.getSingleResult();
    }

    // 전체 목록
    @Override
    public List<CustTableDto> findAll() {

        return em.createQuery(
            "select new com.oracle.comventory.dto.custTable.CustTableDto(c, t.codeContents, s.codeContents) " +
            "from Cust c " +
            "left join StatusType t on t.bcode = 210 and t.mcode = c.cust_type " +
            "left join StatusType s on s.bcode = 220 and s.mcode = c.cust_del_status " +
            "order by c.cust_code",
            CustTableDto.class
        ).getResultList();
    }

    // 페이징 목록 - Oracle 11g ROWNUM 방식
    @Override
    public List<CustTableDto> findPagingCust(
            CustTableDto custDto) {

        StringBuilder sql = new StringBuilder();

        sql.append("""
            SELECT *
            FROM (
            
                SELECT ROWNUM rn, a.*
                
                FROM (
                
                    SELECT
                        c.cust_code,
                        c.cust_name,
                        c.cust_type,
                        t.code_contents AS cust_type_name,
                        c.business_no,
                        c.ceo_name,
                        c.cust_tel,
                        c.cust_email,
                        c.cust_address,
                        c.cust_del_status,
                        s.code_contents AS cust_del_status_name,
                        c.reg_emp_no,
                        c.reg_date
                        
                    FROM cust_table c
                    
                    LEFT JOIN status_type t
                        ON c.cust_type = t.mcode
                        AND t.bcode = 210
                        
                    LEFT JOIN status_type s
                        ON c.cust_del_status = s.mcode
                        AND s.bcode = 220
                        
                    WHERE 1=1
        """);

        // 거래처 유형
        if (custDto.getCustType() != null) {

            sql.append("""
                AND c.cust_type = :custType
            """);
        }

        // 검색
        if (custDto.getKeyword() != null
                && !custDto.getKeyword().trim().isEmpty()) {

            switch (custDto.getSearchType()) {

                case "name":

                    sql.append("""
                        AND c.cust_name LIKE :keyword
                    """);

                    break;

                case "ceo":

                    sql.append("""
                        AND c.ceo_name LIKE :keyword
                    """);

                    break;

                case "business":

                    sql.append("""
                        AND c.business_no LIKE :keyword
                    """);

                    break;

                case "tel":

                    sql.append("""
                        AND c.cust_tel LIKE :keyword
                    """);

                    break;
            }
        }

        sql.append("""
        
                    ORDER BY c.cust_code
                    
                ) a
                
                WHERE ROWNUM <= :end
                
            )
            
            WHERE rn >= :start
        """);

        Query query = em.createNativeQuery(sql.toString());

        // 거래처 유형
        if (custDto.getCustType() != null) {

            query.setParameter(
                    "custType",
                    custDto.getCustType());
        }

        // 검색어
        if (custDto.getKeyword() != null
                && !custDto.getKeyword().trim().isEmpty()) {

            query.setParameter(
                    "keyword",
                    "%" + custDto.getKeyword() + "%");
        }

        query.setParameter("start", custDto.getStart());
        query.setParameter("end", custDto.getEnd());

        List<Object[]> resultList = query.getResultList();

        return resultList.stream()
                .map(obj -> {

                    CustTableDto dto = new CustTableDto();

                    dto.setCustCode(
                            ((Number)obj[1]).longValue());

                    dto.setCustName(
                            (String)obj[2]);

                    dto.setCustType(
                            ((Number)obj[3]).intValue());

                    dto.setCustTypeName(
                            (String)obj[4]);

                    dto.setBusinessNo(
                            (String)obj[5]);

                    dto.setCeoName(
                            (String)obj[6]);

                    dto.setCustTel(
                            (String)obj[7]);

                    dto.setCustEmail(
                            (String)obj[8]);

                    dto.setCustAddress(
                            (String)obj[9]);

                    dto.setCustDelStatus(
                            ((Number)obj[10]).intValue());

                    dto.setCustDelStatusName(
                            (String)obj[11]);

                    dto.setRegEmpNo(
                            ((Number)obj[12]).longValue());

                    dto.setRegDate(
                            obj[13] != null
                            ? obj[13].toString()
                            : null);

                    return dto;

                }).collect(Collectors.toList());
    }

    // 상세 조회
    @Override
    public Cust findById(Long cust_code) {

        return em.find(Cust.class, cust_code);
    }

    // 상태 변경
    @Override
    public void changeStatus(Long cust_code, Integer cust_del_status) {

        Cust cust = em.find(Cust.class, cust_code);

        if (cust != null) {
            cust.changeCustDelStatus(cust_del_status);
        }
    }

    // 수정
    @Override
    public Cust update(Cust cust) {

        Cust findCust = em.find(Cust.class, cust.getCust_code());

        if (findCust != null) {

            findCust.changeCustName(cust.getCust_name());
            findCust.changeCustType(cust.getCust_type());
            findCust.changeBusinessNo(cust.getBusiness_no());
            findCust.changeCeoName(cust.getCeo_name());
            findCust.changeCustTel(cust.getCust_tel());
            findCust.changeCustEmail(cust.getCust_email());
            findCust.changeCustAddress(cust.getCust_address());
            findCust.changeCustDelStatus(cust.getCust_del_status());
        }

        return findCust;
    }

 // 다음 거래처 코드 조회
    @Override
    public Long findNextCustCode() {

        String sql = """
            SELECT NVL(MAX(CUST_CODE), 0) + 1
            FROM CUST_TABLE
            """;

        Number result = (Number) em.createNativeQuery(sql)
                                  .getSingleResult();

        return result.longValue();
    }
}