package com.oracle.comventory.controller;

import com.oracle.comventory.dto.emp.EmpDto;
import com.oracle.comventory.dto.board.BoardDto;
import com.oracle.comventory.service.board.BoardService;
import com.oracle.comventory.service.board.BoardPaging;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    // 문의 목록
    @GetMapping("/list")
    public String list(BoardDto dto, Model model) {
        int total = boardService.getTotalCount(dto);  // dto 추가
        BoardPaging page = new BoardPaging(total, dto.getCurrentPage());
        dto.setStart(page.getStart());
        dto.setEnd(page.getEnd());
        model.addAttribute("list", boardService.getList(dto));
        model.addAttribute("page", page);
        model.addAttribute("dto", dto);  // 검색 조건 JSP로 전달
        return "board/list";
    }

    // 문의 작성 폼
    @GetMapping("/write")
    public String writeForm(HttpSession session) {
        EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        return "board/write";
    }

    @PostMapping("/write")
    public String write(BoardDto dto, HttpSession session) {
        EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        boardService.writeBoard(dto);
        return "redirect:list";
    }

    // 문의 상세
    @GetMapping("/detail/{boardCode}")
    public String detail(@PathVariable("boardCode") int boardCode,
                         HttpSession session, Model model) {
        boardService.updateReadCount(boardCode);
        List<BoardDto> boardList = boardService.getDetail(boardCode);
        model.addAttribute("boardList", boardList);
        model.addAttribute("loginUser", session.getAttribute("loginUser"));
        model.addAttribute("originBoardCode", boardCode);
        
        // 원글 작성자 번호 추가
        int writerEmpNo = boardService.getWriterEmpNo(boardCode);
        model.addAttribute("writerEmpNo", writerEmpNo);
        
        return "board/detail";
    }

    // 답변 등록 - 회사직원만
    @PostMapping("/answer")
    public String answer(BoardDto dto, HttpSession session) {
        EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");
        if (loginUser == null || loginUser.getUser_access() == 800 || loginUser.getUser_access() == 900) {
            return "redirect:/login";
        }
        dto.setRegEmpNo(loginUser.getEmp_no());
        boardService.writeAnswer(dto);
        return "redirect:/board/detail/" + dto.getBoardCode();
    }
    
    // 대댓글 등록
    @PostMapping("/reply")
    public String reply(BoardDto dto, HttpSession session) {
        EmpDto loginUser = (EmpDto) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login";
        }
        // 원글 작성자 번호 조회
        int writerEmpNo = boardService.getWriterEmpNo(dto.getBoardCode());
        // 거래처 직원(800, 900번)이면서 작성자도 아닌 경우 막기
        if (loginUser.getUser_access() == 800 || loginUser.getUser_access() == 900) {
            if (loginUser.getEmp_no() != writerEmpNo) {
                return "redirect:/login";
            }
        }
        dto.setRegEmpNo(loginUser.getEmp_no());
        boardService.writeReply(dto);
        return "redirect:/board/detail/" + dto.getBoardCode();
    }
    
//  임시 로그인1 (테스트용)
//    @GetMapping("/testLogin")
//    public String testLogin(HttpSession session) {
//        EmpDto loginUser = new EmpDto();
//        loginUser.setEmp_no(9701);
//        loginUser.setEmp_name("앱코 주미원사원");
//        loginUser.setUser_access(7);
//        session.setAttribute("loginUser", loginUser);
//        return "redirect:/board/list";
//    }
    
//  임시 로그인2 (테스트용)
//    @GetMapping("/testLogin")
//    public String testLogin(HttpSession session) {
//        EmpDto loginUser = new EmpDto();
//        loginUser.setEmp_no(1001);
//        loginUser.setEmp_name("대표님");
//        loginUser.setUser_access(2);
//        session.setAttribute("loginUser", loginUser);
//        return "redirect:/board/list";
//    }
}