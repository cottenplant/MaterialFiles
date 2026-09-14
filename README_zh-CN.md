# Moby Files

[English](README.md)

Moby Files 是 [Material Files](https://github.com/zhanghai/MaterialFiles)
的一个尊重隐私、完全自由开源的软件发行版。它保留了上游的 Java NIO2
文件系统提供程序架构，同时将本分支的应用身份、品牌、隐私声明和未来的分发渠道
与上游隔离。

## 项目状态

本仓库目前只发布源代码，不提供 Moby Files APK、签名版本或更新渠道。即使存在
自动构建产物，也不要将其视为正式版本或安装到设备上。

固定的 Android 应用 ID 是 `io.github.cottenplant.mobyfiles`。Android 使用此 ID
和签名证书共同确定应用的安装及更新身份。显示名称不决定应用能否共存：其他应用
可以使用相似的名称，但必须使用不同的应用 ID。分发后更改此 ID 会产生另一个
Android 应用，并破坏预期的更新路径。

Moby Files 的永久签名密钥已通过[发布签名流程](docs/release-signing.md)中由用户
私下执行的密钥生成仪式和恢复测试。此状态仅依据不含敏感信息的人工证明；仓库
验证过程未接触密钥或证书身份。该密钥尚未用于签署 Moby 正式版本。

开发目标是运行 Android 17 的 GrapheneOS Pixel 9 Pro。目前尚未完成签名版本的
真机验收，因此本仓库暂不声明已发布通过设备验证的版本。

## 分支边界

与继承的上游源代码相比，Moby 发行版目前：

- 具有独立的安装身份，固定显示名称为 **Moby Files**；
- 使用 Moby 专属的 `deep` 配色、货物图形启动器图标、主题图标和 Android 12+
  启动画面；
- 经验证的运行时依赖边界不包含 Firebase、Google Play Services、Google
  DataTransport、广告、分析或自动向开发者发送崩溃报告的功能；
- 在“关于”页面中提供本分支源代码和隐私声明的链接。

Java/Kotlin 命名空间仍为 `me.zhanghai.android.files`，并有意保留上游的 NIO2
文件系统提供程序设计。仓库保留了用于比较的 `upstream` 构建变体，但不重新分发
上游安装包。

## 功能和隐私边界

Moby Files 是一款 Material Design 文件管理器，支持本地文件、Android 文档提供
程序、压缩文件、可选的 root 或 Shizuku 访问，以及 FTP、SFTP、SMB 和 WebDAV。
它也可以在局域网中发现 SMB 主机，并在用户主动启动后运行可选的 FTP 服务器。

这些功能会处理敏感文件、凭据和网络流量。使用前请阅读[隐私声明](PRIVACY.md)，
特别是其中有关凭据保存、Android 备份、明文网络和普通 FTP 的现有警告。

报告漏洞前请阅读[安全策略](SECURITY.md)。切勿在公开议题中发布密码、私钥、
私有地址、机密文件数据、未经删减的日志或尚未披露的漏洞细节。

## 上游项目和署名

Moby Files 衍生自 [Hai Zhang 的 Material Files
项目](https://github.com/zhanghai/MaterialFiles)。架构、大部分实现代码、翻译、
文档历史和继承的素材均来自上游。仓库保留了源代码历史和版权声明，以确保作者
贡献可追溯。

`fastlane/metadata/` 中的文件是继承的上游资料，并非当前的 Moby Files
应用商店页面、版本公告或截图集。

上游的公开安装包、发布渠道、CI、翻译服务和支持渠道属于 Material Files，而不
属于 Moby Files。安装上游安装包会安装具有其自身软件包身份和签名路径的上游
应用。

## 许可证

Copyright (C) 2018 Hai Zhang and subsequent contributors.

本程序是自由软件：您可以根据自由软件基金会发布的 GNU 通用公共许可证第 3 版
或任何更高版本的条款，重新发布和/或修改本程序。完整条款请参阅
[LICENSE](LICENSE)。
