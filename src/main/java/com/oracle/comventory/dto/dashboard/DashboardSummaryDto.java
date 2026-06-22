package com.oracle.comventory.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardSummaryDto {
    private int todayInQty;
    private int yesterdayInQty;
    private int todayOutQty;
    private int yesterdayOutQty;
    private int lackCount;
    private int totalProductCount;

    public int getInDiff() {
        return todayInQty - yesterdayInQty;
    }

    public int getOutDiff() {
        return todayOutQty - yesterdayOutQty;
    }

    public int getNormalCount() {
        return totalProductCount - lackCount;
    }
}
