<%@page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/WEB-INF/views/inc/header.jsp"></jsp:include>

<script>
	var flag = true;
  	
  	$(document).ready(
  		function(){
  			$("#home").click(
  				function(){
  					var url ="main.app";
					$(location).attr('href',url);
  				}
  			);
  			$("#update").click(
      			function(){
      				var url ="board_manage?cmd=UPDATE_BOARD";
  					$(location).attr('href',url);
      			}
      		);
  			$("#reply").click(
      			function(){
      				var url ="board_manage?cmd=REPLY_BOARD";
  					$(location).attr('href',url);
      			}
      		);
  			/*
  			$("#comment").click(
  				//http://api.jqueryui.com/toggleClass/
          		function(){
          			 $(".toggler").toggleClass( "commentshow");
          		}
  				
          	);
  			*/
  			$("#delete").click(
          		function(){
          			var url ="board_manage?cmd=DELETE_BOARD";
      				$(location).attr('href',url);
          		}
          	);
  		}
  	);
</script>

	<div class="container">
	
		<div class="page-header">
		    <h1>글읽기 <small>이곳은 게시판의 글을 읽는 곳입니다.</small></h1>
		</div>
		
		<!-- Contact Form - START -->
		<div class="container">
		    <div class="row">
		        <div class="col-md-8">
		            <div class="well well-sm">
		                <form class="form-horizontal" method="post">
		                    <fieldset>
		                        <legend class="text-center header">글을 읽어 주세요</legend>
		
		                        <div class="form-group">
		                            <span class="col-md-2 text-center">제목</span>
		                            <div class="col-md-10">
		                                ${board["wr_title"]}
		                            </div>
		                        </div>
		                        <div class="form-group">
		                            <span class="col-md-2 text-center">글쓴이</span>
		                            <div class="col-md-10">
		                                ${board["wr_writer"]}
		                            </div>
		                        </div>
		
		                        <div class="form-group">
		                            <span class="col-md-2 text-center">이메일</span>
		                            <div class="col-md-10">
		                                ${board["wr_email"]}
		                            </div>
		                        </div>
		
		                        <div class="form-group">
		                            <span class="col-md-2 text-center">홈페이지</span>
		                            <div class="col-md-8">
		                                ${board["wr_home"]}
		                            </div>
		                        </div>
		                        
		                        <c:if test='${master["board_upload"] == "y"}'>
		                        <div class="form-group">
		                            <span class="col-md-2 text-center">업로드</span>
		                            <div class="col-md-8">
		                                <a href='board.manage?cmd=DOWNLOAD&path=upload&name=${board["wr_file"]}'>${board["wr_file"]}</a>
		                            </div>
		                        </div>
								</c:if>
								
		                        <div class="form-group">
		                            <span class="col-md-2 text-center">내용</span>
		                            <div class="col-md-10">
		                                ${board["wr_contents"]}
		                            </div>
		                        </div>
		
		                        <div class="form-group">
		                            <div class="col-md-12 text-center">
		                            	<button type="button" class="btn btn-primary" id="home">홈</button>
		                                <button type="button" class="btn btn-primary" id="update">수정</button>
		                                <c:if test='${master["board_reply"] == "y" }'>
		                                	<button type="button" class="btn btn-primary" id="reply">답변</button>
		                                </c:if>
		                                <button type="button" class="btn btn-primary" id="delete">삭제</button>
		                            </div>
		                        </div>
		                    </fieldset>
		                </form>
		            </div>
		        </div>
		    </div>
		</div>
		댓글 여부 : ${master["board_comment"]}
		<!-- 댓글 -->
		<c:if test='${master["board_comment"] == "y"}'>
			<c:forEach var="item" items="${commentList}">
				<div>
					${item["co_name"]}<br/>
					${item["co_comment"]}<br/>
					${item["co_date"]} 
				</div>
				<br/><br/>
			</c:forEach>
			<div class="toggler">
			    <div>
			    	<form method="post" action="board.manage?cmd=READ_BOARD">
			    		<input type="hidden" name="wr_num" value='${board["wr_num"]}'>
			    		<input type="hidden" name="board_num" value='${master["board_num"]}'>
			    		<input type="hidden" name="subcmd" value="COMMENT_WRITE">
			    		
				    	<span class="col-md-2 text-center">댓글 쓰기</span>
				        <div class="col-md-5">
				        	<input type="text" id="co_name" name="co_name" placeholder="Enter your name"/><br/>
				         	<textarea class="form-control" id="co_comment" name="co_comment" placeholder="Enter your massage for us here." rows="3"></textarea>
				        </div>
				        <button type="submit" class="btn btn-primary" id="comment">댓글</button>
			        </form>
			    </div>
			</div>
		</c:if>
		<style>
		    .header {
		        color: #36A0FF;
		        font-size: 27px;
		        padding: 10px;
		    }
		
		    .bigicon {
		        font-size: 35px;
		        color: #36A0FF;
		    }
		    
		    <!-- http://api.jqueryui.com/toggleClass/ -->
		    .toggler {
		    	visibility:hidden;
		  	}
		
		  	.commentshow {
				visibility:visible;
		  	}
		</style>
	
		<!-- Contact Form - END -->
	</div>

<jsp:include page="/WEB-INF/views/inc/footer.jsp" />