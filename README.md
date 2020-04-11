# MiAdmin
MiAdmin is a CUI-based assistant tool for Misskey instance management.

MiAdmin is a resident administrative assistant tool that runs in the JavaVM environment.  
It can be used in any environment where the JavaVM runs.

### Operation requirements
- [x] A Java-enabled environment.
- [x] Java11 or later execution environment.
- [x] MariaDB 5.5 or later or MySQL 5.5 or later.

## Features.

## Development
MiAdmin can be extended through MiModule.  
MiModule can be developed by anyone using Java.

### What you need.
- Java11 or later development environment
- Maven or Gradle

The MiAdmin library can be easily obtained through the following means
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

You can check out the JavaDoc here.  
https://panda.nafuchoco.page/docs/MiAdmin/

### module.yaml
In order to load a module as a MiModule, the resource must contain `module.yaml`.
```yaml
name: "SampleModule"
version: 1.0.0
authors: [PandaSoft]
website: "https://pandasoft.online/"
main: "online.pandasoft.samplemodule.Main"
```
The meaning of each item can be found here.

|Configuration Name|Required or Not|Description|Examples|Remarks|
|:---|:---:|:---|:---|:---|
|**name**|**Required**|**Module name**|**name: SampleModule**|**The data folder of the module is determined by this name setting. /modules/(name). <br>It is recommended that the name of the module's jar file should be the same as the name setting. Example: 'SampleModule.jar'**|
|**version**|**Required**|**Module version**|**version: 1.0.0**|You can set any string in the **version setting. <BR>If you add a new feature or fix a bug, please raise the version number. <br>The version information is displayed when the command "module {module name}" is executed. **|
|description| |module description|description: This is a sample module for MiAdmin. |Please write a summary of the features offered by the module. <BR>The information in the description is displayed when the command "modules {module name}" is executed. |
|authors| |Developer's name|authors: [PandaSoft, NAFU_at]|Please write the developer's name. <br>The developer name information is displayed when the command "modules {module name}" is executed. |
|website| |developer's website|website: https:/pandasoft.online/|Please write the developer's site address. <br>The information on the website is displayed when you run the command "modules {module name}". |
|**main**|**must**|**Main class of module**|**main: online.pandasoft.samplemodule.Main**|**Please describe the main class of the module that extends (extends) the MiModule class. <BR>Include the package name and use the full name of the main class. <br> For example, if the package name is online.pandasoft.samplemodule and the class name is Main, write online.pandasoft.samplemodule.Main. **|
|dependency| |List of modules to depend on|dependency: [HogeHoge, fugafuga]|List of other modules that are required to launch your module. <BR> Put it in the `name` setting name of the other module. <BR>If the module set here is not found, you will get an error when launching the plugin. |
|loadBefore||List of modules to be launched first|loadBefore: [HogeHoge, fugafuga]|This is a list of other modules that should be started before your module. <BR> Put it in the `name` setting name of the other module. <BR>The modules listed here will be loaded before their own modules. <BR>It is possible to launch a module even if multiple modules depend on each other in the loadBefore setting. |
|RequiredVersion||The version required to start the module|requiredVersion: 2.0.0|Enter the version of MiAdmin that is required to launch the module. <MajorVersion.MinorVersion.PatchVersion> <BR>The pre-release version is not judged. |

## Lisence
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

Translated with www.DeepL.com/Translator (free version)