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

//有給取得日数を設定
// 基準日をHTMLから読み込む
var standardDateInput = document.getElementById("standardDateField");
console.log('結果: ' + standardDateInput);
var standardDateValues = standardDateInput.value; // 日付フォーマットはyyyy-mm-dd
// 基準日を分割して年、月、日に分ける
// DATE型のデータを文字列に変換
standardDateValue = String(standardDateValue);
var standardDateParts = standardDateValue.split("-");
var year = parseInt(standardDateParts[0]);
var month = parseInt(standardDateParts[1]) - 1; // 月は0から始まるため、1を引く
var day = parseInt(standardDateParts[2]);
console.log('year: ' + year);
// 申請種別（有給休暇のレコード数）
var paidLeaveRecords = 5; // 例: 5

// 申請種別（午前半休、午後半休のレコード数）
var halfDayRecords = 3; // 例: 3

// システム日付が基準日より前の場合
if (currentDate < new Date(year, month, day)) {
    // システム日付の年数 - 1年 + 基準日の月日
    var result = currentDate.getFullYear() - 1 + month + day;
    // 有給休暇のレコード数を加算
    result += paidLeaveRecords;
    // 午前半休、午後半休のレコード数を加算
    result += halfDayRecords * 0.5;
} else {
    // システム日付の年数 + 基準日の月日
    var result = currentDate.getFullYear() + month + day;
    // 有給休暇のレコード数を加算
    result += paidLeaveRecords;
    // 午前半休、午後半休のレコード数を加算
    result += halfDayRecords * 0.5;
}

// 計算結果を特定のフィールドに自動入力
document.getElementById("resultField").value = result;

// 計算結果をコンソールに表示（デバッグ用）
console.log('結果: ' + result);


//ステータス管理
// 有休残日数（当年度分）をHTMLから取得
var remainingDaysThisYearElement = document.getElementById("remainingDaysThisYear");
var remainingDaysThisYear = parseInt(remainingDaysThisYearElement.textContent);

// 有休残日数（前年度分）をHTMLから取得
var remainingDaysLastYearElement = document.getElementById("remainingDaysLastYear");
var remainingDaysLastYear = parseInt(remainingDaysLastYearElement.textContent);

// 有休基準日をHTMLから取得
var standardDateElement = document.getElementById("standardDate");
var standardDateValue = standardDateElement.textContent; // 日付フォーマットはyyyy-mm-dd

// 有給取得日数をHTMLから取得
var paidLeaveTakenElement = document.getElementById("paidLeaveTaken");
var paidLeaveTaken = parseInt(paidLeaveTakenElement.textContent);

// システム日付を取得
var currentDate = new Date();

// ステータスとステータスマークを初期化
var status = "ブランク";
var statusMark = "なし";

// 条件1: 有休残日数（当年度分）+有休残日数（前年度分）が0以下
if (remainingDaysThisYear + remainingDaysLastYear <= 0) {
    statusMark = "なし";
    status = "有休残日数なし";
} else if (currentDate < standardDate) { // 条件2: 有休基準日から6ヶ月以内かつ有給取得日が0
    if (paidLeaveTaken === 0) {
        statusMark = "なし";
        status = "有休取得日数不足（通知）";
    }
} else if (currentDate < new Date(standardDate.getFullYear(), standardDate.getMonth() + 3, standardDate.getDate()) && paidLeaveTaken < 5) {
    // 条件3: 有休基準日から9ヶ月以内かつ有給取得日が5未満
    statusMark = "▲";
    status = "有休取得日数不足（注意）";
} else if (currentDate < new Date(standardDate.getFullYear(), standardDate.getMonth() + 4, standardDate.getDate()) && paidLeaveTaken < 5) {
    // 条件4: 有休基準日から10ヶ月以内かつ有給取得日が5未満
    statusMark = "▲";
    status = "有休取得日数不足（警告）";
}

// ここで status と statusMark を使用して、HTMLの特定の要素にステータスとステータスマークを表示できます。
// たとえば、IDが "statusElement" と "statusMarkElement" の要素に設定できます。
document.getElementById("statusElement").textContent = status;
document.getElementById("statusMarkElement").textContent = statusMark;


