package com.oracle.comventory.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oracle.comventory.dto.emp.EmpDto;
import com.oracle.comventory.dto.production.ProductionDto;
import com.oracle.comventory.service.Paging;
import com.oracle.comventory.service.closingService.ClosingService;
import com.oracle.comventory.service.production.ProductionService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/production")
public class ProductionController {

    private final ProductionService productionService;
    private final ClosingService closingService;

    @GetMapping("/list")
    public String listPage(ProductionDto productionDto, Model model,
    		 											HttpSession session) {
    	
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}
    	
        int totalCount = productionService.totalProduction();

        Paging page = new Paging(totalCount, productionDto.getCurrentPage());

        productionDto.setStart(page.getStart());
        productionDto.setEnd(page.getEnd());

        List<ProductionDto> productionList =
                productionService.productionList(productionDto);

        boolean isProductionDept = isProductionDept(session);

        model.addAttribute("isProductionDept", isProductionDept);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("productionList", productionList);
        model.addAttribute("page", page);

        return "production/list";
    }

    @GetMapping("/insertForm")
    public String insertForm(Model model,
            HttpSession session){
    	
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}
    	
    	if (isTodayTempClosed()) {
    	    model.addAttribute("msg",
    	            "가마감 처리된 날짜에는 신규 등록할 수 없습니다.");
    	    model.addAttribute("url", "/production/list");

    	    return "alert";
    	}
    	
    	if (isTodayClosed()) {
            return "redirect:/production/list";
        }
    	
    	EmpDto loginUser =
    	        (EmpDto) session.getAttribute("loginUser");

    	boolean isProductionDept =
    	        loginUser != null
    	     && loginUser.getDept_code() == 6000;

    	if (!isProductionDept) {
    	    return "redirect:/production/list";
    	}
    	
    	String today = LocalDate.now()
    	        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    	model.addAttribute("today", today);
        model.addAttribute("finishedProductionList",
                productionService.finishedProductionList());

        return "production/insertForm";
    }

    @PostMapping("/insert")
    public String insertProduction(ProductionDto productionDto,
                                   HttpSession session, Model model) {
    	
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}
    	
    	if (isTodayTempClosed()) {
    	    model.addAttribute("msg", "가마감 처리된 날짜에는 신규 등록할 수 없습니다.");
    	    model.addAttribute("url", "/production/list");

    	    return "alert";
    	}
    	
        if (isTodayClosed()) {
            return "redirect:/production/list";
        }

        if (!isProductionDept(session)) {
            return "redirect:/production/list";
        }

        productionService.insertProduction(productionDto);

        return "redirect:/production/list";
    }
    
    @GetMapping("/detail")
    public String detailProduction(
            @RequestParam("productionCode") int productionCode,
            Model model, HttpSession session) {
    	
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}

        ProductionDto production =
            productionService.productionDetail(productionCode);

        String ymd =
                production.getCompleteDate().replace("-", "");

        boolean isClosed =
                closingService.isBeforeTrueClosed(ymd);
        
        EmpDto loginUser =
            (EmpDto) session.getAttribute("loginUser");

        boolean isAdmin =
                loginUser != null
             && loginUser.getUser_access() == 2;
        
        boolean isProductionDept =
                loginUser != null
             && loginUser.getDept_code() == 6000;

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isClosed", isClosed);
        model.addAttribute("production", production);
        model.addAttribute("isProductionDept",isProductionDept);

        return "production/detail";
    }
    
    @GetMapping("/delete")
    public String deleteProduction(
            @RequestParam("productionCode") int productionCode,
            HttpSession session, Model model) {
    	
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}
    	
    	if (isTodayClosed()) {
    	    return "redirect:/production/list";
    	}

        ProductionDto production =
                productionService.productionDetail(productionCode);

        int status = production.getProductionStatus();
        if (status != 1 && status != 3) {
            return "redirect:/production/detail?productionCode="
                    + productionCode;
        }
        if (!isProductionDept(session) || !isAdmin(session)) {
            return "redirect:/production/detail?productionCode=" + productionCode;
        }
        
        String ymd =
                production.getCompleteDate().replace("-", "");

        if (closingService.isBeforeTrueClosed(ymd)) {
            model.addAttribute("msg", "수불마감 처리된 데이터는 삭제할 수 없습니다.");
            model.addAttribute("url", "/production/detail?productionCode=" + productionCode);

            return "alert";
        }
        
        productionService.deleteProduction(productionCode);

        return "redirect:/production/list";
    }

    @GetMapping("/updateForm")
    public String updateForm(
            @RequestParam("productionCode") int productionCode,
            Model model, HttpSession session) {
    	
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}
    	
    	if (isTodayClosed()) {
    	    return "redirect:/production/list";
    	}
    	
        ProductionDto production =
                productionService.productionDetail(productionCode);

        int status = production.getProductionStatus();
        if (status != 1 && status != 3) {
            return "redirect:/production/detail?productionCode="
                    + productionCode;
        }
        
        String ymd =
                production.getCompleteDate().replace("-", "");

        if (closingService.isBeforeTrueClosed(ymd)) {
            model.addAttribute("msg", "수불마감 처리된 데이터는 수정할 수 없습니다.");
            model.addAttribute("url", "/production/detail?productionCode=" + productionCode);

            return "alert";
        }
        
        EmpDto loginUser =
                (EmpDto) session.getAttribute("loginUser");

        boolean isAdmin =
                loginUser != null
             && loginUser.getUser_access() == 2;
        
        boolean isProductionDept =
                loginUser != null
             && loginUser.getDept_code() == 6000;
        
        if (!isProductionDept) {
            return "redirect:/production/detail?productionCode="
                    + productionCode;
        }
        
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("production", production);
        model.addAttribute(
                "finishedProductionList",
                productionService.finishedProductionList());

        return "production/updateForm";
    }

    @PostMapping("/update")
    public String updateProduction(ProductionDto pro,
                                   HttpSession session, Model model) {
    	
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}
    	
    	String ymd =
    	        pro.getCompleteDate().replace("-", "");

    	if (closingService.isBeforeTrueClosed(ymd)) {
    	    model.addAttribute("msg", "수불마감 처리된 데이터는 수정할 수 없습니다.");
    	    model.addAttribute("url",
    	            "/production/detail?productionCode="
    	            + pro.getProductionCode());

    	    return "alert";
    	}

        if (isTodayClosed()) {
            return "redirect:/production/list";
        }

        if (!isProductionDept(session)) {
            return "redirect:/production/list";
        }

        productionService.updateProduction(pro);

        return "redirect:/production/detail?productionCode="
                + pro.getProductionCode();
    }
    
    private boolean isTodayClosed() {
        String today = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    	//String today = "20991231";
    	
    	boolean result = closingService.isTrueClosed(today);

        System.out.println("today = " + today);
        System.out.println("isClosed = " + result);

        return result;
    }
    
    private boolean isProductionDept(HttpSession session) {
        EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");
        return loginUser != null && loginUser.getDept_code() == 6000;
    }

    private boolean isAdmin(HttpSession session) {
        EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");
        return loginUser != null && loginUser.getUser_access() == 2;
    }
    
    private boolean isExternalEmp(HttpSession session) {
        EmpDto loginUser =
                (EmpDto) session.getAttribute("loginUser");

        return loginUser != null
            && loginUser.getEmp_grade() == 9;
    }
    
    private boolean isTodayTempClosed() {
        String today = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return closingService.isTempClosed(today);
    }
}