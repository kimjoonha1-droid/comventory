package com.oracle.comventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.comventory.service.Paging;
import com.oracle.comventory.service.inventory.InventoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oracle.comventory.dto.inventory.StockListDto;
import com.oracle.comventory.dto.closingDay.ClosingDayDto;
import com.oracle.comventory.dto.emp.EmpDto;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import com.oracle.comventory.dto.realItems.RealItemsDto;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/inventory")
public class InventoryController {
	
	private final InventoryService inventoryService;
	
	@GetMapping("/stockListView")
	public String stockListView(
			@RequestParam(value = "itemType", required = false) String itemType,
			@RequestParam(value = "closingType", required = false, defaultValue = "1") String closingType,
			@RequestParam(value = "searchYm", required = false) String searchYm,
	        @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
	        @RequestParam(value = "category", required = false) String category,
	        @RequestParam(value = "inventoryStatus", required = false) String inventoryStatus,
	        @RequestParam(value = "pageNum", required = false) String pageNum,
	        Model model) {

	    String searchYmValue;

	    if (searchYm == null || searchYm.isBlank()) {
	        searchYmValue = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
	        searchYm = searchYmValue.replace("-", "");
	    } else {
	        searchYmValue = searchYm;
	        searchYm = searchYm.replace("-", "");
	    }
	    
	    int total = inventoryService.getStockListTotal(
	            itemType, closingType, searchYm, searchKeyword, category, inventoryStatus);
	    
	    Paging page = new Paging(total, pageNum);
	    
	    List<StockListDto> stockList =
	    		inventoryService.getStockList(
	    		        itemType,
	    		        closingType,
	    		        searchYm,
	    		        searchKeyword,
	    		        category,
	    		        inventoryStatus,
	    		        page.getStart(),
	    		        page.getEnd()
	    		);
	    
	    model.addAttribute("stockList", stockList);
	    model.addAttribute("page", page);
	    model.addAttribute("lackCount",
	    		inventoryService.getStockLackCount(itemType, closingType, searchYm, searchKeyword, category, inventoryStatus));
	    model.addAttribute("categories", inventoryService.getCategories());
	    model.addAttribute("searchYm", searchYm);
	    model.addAttribute("searchYmValue", searchYmValue);
	    model.addAttribute("closingType", closingType);

	    return "inventory/stockListView";
	}

	@RequestMapping("/physicalInventoryView")
	public String physicalInventoryView(
	        @RequestParam(value = "itemType", required = false) String itemType,
	        @RequestParam(value = "searchYm", required = false) String searchYm,
	        @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
	        @RequestParam(value = "category", required = false) String category,
	        @RequestParam(value = "pageNum", required = false) String pageNum,
	        Model model) {

	    String searchYmValue;

	    if (searchYm == null || searchYm.isBlank()) {
	        searchYmValue = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
	        searchYm = searchYmValue.replace("-", "");
	    } else {
	        searchYmValue = searchYm;
	        searchYm = searchYm.replace("-", "");
	    }
	    
	    int total = inventoryService.getPhysicalInventoryTotal(itemType, searchYm, searchKeyword, category);
	    Paging page = new Paging(total, pageNum);

	    model.addAttribute("physicalList",
	            inventoryService.getPhysicalInventoryList(itemType, searchYm, searchKeyword, category, page.getStart(), page.getEnd()));
	    model.addAttribute("page", page);

	    List<RealItemsDto> productList =
	            inventoryService.getPhysicalInventoryProductList(searchYm);

	    System.out.println("physicalInventoryView searchYm = " + searchYm);
	    System.out.println("productList size = " + productList.size());

	    for (RealItemsDto product : productList) {
	        System.out.println("product = " + product);
	    }

	    model.addAttribute("productList", productList);
	    
	    model.addAttribute("categories", inventoryService.getCategories());
	    model.addAttribute("searchYm", searchYm);
	    model.addAttribute("searchYmValue", searchYmValue);

	    return "inventory/physicalInventoryView";
	}
	
	@PostMapping("/createPhysicalInventory")
	public String createPhysicalInventory(
	        RealItemsDto realItemsDto,
	        @RequestParam(value = "searchYm", required = false) String searchYm,
	        Model model) {

	    try {
	        inventoryService.createPhysicalInventory(realItemsDto);
	    } catch (IllegalStateException e) {
	    	return "redirect:/inventory/physicalInventoryView?error=duplicate&searchYm=" + searchYm;
	    }

	    if (searchYm != null && !searchYm.isBlank()) {
	        return "redirect:/inventory/physicalInventoryView?searchYm=" + searchYm;
	    }

	    return "redirect:/inventory/physicalInventoryView";
	}

