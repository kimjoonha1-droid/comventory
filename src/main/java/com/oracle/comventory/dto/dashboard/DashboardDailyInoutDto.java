package com.oracle.comventory.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardDailyInoutDto {
    private String ymd;
    private String label;
    private int inQty;
    private int outQty;
}
