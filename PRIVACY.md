# Moby Files privacy notice

Effective: 2026-09-13

This notice applies to the Moby Files distribution built from this source tree
with the Android application ID `io.github.cottenplant.mobyfiles`. It describes
the tracked source at the effective date. It does not describe the separately
published upstream Material Files app, a modified build, Android itself, another
app, a remote server, or a service that you choose to use with Moby Files.

## Summary

Moby Files is a file manager, so it processes file names, file contents,
metadata, saved locations, and credentials that you ask it to use. The Moby
Files fork does not include advertising, analytics, Firebase, Google Play
Services, automatic crash reporting, or another developer-operated telemetry
service. Its maintainers do not receive your files, credentials, usage data, or
logs automatically.

Network access remains a core, user-directed feature. Moby Files can connect to
servers, discover SMB hosts on a local network, expose files through its FTP
server, and open links in other apps. Data involved in those actions can leave
your device and is subject to the destination, protocol, network, Android
component, and receiving app.

## Data kept on the device

Depending on the features you use, Moby Files can retain the following in its
app data:

- preferences, theme choices, sort and display settings;
- bookmarks, recent locations, and saved local or remote storage definitions;
- server names or addresses, ports, paths, usernames, passwords, and SFTP
  private keys and key passwords;
- settings for the optional FTP server, including its home directory and login
  details.

Saved storage definitions and credentials are serialized into ordinary
app-private Android shared preferences. The serialization includes Base64
encoding but is not separate encryption. Archive passwords are used while the
relevant archive is open and are not part of the saved storage list.

You can remove saved locations in the app. Clearing the app's storage or
uninstalling it removes the active on-device copy, subject to Android backup and
restore behavior.

## File and system access

Moby Files can request broad file-management access in order to browse and
modify shared storage. It can also work through Android's document-provider
interfaces, inspect installed packages for file-management features, hand APKs
to Android's package installer, and optionally use root or Shizuku access. The
data visible to Moby Files depends on the access that you grant and the feature
that you invoke.

When you select a third-party Android document provider or send a file to
another app, Android and that app participate in the operation. Their data
handling is outside the control of the Moby Files maintainers.

## User-directed network activity

Moby Files declares network permissions for these features:

- FTP, SFTP, SMB, and WebDAV storage connections. The remote endpoint receives
  the protocol traffic needed for authentication, directory listing, metadata,
  thumbnails when enabled, and file operations that you request.
- LAN SMB discovery. Opening the LAN SMB discovery screen starts NetBIOS/SMB
  browser queries and probes addresses on the device's private IPv4 subnet.
- The optional built-in FTP server. When you start it, Moby Files listens for
  incoming FTP connections and exposes the configured home directory with the
  configured read/write and authentication settings. Reachability depends on
  the active network and its controls.
- Explicit external links, such as source, license, author, and privacy links.
  These are opened by a browser or another app only after you select them.

The app permits cleartext network traffic and trusts both system and user-added
certificate authorities. Plain FTP is not encrypted. WebDAV may use HTTP or
HTTPS; transport protection for SMB and other connections depends on the
protocol, server, and configuration. Do not use cleartext protocols or
untrusted networks for sensitive data.

The Moby Files maintainers do not operate an intermediary service for these
connections. A server or receiving app can observe data such as your network
address, credentials, requested paths, file metadata, and transferred content.
Review and trust each endpoint before connecting.

## Android backup

The current manifest allows Android backup and does not define exclusions for
saved settings or storage definitions. Depending on the operating system,
device policy, and configured backup transport, Android may back up app data,
potentially including saved remote credentials or SFTP private-key material.
That backup is controlled by Android and the selected transport, not by the
Moby Files maintainers. Disable app-data backup at the operating-system level or
avoid saving sensitive credentials if this does not meet your threat model.

## Logs and diagnostics

The app may write error details and stack traces to Android's system log. Moby
Files does not automatically upload those logs. Logs or screenshots leave your
device only if you, Android, another privileged component, or a diagnostic tool
exports them. Review diagnostic material for file paths, server details, and
other sensitive information before sharing it.

## Third-party code and external services

Moby Files includes FOSS libraries in the application package. The verified
Moby runtime does not include Firebase, Google Play Services, Google
DataTransport, an advertising SDK, or an analytics SDK. A library used to
implement a protocol communicates with the endpoint you direct it to; it is not
a developer telemetry service.

External websites, remote servers, Android document providers, backup
transports, browsers, package installers, and other apps have their own privacy
and security practices. This notice does not govern them.

## Security limitations

No storage or transmission method is completely secure. Protect the device,
choose trusted endpoints, prefer encrypted protocols, verify server identities,
limit broad storage or elevated access, and stop the FTP server when it is not
needed. Device compromise, a malicious provider, an untrusted network, or a
misconfigured server can expose data available to the app.

## Changes and contact

Material changes to this notice are recorded in the repository history. Review
the notice when updating to a new build.

Questions and privacy reports can be opened through the fork's [public issue
tracker](https://github.com/cottenplant/MaterialFiles/issues). Do not publish
passwords, private keys, unredacted logs, private addresses, confidential file
data, or undisclosed security-vulnerability details in an issue.

Moby Files is a fork of Material Files. Upstream authorship, licensing, and
attribution remain documented in [README.md](README.md), [LICENSE](LICENSE), and
the in-app Licenses screen.
