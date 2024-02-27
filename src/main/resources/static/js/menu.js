//アコーディオンをクリックした時の動作
$('.title').on('click', function() {
  var findElm = $(this).next(".submenu");
  $(findElm).slideToggle();

  if($(this).hasClass('close')){
    $(this).removeClass('close');
  }else{
    $(this).addClass('close');
  }
});

    // < ボタンをクリックしたときにメニューを非表示にする
    $("#menuClose").click(function() {
        $("#menu").animate({width: 'toggle' }, 50);
     $("#menuOpen").show();

    });
        $("#menuOpen").click(function() {
        $("#menu").animate({width: 'toggle' }, 50);
             $("#menuOpen").hide();
    });