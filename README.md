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

- moshiとretrofiでパース処理
moshiのパースについては以下の方法で行なった。
```
//　これでパースを行う
@JsonClass(generateAdapter = true)
data class Item(
    val items: List<Content>
)

@JsonClass(generateAdapter = true)
data class Content(
    val name: String,
    @Json(name = "owner")
    val owner: Owner,
    val language: String?,
    @Json(name = "stargazers_count")
    val stargazersCount: Long,
    @Json(name = "watchers_count")
    val watchersCount: Long,
    @Json(name = "forks_count")
    val forksCount: Long,
    @Json(name = "open_issues_count")
    val openIssuesCount: Long,
)

@JsonClass(generateAdapter = true)
data class Owner(
    @Json(name = "avatar_url")
    val avatarUrl: String
)

// 受け渡す型を定義したもの
@Parcelize
data class ParcelizeItem(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable
```
retrofitについては以下の方法でアクセスした。
```
repository

interface ApiService {
    @GET("search/repositories")
    suspend fun fetchRepositoryData(
        @Header("Accept") header: String,
        @Query("q") inputText: String
    ): Response<Item>
}
moduleでインスタンス作成

@Provides
    @Singleton
    //HttpClientのインスタンスを生成する
    fun provideRetrofit(
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
```

- 階層構造の変更

```
今までの階層構造  
--- view  
|- viewModel  
|- fragment  
|- activity  
|- adapter  
```
```
今回変更した階層構造  
---- ui  
|- result  
|- search  
```
また、OneFragmentの名前をSearchFragmentにするなど、リファクタした。

## 現在取り組んでいるところ
- テスト
