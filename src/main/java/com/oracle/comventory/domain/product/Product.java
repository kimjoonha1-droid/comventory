package com.oracle.comventory.domain.product;

import java.util.Date;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "PRODUCT")
@Getter
@ToString
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "PRODUCT_CODE")
    private Long product_code;

    @Column(name = "PRODUCT_NAME")
    private String product_name;

    @Column(name = "PRODUCT_CATEGORY")
    private Integer product_category;

    @Column(name = "PRODUCT_STATUS")
    private Integer product_status;

    @Column(name = "PRODUCT_PRICE")
    private Integer product_price;

    @Column(name = "PRODUCT_PROPER_QTY")
    private Integer product_proper_qty;

    @ColumnDefault("1")
    @Column(name = "PRODUCT_DEL_STATUS", nullable = false)
    private Integer product_del_status;

    @Column(name = "REG_EMP_NO")
    private Long reg_emp_no;

    @CreationTimestamp
    @Column(name = "REG_DATE")
    private Date reg_date;

    // 등록용 생성자
    public Product(Long product_code,
                   String product_name,
                   Integer product_category,
                   Integer product_status,
                   Integer product_price,
                   Integer product_proper_qty,
                   Long reg_emp_no) {

        this.product_code = product_code;
        this.product_name = product_name;
        this.product_category = product_category;
        this.product_status = product_status;
        this.product_price = product_price;
        this.product_proper_qty = product_proper_qty;
        this.product_del_status = 1; // 기본값: 사용
        this.reg_emp_no = reg_emp_no;
    }

    // 등록/상태 지정용 생성자
    public Product(Long product_code,
                   String product_name,
                   Integer product_category,
                   Integer product_status,
                   Integer product_price,
                   Integer product_proper_qty,
                   Integer product_del_status,
                   Long reg_emp_no) {

        this.product_code = product_code;
        this.product_name = product_name;
        this.product_category = product_category;
        this.product_status = product_status;
        this.product_price = product_price;
        this.product_proper_qty = product_proper_qty;
        this.product_del_status = product_del_status;
        this.reg_emp_no = reg_emp_no;
    }

    public void changeProductName(String product_name) {
        this.product_name = product_name;
    }

    public void changeProductCategory(Integer product_category) {
        this.product_category = product_category;
    }

    public void changeProductStatus(Integer product_status) {
        this.product_status = product_status;
    }

    public void changeProductPrice(Integer product_price) {
        this.product_price = product_price;
    }

    public void changeProductProperQty(Integer product_proper_qty) {
        this.product_proper_qty = product_proper_qty;
    }

    public void changeProductDelStatus(Integer product_del_status) {
        this.product_del_status = product_del_status;
    }
}