<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://code.jquery.com/jquery-3.6.3.min.js" integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>
    <title>save</title>
</head>
<body>
    <form action="/member/save" id="finalCheck" method="post">
        <input type="text" name="memberEmail" placeholder="이메일" id="memberEmail" onblur="emailCheck()">
        <select class="form-control" name="memberEmail2" id="memberEmail2">
            <option >메일주소 선택</option>
            <option >@naver.com</option>
            <option >@gmail.com</option>
            <option >@daum.net</option>
        </select>
        <button type="button" id="email_auth_btn" class="email_auth_btn">인증번호 받기</button>
        <input type="text" placeholder="인증번호 입력" id="email_auth_key">
        <p id="check-result"></p>
        <input type="text" name="memberPassword" placeholder="비밀번호">
        <input type="text" name="memberName" id="memberName" placeholder="이름">
        <button onclick="nameCheck()" type="button">중복 확인</button>
        <input type="text" name="memberAge" placeholder="나이">
        <input type="text" name="memberMobile" placeholder="전화번호">
        <input type="button" onClick="submitCheck()" value="회원가입">


    </form>
    

</body>
<script>
// 이메일 입력값을 가져오고,
// 입력값을 서버로 전송하고 똑같은 이메일이 있는지 체크한 후 
// 사용 가능 여부를 이메일 입력창 아래에 표시 
    var emailCheck2 = false;
    var nameCheck2 = false;

    $(function() {

 	var email_auth_cd = '';

	$(".email_auth_btn").click(function(){
    	 var email = $('#memberEmail').val() + $('#memberEmail2').val();
         

    	 if(email == ''){
    	 	alert("이메일을 입력해주세요.");
    	 	return false;
    	 }
         if(email == '메일주소 선택'){
            alert("이메일을 입력해주세요.");
    	 	return false;
         }

        $.ajax({
			type : "POST",
			url : "/member/emailAuth",
			data : {email : email},
			success: function(data){
				alert("인증번호가 발송되었습니다.");
				email_auth_cd = data;
			},
			error: function(data){
				alert("메일 발송에 실패했습니다.");
			}
		});

        });

    })

    const emailCheck = () => {
        const email = document.getElementById("memberEmail").value;
        const checkResult = document.getElementById("check-result");
        $.ajax({
            // 요청방식: post, url: "email-check", 데이터: 이메일
            type: "post",
            url: "/member/email-check",
            //RequestParam = 클라 -> 서버
            data: {
                "memberEmail": email
            },
            success: function(res){
                if(res== "ok"){
                    checkResult.style.color = "green";
                    checkResult.innerHTML = "사용가능한 이메일";
                    emailCheck2 = true;

                } else {
                    checkResult.style.color = "red";
                    checkResult.innerHTML = "이미 사용중인 이메일";
                    emailCheck2 = false;
                }
            },
            error: function(err){
                console.log("에러발생", err);
            }
        });
    }
   
    const nameCheck = () => {
        const email = document.getElementById("memberEmail").value;
        const name = document.getElementById("memberName").value;
        if(name == ""){
            alert("이름을 입력해주세요.")
        }else {
            $.ajax({
            type:"post",
            url: "/member/name-check",
            data: {
                "memberName": name,
                "memberEmail": email
            },
            success: function(res){
                if(res == "ok"){
                    alert("사용 가능한 이름입니다.")
                    nameCheck2 = true;

                } else {
                    alert("이미 사용중인 이름입니다.")
                    nameCheck2 = false;
                }
            },
            error: function(err){
                console.log("에러발생", err);
            }
        })
        }
    }

    const submitCheck = () => {
        if(emailCheck2 == true && nameCheck2 == true){
            alert("가입이 완료되었습니다.");
            $("#finalCheck").submit();
            
        }else {
            alert("내용을 다시 확인해주세요.");
            

        }
    }
   
</script>

</html>