	@PostMapping("/updatePhysicalInventoryDate")
	public String updatePhysicalInventoryDate(
	        @RequestParam("original_real_ymd") String originalRealYmd,
	        @RequestParam("product_code") int productCode,
	        @RequestParam("real_ymd") String realYmd,
	        @RequestParam(value = "searchYm", required = false) String searchYm) {

	    inventoryService.updatePhysicalInventoryDate(originalRealYmd, productCode, realYmd);

	    if (searchYm != null && !searchYm.isBlank()) {
	        return "redirect:/inventory/physicalInventoryView?searchYm=" + searchYm;
	    }

	    return "redirect:/inventory/physicalInventoryView";
	}
	
	@PostMapping("/approvePhysicalInventory")
	public String approvePhysicalInventory(
	        @RequestParam("real_ymd") String realYmd,
	        @RequestParam("product_code") int productCode,
	        @RequestParam(value = "searchYm", required = false) String searchYm,
	        HttpSession session) {

	    EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");

	    if (!isInventoryManager(loginUser)) {
	        return "redirect:/accessDenied";
	    }

	    inventoryService.approvePhysicalInventory(realYmd, productCode);

	    if (searchYm != null && !searchYm.isBlank()) {
	        return "redirect:/inventory/physicalInventoryView?searchYm=" + searchYm;
	    }

	    return "redirect:/inventory/physicalInventoryView";
	}
	
	@GetMapping("/currentStockView")
	public String currentStockView(
	        @RequestParam(value = "itemType", required = false) String itemType,
	        @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
	        @RequestParam(value = "category", required = false) String category,
	        @RequestParam(value = "inventoryStatus", required = false) String inventoryStatus,
	        @RequestParam(value = "pageNum", required = false) String pageNum,
	        Model model) {
		
		int total = inventoryService.getCurrentStockListTotal(itemType, searchKeyword, category, inventoryStatus);
		Paging page = new Paging(total, pageNum);

		List<StockListDto> currentStockList =
		        inventoryService.getCurrentStockList(itemType, searchKeyword, category, inventoryStatus, page.getStart(), page.getEnd());

		model.addAttribute("currentStockList", currentStockList);
		model.addAttribute("page", page);
		model.addAttribute("lackCount",
		        inventoryService.getCurrentStockLackCount(itemType, searchKeyword, category, inventoryStatus));
	    model.addAttribute("categories", inventoryService.getCategories());
	    model.addAttribute("latestClosingYm", inventoryService.getLatestCurrentStockClosingYm());

	    return "inventory/currentStockView";
	}

	
	@RequestMapping("/inventoryAdjustmentView")
	public String inventoryAdjustmentView(Model model) {
	    LocalDate currentDate = LocalDate.now();
	    LocalDate startDate = currentDate.withDayOfMonth(1);

	    model.addAttribute("currentYmText",
	            currentDate.getYear() + "년 " + currentDate.getMonthValue() + "월 " + currentDate.getDayOfMonth() + "일");

	    List<ClosingDayDto> adjList =
	            inventoryService.getRecentAdjustmentDayList(startDate, currentDate);

	    model.addAttribute("adjList", adjList);

	    int todayClosingStatus = adjList.isEmpty() ? 0 : adjList.get(0).getClosing_status();

	    if (todayClosingStatus == 1) {
	        model.addAttribute("latestClosingStatus", "TEMP_CLOSED");
	    } else if (todayClosingStatus == 2) {
	        model.addAttribute("latestClosingStatus", "CLOSED");
	    } else {
	        model.addAttribute("latestClosingStatus", "READY");
	    }

	    return "inventory/inventoryAdjustmentView";
	}
	
	@PostMapping("/processAdjustmentClosing")
	public String processAdjustmentClosing(
	        @RequestParam("closingStatus") int closingStatus,
	        HttpSession session) {

	    EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");

	    if (loginUser == null) {
	        return "redirect:/login";
	    }
	    
	    if (!isInventoryManager(loginUser)) {
	        return "redirect:/accessDenied";
	    }

	    String closingYmd = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

	    inventoryService.processAdjustmentClosing(
	            closingYmd,
	            closingStatus,
	            loginUser.getEmp_no()
	    );

	    return "redirect:/inventory/inventoryAdjustmentView";
	}
	
	private boolean isInventoryManager(EmpDto loginUser) {
	    return loginUser != null
	            && loginUser.getDept_code() == 7000
	            && loginUser.getUser_access() == 2;
	}

}
