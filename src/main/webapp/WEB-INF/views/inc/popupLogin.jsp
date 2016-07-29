<%@ page contentType="text/html; charset=utf-8"%>

<!-- 로그인 모달창 -->
<div class="modal fade alert" id="squarespaceModal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">x</span><span class="sr-only">Close</span></button>
            <h3 class="modal-title" id="lineModalLabel">Modal Login Window</h3>
        </div>
        <div class="modal-body">
            <form method="post" action="/Netsong7Web/resources/loginProcess.spr">
              <input type="hidden" name="_to" value="${_to}" >
              <div class="form-group">
                <label for="exampleInputEmail1">아이디</label>
                <input type="email" class="form-control" id="mem_id" placeholder="Enter ID">
              </div>
              
              <div class="form-group">
                <label for="exampleInputPassword1">패스워드</label>
                <input type="password" class="form-control" id="mem_pass" placeholder="Enter Password">
              </div>
              <div class="checkbox">
                <label>
                  <input type="checkbox"> Remember login
                </label>
              </div>
              <button type="submit" class="btn btn-primary center-block">Login</button>
            </form>
        </div>
    </div>
  </div>
</div>