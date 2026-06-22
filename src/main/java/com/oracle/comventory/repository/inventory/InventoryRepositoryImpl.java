package com.oracle.comventory.repository.inventory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.comventory.dto.inventory.StockListDto;
import com.oracle.comventory.dto.statusType.StatusTypeDto;
import com.oracle.comventory.dto.realItems.RealItemsDto;
import com.oracle.comventory.dto.closingDay.ClosingDayDto;
import com.oracle.comventory.dto.dashboard.DashboardSummaryDto;
import com.oracle.comventory.dto.dashboard.DashboardDailyInoutDto;


import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class InventoryRepositoryImpl implements InventoryRepository {

    private final EntityManager em;
    
    @Override
    public List<StockListDto> findStockList(
    		String itemType,
    		String closingType,
            String searchYm,
            String searchKeyword,
            String category,
            String inventoryStatus,
            int    start,
            int    end) {

        StringBuilder sql = new StringBuilder("""
			select *
			  from (
			        select rownum rn, a.*
			          from (
							select p.product_code,
							       p.product_name,
							       st.code_contents as category_name,
							       cm.qty,
							       p.product_proper_qty,
							       cm.reg_date
							  from closing_month cm
							  join product p
							    on p.product_code = cm.product_code
						 left join status_type st
							    on st.bcode = 230
							   and st.mcode = p.product_category
							 where cm.closing_ym = :searchYm
							   and cm.closing_type = :closingType
            """);

        if (searchKeyword != null && !searchKeyword.isBlank()) {
            sql.append("""
               and (lower(p.product_name) like lower(:searchKeyword)
                    or to_char(p.product_code) like :searchKeyword)
            """);
        }
        
        if (category != null && !category.isBlank()) {
        	sql.append(" and to_char(p.product_category) = :category ");
        }
        
        if ("RAW".equals(itemType)) {
            sql.append(" and p.product_category < 102 ");
        } else if ("PRODUCT".equals(itemType)) {
            sql.append(" and p.product_category >= 102 ");
        }
        
        if ("LACK".equals(inventoryStatus)) {
            sql.append(" and nvl(cm.qty, 0) < nvl(p.product_proper_qty, 0) ");
        } else if ("NORMAL".equals(inventoryStatus)) {
            sql.append(" and nvl(cm.qty, 0) >= nvl(p.product_proper_qty, 0) ");
        }

        sql.append("""
	                order by p.product_code
	               ) a
	         where rownum <= :end
	       )
	     where rn >= :start
	    """);

        var query = em.createNativeQuery(sql.toString())
        		.setParameter("searchYm", searchYm)
        		.setParameter("closingType", Integer.parseInt(closingType))
        		.setParameter("start", start)
        		.setParameter("end", end);

        if (searchKeyword != null && !searchKeyword.isBlank()) {
            query.setParameter("searchKeyword", "%" + searchKeyword + "%");
        }

        if (category != null && !category.isBlank()) {
            query.setParameter("category", category);
        }

        List<Object[]> rows = query.getResultList();

        List<StockListDto> result = new ArrayList<>();

        for (Object[] row : rows) {
        	result.add(new StockListDto(
        	        ((Number) row[1]).intValue(),
        	        (String) row[2],
        	        (String) row[3],
        	        row[4] == null ? 0 : ((Number) row[4]).intValue(),
        	        row[5] == null ? 0 : ((Number) row[5]).intValue(),
        	        row[6] == null ? null : java.sql.Timestamp.valueOf((LocalDateTime) row[6])
        	));
        }

        return result;
    }
    
    @Override
    public int countStockList(
            String itemType,
            String closingType,
            String searchYm,
            String searchKeyword,
            String category,
            String inventoryStatus) {

        StringBuilder sql = new StringBuilder("""
            select count(*)
              from closing_month cm
              join product p
                on p.product_code = cm.product_code
             where cm.closing_ym = :searchYm
               and cm.closing_type = :closingType
        """);

        if (searchKeyword != null && !searchKeyword.isBlank()) {
            sql.append("""
               and (lower(p.product_name) like lower(:searchKeyword)
                    or to_char(p.product_code) like :searchKeyword)
            """);
        }

        if (category != null && !category.isBlank()) {
            sql.append(" and to_char(p.product_category) = :category ");
        }

        if ("RAW".equals(itemType)) {
            sql.append(" and p.product_category < 102 ");
        } else if ("PRODUCT".equals(itemType)) {
            sql.append(" and p.product_category >= 102 ");
        }

        if ("LACK".equals(inventoryStatus)) {
            sql.append(" and nvl(cm.qty, 0) < nvl(p.product_proper_qty, 0) ");
        } else if ("NORMAL".equals(inventoryStatus)) {
            sql.append(" and nvl(cm.qty, 0) >= nvl(p.product_proper_qty, 0) ");
        }

        var query = em.createNativeQuery(sql.toString())
                .setParameter("searchYm", searchYm)
                .setParameter("closingType", Integer.parseInt(closingType));

        if (searchKeyword != null && !searchKeyword.isBlank()) {
            query.setParameter("searchKeyword", "%" + searchKeyword + "%");
        }

        if (category != null && !category.isBlank()) {
            query.setParameter("category", category);
        }

        return ((Number) query.getSingleResult()).intValue();
    }

    @Override
    public int countStockLack(
            String itemType,
            String closingType,
            String searchYm,
            String searchKeyword,
            String category,
            String inventoryStatus) {

        if ("NORMAL".equals(inventoryStatus)) {
            return 0;
        }

        StringBuilder sql = new StringBuilder("""
            select count(*)
              from closing_month cm
              join product p
                on p.product_code = cm.product_code
             where cm.closing_ym = :searchYm
			   and cm.closing_type = :closingType
               and nvl(cm.qty, 0) < nvl(p.product_proper_qty, 0)
        """);

        if (searchKeyword != null && !searchKeyword.isBlank()) {
            sql.append("""
               and (lower(p.product_name) like lower(:searchKeyword)
                    or to_char(p.product_code) like :searchKeyword)
            """);
        }

        if (category != null && !category.isBlank()) {
            sql.append(" and to_char(p.product_category) = :category ");
        }

        if ("RAW".equals(itemType)) {
            sql.append(" and p.product_category < 102 ");
        } else if ("PRODUCT".equals(itemType)) {
            sql.append(" and p.product_category >= 102 ");
        }

        var query = em.createNativeQuery(sql.toString())
                .setParameter("searchYm", searchYm)
                .setParameter("closingType", Integer.parseInt(closingType));

        if (searchKeyword != null && !searchKeyword.isBlank()) {
            query.setParameter("searchKeyword", "%" + searchKeyword + "%");
        }

        if (category != null && !category.isBlank()) {
            query.setParameter("category", category);
        }

        return ((Number) query.getSingleResult()).intValue();
    }

    @Override
    public List<StatusTypeDto> findCategories() {
        List<Object[]> rows = em.createNativeQuery("""
                select bcode, mcode, code_contents
                  from status_type
                 where bcode = 230
                   and mcode <> 999
                 order by mcode
                """)
                .getResultList();

        List<StatusTypeDto> result = new ArrayList<>();

        for (Object[] row : rows) {
            result.add(new StatusTypeDto(
                    ((Number) row[0]).intValue(),
                    ((Number) row[1]).intValue(),
                    (String) row[2]
            ));
        }

        return result;
    }
    
    @Override
    public List<RealItemsDto> findPhysicalInventoryList(
            String itemType,
            String searchYm,
            String searchKeyword,
            String category,
            int start,
            int end) {

        StringBuilder sql = new StringBuilder("""
            select *
              from (
                    select rownum rn, a.*
                      from (
                            select ri.real_ymd,
                                   ri.product_code,
                                   p.product_name,
                                   st.code_contents as category_name,
                                   nvl(cm.qty, 0) as warehouse_qty,
                                   ri.change_qty,
                                   ri.change_reason,
                                   ri.confirm_status,
                                   ri.reg_emp_no,
                                   ri.reg_date
                              from real_items ri
                              join product p
                                on p.product_code = ri.product_code
                              left join status_type st
                                on st.bcode = 230
                               and st.mcode = p.product_category
                              left join closing_month cm
                                on cm.product_code = ri.product_code
                               and cm.closing_ym = substr(ri.real_ymd, 1, 6)
                               and cm.closing_type = 1
                             where substr(ri.real_ymd, 1, 6) = :searchYm
            """);

        if (searchKeyword != null && !searchKeyword.isBlank()) {
            sql.append("""
                 and (lower(p.product_name) like lower(:searchKeyword)
                      or to_char(p.product_code) like :searchKeyword)
            """);
        }

        if (category != null && !category.isBlank()) {
            sql.append(" and to_char(p.product_category) = :category ");
        }

        if ("RAW".equals(itemType)) {
            sql.append(" and p.product_category < 102 ");
        } else if ("PRODUCT".equals(itemType)) {
            sql.append(" and p.product_category >= 102 ");
        }

        sql.append("""
                            order by ri.real_ymd desc, ri.product_code
                           ) a
                     where rownum <= :end
                   )
             where rn >= :start
            """);

        var query = em.createNativeQuery(sql.toString())
                .setParameter("searchYm", searchYm)
                .setParameter("start", start)
                .setParameter("end", end);

        if (searchKeyword != null && !searchKeyword.isBlank()) {
            query.setParameter("searchKeyword", "%" + searchKeyword + "%");
        }

        if (category != null && !category.isBlank()) {
            query.setParameter("category", category);
        }

        List<Object[]> rows = query.getResultList();
        List<RealItemsDto> result = new ArrayList<>();

        for (Object[] row : rows) {
            RealItemsDto dto = new RealItemsDto();

            dto.setReal_ymd((String) row[1]);
            dto.setProduct_code(((Number) row[2]).intValue());
            dto.setProduct_name((String) row[3]);
            dto.setCategory_name((String) row[4]);
            dto.setWarehouse_qty(row[5] == null ? 0 : ((Number) row[5]).intValue());
            dto.setChange_qty(row[6] == null ? 0 : ((Number) row[6]).intValue());
            dto.setChange_reason((String) row[7]);
            dto.setConfirm_status(row[8] == null ? 0 : ((Number) row[8]).intValue());
            dto.setReg_emp_no(row[9] == null ? 0 : ((Number) row[9]).intValue());
            dto.setReg_date(row[10] == null ? null : java.sql.Timestamp.valueOf((LocalDateTime) row[10]));

            result.add(dto);
        }

        return result;
    }
    @Override
    public int countPhysicalInventoryList(
            String itemType,
            String searchYm,
            String searchKeyword,
            String category) {

        StringBuilder sql = new StringBuilder("""
            select count(*)
              from real_items ri
              join product p
                on p.product_code = ri.product_code
             where substr(ri.real_ymd, 1, 6) = :searchYm
        """);

        if (searchKeyword != null && !searchKeyword.isBlank()) {
            sql.append("""
                 and (lower(p.product_name) like lower(:searchKeyword)
                      or to_char(p.product_code) like :searchKeyword)
            """);
        }

        if (category != null && !category.isBlank()) {
            sql.append(" and to_char(p.product_category) = :category ");
        }

        if ("RAW".equals(itemType)) {
            sql.append(" and p.product_category < 102 ");
        } else if ("PRODUCT".equals(itemType)) {
            sql.append(" and p.product_category >= 102 ");
        }

        var query = em.createNativeQuery(sql.toString())
                .setParameter("searchYm", searchYm);

        if (searchKeyword != null && !searchKeyword.isBlank()) {
            query.setParameter("searchKeyword", "%" + searchKeyword + "%");
        }

        if (category != null && !category.isBlank()) {
            query.setParameter("category", category);
        }

        return ((Number) query.getSingleResult()).intValue();
    }

    
    @Override
    public List<RealItemsDto> findPhysicalInventoryProductList(String searchYm) {
        List<Object[]> rows = em.createNativeQuery("""
            select p.product_code,
                   p.product_name,
                   nvl(cm.qty, 0) as warehouse_qty
              from product p
              left join closing_month cm
                on cm.product_code = p.product_code
               and cm.closing_ym = :searchYm
               and cm.closing_type = 1
             where p.product_del_status = 1
             order by p.product_code
            """)
            .setParameter("searchYm", searchYm)
            .getResultList();
        	
        System.out.println("Repository findPhysicalInventoryProductList searchYm = " + searchYm);
        System.out.println("Repository rows size = " + rows.size());

        for (Object[] row : rows) {
            System.out.println("row[0] product_code = " + row[0]
                    + ", row[1] product_name = " + row[1]
                    + ", row[2] warehouse_qty = " + row[2]);
        }
        
        List<RealItemsDto> result = new ArrayList<>();

        for (Object[] row : rows) {
            RealItemsDto dto = new RealItemsDto();
            dto.setProduct_code(((Number) row[0]).intValue());
            dto.setProduct_name((String) row[1]);
            dto.setWarehouse_qty(row[2] == null ? 0 : ((Number) row[2]).intValue());
            result.add(dto);
        }

        return result;
    }
    
    @Override
    public boolean existsPhysicalInventory(String realYmd, int productCode) {
        Number count = (Number) em.createNativeQuery("""
            select count(*)
              from real_items
             where real_ymd = :realYmd
               and product_code = :productCode
            """)
            .setParameter("realYmd", realYmd)
            .setParameter("productCode", productCode)
            .getSingleResult();

        return count.intValue() > 0;
    }

    @Override
    @Transactional
    public void insertPhysicalInventory(RealItemsDto dto) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        if (existsPhysicalInventory(today, dto.getProduct_code())) {
            throw new IllegalStateException("이미 오늘 등록된 실사재고입니다.");
        }

        em.createNativeQuery("""
            insert into real_items (
                real_ymd,
                product_code,
                change_qty,
                change_reason,
                confirm_status,
                reg_emp_no,
                reg_date
            ) values (
                :realYmd,
                :productCode,
                :changeQty,
                :changeReason,
                1,
                :regEmpNo,
                sysdate
            )
            """)
            .setParameter("realYmd", today)
            .setParameter("productCode", dto.getProduct_code())
            .setParameter("changeQty", dto.getChange_qty())
            .setParameter("changeReason", dto.getChange_reason())
            .setParameter("regEmpNo", dto.getReg_emp_no())
            .executeUpdate();
    }
    
    @Override
    @Transactional
    public void updatePhysicalInventoryDate(String originalRealYmd, int productCode, String realYmd) {
        String newRealYmd = realYmd.replace("-", "");

        em.createNativeQuery("""
            update real_items
               set real_ymd = :newRealYmd
             where real_ymd = :originalRealYmd
               and product_code = :productCode
            """)
            .setParameter("newRealYmd", newRealYmd)
            .setParameter("originalRealYmd", originalRealYmd)
            .setParameter("productCode", productCode)
            .executeUpdate();
    }
    
    @Override
    @Transactional
    public void updatePhysicalInventoryConfirmStatus(String realYmd, int productCode, int confirmStatus) {
        em.createNativeQuery("""
            update real_items
               set confirm_status = :confirmStatus
             where real_ymd = :realYmd
               and product_code = :productCode
               and confirm_status = 1
            """)
            .setParameter("confirmStatus", confirmStatus)
            .setParameter("realYmd", realYmd)
            .setParameter("productCode", productCode)
            .executeUpdate();
    }
    
    @Override
    public List<StockListDto> findCurrentStockList(
            String itemType,
            String searchKeyword,
            String category,
            String inventoryStatus,
            int   start,
            int   end) {

        StringBuilder sql = new StringBuilder("""
            select *
              from (
                    select rownum page_rn, a.*
                      from (
                            select product_code,
                                   product_name,
                                   category_name,
                                   qty,
                                   product_proper_qty,
                                   reg_date
                              from (
                                    select p.product_code,
                                           p.product_name,
                                           p.product_category,
                                           st.code_contents as category_name,
                                           cm.qty,
                                           p.product_proper_qty,
                                           cm.reg_date,
                                           row_number() over (
                                               partition by cm.product_code
                                               order by cm.closing_ym desc, cm.closing_type desc
                                           ) as rn
                                      from closing_month cm
                                      join product p
                                        on p.product_code = cm.product_code
                                      left join status_type st
                                        on st.bcode = 230
                                       and st.mcode = p.product_category
                                     where cm.closing_type in (1, 2)
                                       and p.product_del_status = 1
                                   )
                             where rn = 1
        """);

        if (searchKeyword != null && !searchKeyword.isBlank()) {
            sql.append("""
                 and (lower(product_name) like lower(:searchKeyword)
                      or to_char(product_code) like :searchKeyword)
            """);
        }

        if (category != null && !category.isBlank()) {
            sql.append(" and to_char(product_category) = :category ");
        }

        if ("RAW".equals(itemType)) {
            sql.append(" and product_category < 102 ");
        } else if ("PRODUCT".equals(itemType)) {
            sql.append(" and product_category >= 102 ");
        }

        if ("LACK".equals(inventoryStatus)) {
            sql.append(" and nvl(qty, 0) < nvl(product_proper_qty, 0) ");
        } else if ("NORMAL".equals(inventoryStatus)) {
            sql.append(" and nvl(qty, 0) >= nvl(product_proper_qty, 0) ");
        }

        sql.append("""
                            order by product_code
                           ) a
                     where rownum <= :end
                   )
             where page_rn >= :start
        """);

        var query = em.createNativeQuery(sql.toString())
                .setParameter("start", start)
                .setParameter("end", end);

        if (searchKeyword != null && !searchKeyword.isBlank()) {
            query.setParameter("searchKeyword", "%" + searchKeyword + "%");
        }

        if (category != null && !category.isBlank()) {
            query.setParameter("category", category);
        }

        List<Object[]> rows = query.getResultList();
        List<StockListDto> result = new ArrayList<>();

        for (Object[] row : rows) {
            result.add(new StockListDto(
                    ((Number) row[1]).intValue(),
                    (String) row[2],
                    (String) row[3],
                    row[4] == null ? 0 : ((Number) row[4]).intValue(),
                    row[5] == null ? 0 : ((Number) row[5]).intValue(),
                    row[6] == null ? null : java.sql.Timestamp.valueOf((LocalDateTime) row[6])
            ));
        }

        return result;
    }
    
    @Override
    public int countCurrentStockList(
            String itemType,
            String searchKeyword,
            String category,
            String inventoryStatus) {

        StringBuilder sql = new StringBuilder("""
            select count(*)
              from (
                    select product_code,
                           product_name,
                           product_category,
                           category_name,
                           qty,
                           product_proper_qty
                      from (
                            select p.product_code,
                                   p.product_name,
                                   p.product_category,
                                   st.code_contents as category_name,
                                   cm.qty,
                                   p.product_proper_qty,
                                   row_number() over (
                                       partition by cm.product_code
                                       order by cm.closing_ym desc, cm.closing_type desc
                                   ) as rn
                              from closing_month cm
                              join product p
                                on p.product_code = cm.product_code
                              left join status_type st
                                on st.bcode = 230
                               and st.mcode = p.product_category
                             where cm.closing_type in (1, 2)
                               and p.product_del_status = 1
                           )
                     where rn = 1
        """);

        if (searchKeyword != null && !searchKeyword.isBlank()) {
            sql.append("""
                 and (lower(product_name) like lower(:searchKeyword)
                      or to_char(product_code) like :searchKeyword)
            """);
        }

        if (category != null && !category.isBlank()) {
            sql.append(" and to_char(product_category) = :category ");
        }

        if ("RAW".equals(itemType)) {
            sql.append(" and product_category < 102 ");
        } else if ("PRODUCT".equals(itemType)) {
            sql.append(" and product_category >= 102 ");
        }

        if ("LACK".equals(inventoryStatus)) {
            sql.append(" and nvl(qty, 0) < nvl(product_proper_qty, 0) ");
        } else if ("NORMAL".equals(inventoryStatus)) {
            sql.append(" and nvl(qty, 0) >= nvl(product_proper_qty, 0) ");
        }

        sql.append(" ) ");

        var query = em.createNativeQuery(sql.toString());

        if (searchKeyword != null && !searchKeyword.isBlank()) {
            query.setParameter("searchKeyword", "%" + searchKeyword + "%");
        }

        if (category != null && !category.isBlank()) {
            query.setParameter("category", category);
        }

        return ((Number) query.getSingleResult()).intValue();
    }

    @Override
    public int countCurrentStockLack(
            String itemType,
            String searchKeyword,
            String category,
            String inventoryStatus) {

        if ("NORMAL".equals(inventoryStatus)) {
            return 0;
        }

        StringBuilder sql = new StringBuilder("""
            select count(*)
              from (
                    select product_code,
                           product_name,
                           product_category,
                           qty,
                           product_proper_qty
                      from (
                            select p.product_code,
                                   p.product_name,
                                   p.product_category,
                                   cm.qty,
                                   p.product_proper_qty,
                                   row_number() over (
                                       partition by cm.product_code
                                       order by cm.closing_ym desc, cm.closing_type desc
                                   ) as rn
                              from closing_month cm
                              join product p
                                on p.product_code = cm.product_code
                             where cm.closing_type in (1, 2)
                               and p.product_del_status = 1
                           )
                     where rn = 1
                       and nvl(qty, 0) < nvl(product_proper_qty, 0)
        """);

        if (searchKeyword != null && !searchKeyword.isBlank()) {
            sql.append("""
                 and (lower(product_name) like lower(:searchKeyword)
                      or to_char(product_code) like :searchKeyword)
            """);
        }

        if (category != null && !category.isBlank()) {
            sql.append(" and to_char(product_category) = :category ");
        }

        if ("RAW".equals(itemType)) {
            sql.append(" and product_category < 102 ");
        } else if ("PRODUCT".equals(itemType)) {
            sql.append(" and product_category >= 102 ");
        }

        sql.append(" ) ");

        var query = em.createNativeQuery(sql.toString());

        if (searchKeyword != null && !searchKeyword.isBlank()) {
            query.setParameter("searchKeyword", "%" + searchKeyword + "%");
        }

        if (category != null && !category.isBlank()) {
            query.setParameter("category", category);
        }

        return ((Number) query.getSingleResult()).intValue();
    }

    @Override
    public DashboardSummaryDto findDashboardSummary(LocalDate today, LocalDate yesterday) {
        List<DashboardDailyInoutDto> rows = findDashboardDailyInout(yesterday, today);

        int yesterdayIn = rows.size() > 0 ? rows.get(0).getInQty() : 0;
        int yesterdayOut = rows.size() > 0 ? rows.get(0).getOutQty() : 0;
        int todayIn = rows.size() > 1 ? rows.get(1).getInQty() : 0;
        int todayOut = rows.size() > 1 ? rows.get(1).getOutQty() : 0;

        int lackCount = findDashboardLackCount();
        int totalProductCount = findDashboardTotalProductCount();

        return new DashboardSummaryDto(
                todayIn,
                yesterdayIn,
                todayOut,
                yesterdayOut,
                lackCount,
                totalProductCount
        );
    }

    @Override
    public List<DashboardDailyInoutDto> findDashboardDailyInout(LocalDate startDate, LocalDate endDate) {
        List<Object[]> rows = em.createNativeQuery("""
            select d.ymd,
                   to_char(to_date(d.ymd, 'YYYYMMDD'), 'MM/DD') as label,
                   nvl(pur.in_qty, 0) + nvl(prod.in_qty, 0) as in_qty,
                   nvl(ord.out_qty, 0) as out_qty
              from (
                    select to_char(:startDate + level - 1, 'YYYYMMDD') as ymd
                      from dual
                   connect by level <= (:endDate - :startDate + 1)
                   ) d
              left join (
                    select to_char(po.inbound_date, 'YYYYMMDD') as ymd,
                           sum(pd.purchase_amount) as in_qty
                      from purchase_order po
                      join purchase_detail pd
                        on pd.purchase_id = po.purchase_id
                     where po.status in (5, 7)
                     group by to_char(po.inbound_date, 'YYYYMMDD')
                   ) pur
                on pur.ymd = d.ymd
              left join (
                    select complete_date as ymd,
                           sum(production_qty) as in_qty
                      from production
                     where production_status in (5, 7)
                     group by complete_date
                   ) prod
                on prod.ymd = d.ymd
              left join (
                    select to_char(o.order_confirmed_date, 'YYYYMMDD') as ymd,
                           sum(od.order_amount) as out_qty
                      from orders o
                      join orders_detail od
                        on od.order_code = o.order_code
                     where o.eapp_status in (410, 420, 710)
                     group by to_char(o.order_confirmed_date, 'YYYYMMDD')
                   ) ord
                on ord.ymd = d.ymd
             order by d.ymd
            """)
            .setParameter("startDate", java.sql.Date.valueOf(startDate))
            .setParameter("endDate", java.sql.Date.valueOf(endDate))
            .getResultList();

        List<DashboardDailyInoutDto> result = new ArrayList<>();

        for (Object[] row : rows) {
            result.add(new DashboardDailyInoutDto(
                    (String) row[0],
                    (String) row[1],
                    row[2] == null ? 0 : ((Number) row[2]).intValue(),
                    row[3] == null ? 0 : ((Number) row[3]).intValue()
            ));
        }

        return result;
    }

    @Override
    public List<StockListDto> findDashboardShortageItems(int limit) {
        List<Object[]> rows = em.createNativeQuery("""
            select *
              from (
                    select p.product_code,
                           p.product_name,
                           st.code_contents as category_name,
                           nvl(cm.qty, 0) as qty,
                           nvl(p.product_proper_qty, 0) as product_proper_qty,
                           cm.reg_date
                      from product p
                      left join (
                            select product_code, qty, reg_date
                              from (
                                    select cm.*,
                                           row_number() over (
                                               partition by cm.product_code
                                               order by cm.closing_ym desc, cm.closing_type desc
                                           ) as rn
                                      from closing_month cm
                                     where cm.closing_type in (1, 2)
                                   )
                             where rn = 1
                           ) cm
                        on cm.product_code = p.product_code
                      left join status_type st
                        on st.bcode = 230
                       and st.mcode = p.product_category
                     where p.product_del_status = 1
                       and nvl(cm.qty, 0) < nvl(p.product_proper_qty, 0)
                     order by (nvl(p.product_proper_qty, 0) - nvl(cm.qty, 0)) desc,
                              p.product_code
                   )
             where rownum <= :limit
            """)
            .setParameter("limit", limit)
            .getResultList();

        List<StockListDto> result = new ArrayList<>();

        for (Object[] row : rows) {
            result.add(new StockListDto(
                    ((Number) row[0]).intValue(),
                    (String) row[1],
                    (String) row[2],
                    row[3] == null ? 0 : ((Number) row[3]).intValue(),
                    row[4] == null ? 0 : ((Number) row[4]).intValue(),
                    row[5] == null ? null : java.sql.Timestamp.valueOf((LocalDateTime) row[5])
            ));
        }

        return result;
    }

    private int findDashboardTotalProductCount() {
        Object result = em.createNativeQuery("""
            select count(*)
              from product
             where product_del_status = 1
            """).getSingleResult();

        return ((Number) result).intValue();
    }

    private int findDashboardLackCount() {
        Object result = em.createNativeQuery("""
            select count(*)
              from product p
              left join (
                    select product_code, qty
                      from (
                            select cm.*,
                                   row_number() over (
                                       partition by cm.product_code
                                       order by cm.closing_ym desc, cm.closing_type desc
                                   ) as rn
                              from closing_month cm
                             where cm.closing_type in (1, 2)
                           )
                     where rn = 1
                   ) cm
                on cm.product_code = p.product_code
             where p.product_del_status = 1
               and nvl(cm.qty, 0) < nvl(p.product_proper_qty, 0)
            """).getSingleResult();

        return ((Number) result).intValue();
    }

    
    @Override
    public String findLatestCurrentStockClosingYm() {
        Object result = em.createNativeQuery("""
            select max(closing_ym)
              from closing_month
             where closing_type in (1, 2)
            """)
            .getSingleResult();

        if (result == null) {
            return null;
        }

        String closingYm = (String) result;

        if (closingYm.length() == 6) {
            return closingYm.substring(0, 4) + "년 " + closingYm.substring(4, 6) + "월";
        }

        return closingYm;
    }

    @Override
    public List<ClosingDayDto> findRecentAdjustmentDayList(LocalDate startDate, LocalDate endDate) {
        List<Object[]> rows = em.createNativeQuery("""
            select *
              from (
                    select d.closing_ymd,
                           nvl(cd.closing_status, 0) as closing_status,
                           cd.reg_emp_no,
                           cd.reg_date
                      from (
                            select to_char(:startDate + level - 1, 'YYYYMMDD') as closing_ymd
                              from dual
                           connect by level <= (:endDate - :startDate + 1)
                           ) d
                      left join closing_day cd
                        on cd.closing_ymd = d.closing_ymd
                     order by d.closing_ymd desc
                   )
             where rownum <= 5
            """)
            .setParameter("startDate", java.sql.Date.valueOf(startDate))
            .setParameter("endDate", java.sql.Date.valueOf(endDate))
            .getResultList();

        List<ClosingDayDto> result = new ArrayList<>();

        for (Object[] row : rows) {
            ClosingDayDto dto = new ClosingDayDto();

            dto.setClosing_ymd((String) row[0]);
            dto.setClosing_status(row[1] == null ? 0 : ((Number) row[1]).intValue());
            dto.setReg_emp_no(row[2] == null ? 0 : ((Number) row[2]).intValue());
            dto.setReg_date(row[3] == null ? null : java.sql.Timestamp.valueOf((LocalDateTime) row[3]));

            result.add(dto);
        }

        return result;
    }
    
    @Override
    @Transactional
    public void callAdjustmentClosingPackage(String closingYmd, int closingStatus, int regEmpNo) {
        em.createNativeQuery("""
            BEGIN
                MM_COLLECTION_PKG.MM_COLLECTION_MAIN(
                    :closingYmd,
                    :closingStatus,
                    :regEmpNo
                );
            END;
            """)
            .setParameter("closingYmd", closingYmd)
            .setParameter("closingStatus", closingStatus)
            .setParameter("regEmpNo", regEmpNo)
            .executeUpdate();
    }


}
