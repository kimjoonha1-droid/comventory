package com.oracle.comventory.service.board;

import java.util.List;

import com.oracle.comventory.dto.board.BoardDto;

public interface BoardService {
    List<BoardDto> getList(BoardDto dto);
    List<BoardDto> getDetail(int boardCode);
    void 		 writeBoard(BoardDto dto);
    void 		 writeAnswer(BoardDto dto);
    void 		 updateReadCount(int boardCode);
    void 		 writeReply(BoardDto dto);
    int 		 getWriterEmpNo(int boardRef);
    int 		 getTotalCount(BoardDto dto);
}