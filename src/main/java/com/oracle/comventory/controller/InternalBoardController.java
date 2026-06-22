package com.oracle.comventory.controller;

import com.oracle.comventory.dto.emp.EmpDto;
import com.oracle.comventory.dto.board.InternalBoardDto;
import com.oracle.comventory.service.board.BoardPaging;
import com.oracle.comventory.service.board.InternalBoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/internal")
public class InternalBoardController {

    private final InternalBoardService internalBoardService;

    // 목록
    @GetMapping("/list")
    public String list(InternalBoardDto dto, HttpSession session, Model model) {
        EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/login";
        if (loginUser.getUser_access() == 800 || loginUser.getUser_access() == 900) return "common/accessDenied";
        int total = internalBoardService.getTotalCount(dto);
        BoardPaging page = new BoardPaging(total, dto.getCurrentPage());
        dto.setStart(page.getStart());
        dto.setEnd(page.getEnd());
        model.addAttribute("list", internalBoardService.getList(dto));
        model.addAttribute("page", page);
        model.addAttribute("dto", dto);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("noticeList", internalBoardService.getNoticeList());
        return "internalBoard/list";
    }

    // 글 작성 폼
    @GetMapping("/write")
    public String writeForm(HttpSession session, Model model) {
        EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");
        if (loginUser == null) return "board/loginAlert";
        if (loginUser.getUser_access() == 800 || loginUser.getUser_access() == 900) return "common/accessDenied";
        model.addAttribute("loginUser", loginUser);
        return "internalBoard/write";
    }

    // 글 등록
    @PostMapping("/write")
    public String write(InternalBoardDto dto, HttpSession session) {
        EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/login";
        if (loginUser.getUser_access() == 800 || loginUser.getUser_access() == 900) return "common/accessDenied";
        dto.setRegEmpNo(loginUser.getEmp_no());
        internalBoardService.writeBoard(dto);
        return "redirect:list";
    }

    // 상세
    @GetMapping("/detail/{boardCode}")
    public String detail(@PathVariable("boardCode") int boardCode,
                         HttpSession session, Model model) {
        EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/login";
        if (loginUser.getUser_access() == 800 || loginUser.getUser_access() == 900) return "common/accessDenied";
        internalBoardService.updateReadCount(boardCode);
        model.addAttribute("boardList", internalBoardService.getDetail(boardCode));
        model.addAttribute("loginUser", loginUser);
        int writerEmpNo = internalBoardService.getWriterEmpNo(boardCode);
        model.addAttribute("writerEmpNo", writerEmpNo);
        model.addAttribute("originBoardCode", boardCode);
        return "internalBoard/detail";
    }

    // 답변 등록
    @PostMapping("/answer")
    public String answer(InternalBoardDto dto, HttpSession session) {
        EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/login";
        if (loginUser.getUser_access() == 800 || loginUser.getUser_access() == 900) return "common/accessDenied";
        dto.setRegEmpNo(loginUser.getEmp_no());
        internalBoardService.writeAnswer(dto);
        return "redirect:/internal/detail/" + dto.getBoardCode();
    }

    // 대댓글 등록
    @PostMapping("/reply")
    public String reply(InternalBoardDto dto, HttpSession session) {
        EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");
        if (loginUser == null) return "redirect:/login";
        if (loginUser.getUser_access() == 800 || loginUser.getUser_access() == 900) return "common/accessDenied";
        dto.setRegEmpNo(loginUser.getEmp_no());
        internalBoardService.writeReply(dto);
        return "redirect:/internal/detail/" + dto.getBoardCode();
    }

    // 임시 로그인1(테스트용)
//    @GetMapping("/testLogin")
//    public String testLogin(HttpSession session) {
//        EmpDto loginUser = new EmpDto();
//        loginUser.setEmp_no(1001);
//        loginUser.setEmp_name("대표님");
//        loginUser.setUser_access(2);
//        session.setAttribute("loginUser", loginUser);
//        return "redirect:/internal/list";
//    }
    
    // 임시 로그인2(테스트용)
//    @GetMapping("/testLogin")
//    public String testLogin(HttpSession session) {
//    	EmpDto loginUser = new EmpDto();
//    	loginUser.setEmp_no(9871);
//    	loginUser.setEmp_name("앱코 주미원사원");
//    	loginUser.setUser_access(800);
//    	session.setAttribute("loginUser", loginUser);
//    	return "redirect:/internal/list";
//    }
    
}