package com.oracle.comventory.dto.board;

import lombok.Data;
import java.util.Date;

@Data
public class InternalBoardDto {
    private int    boardCode;
    private String boardTitle;
    private String boardContents;
    private int    boardReadCount;
    private int    boardRef;
    private int    boardRefLvl;
    private int    boardRefStep;
    private int    regEmpNo;
    private Date   regDate;
    private String empName;
    private int    userAccess;
    private int    answerCount;
    private String category;      	// 카테고리 (일반, 공지사항)
    private String currentPage;
    private int    start;
    private int    end;
    private String searchType;
    private String searchKeyword;
    private String answerFilter;
    private int replyCount;  		// 답변 + 대댓글 총 개수
}