package com.oracle.comventory.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.oracle.comventory.dto.dashboard.DashboardDailyInoutDto;
import com.oracle.comventory.dto.dashboard.DashboardSummaryDto;
import com.oracle.comventory.dto.inventory.StockListDto;
import com.oracle.comventory.service.inventory.InventoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final InventoryService inventoryService;

    @GetMapping("/")
    public String mainPage(Model model) {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate startDate = today.minusDays(6);

        DashboardSummaryDto summary =
                inventoryService.getDashboardSummary(today, yesterday);

        List<DashboardDailyInoutDto> dailyInout =
                inventoryService.getDashboardDailyInout(startDate, today);

        List<StockListDto> shortageItems =
                inventoryService.getDashboardShortageItems(3);

        model.addAttribute("summary", summary);
        model.addAttribute("shortageItems", shortageItems);

        model.addAttribute("chartLabelsJson", toStringArrayJson(
                dailyInout.stream().map(DashboardDailyInoutDto::getLabel).collect(Collectors.toList())
        ));
        model.addAttribute("chartInQtyJson", toNumberArrayJson(
                dailyInout.stream().map(DashboardDailyInoutDto::getInQty).collect(Collectors.toList())
        ));
        model.addAttribute("chartOutQtyJson", toNumberArrayJson(
                dailyInout.stream().map(DashboardDailyInoutDto::getOutQty).collect(Collectors.toList())
        ));

        return "mainPage";
    }

    private String toStringArrayJson(List<String> values) {
        return values.stream()
                .map(value -> "\"" + value + "\"")
                .collect(Collectors.joining(",", "[", "]"));
    }

    private String toNumberArrayJson(List<Integer> values) {
        return values.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",", "[", "]"));
    }
    
    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "common/accessDenied";
    }
}
