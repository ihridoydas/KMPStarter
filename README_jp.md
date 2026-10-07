# KMPStarter

このリポジトリを使用して、**Kotlin Multiplatform (KMP)** 開発をスタートしましょう。[Hridoy Chandra Das](https://github.com/ihridoydas)によって設計され、Android、iOS、デスクトップ、Web (Wasm)、バックエンド、TV、Wear OS、自動車、XRアプリケーションのための堅牢な出発点を提供します。

## なぜ KMPStarter？

- **デフォルトでマルチプラットフォーム:** Android、iOS、デスクトップ、Web向けの共有ロジックとUI (Compose Multiplatform)。
- **ユニバーサル適応型UI:** スマートフォン、タブレット、折りたたみ端末、デスクトップ、Web、TV、Wear OS、自動車、XRに自動適応する単一のコアUI (`FormFactorMode`対応)。
- **Ktorバックエンド & Swagger UI:** `/docs`でインタラクティブなSwagger UIを提供する本番対応のKtorバックエンドサーバー (`:apps:serverApp`)。
- **スマートセットアップ & フォームファクター切り替え:** プロジェクト名の変更や、ライブラリおよびフォームファクター (`useAndroidTV`, `useWearOS`, `useAndroidAutomotive`, `useAndroidXR`) のトグル設定、不要なソース・依存関係の自動削除。
- **多言語対応:** 英語、日本語、ベンガル語、システムデフォルトの内蔵サポート。

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

## プロジェクト構造

- `:apps:androidApp`: Androidアプリケーションモジュール。
- `:apps:desktopApp`: デスクトップアプリケーションモジュール。
- `:apps:webApp`: Webアプリケーションモジュール。
- `:apps:serverApp`: Swagger UI付きKtorバックエンドサーバー。
- `:core:designsystem`: 共有 Material 3 デザインシステム、適応型UI、フォームファクター画面。
- `:core:common`: 共有ユーティリティと言語マネージャー。
- `:core:model` & `:core:network`: 共有DTO、モデル、Ktorクライアント。
- `:navigation`: 共有ナビゲーション設定。
