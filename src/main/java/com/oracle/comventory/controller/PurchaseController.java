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
import com.oracle.comventory.dto.purchase.PurchaseDetailDto;
import com.oracle.comventory.dto.purchase.PurchaseOrderDto;
import com.oracle.comventory.service.Paging;
import com.oracle.comventory.service.closingService.ClosingService;
import com.oracle.comventory.service.purchase.PurchaseService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/purchase")
public class PurchaseController {
	
	private final PurchaseService purchaseService;
	private final ClosingService closingService;

	@GetMapping("/list")
	public String listPage(PurchaseOrderDto pOrder,
	                       Model model,
	                       HttpSession session) {
		if (isExternalEmp(session)) {
		    model.addAttribute("msg", "권한이 없습니다.");
		    model.addAttribute("url", "/");

		    return "alert";
		}

	    int totalCount = purchaseService.totalPor();

	    Paging page = new Paging(totalCount, pOrder.getCurrentPage());

	    pOrder.setStart(page.getStart());
	    pOrder.setEnd(page.getEnd());

	    List<PurchaseOrderDto> purchaseList = purchaseService.porList(pOrder);

	    boolean isPurchaseDept = isPurchaseDept(session);

	    EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");

	    boolean isAdmin =
	            loginUser != null
	         && loginUser.getUser_access() == 2;

	    model.addAttribute("isPurchaseDept", isPurchaseDept);
	    model.addAttribute("isAdmin", isAdmin);

	    model.addAttribute("purchaseList", purchaseList);
	    model.addAttribute("page", page);
	    model.addAttribute("totalCount", totalCount);

	    return "purchase/list";
	}
    
    @GetMapping("/insertForm")
    public String insertForm(Model model,
                             HttpSession session) {
    	
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}
    	
    	if (isTodayTempClosed()) {
    	    return "redirect:/purchase/list";
    	}
    	
    	if (isTodayClosed()) {
            return "redirect:/purchase/list";
        }
    	
    	EmpDto loginUser =
    	        (EmpDto) session.getAttribute("loginUser");

    	boolean isPurchaseDept =
    	        loginUser != null
    	     && loginUser.getDept_code() == 5500;

    	if (!isPurchaseDept) {
    	    return "redirect:/purchase/list";
    	}
    	
    	String today = LocalDate.now()
    	        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

    	model.addAttribute("today", today);
        model.addAttribute("partList", purchaseService.partList());
        model.addAttribute("custList", purchaseService.custList());

