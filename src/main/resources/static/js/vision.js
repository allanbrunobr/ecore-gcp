   // Configuração do cliente WebSocket para Vision FACE
    var socket = new SockJS('/ws');
    var stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {

    console.log('Conectado ao WebSocket');
    stompClient.subscribe('/topic/analysisResult', function(message) {
        var eventData = JSON.parse(message.body);
        console.log("Resultado recebido:", eventData);

        document.getElementById('result-container').innerText = interpretarAnaliseCloudVision(JSON.stringify(eventData));//JSON.stringify(eventData);
        document.getElementById('loading-container').style.display = 'none';
        document.getElementById('result-container').style.display = 'block';
    });
   // Configuração do cliente WebSocket para Vision LANDMARK
       stompClient.subscribe('/topic/analysisResultLandmarks', function(message) {
           var eventData = JSON.parse(message.body);
           console.log("Resultado LANDMARK recebido:", eventData);

           document.getElementById('result-container-landmark').innerText = JSON.stringify(eventData);
           document.getElementById('loading-container').style.display = 'none';
           document.getElementById('result-container-landmark').style.display = 'block';
       });
   });


    function interpretarAnaliseCloudVision(resultado) {
    const likelihoodMap = {
    VERY_UNLIKELY: "Muito improvável",
    UNLIKELY: "Improvável",
    POSSIBLE: "Possível",
    LIKELY: "Provável",
    VERY_LIKELY: "Muito provável"
};
    const analise = JSON.parse(resultado);
    const joyMessage = likelihoodMap[analise[0].joyLikelihood];
    const angerMessage = likelihoodMap[analise[0].angerLikelihood];
    const sorrowMessage = likelihoodMap[analise[0].sorrowLikelihood];
    const surpriseMessage = likelihoodMap[analise[0].surpriseLikelihood];

    const mensagem = `
        Alegria: ${joyMessage}\n
        Raiva: ${angerMessage}\n
        Tristeza: ${sorrowMessage}\n
        Surpresa: ${surpriseMessage}
    `;

    return mensagem;
}