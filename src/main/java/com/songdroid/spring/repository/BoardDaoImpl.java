package com.songdroid.spring.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.songdroid.spring.controller.BoardController;
import com.songdroid.spring.domain.board.BasicBoardDto;
import com.songdroid.spring.domain.board.CommentBoardDto;
import com.songdroid.spring.domain.board.MasterBoardDto;
import com.songdroid.spring.util.WebConstants;

@Repository
public class BoardDaoImpl implements BoardDao {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	private SqlSession sqlSession;
	
	@Override
	public void createBoard(String boardName, String boardTitle, String[] chkOption) {
		String reply="n", comment="n", upload="n";
		try{
			if(chkOption != null){
				for(int i=0; i<chkOption.length; i++){
					if("reply".equals(chkOption[i])){
						reply="y";
					}
					else if("comment".equals(chkOption[i])){
						comment="y";
					}
					else if("upload".equals(chkOption[i])){
						upload="y";
					}
				}
			}
		}
		catch(NullPointerException err){ }
		
		MasterBoardDto masterBoardDto = new MasterBoardDto();
		masterBoardDto.setBoard_comment(comment);
		masterBoardDto.setBoard_disp_name(boardName);
		masterBoardDto.setBoard_reply(reply);
		masterBoardDto.setBoard_tab_name(boardTitle);
		masterBoardDto.setBoard_upload(upload);
		
		sqlSession.insert(WebConstants.BOARD_NAMESPACE + ".create", masterBoardDto);
	}

	@Override
	public List getTables() {
		return sqlSession.selectList(WebConstants.BOARD_NAMESPACE + ".getTables");
	}

	@Override
	public List getBoardList(int board_num) {
		return sqlSession.selectList(WebConstants.BOARD_NAMESPACE +".getBoardList", board_num);
	}

	@Override
	public MasterBoardDto getMasterTable(int board_num) {
		return sqlSession.selectOne(WebConstants.BOARD_NAMESPACE +".getMasterTable", board_num);
	}

	@Override
	public int writeBoard(BasicBoardDto basicDto) {
		return sqlSession.insert(WebConstants.BOARD_NAMESPACE +".writeBoard", basicDto);
	}

	@Override
	public BasicBoardDto getBoard(int wr_num, String board_upload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getDuplicatedTableName(String board_tab_name) {
		logger.info("dup before: " + board_tab_name);
		String tname = sqlSession.selectOne(WebConstants.BOARD_NAMESPACE +".getDuplicatedTableName", board_tab_name);
		logger.info("dup after: " + tname);
		try{
			if(tname.equalsIgnoreCase(board_tab_name))
				return true;
			else
				return false;
		}
		catch(NullPointerException err){
			return false;
		}
	}
	
	
	@Override
	public int removeBoard(int board_num) {
		// 먼저 BoardBasic테이블에 글을 작성했을 경우 검색하여 삭제할 수 있도록 한다.
		List list = sqlSession.selectList(WebConstants.BOARD_NAMESPACE +".getBoardBasic", board_num);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("board_num", board_num);
		
		return sqlSession.delete(WebConstants.BOARD_NAMESPACE +".allDropBoard", map);
	}

	@Override
	public int setCommentBoard(CommentBoardDto commentBoardDto) {
		return sqlSession.insert(WebConstants.BOARD_NAMESPACE + ".setCommentBoard", commentBoardDto);
	}

	@Override
	public List getCommentList(int wr_num) {
		return sqlSession.selectList(WebConstants.BOARD_NAMESPACE + ".getCommentList", wr_num);
	}

}
