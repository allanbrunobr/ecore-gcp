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
 });

   stompClient.connect({}, function(frame) {
       console.log('Conectado ao WebSocket');
       stompClient.subscribe('/topic/landmarkData', function(message) {
           var landmarkDataList = JSON.parse(message.body);
           var imageUrl = ""; // Defina a URL da imagem, se aplicável
           var landmarkDetectionMessage = convertToLandmarkDetectionMessage(imageUrl, landmarkDataList);
           // Faça o que quiser com o objeto LandmarkDetectionMessage
           console.log(landmarkDetectionMessage);

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

   function convertToLandmarkDetectionMessage(imageUrl, landmarkDataList) {
       var landmarkData = [];
       for (var i = 0; i < landmarkDataList.length; i++) {
           var response = landmarkDataList[i];
           if (!response.hasOwnProperty('error')) {
               var annotations = response.landmarkAnnotations;
               for (var j = 0; j < annotations.length; j++) {
                   var annotation = annotations[j];
                   var locationInfo = annotation.locations[0];
                   var landmark = {
                       mid: annotation.mid,
                       description: annotation.description,
                       score: annotation.score,
                       boundingPoly: {
                           vertices: annotation.boundingPoly.vertices,
                           normalizedVertices: annotation.boundingPoly.normalizedVertices
                       },
                       locations: {
                           latLng: {
                               latitude: locationInfo.latLng.latitude,
                               longitude: locationInfo.latLng.longitude
                           }
                       }
                   };
                   landmarkData.push(landmark);
               }
           }
       }
       return {
           imageUrl: imageUrl,
           landmarkData: landmarkData
       };
   }


   $(document).ready(function() {
       $('.send-coordinates').click(function() {
           var latitude = $(this).data('latitude');
           var longitude = $(this).data('longitude');
alert(latitude);
           // Enviar os dados para o controller via AJAX
           $.ajax({
               type: 'POST',
               url: '/maps',
               data: {
                   latitude: latitude,
                   longitude: longitude
               },
               success: function(response) {
                   // Lidar com a resposta do controller, se necessário
               },
               error: function(xhr, status, error) {
                   // Lidar com erros de requisição, se necessário
               }
           });
       });
   });
