// 최초 웹소켓 연결을 위한 핸드쉐이크 주소
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    // 연결에 성공하면 대화 내역을 볼 수 있는 conversation 화면 보이게 함
    setConnected(true);
    alert('Connected successfully')
    console.log('Connected: ' + frame);
    // Client들이 /topic/greetings를 구독하게 설정
    // Controller에서 @SendTo("/topic/greetings")를 통해 구독자들에게 메시지 전달
    stompClient.subscribe('/topic/greetings', (greeting) => {
    // html의 greetings라는 공간에 발신자가 보낸 메시지 append
        showGreeting(JSON.parse(greeting.body).content);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    // 최초 채팅 공간은 ""로 초기화
    $("#greetings").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
    alert('Disconnected successfully')
}

function sendName() {
    // sencd 버튼을 누르는 순간 컨트롤러의 @MessageMapping("/hello")와 매핑
    // Client가 입력한 이름이
    stompClient.publish({
        destination: "/app/hello",
        // 자바스크립트의 name 값을 JSON 문자열로 변환
        body: JSON.stringify({'name': $("#name").val()})
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendName());
});