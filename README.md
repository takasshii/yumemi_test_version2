# 株式会社ゆめみ Android エンジニアコードチェック課題

## 変更点

### 環境
- DataBinding
- Hilt 2.41

>build.gradle(app)
>``` 
>//dataBinding
>dataBinding {
>  enabled = true
>}     
>//Hilt
>implementation "com.google.dagger:hilt-android:2.41"
>kapt "com.google.dagger:hilt-android-compiler:2.41"
>```
>
>build.gradle(module)
>``` 
>//Hilt
>classpath 'com.google.dagger:hilt-android-gradle-plugin:2.41'
>```

### アーキテクチャ
CleanArchitecture+MVVMを意識した構造にした。
UIを操作するプレゼンテーション層、interfaceでインフラ層からの操作を可能にするドメイン層、データを取得するインフラ層に分け、
ViewModelの変更通知をLiveDataで行い、データの取得通知をFlowで行った。また、DIライブラリとしてHiltを用いた。
<img src="https://user-images.githubusercontent.com/83356340/167238818-29caec90-77eb-4875-ba6c-7c1d4bb743be.jpg">

### コードレビューで教えていただきたい点
**1. 例外処理**

> 現在のコードの例外処理の仕組み
> - Flowのcatchで例外を補足
> - 例外のメッセージをユーザーに表示

>問題点
> - 例外で渡されるメッセージが一部Json型でないため、パースが出来ずエラー全体を表示してしまっている
> - それゆえ、インターネット接続がないといった具体的な処置をお知らせできない

>教えていただきたいこと
> - どのようにエラーを取得してユーザーにお知らせするのか

**2. テストの書き方について**

>問題点
> - Hiltを用いたテストを[公式ドキュメント](https://developer.android.com/training/dependency-injection/hilt-testing?hl=ja)に沿っ>て書こうとしていたが、以下のようなエラーが消えず困っています。
 >``` 
> No instrumentation registered! Must run under a registering instrumentation.
 >```

>教えていただきたいこと
> - そもそも単体テストやエンドツーテストのような違いがわかっていないので、体系的に学べるテストの記事や本を教えていただけると助かります。

**3. lateinitについて**

>現在のコード
> - lateinitに値が代入される前に呼び出される危険性があると知ってはいるが、latainitの部分のリファクタができていない

>教えていただきたいこと
> - 今回のコードだとどのようにlateinitの部分を書き換えることができるのか

## 概要

本プロジェクトは株式会社ゆめみ（以下弊社）が、弊社に Android エンジニアを希望する方に出す課題のベースプロジェクトです。本課題が与えられた方は、下記の概要を詳しく読んだ上で課題を取り組んでください。

## アプリ仕様

本アプリは GitHub のリポジトリを検索するアプリです。

<img src="docs/app.gif" width="320">

### 環境

- IDE：Android Studio Arctic Fox | 2020.3.1 Patch 1
- Kotlin：1.5.31
- Java：11
- Gradle：7.0.1
- minSdk：23
- targetSdk：31

※ ライブラリの利用はオープンソースのものに限ります。

### 動作

1. 何かしらのキーワードを入力
2. GitHub API（`search/repositories`）でリポジトリを検索し、結果一覧を概要（リポジトリ名）で表示
3. 特定の結果を選択したら、該当リポジトリの詳細（リポジトリ名、オーナーアイコン、プロジェクト言語、Star 数、Watcher 数、Fork 数、Issue 数）を表示

## 課題取り組み方法

Issues を確認した上、本プロジェクトを [**Duplicate** してください](https://help.github.com/en/github/creating-cloning-and-archiving-repositories/duplicating-a-repository)（Fork しないようにしてください。必要ならプライベートリポジトリにしても大丈夫です）。今後のコミットは全てご自身のリポジトリで行ってください。

コードチェックの課題 Issue は全て [`課題`](https://github.com/yumemi-inc/android-engineer-codecheck/milestone/1) Milestone がついており、難易度に応じて Label が [`初級`](https://github.com/yumemi-inc/android-engineer-codecheck/issues?q=is%3Aopen+is%3Aissue+label%3A初級+milestone%3A課題)、[`中級`](https://github.com/yumemi-inc/android-engineer-codecheck/issues?q=is%3Aopen+is%3Aissue+label%3A中級+milestone%3A課題+) と [`ボーナス`](https://github.com/yumemi-inc/android-engineer-codecheck/issues?q=is%3Aopen+is%3Aissue+label%3Aボーナス+milestone%3A課題+) に分けられています。課題の必須／選択は下記の表とします。

|   | 初級 | 中級 | ボーナス
|--:|:--:|:--:|:--:|
| 新卒／未経験者 | 必須 | 選択 | 選択 |
| 中途／経験者 | 必須 | 必須 | 選択 |

課題 Issueをご自身のリポジトリーにコピーするGitHub Actionsをご用意しております。  
[こちらのWorkflow](./.github/workflows/copy-issues.yml)を[手動でトリガーする](https://docs.github.com/ja/actions/managing-workflow-runs/manually-running-a-workflow)ことでコピーできますのでご活用下さい。

課題が完成したら、リポジトリのアドレスを教えてください。

## 参考記事

提出された課題の評価ポイントに関しては、[こちらの記事](https://qiita.com/blendthink/items/aa70b8b3106fb4e3555f)に詳しく書かれてありますので、ぜひご覧ください。
