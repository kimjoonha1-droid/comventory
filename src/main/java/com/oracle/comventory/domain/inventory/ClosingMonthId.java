package com.oracle.comventory.domain.inventory;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClosingMonthId implements Serializable {
    private String closing_ym;
    private int closing_type;
    private int product_code;
}
