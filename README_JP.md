# MiAdmin
MiAdmin(まいあどみん)はCUIベースのMisskeyインスタンス管理補助ツールです。

MiAdminはJavaVM環境で動作する常駐型の管理補助ツールです。  
JavaVMが動作するあらゆる環境で使用することができます。

### 動作要項
- [x] Javaの動作可能な環境
- [x] Java11以降の実行環境
- [x] MariaDB 5.5以降 若しくは MySQL 5.5以降

## 機能

## 開発
MiAdminはMiModuleを通じて機能を拡張することが可能になっています。  
MiModuleはJavaを用いて誰でも開発することができます。

### 必要なもの
- Java11以降の開発環境
- Maven または Gradle

MiAdminライブラリは以下の手段で簡単に入手できます。
```xml
<repositories>
    <repository>
        <id>pandasoft</id>
        <name>PandaSoft Maven Repository</name>
        <url>https://maven.nafuchoco.page/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>online.pandasoft</groupId>
        <artifactId>MiAdmin</artifactId>
        <version>0.0.3</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```
```groovy
repositories {
  maven {
    url "https://maven.nafuchoco.page/"
  }
}

dependencies {
    compileOnly 'online.pandasoft:MiAdmin:0.0.3'
}
```

JavaDocはこちらから確認することができます。  
https://panda.nafuchoco.page/docs/MiAdmin/

### module.yaml
作成したモジュールをMiModuleとしてロードされるにはリソースに`module.yaml`が含まれている必要があります。
```yaml
name: "SampleModule"
version: 1.0.0
authors: [PandaSoft]
website: "https://pandasoft.online/"
main: "online.pandasoft.samplemodule.Main"
```

それぞれの項目の持つ意味はこちらを参照してください。

|設定名|必須かどうか|説明|設定例|備考|
|:---|:---:|:---|:---|:---|
|**name**|**必須**|**モジュール名**|**name: SampleModule**|**モジュールのデータフォルダは、このname設定により決定されます。./modules/(name) になります。<br>モジュールのjarファイルのファイル名は、name設定と同一にすることが推奨されます。例：’SampleModule.jar’**|
|**version**|**必須**|**モジュールのバージョン**|**version: 1.0.0**|**version設定には、任意の文字列が設定可能です。<br>新機能を追加したり、バグを修正したら、バージョン番号を上げてください。<br>バージョン情報は、「module {モジュール名}」のコマンドを実行したときに表示されます。**|
|description| |モジュールの説明|description: MiAdmin用のサンプルモジュールです。|モジュールが提供している機能の概要を書いてください。<br>説明の情報は、「modules {モジュール名}」のコマンドを実行したときに表示されます。|
|authors| |開発者名|authors: [PandaSoft, NAFU_at]|開発者名を書いてください。<br>開発者名の情報は、「modules {モジュール名}」のコマンドを実行したときに表示されます。|
|website| |開発者のウェブサイト|website: https:/pandasoft.online/|開発者のサイトアドレスを書いてください。<br>ウェブサイトの情報は、「modules {モジュール名}」のコマンドを実行したときに表示されます。|
|**main**|**必須**|**モジュールのメインクラス**|**main: online.pandasoft.samplemodule.Main**|**MiModuleクラスを拡張(extends)した、モジュールのメインクラスを記載してください。<br>パッケージ名を含めた、メインクラスのフルネームで記載してください。<br>例えば、パッケージ名がonline.pandasoft.samplemoduleで、クラス名が Mainなら、 online.pandasoft.samplemodule.Mainと記載してください。**|
|dependency| |依存先のモジュール一覧|dependency: [HogeHoge, fugafuga]|自分のモジュールを起動するために必要となる、他のモジュールの一覧を記載します。<br>他モジュールの `name` 設定名で、記載してください。<br>ここに設定されたモジュールが見つからない場合は、プラグインの起動でエラーになります。|
|loadBefore| |先に起動すべきモジュール一覧|loadBefore: [HogeHoge, fugafuga]|自分のモジュールよりも先に起動すべき、他のモジュールの一覧を記載します。<br>他モジュールの `name` 設定名で、記載してください。<br>ここに記載されているモジュールが、自分のモジュールよりも前にロードされるようになります。<br>複数のモジュールがloadBefore設定で互いに依存していたとしても、モジュールを起動することができます。|
|requiredVersion| |モジュール起動に必要なバージョン|requiredVersion: 2.0.0|モジュールを起動するために必要となる、MiAdminのバージョンを記載します。<br>MajorVersion.MinorVersion.PatchVersion の形式に則って記載してください。<br>プレリリースバージョンは判定しません。|

## License
    Copyright 2020 PandaSoft.Dev Social Networks.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

### Thaad Party License
#### MIT License
- SLF4J api
- Lavalink-Client

#### The 3-Clause BSD License
- Sentry Java For Logback

#### GNU General Public License, version 2
**[(The Universal FOSS Exception, Version 1.0)](https://oss.oracle.com/licenses/universal-foss-exception/)**
- MySQL Connector/J

#### GNU Lesser General Public License 2.1
- Logback
- MariaDB Connector/J

#### Apache Lisence, Version 2.0
- OkHttp3
- Gson
- Jackson Core
- Jackson Databind
- jackson-dataformat-yaml
- Apache Commons Codec
- Apache Commons IO
- Apache Commons Lang
- Apache Commons Collections
- HikariCP
- ASCII Table