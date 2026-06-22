package com.oracle.comventory.domain.inventory;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealItemsId implements Serializable {
    private String real_ymd;
    private int product_code;
}