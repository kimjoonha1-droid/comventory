package com.oracle.comventory.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oracle.comventory.dto.bom.BomDto;
import com.oracle.comventory.dto.emp.EmpDto;
import com.oracle.comventory.service.Paging;
import com.oracle.comventory.service.bom.BomService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/bom")
public class BomController {

    private final BomService bomService;
    //private final EmpService empService

    @GetMapping("/list")
    public String listPage(BomDto bom, Model model, HttpSession session) {
    	
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}
    	
        int totalCount = bomService.totalBom();

        Paging page = new Paging(totalCount, bom.getCurrentPage());
        bom.setStart(page.getStart());
        bom.setEnd(page.getEnd());

        List<BomDto> bomList = bomService.bomList(bom);
        
        EmpDto loginUser =
                (EmpDto) session.getAttribute("loginUser");

        boolean isAdmin =
                loginUser != null
             && loginUser.getUser_access() == 2;

        boolean isProductionDept =
                loginUser != null
             && loginUser.getDept_code() == 6000;

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isProductionDept", isProductionDept);

        model.addAttribute("totalCount", totalCount);
        model.addAttribute("bomList", bomList);
        model.addAttribute("page", page);

        return "bom/list";
    }

    @GetMapping("/detail")
    public String detailPage(@RequestParam("productCode") int productCode,
                             Model model, HttpSession session) {
    	
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}
    	
        List<BomDto> bomDetailList = bomService.bomDetailList(productCode);

        EmpDto loginUser =
                (EmpDto) session.getAttribute("loginUser");

        boolean isAdmin =
                loginUser != null
             && loginUser.getUser_access() == 2;

        boolean isProductionDept =
                loginUser != null
             && loginUser.getDept_code() == 6000;

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isProductionDept", isProductionDept);
        model.addAttribute("bomDetailList", bomDetailList);

        return "bom/detail";
    }

    @GetMapping("/insertForm")
    public String insertForm(
            @RequestParam(value = "productCode", required = false) Integer productCode,
            Model model,
            HttpSession session) {
    	
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}

        if (!isProductionDept(session)) {
            return "redirect:/bom/list";
        }

        if (productCode != null) {
            model.addAttribute("finishedProductList",
                    bomService.finishedProductListForAdd(productCode));
        } else {
            model.addAttribute("finishedProductList",
                    bomService.finishedProductList());
        }

        model.addAttribute("partList", bomService.partList());
        model.addAttribute("selectedProductCode", productCode);

        return "bom/insertForm";
    }

    @PostMapping("/insert")
    public String insertBom(BomDto bom, HttpSession session, Model model) {
    	
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}
    	
    	if (!isProductionDept(session)) {
    	    return "redirect:/bom/list";
    	}

    	bomService.insertBom(bom);

        return "redirect:/bom/list";
    }
    
//    @PostMapping("/insert")
//    public String insertBom(BomDto bom, Authentication authentication) {
//
//        String loginId = authentication.getName();
//
//        // loginId로 사원번호 조회
//        int empNo = employeeService.findEmpNoByLoginId(loginId);
//
//        bom.setRegEmpNo(empNo);
//
//        bomService.insertBom(bom);
//
//        return "redirect:/bom/list";
//    }

    @GetMapping("/delete")
    public String deleteBom(@RequestParam("productCode") int productCode,
                            @RequestParam("productWon") int productWon
                            , HttpSession session, Model model) {
    	
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}
        
        if (!isProductionDept(session) || !isAdmin(session)) {
        	return "redirect:/bom/detail?productCode=" + productCode;
        }
        
		bomService.deleteBom(productCode, productWon);
		
        return "redirect:/bom/detail?productCode=" + productCode;
    }

    @GetMapping("/updateForm")
    public String updateForm(@RequestParam("productCode") int productCode,
                             @RequestParam("productWon") int productWon,
                             Model model,
                             HttpSession session) {
    	
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}

        if (!isProductionDept(session) || !isAdmin(session)) {
            return "redirect:/bom/detail?productCode=" + productCode;
        }

        BomDto bom = bomService.bomOne(productCode, productWon);
        model.addAttribute("bom", bom);

        return "bom/updateForm";
    }

    @PostMapping("/update")
    public String updateBom(BomDto bom, HttpSession session, Model model) {
    	
    	if (isExternalEmp(session)) {
    	    model.addAttribute("msg", "권한이 없습니다.");
    	    model.addAttribute("url", "/");

    	    return "alert";
    	}

        if (!isProductionDept(session) || !isAdmin(session)) {
        	return "redirect:/bom/list";
        }
        
		bomService.updateBom(bom);
		
        return "redirect:/bom/detail?productCode=" + bom.getProductCode();
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
}