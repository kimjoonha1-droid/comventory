package com.oracle.comventory.domain.inventory;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "real_items")
@IdClass(RealItemsId.class)
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RealItems {
    @Id
    private String real_ymd;

    @Id
    private int product_code;

    private int change_qty;
    private String change_reason;
    private int confirm_status;
    private int reg_emp_no;

    @CreatedDate
    private LocalDateTime reg_date;
}