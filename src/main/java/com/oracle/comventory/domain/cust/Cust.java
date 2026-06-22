package com.oracle.comventory.domain.cust;

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
@Table(name = "CUST_TABLE")
@Getter
@ToString
@NoArgsConstructor
public class Cust {

    @Id
    @Column(name = "CUST_CODE")
    private Long cust_code;

    @Column(name = "CUST_NAME")
    private String cust_name;

    @Column(name = "CUST_TYPE")
    private Integer cust_type;

    @Column(name = "BUSINESS_NO")
    private String business_no;

    @Column(name = "CEO_NAME")
    private String ceo_name;

    @Column(name = "CUST_TEL")
    private String cust_tel;

    @Column(name = "CUST_EMAIL")
    private String cust_email;

    @Column(name = "CUST_ADDRESS")
    private String cust_address;

    @ColumnDefault("1")
    @Column(name = "CUST_DEL_STATUS", nullable = false)
    private Integer cust_del_status;

    @Column(name = "REG_EMP_NO")
    private Long reg_emp_no;

    @CreationTimestamp
    @Column(name = "REG_DATE")
    private Date reg_date;

    // 등록용 생성자
    public Cust(Long cust_code,
                String cust_name,
                Integer cust_type,
                String business_no,
                String ceo_name,
                String cust_tel,
                String cust_email,
                String cust_address,
                Long reg_emp_no) {

        this.cust_code = cust_code;
        this.cust_name = cust_name;
        this.cust_type = cust_type;
        this.business_no = business_no;
        this.ceo_name = ceo_name;
        this.cust_tel = cust_tel;
        this.cust_email = cust_email;
        this.cust_address = cust_address;
        this.cust_del_status = 1; // 기본값: 사용중
        this.reg_emp_no = reg_emp_no;
    }

    // 상태 포함 생성자
    public Cust(Long cust_code,
                String cust_name,
                Integer cust_type,
                String business_no,
                String ceo_name,
                String cust_tel,
                String cust_email,
                String cust_address,
                Integer cust_del_status,
                Long reg_emp_no) {

        this.cust_code = cust_code;
        this.cust_name = cust_name;
        this.cust_type = cust_type;
        this.business_no = business_no;
        this.ceo_name = ceo_name;
        this.cust_tel = cust_tel;
        this.cust_email = cust_email;
        this.cust_address = cust_address;
        this.cust_del_status = cust_del_status;
        this.reg_emp_no = reg_emp_no;
    }

    // change 메서드
    public void changeCustName(String cust_name) {
        this.cust_name = cust_name;
    }

    public void changeCustType(Integer cust_type) {
        this.cust_type = cust_type;
    }

    public void changeBusinessNo(String business_no) {
        this.business_no = business_no;
    }

    public void changeCeoName(String ceo_name) {
        this.ceo_name = ceo_name;
    }

    public void changeCustTel(String cust_tel) {
        this.cust_tel = cust_tel;
    }

    public void changeCustEmail(String cust_email) {
        this.cust_email = cust_email;
    }

    public void changeCustAddress(String cust_address) {
        this.cust_address = cust_address;
    }

    public void changeCustDelStatus(Integer cust_del_status) {
        this.cust_del_status = cust_del_status;
    }
}