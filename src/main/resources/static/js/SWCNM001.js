var table = document.querySelector("table");
var tr = table.querySelectorAll("tr");
//表クリック時
table.addEventListener("click", function(e) {
  if(e.target.tagName.toLowerCase() === "td") {
    //まずは全て背景色白
    for(var i = 1; i < tr.length; i++) {
      // alert(i +': '+ tr[i].style.backgroundColor);
      tr[i].style.backgroundColor = "white";
    }
    //選択行だけ色を変える
    e.target.parentNode.style.backgroundColor = "#eef";
  }
}, false);

//表ダブルクリック時
table.addEventListener("dblclick", function(e) {
  // ダブルクリックされた行のseqIdを取得してパラメータに設定する
  if(e.target.tagName.toLowerCase() === "td") {
    var seqId = e.target.parentNode.children.item(1).innerHTML;
    location.href = "/SWCNM002?seqId="+seqId;
  }
}, false);

// 検索チェック
function checkSelect() {
  // 社員番号に3桁の整数以外の入力があった場合エラーとする
  var employeeId = document.getElementById('employee_id').value;
  errorMessage.innerHTML = '';
  var regex = new RegExp(/^\d{0,3}$/);
  if (regex.test(employeeId)) {
    document.selectForm.submit();
  } else {
    errorMessage.innerHTML = 'WHC01E002:社員番号には3桁以下の整数を入力してください。';
  }
}

// 削除チェック
function checkDelete() {
  // チェックされている行が1件以上存在するか確認
  var ckFlg = Boolean("");
  document.querySelectorAll("td:first-of-type input").forEach(function(check) {
    if(check.checked && !ckFlg) {
      // チェックされていた場合フラグを立てる
      ckFlg = Boolean("true");
    }
  });

  var errorMessage = document.getElementById('errorMessage');
  errorMessage.innerHTML = '';
  if (ckFlg) {
    // 削除の最終確認
    var result = window.confirm('選択したデータを削除します。よろしいですか。');
    if( result ) {
      // submit
      document.deleteForm.submit();
    }
  } else {
    // エラーを出力
    errorMessage.innerHTML = 'COM01W003:対象が選択されていません。対象を選択してください。';
  }
}

// 画面初期表示
var fold = document.getElementById("fold");
window.onload = onLoad;
function onLoad() {
  // 10件以上の場合は10件表示、10件未満の場合は全件表示
  var tables=document.getElementById('table');
  tables.style.height=(tables.clientHeight>=436)?'436px':'auto';
}

// 画面表示
function formDisplay() {
  fold.style.transform = 'rotate(270deg)';
  // 検索条件の表示を変更
  var obj=document.getElementById('open').style;
  obj.display=(obj.display=='none')?'block':'none';

  var tables=document.getElementById('table');
  // 全量表示に初期化
  tables.style.height='auto';
  if (obj.display!='none') {
    fold.style.transform = 'rotate(0deg)';
    // 検索条件表示の場合は10件表示（10件未満の場合は全件表示）
    tables.style.height=(tables.clientHeight>=436)?'436px':'auto';
  }
}

// チェックボックス
var checkall = document.getElementById("checksAll");
var checks = document.querySelectorAll(".checks");
checkall.addEventListener('click', () => {
  // 見出しのチェックを押下した場合、表示されている行のチェックをすべて同じように変更する
  for (val of checks) {
    checkall.checked == true ? val.checked = true : val.checked = false;
  }
});
