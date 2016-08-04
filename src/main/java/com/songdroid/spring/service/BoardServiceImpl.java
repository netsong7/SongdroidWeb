package com.songdroid.spring.service;

import java.lang.annotation.Annotation;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.songdroid.spring.controller.BoardController;
import com.songdroid.spring.domain.board.BasicBoardDto;
import com.songdroid.spring.domain.board.CommentBoardDto;
import com.songdroid.spring.domain.board.MasterBoardDto;
import com.songdroid.spring.repository.BoardDao;

@Service
public class BoardServiceImpl implements BoardService {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	BoardDao dao;
	
	public void createBoard(String boardName, String boardTitle, String[] chkOption) throws Exception{
		dao.createBoard(boardName, boardTitle, chkOption);
	}
	
	// 화면에 테이블 목록 뿌려주기 위한 기능
	public List getTables(){
		return dao.getTables();
	}
	
	// 마스터 테이블 정보 알아내기
	public MasterBoardDto getMasterTable(int board_num){
		return dao.getMasterTable(board_num);
	}
	
	public List getBoardList(int board_num){
		return dao.getBoardList(board_num);
	}
	
	// 글 저장 페이지 기능
	public void writeBoard(BasicBoardDto basicDto){
		/*
		logger.info("contents : " + basicDto.getWr_contents());
		logger.info("email : " + basicDto.getWr_email());
		logger.info("home : " + basicDto.getWr_home());
		logger.info("ip : " + basicDto.getWr_ip());
		logger.info("pass : " + basicDto.getWr_pass());
		logger.info("title : " + basicDto.getWr_title());
		logger.info("writer : " + basicDto.getWr_writer());
		logger.info("board_num : " + new String().valueOf(basicDto.getBoard_num()));
		*/
		
		Integer result = dao.writeBoard(basicDto);
		//logger.info("글 저장 여부 : " + result.toString());
	}
	
	// 글 읽기 페이지 기능
	public BasicBoardDto getBoard(int wr_num){
		return dao.getBoard(wr_num);
	}
	
	// 테이블 이름 중복검사
	public boolean getDuplicatedTableName(String tableName){
		return dao.getDuplicatedTableName(tableName);
	}
	
	// 테이블 삭제
	public void removeBoard(int board_num){
		dao.removeBoard(board_num);
	}
	
	@Override
	public void commentBoard(CommentBoardDto commentBoardDto) {
		
	}
	
	public List getCommentList(int wr_num){
		return null;
	}

	@Override
	public String getTableDispName(int board_num) {
		return dao.getTableDispName(board_num);
	}
}
