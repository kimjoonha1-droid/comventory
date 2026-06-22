package com.oracle.comventory.service.inventory;

import java.util.List;
import java.time.LocalDate;

import com.oracle.comventory.dto.inventory.StockListDto;
import com.oracle.comventory.dto.statusType.StatusTypeDto;
import com.oracle.comventory.dto.closingDay.ClosingDayDto;
import com.oracle.comventory.dto.dashboard.DashboardSummaryDto;
import com.oracle.comventory.dto.dashboard.DashboardDailyInoutDto;

import com.oracle.comventory.dto.realItems.RealItemsDto;

public interface InventoryService {

	public List<StockListDto> getStockList(
	        String itemType,
	        String closingType,
	        String searchYm,
	        String searchKeyword,
	        String category,
	        String inventoryStatus,
	        int    start,
	        int    end);
    
    public int getStockListTotal(String itemType, String closingType, String searchYm, String searchKeyword, String category,
            String inventoryStatus);
	
	public int getStockLackCount(
	        String itemType,
	        String closingType,
	        String searchYm,
	        String searchKeyword,
	        String category,
	        String inventoryStatus);
	
    public List<StatusTypeDto> getCategories();
    
    public List<RealItemsDto> getPhysicalInventoryList(
            String itemType,
            String searchYm,
            String searchKeyword,
            String category,
            int    start,
            int    end);

	public int getPhysicalInventoryTotal(String itemType, String searchYm, String searchKeyword, String category);

    public List<RealItemsDto> getPhysicalInventoryProductList(String searchYm);

    public void createPhysicalInventory(RealItemsDto realItemsDto);

    public void updatePhysicalInventoryDate(String originalRealYmd, int productCode, String realYmd);
    
    public void approvePhysicalInventory(String realYmd, int productCode);
    
    public List<StockListDto> getCurrentStockList(
            String itemType,
            String searchKeyword,
            String category,
            String inventoryStatus,
            int    start,
            int    end);

	public int getCurrentStockListTotal(String itemType, String searchKeyword, String category, String inventoryStatus);
	
	public int getCurrentStockLackCount(
	        String itemType,
	        String searchKeyword,
	        String category,
	        String inventoryStatus);

    public String getLatestCurrentStockClosingYm();

    public List<ClosingDayDto> getRecentAdjustmentDayList(LocalDate startDate, LocalDate endDate);

    public void processAdjustmentClosing(String closingYmd, int closingStatus, int regEmpNo);

    DashboardSummaryDto getDashboardSummary(LocalDate today, LocalDate yesterday);

    List<DashboardDailyInoutDto> getDashboardDailyInout(LocalDate startDate, LocalDate endDate);

    List<StockListDto> getDashboardShortageItems(int limit);

}
