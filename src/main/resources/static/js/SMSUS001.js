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