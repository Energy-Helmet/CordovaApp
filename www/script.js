//
// MAP
//

function zoomMap() {
  $('#template-container').hide();
  $('.gifz').hide();

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
  $('#template-container').show();
  $('.gifz').show();

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

//
// SLACK
//

function zoomSlack() {
  $('.map-container').hide();
  $('.gifz').hide();

  $('#template-container').css({
    bottom: '0',
    left:  '0',
    'font-size': '34px'
  });
}

function unZoomSlack() {
  $('.map-container').show();
  $('.gifz').show();

  $('#template-container').css({
    bottom: '20px',
    left:   '20px',
    'font-size': '14px'
  });
}

//
// GIFZ
//

function zoomGifz() {
  $('.map-container').hide();
  $('#template-container').hide();

  $('.gifz-gif img').css({
    top: 0,
    right: 0,
    height: '100%',
    width: 'auto'
  });
  $('.gifz').css({
    bottom: '0',
    right:  '0',
    height: '100%',
    width:  'auto'
  });
}

function unZoomGifz() {
  $('.map-container').show();
  $('#template-container').show();

  $('.gifz-gif img').css({
    top: 0,
    right: 0,
    height: '100px',
    width: '100px'
  });
  $('.gifz').css({
    top: '20px',
    right:   '20px',
    height: '100px',
    width: '100px'
  });
}

