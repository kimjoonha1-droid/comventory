package com.oracle.comventory.dto.board;

import lombok.Data;
import java.util.Date;

@Data
public class BoardDto {
    private int    boardCode;       // 게시판코드 (board_code)
    private String boardTitle;      // 제목 (board_title)
    private String boardContents;   // 내용 (board_contents)
    private int    boardReadCount;  // 조회수 (board_read_count)
    private int    boardRef;        // 댓글그룹 (board_ref)
    private int    boardRefLvl;     // 댓글레벨 (board_ref_lvl)
    private int    boardRefStep;    // 댓글순서 (board_ref_step)
    private int    regEmpNo;        // 등록사원코드 (reg_emp_no)
    private Date   regDate;         // 등록일 (reg_date)
    private int    userAccess;		// 대댓글 이름뜨게하는거 구분
    
    // 사원 테이블 조인용 (작성자 이름 표시)
    private String empName;         // 사원명 (emp_name)
    private String answerer;		// 답변자
    
    // 페이지
    private String currentPage;  	// 현재 페이지
    private int    start;           // 시작 번호
    private int    end;             // 끝 번호
    
    //게시글 검색
    private String searchType;   	// 검색 유형 (title, writer)
    private String searchKeyword; 	// 검색어
    
    private int    answerCount;		// 답변 여부 (0: 미답변, 1이상: 답변완료)
    
    private String answerFilter;  	// 답변 필터 (done: 답변완료, wait: 미답변)
}