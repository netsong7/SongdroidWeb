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
		// 기본키값을 알아낸다.
		int primaryKey = sqlSession.insert(WebConstants.BOARD_NAMESPACE +".writeBoard", basicDto);
		
		// 알아낸 기본키값과 함께 업로드 테이블(tblBoardUpload)에 업로드한 파일 정보를 저장시킨다.
		if(basicDto.getWr_file() != null || !basicDto.getWr_file().isEmpty()){
			Map map = new HashMap();
			map.put("wr_num", primaryKey);
			map.put("wr_file", basicDto.getWr_file());
			
			sqlSession.insert(WebConstants.BOARD_NAMESPACE + ".uploadBoard", map);
		}
		
		return primaryKey;
	}

	// 글 읽기
	@Override
	public BasicBoardDto getBoard(int wr_num) {
		BasicBoardDto dto = sqlSession.selectOne(WebConstants.BOARD_NAMESPACE + ".getRead", wr_num);
		logger.info("BoardDaoImpl-getBoard : " + dto.getWr_num());
		return dto;
	}

	@Override
	public boolean getDuplicatedTableName(String board_tab_name) {
		String tExistName = sqlSession.selectOne(WebConstants.BOARD_NAMESPACE +".getExistDuplicatedTableName", board_tab_name);
		String tMasterName = sqlSession.selectOne(WebConstants.BOARD_NAMESPACE + ".getMasterDuplicatedTableName", board_tab_name);
		
		// 동일한 테이블명이 없을 경우 null이 리턴되므로 catch문에서 false가 리턴된다.
		// 때문에 true를 리턴하기 위해서는 NullPointerException으로 떨어지는 것을 막기위해 임의의 문자열("null")을 넣어주었다.
		if(tExistName == null){ tExistName = "null"; }
		if(tMasterName == null){ tMasterName = "null"; }
		
		try{
			if(tExistName.equalsIgnoreCase(board_tab_name) || tMasterName.equalsIgnoreCase(board_tab_name))
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

	@Override
	public String getTableDispName(int board_num) {
		return sqlSession.selectOne(WebConstants.BOARD_NAMESPACE + ".getTableDispName", board_num);
	}
}
