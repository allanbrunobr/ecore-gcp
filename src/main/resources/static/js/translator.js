   // Configuração do cliente WebSocket
    var socket = new SockJS('/ws');
    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {

    console.log('Conectado ao WebSocket');
    stompClient.subscribe('/topic/analysisResult', function(message) {
        var eventData = JSON.parse(message.body);
        console.log("Tradução recebida:", eventData);

        document.getElementById('result-container-translator').innerText = JSON.stringify(eventData);
        document.getElementById('loading-container').style.display = 'none';
        document.getElementById('result-container-translator').style.display = 'block';
    });
});
