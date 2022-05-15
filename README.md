# 株式会社ゆめみ Android エンジニアコードチェック課題

## 変更点
- 例外処理

**リポジトリの変更点**
```
//結果を返すクラス
sealed class Result<out R> {
    //読み込み中の時
    object Process : Result<Nothing>()
    //成功した場合
    data class Success<out T>(val data: T) : Result<T>()
    //失敗した場合
    data class Error(val exception: Throwable) : Result<Nothing>()
}
```

このようなsealed classを定義し、この型をflowで流すように変更した。

**viewModelの変更点**
```
 when (result) {
                    is Result.Process -> {
                        //ここでロード画面を見せる
                    }
                    is Result.Error -> { ...
```

と受け取った型によって処理を変えるように変更した。

## 現在取り組んでいるところ
- 例外処理
