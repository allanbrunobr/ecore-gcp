   // Configuração do cliente WebSocket para Vision FACE
   const socket = new SockJS('/ws');
   const stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {

    console.log('Conectado ao WebSocket');
    stompClient.subscribe('/topic/analysisResult', function(message) {
        const eventData = JSON.parse(message.body);
        console.log("Resultado recebido:", eventData);

        document.getElementById('result-container').innerText = interpretarAnaliseCloudVision(JSON.stringify(eventData));
        document.getElementById('loading-container').style.display = 'none';
        document.getElementById('result-container').style.display = 'block';
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


   $(document).ready(function() {
       $('.send-coordinates').click(function() {
           const latitude = $(this).data('latitude');
           const longitude = $(this).data('longitude');
           // Enviar os dados para o controller via AJAX
           $.ajax({
               type: 'POST',
               url: '/maps',
               data: {
                   latitude: latitude,
                   longitude: longitude
               },
               success: function(response) {
               },
               error: function(xhr, status, error) {
               }
           });
       });
   });
