package com.songdroid.spring.repository;

import java.util.List;

import com.songdroid.spring.domain.board.BasicBoardDto;
import com.songdroid.spring.domain.board.CommentBoardDto;
import com.songdroid.spring.domain.board.MasterBoardDto;

public interface BoardDao {
	public void createBoard(String boardName, String boardTitle, String[] chkOption);  
	public List getTables();
	public List getBoardList(int board_num);
	public MasterBoardDto getMasterTable(int board_num);
	public int writeBoard(BasicBoardDto basicDto);
	public BasicBoardDto getBoard(int wr_num);
	public boolean getDuplicatedTableName(String tableName);
	public int removeBoard(int board_num);
	public int setCommentBoard(CommentBoardDto commentBoardDto);
	public List getCommentList(int wr_num);
	public String getTableDispName(int board_num);
}
