package com.oracle.comventory.service.board;

import com.oracle.comventory.dto.board.InternalBoardDto;
import java.util.List;

public interface InternalBoardService {
    List<InternalBoardDto> getList(InternalBoardDto dto);
    List<InternalBoardDto> getDetail(int boardCode);
    void 				   writeBoard(InternalBoardDto dto);
    void 				   writeAnswer(InternalBoardDto dto);
    void 				   writeReply(InternalBoardDto dto);
    void 				   updateReadCount(int boardCode);
    int 				   getTotalCount(InternalBoardDto dto);
    int 				   getWriterEmpNo(int boardCode);
    List<InternalBoardDto> getNoticeList();
}