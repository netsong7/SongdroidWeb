package com.songdroid.spring.service;

import java.util.List;

import com.songdroid.spring.domain.board.BasicBoardDto;
import com.songdroid.spring.domain.board.CommentBoardDto;
import com.songdroid.spring.domain.board.MasterBoardDto;

public interface BoardService {
	public void createBoard(String boardName, String boardTitle, String[] chkOption) throws Exception;  
	public List getTables();
	public List getBoardList(int board_num);
	public MasterBoardDto getMasterTable(int board_num);
	public void writeBoard(BasicBoardDto basicDto);
	public BasicBoardDto getBoard(int wr_num);
	public boolean getDuplicatedTableName(String tableName);
	public void removeBoard(int board_num);
	public void commentBoard(CommentBoardDto commentBoardDto);
	public List getCommentList(int wr_num);
	public String getTableDispName(int board_num);
}
