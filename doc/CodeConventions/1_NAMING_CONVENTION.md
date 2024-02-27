
##  命名規則
  ### 1. 使用できる名前<br/>
クラス名やメソッド名などの名前は、英字(A-Z、a-z)、「_」、「$」のいずれかで始まり、
2文字目からは先に挙げた文字と数字という構成である。<br/>
数字から始まる名前や、「%」や「@」等の記号を使った名前は許されていない。
また、大文字と小文字は区別される（「Cart」「cart」は別物とみなされる）

  ### 2. キーワード<br/>
javaのキーワードには下記のものがある。クラス名や変数名、メソッド名には使用できないので注意すること。
大文字のキーワードはjava特有(C++と比較して)のものであるため特に注意すること。<br/>
  <br/>Javaの予約語<br/> 
|予約語一覧|          |          |          |          |
| :-------| :------- | :------- | :------- | :------- |
|abstract |assert    |boolean   |break     |byte      |
|case     |catch     |char      |class     |const     |
|continue |default   |do        |double    |else      |
|enum     |extends   |final     |finally   |float     |
|for      |goto      |if        |implements|import    |
|instanceof|int      |interface |long      |native    |
|new      |package   |private   |protected |public    |
|return   |short     |static    |strictfp  |super     |
|switch   |synchrnized|this     |throw     |throws    |
|transient|try       |void      |volatile  |while     |
|_        |          |          |          |          |
  ### 3. ファイル名<br/>
  後日定義

  ### 4. パッケージ名<br/>
パッケージ名は次のパッケージのサブパッケージとする。サブパッケージの名称は後法とする。<br/>
　<br/>パッケージ：com.jp.co.nexus<br/>

  ### 5. クラス/インターフェイス名<br/>
クラス名の先頭が大文字の名詞とする。各単語の先頭文字を大文字、その他を小文字とする。<br/>
なるべくシンプルで、クラスの内容が推測可能な名前を付ける。<br/>
<br/>→略語は避け、単語全体を使用する。<br/>
        <br/>クラスが例外クラスの場合はクラス名の最後に「Exception」(例：SQLException)という文字列を付加する。<br/>
        クラス名があまりにも長い場合、「Exception」を「Ex」と略してもよい。<br/>

```java
  　　〇public MyClass{ 
　　　×public MakeMyClass{  ←  動詞 
　　　×public myClass{  ←  先頭が小文字 
　　　×public MyCls{  ←  省略表記 
```                             

  ### 6.  メソッド名<br/>
メソッド名は先頭が小文字の動詞で始まり、続く各単語の先頭は大文字で始まるものとする。<br/>
 デバッグ用のメソッドは接頭子として「debug」を付け、その他のメソッドと区別する。<br/>
 JavaBeansのメソッド名に関しては、フィールドに値をセットするメソッドは”set”＋フィールド名<br/>
 とし、フィールドから値を取得するメソッドはget＋フィールド名とする。<br/>
 ただし、フィールドから取得する値がbooleanの場合は、is＋フィールド名とする。<br/>

```java
      〇doMyMethod(){
      ×myMethod(){   ←  名詞
      ×doMymethod(){   ←  単語の先頭が小文字
```
  
  ### 7.  変数名<br/>
変数名は、小文字の名詞から始まり、続く各単語の先頭は大文字で始まるものとする<br/>
 変数名は「_（アンダーバー）」、「$（ドル）」から始めない。変数名は簡潔な名前とし、<br/>
 その用途が容易に推測できるものとする、1文字の変数名はなるべく避ける。ただし、forループの<br/>
 カウンタ変数に関しては1文字の変数名を許す（for(int i=0; i<5; i++）など。）<br/>

```java
      〇myVariable
      ×MyVariable    ←  最初が大文字
```


  ### 8.  定数<br/>
定数はすべて大文字で記述し、単語間を「_（アンダーバー）」で区切る。<br/>
        
```java
        〇MY_CONSTANT_VALUE
        ×My_Constant_Value    ←  小文字が含まれている
        ×MYCONSTANTVALUE      ←  _で区切られていない
```

[目次に戻る](CONTENTS.md)

