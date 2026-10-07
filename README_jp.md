# KMPStarter

このリポジトリを使用して、**Kotlin Multiplatform (KMP)** 開発をスタートしましょう。[Hridoy Chandra Das](https://github.com/ihridoydas)によって設計され、Android、iOS、デスクトップ、Web (Wasm)、バックエンド、TV、Wear OS、自動車、XRアプリケーションのための堅牢な出発点を提供します。

## なぜ KMPStarter？

- **デフォルトでマルチプラットフォーム:** Android、iOS、デスクトップ、Web向けの共有ロジックとUI (Compose Multiplatform)。
- **ユニバーサル適応型UI:** スマートフォン、タブレット、折りたたみ端末、デスクトップ、Web、TV、Wear OS、自動車、XRに自動適応する単一のコアUI (`FormFactorMode`対応)。
- **Ktorバックエンド & Swagger UI:** `/docs`でインタラクティブなSwagger UIを提供する本番対応のKtorバックエンドサーバー (`:apps:serverApp`)。
- **スマートセットアップ & フォームファクター切り替え:** プロジェクト名の変更や、ライブラリおよびフォームファクター (`useAndroidTV`, `useWearOS`, `useAndroidAutomotive`, `useAndroidXR`) のトグル設定、不要なソース・依存関係の自動削除。
- **多言語対応:** 英語、日本語、ベンガル語、システムデフォルトの内蔵サポート。

## アーキテクチャ

このプロジェクトは **クリーンアーキテクチャ** 原則と **モジュール化マルチプラットフォームレイヤリング** 戦略に従っています：

```text
├── :apps               # プラットフォーム固有のエントリーポイント (androidApp, desktopApp, webApp, serverApp)
├── iosApp              # iOS Swift アプリケーションラッパー (Xcode プロジェクト & Compose Multiplatform)
├── :feature            # 独立した機能モジュール (auth, home, profile, settings)
├── :navigation         # 型安全な Navigation3 API と実装
└── :core               # 基盤モジュール
    ├── :core:designsystem # Material 3 テーマ、コンポーネント、適応型フォームファクター UI
    ├── :core:common       # ユーティリティ、LanguageManager、定数
    ├── :core:model        # 共有ドメインモデル & DTO
    ├── :core:network      # Ktor HTTP クライアント & API サービス
    ├── :core:database     # Room KMP データベース & DAO
    ├── :core:datastore    # 型安全な DataStore プリファレンス
    ├── :core:platform     # プラットフォーム固有の expect/actual 抽象化
    └── :core:testing      # テストハーネス & モック
```

詳細なアーキテクチャガイドラインについては、[プロダクションアーキテクチャガイド](documentation/architecture/KmpTemplate_Production_Architecture.md)を参照してください。

## スタートガイド

1. **"このテンプレートを使用"**をクリックして、アカウントにリポジトリを作成します。
2. `buildscripts/setup.gradle` を開き、プロジェクトの詳細とフォームファクターを設定します：
    ```groovy
    def renameConfig = [
        newTemplateName          : "MyAwesomeApp",    // プロジェクト名
        newTemplateAppId         : "com.example.app", // パッケージID
        newMaterialThemeName     : "AppTheme",        // Material Theme名

        // ライブラリ & フォームファクターの切り替え
        useKoin                  : true,
        useKtor                  : true,
        useRoomKmp               : true,
        useComposeMultiplatform  : true,
        useAndroidXR             : true,
        useAndroidTV             : true,
        useWearOS                : true,
        useAndroidAutomotive     : true,
    ]
    ```
3. ターミナルでセットアップコマンドを実行します：
    ```bash
    ./gradlew renameTemplate
    ```
4. **Android Studioを再起動**し、Gradleを再同期すれば、ビルドの準備は完了です！

## Ktorバックエンド & Swagger UI の実行

バックエンドサーバーを起動します：
```bash
./gradlew :apps:serverApp:run
```
ブラウザで **[http://localhost:8080/docs](http://localhost:8080/docs)** を開くと、インタラクティブなSwagger UIドキュメントを確認できます。

## APIドキュメントの生成 (Dokka)

全共有ライブラリおよびコアモジュールの統合マルチモジュールAPIドキュメントを生成します：
```bash
./gradlew dokkaGenerate
```
生成されたドキュメントサイトは **`dokkaDocs/index.html`** で確認できます。
