package com.oracle.comventory.service.inventory;

import java.util.List;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.oracle.comventory.dto.inventory.StockListDto;
import com.oracle.comventory.repository.inventory.InventoryRepository;
import com.oracle.comventory.dto.statusType.StatusTypeDto;
import com.oracle.comventory.dto.closingDay.ClosingDayDto;
import com.oracle.comventory.dto.dashboard.DashboardSummaryDto;
import com.oracle.comventory.dto.dashboard.DashboardDailyInoutDto;


import com.oracle.comventory.dto.realItems.RealItemsDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    
    @Override
    public List<StockListDto> getStockList(
            String itemType,
            String closingType,
            String searchYm,
            String searchKeyword,
            String category,
            String inventoryStatus,
            int start,
            int end) {

    	return inventoryRepository.findStockList(itemType, closingType, searchYm, searchKeyword, category, inventoryStatus, start, end);
    }
    
    @Override
    public int getStockListTotal(
            String itemType,
            String closingType,
            String searchYm,
            String searchKeyword,
            String category,
            String inventoryStatus) {

    	return inventoryRepository.countStockList(itemType, closingType, searchYm, searchKeyword, category, inventoryStatus);
    }

    @Override
    public int getStockLackCount(
            String itemType,
            String closingType,
            String searchYm,
            String searchKeyword,
            String category,
            String inventoryStatus) {

    	return inventoryRepository.countStockLack(
    	        itemType, closingType, searchYm, searchKeyword, category, inventoryStatus);
    }
    
    @Override
    public List<StatusTypeDto> getCategories() {
        return inventoryRepository.findCategories();
    }
    
    @Override
    public List<RealItemsDto> getPhysicalInventoryList(
            String itemType,
            String searchYm,
            String searchKeyword,
            String category,
            int start,
            int end) {

        return inventoryRepository.findPhysicalInventoryList(itemType, searchYm, searchKeyword, category, start, end);
    }

    @Override
    public int getPhysicalInventoryTotal(
            String itemType,
            String searchYm,
            String searchKeyword,
            String category) {

        return inventoryRepository.countPhysicalInventoryList(itemType, searchYm, searchKeyword, category);
    }

    
    @Override
    public List<RealItemsDto> getPhysicalInventoryProductList(String searchYm) {
        System.out.println("Service getPhysicalInventoryProductList searchYm = " + searchYm);

        List<RealItemsDto> productList =
                inventoryRepository.findPhysicalInventoryProductList(searchYm);

        System.out.println("Service productList size = " + productList.size());

        return productList;
    }
    
    @Override
    public void createPhysicalInventory(RealItemsDto realItemsDto) {
        inventoryRepository.insertPhysicalInventory(realItemsDto);
    }
    
    @Override
    public void updatePhysicalInventoryDate(String originalRealYmd, int productCode, String realYmd) {
        inventoryRepository.updatePhysicalInventoryDate(originalRealYmd, productCode, realYmd);
    }
    
    @Override
    public void approvePhysicalInventory(String realYmd, int productCode) {
        inventoryRepository.updatePhysicalInventoryConfirmStatus(realYmd, productCode, 2);
    }
    
    @Override
    public List<StockListDto> getCurrentStockList(
            String itemType,
            String searchKeyword,
            String category,
            String inventoryStatus,
            int    start,
            int    end) {

        return inventoryRepository.findCurrentStockList(itemType, searchKeyword, category, inventoryStatus, start, end);
    }

    @Override
    public int getCurrentStockListTotal(
            String itemType,
            String searchKeyword,
            String category,
            String inventoryStatus) {

        return inventoryRepository.countCurrentStockList(itemType, searchKeyword, category, inventoryStatus);
    }

    @Override
    public int getCurrentStockLackCount(
            String itemType,
            String searchKeyword,
            String category,
            String inventoryStatus) {

        return inventoryRepository.countCurrentStockLack(
                itemType, searchKeyword, category, inventoryStatus);
    }
    
    @Override
    public String getLatestCurrentStockClosingYm() {
        return inventoryRepository.findLatestCurrentStockClosingYm();
    }

    @Override
    public List<ClosingDayDto> getRecentAdjustmentDayList(LocalDate startDate, LocalDate endDate) {
        return inventoryRepository.findRecentAdjustmentDayList(startDate, endDate);
    }

    @Override
    public void processAdjustmentClosing(String closingYmd, int closingStatus, int regEmpNo) {
        inventoryRepository.callAdjustmentClosingPackage(closingYmd, closingStatus, regEmpNo);
    }

    @Override
    public DashboardSummaryDto getDashboardSummary(LocalDate today, LocalDate yesterday) {
        return inventoryRepository.findDashboardSummary(today, yesterday);
    }

    @Override
    public List<DashboardDailyInoutDto> getDashboardDailyInout(LocalDate startDate, LocalDate endDate) {
        return inventoryRepository.findDashboardDailyInout(startDate, endDate);
    }

    @Override
    public List<StockListDto> getDashboardShortageItems(int limit) {
        return inventoryRepository.findDashboardShortageItems(limit);
    }

    
}
