package com.springbook.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String boardList(Model model) {
		model.addAttribute("data", service.selBoardList());		
		return "board/list";
	}	
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String boardWrite() {
		return "board/write";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String boardWrite(BoardVO param) {
		System.out.println("title : " + param.getTitle());
		System.out.println("ctnt : " + param.getCtnt());		
		int result = service.insBoard(param);
		return "redirect:/board/list";
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String boardDetail(BoardVO param, Model model) {
		System.out.println("i_board : " + param.getI_board());
		model.addAttribute("data", service.selBoard(param));
		return "board/detail";
	}	
	
	@RequestMapping(value = "/upd", method = RequestMethod.GET)
	public String boardUpd(BoardVO param, Model model) {
		model.addAttribute("data", service.selBoard(param));
		return "board/write";
	}
	
	@RequestMapping(value = "/upd", method = RequestMethod.POST)
	public String boardUpd(BoardVO param) {
		int result = service.updBoard(param);
		return "redirect:/board/detail?i_board=" + param.getI_board();
	}	
	
	@RequestMapping(value="/del", method=RequestMethod.GET)
	public String boardDel(@RequestParam int i_board) {
		System.out.println("del - i_board : " + i_board);		
		int result = service.delBoard(i_board);
		return "redirect:/board/list";
	}
	
	
}







