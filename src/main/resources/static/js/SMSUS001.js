/**
 * SMSUS001.js
 */
(function() {
	'use strict';
	window.addEventListener('load', function() {
		var forms = document.getElementsByClassName('needs-validation');
		var validation = Array.prototype.filter.call(forms, function(form) {
			form.addEventListener('submit', function(event) {
				if (form.checkValidity() === false) {
					event.preventDefault();
					event.stopPropagation();
				}
				form.classList.add('was-validated');
			}, false);
		});
	}, false);
})();

/*ユーザマスタ一覧(SMSUS001)から選択行削除*/
$(document).ready(function() {
	$('#delete').click(function() {
		if (confirm('削除しますがよろしいでしょうか？')) {
			var form = $(this).parents('form');
			form.attr('action', '/user/delete');
			form.submit();
		} else {
			return false;
		}
	});
});

//タイトルをクリックしたときに伸び縮み（タイトル名のhタグにidを付与、
//伸び縮みする箇所を囲むdivにclass="accordion"を付与）
/*リストの行数を固定してスクロールバー表示（tableタグを囲むdivタグに付与）*/
$(document).ready(function() {
	//画面が読み込まれた時はcss適用
	$('#listLimit').css({
		'max-height': 'calc(33px * 12)',
		'overflow-y': 'scroll'
	});

	$('#folding').click(function() {
		var accordion = $('.accordion');
		accordion.slideToggle(500, function() {
			if (!accordion.is(':visible')) {
				// .accordionが非表示の場合、20行分表示させる
				$('#listLimit').css({
					'max-height': '',
					'overflow-y': ''
				});
			} else {
				// .accordionが表示されている場合、10行分表示してスクロール
				$('#listLimit').css({
					'max-height': 'calc(33px * 12)',
					'overflow-y': 'scroll'
				});
			}
		});
	});
});