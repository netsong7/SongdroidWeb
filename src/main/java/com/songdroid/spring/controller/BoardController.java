package com.songdroid.spring.controller;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.songdroid.spring.domain.board.BasicBoardDto;
import com.songdroid.spring.domain.board.MasterBoardDto;
import com.songdroid.spring.service.BoardService;

/**
 * 
 * @author Netsong7
 *
 */
@Controller
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService service;
	
	// 멀티 게시판 메인 페이지(create.jsp) 이동
	@RequestMapping("/board/create")
	public void createGet(HttpServletRequest req){
		req.setAttribute("tableList", service.getTables());
	}
	
	// 테이블 생성
	@RequestMapping(value="/board/create", method=RequestMethod.POST)
	public void createPost(String board_disp_name, String board_tab_name, String[] chkOption , Writer writer) throws Exception{
		logger.info("createPost : " + board_tab_name);
		// 중복된 테이블명이 있다면
		if(service.getDuplicatedTableName(board_tab_name)){
			// AJAX로 값을 돌려주기 위해 true.jsp를 만들어 이동하면 true문자열을 리턴하게 하였다.
			writer.write("true");
		}
		else{
			service.createBoard(board_disp_name, board_tab_name, chkOption);
		}
	}
	
	// 테이블 삭제
	@RequestMapping("/board/drop")
	public String dropBoard(int board_num){
		service.removeBoard(board_num);
		return "redirect:/board/create";
	}
	
	// 리스트 페이지 보기
	@RequestMapping("/board/list")
	public void list(@RequestParam("board_num") int board_num, HttpServletRequest req){
		// 해당 게시판에 글을 썼을 경우 BasicBoardDto객체를 가져온다.
		req.setAttribute("boardList", service.getBoardList(board_num));
		req.setAttribute("board_num", board_num);
		req.setAttribute("board_disp_name", service.getTableDispName(board_num));
	}
	
	// 글쓰기 페이지로 이동(servlet-context.xml에서 mvc:view-controller로 처리하려고 했으나)
	// 그런데 writePost()가 같은 이름의 요청이다보니 xml에 있는 설정보다 아래 자바코드에 의한 설정이 우선하는 것 같음. 그래서 다시 원복함.
	// 파라미터까지 전달해야 했기에 어차피 xml설정을 사용할 수 없었다.
	@RequestMapping("/board/write")
	public void writeGet(int board_num, HttpServletRequest req){
		//마스터 테이블에서 업로드 여부, 댓글여부 등의 정보를 가져와야 하기 때문에 필요한 코드이다. 
		MasterBoardDto dto = service.getMasterTable(board_num);
		req.setAttribute("master", dto);
	}

	
	@RequestMapping(value="/board/write", method=RequestMethod.POST)
	public String writePost(@ModelAttribute BasicBoardDto basicBoardDto, @RequestParam String cmd, HttpServletRequest req) throws IOException{		
		if("WRITE_PROC_BOARD".equals(cmd)){
			service.writeBoard(basicBoardDto);			
		}
		else if("WRITE_PROC_UPLOAD_BOARD".equals(cmd)){ 
			
			String saveDir = req.getServletContext().getRealPath("/resources/upload");
			MultipartRequest multiReq = new MultipartRequest(req, saveDir, 50*1024*1024, "utf-8", new DefaultFileRenamePolicy());

			String wr_file = null;
			Enumeration enumer = multiReq.getFileNames();
			while(enumer.hasMoreElements()){
				String name = (String)enumer.nextElement();
				wr_file = multiReq.getFilesystemName(name);
			}
			basicBoardDto.setWr_file(wr_file);
			
			String wr_title = multiReq.getParameter("wr_title");
			String wr_writer = multiReq.getParameter("wr_writer");
			String wr_email = multiReq.getParameter("wr_email");
			String wr_home = multiReq.getParameter("wr_home");
			String wr_pass = multiReq.getParameter("wr_pass");
			String wr_contents = multiReq.getParameter("wr_contents");
			String wr_ip = req.getRemoteAddr();
			int board_num = Integer.parseInt(multiReq.getParameter("board_num"));
			
			basicBoardDto.setWr_title(wr_title);
			basicBoardDto.setWr_writer(wr_writer);
			basicBoardDto.setWr_email(wr_email);
			basicBoardDto.setWr_home(wr_home);
			basicBoardDto.setWr_pass(wr_pass);
			basicBoardDto.setWr_contents(wr_contents);
			basicBoardDto.setWr_ip(wr_ip);
			basicBoardDto.setBoard_num(board_num);
			
			service.writeBoard(basicBoardDto);
		}
		
		return "redirect:list?board_num=" + basicBoardDto.getBoard_num();
	}
	
	/**
	 * 게시판 글읽기
	 * @param int board_num : 게시판 번호(다시 리스트페이지로 돌아갈 때 필요할 수 있기 때문)
	 * @param int wr_num : 글 번호(실제 처리는 이것만 있어도 됨.)
	 */
	@RequestMapping("/board/read")
	public void read(@ModelAttribute BasicBoardDto dto, int board_num, int wr_num){
		dto =  service.getBoard(wr_num);
		logger.info("controller-read : " + dto.getWr_title());
	}
}
