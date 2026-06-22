package com.oracle.comventory.dto.inventory;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockListDto {
    private int product_code;
    private String product_name;
    private String category_name;
    private int qty;
    private int proper_qty;
    private Date reg_date;
}
