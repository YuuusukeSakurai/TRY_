# 文
## 1. 単純な文 
1行に1つの文を記述する。

```java
〇i++;
  j++;

×i++;j++;

```

## 2. 複合文
中括弧で囲まれた複数の文を複合文とする。<br>
複合文のうち中括弧に囲まれた部分は、中括弧を含む文よりも1段インデントする。<br>
開き中括弧は複合文の記述の始まる行の最後に記述する。<br>
閉じ中括弧は行の最初に記述し、複合文を最初の文と同じインデントとする。<br>
文がforやifの制御構造の一部であれば、中身が1文だけであっても必ず中括弧を使用する。

```java
〇if(aaa){
        i++;
 }

×if(aaa)i++;
```

## 3. 制御文
### return文<br>
return文の返り値には、条件式がある場合を除いて小括弧を使用しない。

```java
〇return;

〇return(size?size:default)

×return();
```

### if-else if文
elseに続けてif文を記述する時は、elseの後にスペースを1つ配置してif文を続ける。

```java
if(aaa){
        ・・・・
else if(bbb){
        ・・・
}else{
        ・・・
}
}
```

### for文
for文に続く実行文は1文であっても中括弧でくくる。実行文がない場合は、中括弧は使用しない。<br>
反復節をカンマで区切って複数の文を記述する時は、文の数が3つ以下となるようにする。

```java
for(aaaa;bbbb;cccc){
 xxxxxxxxxxxx;
}
```

### while文
while文に続く実行文は1文であっても中括弧でくくる。実行文がない場合は、中括弧は使用しない。<br>

```java
while(aaaa){
 xxxxxxxxxxxx;
}
```

### do-while文
do文に続く実行文は、1文であっても中括弧でくくる。

```java
do{
 xxxxxxxxxxxx;
}while(aaaa);
```
### switch文
break文を伴わないcase節の最後には、`/*Breakなし*/`と記したコメント行を置き、<br>
break文が行われないことを明記する。<br>
すべてのswitch文にはdefault節を定義し、break文を置く。

```java
switch(aaaa){
 case ABC:
 	bbbbb;
 	/*Breakなし*/
 	
 case DEF:
 	cccc;
 	break;
 
 case GHI:
 	dddd;
 	break;
 
 defalut:
 	eeee;
 	break;

}
```

### try-catch-finally文
try文、catch文、finally文に続く実行文は、1文であっても中括弧でくくる。

```java
try{
	aaaaaa;
	
}catch(ExceptionClass e){
	bbbbbb;

}finally{
	cccccc;

}
```

[目次に戻る](CONTENTS.md)
