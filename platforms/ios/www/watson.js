navigator.getUserMedia =
  navigator.getUserMedia
  || navigator.webkitGetUserMedia
  || navigator.mozGetUserMedia
  || navigator.msGetUserMedia;

$.post('https://energy-helmet.herokuapp.com/watson-key').then(function(data) {
  var key = data.key;

  var stream = WatsonSpeech.SpeechToText.recognizeMicrophone({ token: key, outputElement: '#invis' });

  var currentZoom = undefined;

  console.log(1)

  stream.on('error', function(err) {
    console.log(err)
  })

  stream.on('data', function(data) {
    console.log(2)
    for (var i in data.alternatives) {
      console.log(3)
      var alt = data.alternatives[i];
      if (data.final) {
        console.log(4)
        var text = alt.transcript;
        console.log(text);
        if (/zoom/i.test(text)) {
          console.log('ZOOMING ', text);
          if (currentZoom) {
            if (currentZoom == 'map')      unZoomMap();
            if (currentZoom == 'slack')    unZoomSlack();
            if (currentZoom == 'readouts') unZoomGifz();
          } else {
            if (/map/i.test(text))      zoomMap();
            if (/slack/i.test(text))    zoomSlack();
            if (/readouts/i.test(text)) zoomGifz();

            if (/out/i.test(text)) { unZoomMap(); unZoomSlack(); unZoomGifz(); }
          }
        }
      }
    }
  });
});
