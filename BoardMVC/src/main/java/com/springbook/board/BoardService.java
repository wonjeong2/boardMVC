package com.springbook.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {	
	
	@Autowired
	private BoardMapper mapper;
	
	public int insBoard(BoardVO param) {
		return mapper.insBoard(param);
	}
	
	public List<BoardVO> selBoardList() {
		return mapper.selBoardList();
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