        return "purchase/insertForm";
    }
    
    @PostMapping("/insert")
    public String insertPurchase(PurchaseOrderDto orderDto,
                                 PurchaseDetailDto detailDto,
                                 HttpSession session,
                                 Model model) {
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}
    	
    	if (isTodayTempClosed()) {
    	    model.addAttribute("msg", "가마감 처리된 날짜에는 신규 등록할 수 없습니다.");
    	    model.addAttribute("url", "/purchase/list");

    	    return "alert";
    	}

        if (isTodayClosed()) {
            return "redirect:/purchase/list";
        }

        if (!isPurchaseDept(session)) {
            return "redirect:/purchase/list";
        }

        purchaseService.insertPurchase(orderDto, detailDto);

        return "redirect:/purchase/list";
    }
    
    @GetMapping("/detail")
    public String detailPage(@RequestParam("purchaseId")int purchaseId,
                             Model model,
                             HttpSession session) {
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}

        PurchaseOrderDto purchase =
            purchaseService.purchaseDetail(purchaseId);
        
        String ymd = new java.text.SimpleDateFormat("yyyyMMdd")
                .format(purchase.getInboundDate());

        boolean isClosed =
                closingService.isBeforeTrueClosed(ymd);

        List<PurchaseDetailDto> detailList =
            purchaseService.purchaseItemList(purchaseId);

        EmpDto loginUser =
            (EmpDto) session.getAttribute("loginUser");

        boolean isAdmin =
                loginUser != null
             && loginUser.getUser_access() == 2;
        
        boolean isPurchaseDept =
                loginUser != null
             && loginUser.getDept_code() == 5500;

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isClosed", isClosed);
        model.addAttribute("purchase", purchase);
        model.addAttribute("detailList", detailList);
        model.addAttribute("isPurchaseDept", isPurchaseDept);

        return "purchase/detail";
    }
    
    @GetMapping("/delete")
    public String deletePurchase(@RequestParam("purchaseId") int purchaseId,
                                 HttpSession session, Model model) {
    	
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}

        if (isTodayClosed()) {
            return "redirect:/purchase/list";
        }

        EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");
        boolean isAdmin = loginUser != null && loginUser.getUser_access() == 2;

        if (!isAdmin || !isPurchaseDept(session)) {
            return "redirect:/purchase/detail?purchaseId=" + purchaseId;
        }

        PurchaseOrderDto purchase = purchaseService.purchaseDetail(purchaseId);

        int status = purchase.getStatus();
        if (status != 1 && status != 3) {
            return "redirect:/purchase/detail?purchaseId=" + purchaseId;
        }
        
        String ymd = new java.text.SimpleDateFormat("yyyyMMdd")
                .format(purchase.getInboundDate());

        if (closingService.isBeforeTrueClosed(ymd)) {
            model.addAttribute("msg", "수불마감 처리된 데이터는 삭제할 수 없습니다.");
            model.addAttribute("url", "/purchase/detail?purchaseId=" + purchaseId);

            return "alert";
        }

        purchaseService.deletePurchase(purchaseId);

        return "redirect:/purchase/list";
    }
    
    @GetMapping("/updateForm")
    public String updateForm(@RequestParam("purchaseId") int purchaseId,
                             Model model, HttpSession session) {
    	
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}
    	
    	if (isTodayClosed()) {
    	    return "redirect:/purchase/list";
    	}
    	
        PurchaseOrderDto purchase =
            purchaseService.purchaseDetail(purchaseId);
        
        if (purchase.getStatus() != 1) {
            return "redirect:/purchase/detail?purchaseId=" + purchaseId;
        }
        
        String ymd = new java.text.SimpleDateFormat("yyyyMMdd")
                .format(purchase.getInboundDate());

        if (closingService.isBeforeTrueClosed(ymd)) {
            model.addAttribute("msg", "수불마감 처리된 데이터는 수정할 수 없습니다.");
            model.addAttribute("url", "/purchase/detail?purchaseId=" + purchaseId);

            return "alert";
        }

        List<PurchaseDetailDto> detailList =
            purchaseService.purchaseItemList(purchaseId);

        model.addAttribute("purchase", purchase);
        EmpDto loginUser =
        	    (EmpDto) session.getAttribute("loginUser");

        boolean isAdmin =
                loginUser != null
             && loginUser.getUser_access() == 2;
        
        boolean isPurchaseDept =
                loginUser != null
             && loginUser.getDept_code() == 5500;
        
        if (!isPurchaseDept) {
            return "redirect:/purchase/detail?purchaseId="
                    + purchaseId;
        }
          
        String today = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        model.addAttribute("today", today);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("detailList", detailList);
        model.addAttribute("custList", purchaseService.custList());
        model.addAttribute("partList", purchaseService.partList());
        
        return "purchase/updateForm";
    }
    
    @PostMapping("/update")
    public String updatePurchase(PurchaseOrderDto orderDto,
                                 PurchaseDetailDto detailDto,
                                 HttpSession session, Model model) {
    	
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}
    	
    	String ymd = new java.text.SimpleDateFormat("yyyyMMdd")
    			.format(orderDto.getInboundDate());

    	if (closingService.isBeforeTrueClosed(ymd)) {
            model.addAttribute("msg", "수불마감 처리된 데이터는 수정할 수 없습니다.");
            model.addAttribute("url", "/purchase/detail?purchaseId=" + orderDto.getPurchaseId());

            return "alert";
        }

        if (isTodayClosed()) {
            return "redirect:/purchase/list";
        }

        if (!isPurchaseDept(session)) {
            return "redirect:/purchase/list";
        }

        purchaseService.updatePurchase(orderDto, detailDto);

        return "redirect:/purchase/detail?purchaseId="
                + orderDto.getPurchaseId();
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
    
    private boolean isPurchaseDept(HttpSession session) {
        EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");
        return loginUser != null && loginUser.getDept_code() == 5500;
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
