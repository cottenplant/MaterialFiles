# Moby Files

[简体中文](README_zh-CN.md)

Moby Files is a privacy-respecting, fully FOSS distribution of
[Material Files](https://github.com/zhanghai/MaterialFiles). It preserves the
upstream Java NIO2 provider architecture while isolating the fork's application
identity, branding, privacy notice, and eventual distribution path.

## Project status

This repository currently publishes source code only. It does not publish a
Moby Files APK, signed release, or update channel. Do not treat automated build
artifacts, if present, as releases or install them on a device.

The permanent Android application ID is `io.github.cottenplant.mobyfiles`.
Android uses this ID, together with the signing certificate, for install and
update identity. Visible app names do not determine coexistence: other apps can
have similar names, but they must use different application IDs. Changing this
ID after distribution would create a different Android app and break the
intended update lineage.

The user-operated release-signing and key-custody procedure is
[documented](docs/release-signing.md), but no permanent key has been generated
or used by this project.

Development is aimed at a GrapheneOS Pixel 9 Pro running Android 17. Signed
device acceptance has not happened yet, so the repository does not currently
claim a device-qualified release.

## Fork boundary

Compared with the inherited upstream source, the Moby distribution currently:

- has a separate install identity and the invariant name **Moby Files**;
- uses a Moby-specific `deep` palette, cargo launcher icon, themed icon, and
  Android 12+ splash screen;
- excludes Firebase, Google Play Services, Google DataTransport, advertising,
  analytics, and automatic developer crash reporting from its verified runtime
  dependency boundary; and
- exposes the fork source and privacy notice from its About screen.

The Java/Kotlin namespace remains `me.zhanghai.android.files`, and the upstream
NIO2 provider design is intentionally preserved. An `upstream` build flavor is
retained for comparison, but this repository does not redistribute upstream
packages.

## Features and privacy boundary

Moby Files is a Material Design file manager with local and Android document
provider access, archives, optional root or Shizuku access, and FTP, SFTP, SMB,
and WebDAV support. It can also discover SMB hosts on a local network and run an
optional FTP server when the user starts it.

Those capabilities handle sensitive files, credentials, and network traffic.
Before using them, read the [privacy notice](PRIVACY.md), including its current
warnings about saved credentials, Android backup, cleartext networking, and
plain FTP.

For vulnerability reporting, read the [security policy](SECURITY.md). Never put
passwords, private keys, private addresses, confidential file data, unredacted
logs, or undisclosed vulnerability details in a public issue.

## Upstream and attribution

Moby Files is derived from
[Hai Zhang's Material Files project](https://github.com/zhanghai/MaterialFiles).
The architecture, most implementation code, translations, documentation
history, and inherited assets originate upstream. The source history and
copyright notices are retained so that authorship remains traceable.

The files under `fastlane/metadata/` are inherited upstream material. They are
not a current Moby Files store listing, release announcement, or screenshot
set.

Upstream's public app packages, release channel, CI, translation service, and
support channels belong to Material Files, not Moby Files. Installing an
upstream package installs the upstream application with its own package identity
and signing lineage.

## License

Copyright (C) 2018 Hai Zhang and subsequent contributors.

This program is free software: you can redistribute it and/or modify it under
the terms of the GNU General Public License as published by the Free Software
Foundation, either version 3 of the License, or (at your option) any later
version. See [LICENSE](LICENSE) for the complete terms.
