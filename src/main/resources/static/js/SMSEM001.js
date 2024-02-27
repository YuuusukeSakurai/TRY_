// dialog.js
// ページの読み込みが完了したら実行
document.addEventListener("DOMContentLoaded", function () {
    // ダイアログを非表示に初期化
    document.getElementById("customerDialog").style.display = "none";
    
    // ボタンクリック時にダイアログを表示
    document.getElementById("showDialogButton").addEventListener("click", function () {
        // ダイアログを表示
        document.getElementById("customerDialog").style.display = "block";
    });
    
    // ダイアログ内の顧客を選択したときの処理
    var customerRows = document.querySelectorAll("#customerDialog tbody tr");
    customerRows.forEach(function (row) {
        row.addEventListener("click", function () {
            // 顧客番号と顧客名を取得
            var customerId = row.querySelector("td:first-child").textContent;
            var customerName = row.querySelector("td:last-child").textContent;
            
            // 顧客番号をフィールドに自動入力
            document.getElementById("customerNumberField").value = customerId;
            
            // 担当顧客名をフィールドに自動入力
            document.getElementById("customerNameField").value = customerName;
            
            // ダイアログを非表示にする
            document.getElementById("customerDialog").style.display = "none";
        });
    });
})





   