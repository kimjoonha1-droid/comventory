package com.oracle.comventory.service.board;

import com.oracle.comventory.dto.board.InternalBoardDto;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InternalBoardServiceImpl implements InternalBoardService {

    private final SqlSession session;

    @Override
    public List<InternalBoardDto> getList(InternalBoardDto dto) {
        return session.selectList("internalBoard.getList", dto);
    }

    @Override
    public List<InternalBoardDto> getDetail(int boardCode) {
        return session.selectList("internalBoard.getDetail", boardCode);
    }

    @Override
    public void writeBoard(InternalBoardDto dto) {
        session.insert("internalBoard.writeBoard", dto);
    }

    @Override
    public void writeAnswer(InternalBoardDto dto) {
        session.insert("internalBoard.writeAnswer", dto);
    }

    @Override
    public void writeReply(InternalBoardDto dto) {
        session.insert("internalBoard.writeReply", dto);
    }

    @Override
    public void updateReadCount(int boardCode) {
        session.update("internalBoard.updateReadCount", boardCode);
    }

    @Override
    public int getTotalCount(InternalBoardDto dto) {
        return session.selectOne("internalBoard.getTotalCount", dto);
    }

    @Override
    public int getWriterEmpNo(int boardCode) {
        return session.selectOne("internalBoard.getWriterEmpNo", boardCode);
    }

    @Override
    public List<InternalBoardDto> getNoticeList() {
        return session.selectList("internalBoard.getNoticeList");
    }
}