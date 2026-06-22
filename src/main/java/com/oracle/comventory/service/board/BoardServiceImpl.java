package com.oracle.comventory.service.board;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.oracle.comventory.dto.board.BoardDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final SqlSession session;

    @Override
    public List<BoardDto> getList(BoardDto dto) {
        return session.selectList("board.getList", dto);
    }

    @Override
    public List<BoardDto> getDetail(int boardCode) {
        return session.selectList("board.getDetail", boardCode);  // selectOne → selectList
    }

    @Override
    public void writeBoard(BoardDto dto) {
        session.insert("board.writeBoard", dto);
    }

    @Override
    public void writeAnswer(BoardDto dto) {
        session.insert("board.writeAnswer", dto);
    }

	@Override
	public void updateReadCount(int boardCode) {
	    session.update("board.updateReadCount", boardCode);
	}

	@Override
	public void writeReply(BoardDto dto) {
		session.insert("board.writeReply", dto);
	}

	@Override
	public int getWriterEmpNo(int boardRef) {
	    return session.selectOne("board.getWriterEmpNo", boardRef);
	}

	@Override
	public int getTotalCount(BoardDto dto) {
	    return session.selectOne("board.getTotalCount", dto);
	}
	
}