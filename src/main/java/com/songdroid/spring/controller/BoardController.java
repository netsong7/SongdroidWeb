package com.songdroid.spring.controller;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.songdroid.spring.service.BoardService;

@Controller
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService service;
	
	@RequestMapping("/board/create")
	public void createGet(HttpServletRequest req){
		req.setAttribute("tableList", service.getTables());
	}
	
	@RequestMapping(value="/board/create", method=RequestMethod.POST)
	public void createPost(String board_disp_name, String board_tab_name, String[] chkOption , Writer writer) throws Exception{
		// 중복된 테이블명이 있다면
		if(service.getDuplicatedTableName(board_tab_name)){
			// AJAX로 값을 돌려주기 위해 true.jsp를 만들어 이동하면 true문자열을 리턴하게 하였다.
			writer.write("true");
		}
		else{
			service.createBoard(board_disp_name, board_tab_name, chkOption);
		}
	}
	
	@RequestMapping("/board/drop")
	public String dropBoard(int board_num){
		service.removeBoard(board_num);
		return "redirect:/board/create";
	}
}
