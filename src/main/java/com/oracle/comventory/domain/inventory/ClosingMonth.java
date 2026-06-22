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
@Table(name = "closing_month")
@IdClass(ClosingMonthId.class)
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClosingMonth {
    @Id
    private String closing_ym;

    @Id
    private int closing_type;

    @Id
    private int product_code;

    private int qty;
    private int reg_emp_no;

    @CreatedDate
    private LocalDateTime reg_date;
}
