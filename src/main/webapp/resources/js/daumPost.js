
function daumPost(){
    new daum.Postcode({
        oncomplete: function(data) {
        	console.log(data);
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
            // 예제를 참고하여 다양한 활용법을 확인해 보세요.
            const addr1 = document.getElementById("addr1");
            const addr2 = document.getElementById("addr2");
            const addr3 = document.getElementById("addr3");
            let addr="";
            if(data.userSelectedType === "R"){
            	//도로명 선택
            	addr = data.roadAddress;
            }else{
            	addr = data.jibunAddress;
            }
			addr1.value	= data.zonecode;
			addr2.value = addr;
			addr3.focus();
        }
    }).open();
   }

