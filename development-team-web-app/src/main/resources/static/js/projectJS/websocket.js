'use strict';

var table = document.querySelector('#newRow');
var refresh = document.querySelector('#refresh');

var stompClient = null;

function connect(event) {

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        console.log('_____________________HELLO' );
        stompClient.connect({}, onConnected, onError);

    event.preventDefault();
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/all', onMessageReceived);

    stompClient.send("/app/chat.getAll");

}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {


}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('td');

//messageElement.classList.add('chat-message');
console.log('_____________________HELLO' )

//console.log('++++++++++++++++++++' + message.length)
//var listElement2 = document.createElement('ul');
//     for (var i = 0; i < message.length; i++) {
//         var listElement1 = document.createElement('li');
//         var text = document.createTextNode(message[i]);
//         console.log('/////////////////////////////1' );
//         listElement1.appendChild(text);
//         listElement2.appendChild(listElement1);
//     }
//messageElement.appendChild(listElement2);
//message.content = '';
//}
//    var textElement = document.createElement('p');
//    var messageText = document.createTextNode(message.content);
//    textElement.appendChild(messageText);
//    messageElement.appendChild(textElement);
//
////      var textElement = document.createElement('p');
////    var messageText = document.createTextNode('Hello');
////    textElement.appendChild(messageText);
////    messageElement.appendChild(textElement);
//
//
//    messageArea.appendChild(messageElement);
//    messageArea.scrollTop = messageArea.scrollHeight;
}


//function getAvatarColor(messageSender) {
//    var hash = 0;
//    for (var i = 0; i < messageSender.length; i++) {
//        hash = 31 * hash + messageSender.charCodeAt(i);
//    }
//
//    var index = Math.abs(hash % colors.length);
//    return colors[index];
//}
//
refresh.addEventListener("click", connect, true)
//messageForm.addEventListener('submit', sendMessage, true)