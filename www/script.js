// var addWidget = (function() {
//   var x = {
//     top: { bottom: '20px' },
//     bottom: { top: '20px' }
//   };
//   var y = {
//     left: { left: '20px' },
//     right: { right: '20px' }
//   };
//   var i = 0;
//   return function(yPos, xPos, template, updateInterval, render) {
//     var widgetId         = 'widget-' + i++;
//     var templateFunction = _.template($(template).html);
//     var widget           = $('<div id="' + widgetId + '">');
//     widget.css(y[yPos]);
//     widget.css(x[xPos]);

//     $('body').append(widget);

//     (function loop() {
//       render();
//     })();
//   };
// })();

function zoomMap() {
  $('.map-container').css({
    bottom:   '0',
    right:  '0',
    height:'100%',
    width: 'auto'
  });
  $('#the-map').css({
    bottom:   '0',
    right:  '0',
    height:'100%',
    width: 'auto'
  });
  $('.map-rotation').css({
    bottom:   '0',
    right:  '0',
    height:'100%',
    width: 'auto'
  });
}

function unZoomMap() {
  $('.map-container').css({
    bottom:   '20px',
    right:  '20px',
    height:'100px',
    width: '100px'
  });
  $('#the-map').css({
    bottom:   '20px',
    right:  '20px',
    height:'100px',
    width: '100px'
  });
  $('.map-rotation').css({
    bottom:   '20px',
    right:  '20px',
    height:'100px',
    width: '100px'
  });
}

