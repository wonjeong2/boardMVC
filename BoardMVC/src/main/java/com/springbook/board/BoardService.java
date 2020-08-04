package com.springbook.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbook.board.common.Const;

@Service
public class BoardService {	
	
	@Autowired
	private BoardMapper mapper;
	
	public int insBoard(BoardVO param) {
		return mapper.insBoard(param);
	}
	
	public List<BoardVO> selBoardList(int page) {
		
		int sIdx = (page - 1) * Const.ROW_COUNT; 
		
		BoardVO param = new BoardVO();
		param.setsIdx(sIdx);
		param.setCount(Const.ROW_COUNT);
		
		return mapper.selBoardList(param);
		
//		페이징하는 쿼리문 : SELECT * FROM t_board ORDER BY i_board desc LIMIT 0, 30;

//		페이지 > LIMIT의 앞에 값
//		1 > 0
//		2 > 30
//		3 > 60
//		4 > 90
	}
	
	public BoardVO selBoard(BoardVO param) {
		return mapper.selBoard(param);
	}
	
	public int updBoard(BoardVO param) {
		return mapper.updBoard(param);
	}
	
	public int delBoard(int param) {
		return mapper.delBoard(param);
	}
}
