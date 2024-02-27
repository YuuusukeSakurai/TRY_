
/* 顧客選択↓↓ */

//検索ボタン押されていたら顧客ダイアログ開く
document.addEventListener("DOMContentLoaded", function () {
    var str = $("#clientDialog").val();
    if (str == "open") {
        openClientDialog();
    }
});

$(function () {
    $('#openClientModal').click(function () {
        openClientDialog();
    });
    $('#closeModal , #modalBg').click(function () {
        $('body').css('overflow', 'auto'); //スクロール有効化
        $('#clientModal').fadeOut();
    });
});

//表示する
function openClientDialog() {
    $('body').css('overflow', 'hidden'); //スクロール無効化
    $('#clientModal').fadeIn();
}

//行クリック時
document.addEventListener("DOMContentLoaded", function () {
    // 顧客選択ダイアログ内の各行に対してクリックイベントを設定
    var rows = document.querySelectorAll("#clientModal tbody tr");

    rows.forEach(function (row) {

        //クリック
        row.addEventListener("click", function () {

            // クリックされた行の背景色を変更
            rows.forEach(function (r) {
                r.style.backgroundColor = ""; // 他の行の背景色をリセット
            });
            row.style.backgroundColor = "rgba(169, 169, 169, 0.75)"; // クリックされた行の背景色を変更

            // クリックされた行から顧客番号と顧客名を取得
            var clientId = row.cells[0].textContent; // 顧客番号のセル
            var clientName = row.cells[1].textContent; // 顧客名のセル

            // 取得した値を保存
            selectedClientName = clientName;
            selectedClientId = clientId;
        });

        //ダブルクリック
        row.addEventListener("dblclick", function () {

            // クリックされた行から顧客番号と顧客名を取得
            var clientId = row.cells[0].textContent; // 顧客番号のセル
            var clientName = row.cells[1].textContent; // 顧客名のセル

            // もと画面の検索欄に顧客名を設定
            $('input[name="client_name"]').val(clientName);
            $('input[name="client_id"]').val(clientId);

            // モーダルを閉じる
            $('body').css('overflow', 'auto'); // スクロール有効化
            $('#clientModal').fadeOut();
        });

    });
});

// モーダルで選択ボタンをクリックしたときの処理
$('#selectClient').click(function () {
    // もと画面の検索欄に顧客名を設定
    $('input[name="client_name"]').val(selectedClientName);
    $('input[name="client_id"]').val(selectedClientId);

    // モーダルを閉じる
    $('body').css('overflow', 'auto'); // スクロール有効化
    $('#clientModal').fadeOut();
});

/* 顧客選択ここまで↑↑ */

/* 社員選択↓↓ */

//検索ボタン押されていたら社員ダイアログ開く
document.addEventListener("DOMContentLoaded", function () {
    var str = $("#employeeDialog").val();
    if (str == "open") {
        openEmployeeDialog();
    }
});

$(function () {
    $('#openEmployeeModal').click(function () {
        openEmployeeDialog();
    });
    $('#closeModal , #modalBg').click(function () {
        $('body').css('overflow', 'auto'); //スクロール有効化
        $('#employeeModal').fadeOut();
    });
});

//表示する
function openEmployeeDialog() {
    $('body').css('overflow', 'hidden'); //スクロール無効化
    $('#employeeModal').fadeIn();
}

//行クリック時
document.addEventListener("DOMContentLoaded", function () {
    // ダイアログの各行にクリックイベントを設定
    var rows = document.querySelectorAll("#employeeModal tbody tr");

    rows.forEach(function (row) {
        //クリック
        row.addEventListener("click", function () {

            // クリックされた行の背景色を変更
            rows.forEach(function (r) {
                r.style.backgroundColor = ""; // 他の行の背景色をリセット
            });
            row.style.backgroundColor = "rgba(169, 169, 169, 0.75)"; // クリックされた行の背景色を変更

            // クリックされた行から社員番号と社員名を取得
            var employeeId = row.cells[0].textContent; // 社員番号のセル
            var employeeName = row.cells[1].textContent; // 社員名のセル

            // 取得した値を保存
            selectedEmployeeName = employeeName;
            selectedEmployeeId = employeeId;
        });

        //ダブルクリック
        row.addEventListener("dblclick", function () {

            // クリックされた行から社員番号と社員名を取得
            var employeeId = row.cells[0].textContent; // 社員番号のセル
            var employeeName = row.cells[1].textContent; // 社員名のセル

            // もと画面の検索欄に入力
            $('input[name="employee_name"]').val(employeeName);
            $('input[name="employee_id"]').val(employeeId);

            // モーダルを閉じる
            $('body').css('overflow', 'auto'); // スクロール有効化
            $('#employeeModal').fadeOut();
        });
    });
});

// 選択ボタンをクリックしたとき
$('#selectEmployee').click(function () {

    // もと画面の検索欄に入力
    $('input[name="employee_name"]').val(selectedEmployeeName);
    $('input[name="employee_id"]').val(selectedEmployeeId);

    // モーダルを閉じる
    $('body').css('overflow', 'auto'); // スクロール有効化
    $('#employeeModal').fadeOut();
});

/* 社員選択ここまで↑↑ */

//検索ボタン押下時の遷移先を指定するコードはhtmlに記載


