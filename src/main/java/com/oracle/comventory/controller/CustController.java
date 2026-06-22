package com.oracle.comventory.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oracle.comventory.domain.cust.Cust;
import com.oracle.comventory.dto.custTable.CustTableDto;
import com.oracle.comventory.dto.emp.EmpDto;
import com.oracle.comventory.service.statusType.StatusTypeService;

import jakarta.servlet.http.HttpSession;

import com.oracle.comventory.service.cust.CustService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/cust")
public class CustController {

    private final CustService custService;
    private final StatusTypeService statusTypeService;

    @GetMapping("/custMain")
    public String main() {
        return "cust/custMain";
    }

    @GetMapping("/custList")
    public String list(CustTableDto custDto,
                       Model model,
                       HttpSession session) {

        log.info("CustController custList start...");

        boolean canManageCust =
                canManageCust(session);

        String currentPage = custDto.getCurrentPage();

        if (currentPage == null) {
            currentPage = "1";
        }

        int currentPageInt = Integer.parseInt(currentPage);

        int rowPage = 10;

        int start = (currentPageInt - 1) * rowPage + 1;
        int end = start + rowPage - 1;

        custDto.setStart(start);
        custDto.setEnd(end);

        // 검색조건 포함 totalCount
        Long total = custService.totalCount(custDto);

        List<CustTableDto> custList =
                custService.findPagingCust(custDto);

        int pageCount =
                (int)Math.ceil((double)total / rowPage);

        int pageBlock = 10;

        int startPage =
                ((currentPageInt - 1) / pageBlock)
                * pageBlock + 1;

        int endPage = startPage + pageBlock - 1;

        if (endPage > pageCount) {
            endPage = pageCount;
        }

        model.addAttribute("custList", custList);

        model.addAttribute("currentPage", currentPageInt);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("pageCount", pageCount);

        // 검색조건 유지용
        model.addAttribute("keyword", custDto.getKeyword());
        model.addAttribute("searchType", custDto.getSearchType());
        model.addAttribute("custType", custDto.getCustType());

        // 권한 체크
        model.addAttribute("canManageCust", canManageCust);

        return "cust/custList";
    }

    @GetMapping("/custSaveForm")
    public String saveForm(Model model,
                           HttpSession session,
                           RedirectAttributes rttr) {

        if (!canManageCust(session)) {
            rttr.addFlashAttribute("msg", "거래처 관리 권한이 없습니다.");
            return "redirect:/cust/custList";
        }

        log.info("CustController custSaveForm start...");

        Long nextCustCode = custService.findNextCustCode();
        model.addAttribute("nextCustCode", nextCustCode);

        model.addAttribute("custTypeList",
                statusTypeService.findByBcode(210));

        model.addAttribute("custStatusList",
                statusTypeService.findByBcode(220));

        return "cust/custSaveForm";
    }

    @PostMapping("/save")
    public String save(CustTableDto custDto,
                       HttpSession session,
                       RedirectAttributes rttr) {

        if (!canManageCust(session)) {
            rttr.addFlashAttribute("msg", "거래처 등록 권한이 없습니다.");
            return "redirect:/cust/custList";
        }

        log.info("CustController save start custDto -> {}", custDto);

        EmpDto loginUser =
                (EmpDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            rttr.addFlashAttribute("msg", "로그인 후 이용해주세요.");
            return "redirect:/login";
        }

        Cust cust = new Cust(
                custDto.getCustCode(),
                custDto.getCustName(),
                custDto.getCustType(),
                custDto.getBusinessNo(),
                custDto.getCeoName(),
                custDto.getCustTel(),
                custDto.getCustEmail(),
                custDto.getCustAddress(),
                Long.valueOf(loginUser.getEmp_no())
        );

        custService.save(cust);

        rttr.addFlashAttribute("msg", "거래처가 등록되었습니다.");

        return "redirect:/cust/custList";
    }

    @GetMapping("/custUpdateForm")
    public String updateForm(@RequestParam("cust_code") Long cust_code,
                             Model model,
                             HttpSession session,
                             RedirectAttributes rttr) {

        if (!canManageCust(session)) {
            rttr.addFlashAttribute("msg", "거래처 수정 권한이 없습니다.");
            return "redirect:/cust/custList";
        }

        log.info("CustController custUpdateForm start cust_code -> {}",
                cust_code);

        model.addAttribute("cust",
                custService.findById(cust_code));

        model.addAttribute("custTypeList",
                statusTypeService.findByBcode(210));

        model.addAttribute("custStatusList",
                statusTypeService.findByBcode(220));

        return "cust/custUpdateForm";
    }

    @PostMapping("/update")
    public String update(CustTableDto custDto,
                         HttpSession session,
                         RedirectAttributes rttr) {

        if (!canManageCust(session)) {
            rttr.addFlashAttribute("msg", "거래처 수정 권한이 없습니다.");
            return "redirect:/cust/custList";
        }

        log.info("CustController update start custDto -> {}", custDto);

        Cust originCust =
                custService.findById(custDto.getCustCode());

        Cust cust = new Cust(
                custDto.getCustCode(),
                custDto.getCustName(),
                custDto.getCustType(),
                custDto.getBusinessNo(),
                custDto.getCeoName(),
                custDto.getCustTel(),
                custDto.getCustEmail(),
                custDto.getCustAddress(),
                custDto.getCustDelStatus(),
                originCust.getReg_emp_no()
        );

        custService.update(cust);

        rttr.addFlashAttribute("msg", "거래처 정보가 수정되었습니다.");

        return "redirect:/cust/custList";
    }

    @GetMapping("/stop")	 // 거래정지 2
    public String stop(@RequestParam("cust_code") Long cust_code,
                       HttpSession session,
                       RedirectAttributes rttr) {

        if (!canManageCust(session)) {
            rttr.addFlashAttribute("msg", "거래처 상태변경 권한이 없습니다.");
            return "redirect:/cust/custList";
        }

        log.info("CustController stop start cust_code -> {}", cust_code);

        custService.changeStatus(cust_code, 2);

        return "redirect:/cust/custList";
    }

    @GetMapping("/inactive")	// 사용중지 9
    public String inactive(@RequestParam("cust_code") Long cust_code,
                           HttpSession session,
                           RedirectAttributes rttr) {

        if (!canManageCust(session)) {
            rttr.addFlashAttribute("msg", "거래처 상태변경 권한이 없습니다.");
            return "redirect:/cust/custList";
        }

        log.info("CustController inactive start cust_code -> {}", cust_code);

        custService.changeStatus(cust_code, 9);

        return "redirect:/cust/custList";
    }

    @GetMapping("/active")	 // 사용중 1
    public String active(@RequestParam("cust_code") Long cust_code,
                         HttpSession session,
                         RedirectAttributes rttr) {

        if (!canManageCust(session)) {
            rttr.addFlashAttribute("msg", "거래처 상태변경 권한이 없습니다.");
            return "redirect:/cust/custList";
        }

        log.info("CustController active start cust_code -> {}", cust_code);

        custService.changeStatus(cust_code, 1);

        return "redirect:/cust/custList";
    }

    private boolean canManageCust(HttpSession session) {

        EmpDto loginUser =
                (EmpDto) session.getAttribute("loginUser");

        return loginUser != null
            && (loginUser.getUser_access() == 1
                || loginUser.getDept_code() == 5000);
    }
}