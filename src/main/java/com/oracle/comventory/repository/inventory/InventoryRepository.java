package com.oracle.comventory.repository.inventory;

import java.util.List;
import java.time.LocalDate;

import com.oracle.comventory.dto.inventory.StockListDto;
import com.oracle.comventory.dto.statusType.StatusTypeDto;
import com.oracle.comventory.dto.realItems.RealItemsDto;
import com.oracle.comventory.dto.closingDay.ClosingDayDto;
import com.oracle.comventory.dto.dashboard.DashboardSummaryDto;
import com.oracle.comventory.dto.dashboard.DashboardDailyInoutDto;

public interface InventoryRepository {

    public List<StockListDto> findStockList(
    		String itemType,
    		String closingType,
            String searchYm,
            String searchKeyword,
            String category,
            String inventoryStatus,
            int    start,
            int    end);

	public int countStockList(
			String itemType,
			String closingType,
			String searchYm,
			String searchKeyword,
			String category,
			String inventoryStatus);

	public int countStockLack(
	        String itemType,
	        String closingType,
	        String searchYm,
	        String searchKeyword,
	        String category,
	        String inventoryStatus);

    public List<StatusTypeDto> findCategories();
    
    public List<RealItemsDto> findPhysicalInventoryList(
            String itemType,
            String searchYm,
            String searchKeyword,
            String category,
            int start,
            int end);

	public int countPhysicalInventoryList(String itemType, String searchYm, String searchKeyword, String category);

    public List<RealItemsDto> findPhysicalInventoryProductList(String searchYm);
    
    public boolean existsPhysicalInventory(String realYmd, int productCode);

    public void insertPhysicalInventory(RealItemsDto dto);

    public void updatePhysicalInventoryDate(String originalRealYmd, int productCode, String realYmd);

    public void updatePhysicalInventoryConfirmStatus(String realYmd, int productCode, int confirmStatus);

    public List<StockListDto> findCurrentStockList(
            String itemType,
            String searchKeyword,
            String category,
            String inventoryStatus,
            int    start,
            int    end);

	public int countCurrentStockList(String itemType, String searchKeyword, String category, String inventoryStatus);

	public int countCurrentStockLack(
	        String itemType,
	        String searchKeyword,
	        String category,
	        String inventoryStatus);

    public String findLatestCurrentStockClosingYm();

    public List<ClosingDayDto> findRecentAdjustmentDayList(LocalDate startDate, LocalDate endDate);

    public void callAdjustmentClosingPackage(String closingYmd, int closingStatus, int regEmpNo);

    DashboardSummaryDto findDashboardSummary(LocalDate today, LocalDate yesterday);

    List<DashboardDailyInoutDto> findDashboardDailyInout(LocalDate startDate, LocalDate endDate);

    List<StockListDto> findDashboardShortageItems(int limit);

}
