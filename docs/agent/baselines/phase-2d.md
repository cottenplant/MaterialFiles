# Phase 2D public repository identity

Recorded: 2026-09-13

## Implementation

The Phase 2D documentation implementation was completed at
`d80b9ed70378ac386a3cd1ec037caa0806761f84`. Its commits are:

- `e951b016` defines the Phase 2D contract; and
- `d80b9ed7` replaces inherited public repository and security guidance with
  fork-specific documentation.

The English and Chinese landing pages now identify Moby Files as a
privacy-respecting FOSS distribution derived from upstream Material Files. They
record the current source-only status, provisional application ID, intended
GrapheneOS device target, uncompleted signed-device acceptance, fork-specific
identity and branding, verified dependency boundary, retained NIO2 architecture,
and material privacy limits.

The inherited upstream CI and release badges, Google Play, F-Droid, and GitHub
download buttons, Transifex invitation, screenshots, and first-person upstream
maintainer narrative were removed from the fork landing pages. Upstream is
still linked and credited for the architecture, implementation, translations,
documentation history, inherited assets, source history, and copyright. The
pages explicitly distinguish upstream packages and services from Moby Files
and identify inherited Fastlane metadata as non-Moby material.

`SECURITY.md` no longer sends fork vulnerability reports to the upstream
author's inherited email address. It prefers GitHub private vulnerability
reporting when the repository exposes that facility. Because no private fork
channel is established in tracked evidence, it otherwise instructs reporters
to make only a no-details public request for private contact and lists sensitive
content that must not be posted. Issues reproduced in an unmodified upstream
build are routed to the labeled upstream policy.

## Claim audit

The public claims were checked against tracked repository evidence:

- `app/build.gradle` defines the `moby` flavor with application ID
  `io.github.cottenplant.mobyfiles` and retains the
  `me.zhanghai.android.files` namespace;
- Moby resources provide the invariant `Moby Files` name, `deep` palette,
  cargo launcher identity, themed icon, and Android 12+ splash resources;
- Phase 2C verifies the absence of Firebase, Google Play Services, Google
  DataTransport, advertising, analytics, and automatic developer crash
  reporting from the Moby runtime boundary;
- the tracked application and privacy notice describe local and Android
  document-provider access, archives, root and Shizuku access, FTP, SFTP, SMB,
  WebDAV, LAN SMB discovery, the optional FTP server, saved credentials,
  Android backup, and cleartext-network limits; and
- the user handoff and prior phase reports establish that no signed Moby
  distribution or completed device acceptance exists. No remote service was
  contacted to extend those claims.

The READMEs deliberately call the application ID provisional. A signed release,
certificate, update lineage, private security channel, and device-qualified
compatibility remain unclaimed.

## Offline acceptance

Static checks at the final Phase 2D tree confirmed:

- the English and Chinese READMEs contain the Moby name, provisional application
  ID, source-only status, privacy link, security link, upstream attribution, Hai
  Zhang copyright, and GPL-3.0-or-later notice;
- the inherited upstream badge, download, release, CI, Transifex, first-person
  maintainer, and security-email markers are absent from the three edited public
  documents;
- every repository-relative Markdown link in the edited public documents points
  to an existing tracked file;
- app source and resources, manifests, Gradle and signing configuration,
  dependency locks, verification metadata, GitHub workflows, Fastlane metadata,
  versions, and generated assets are byte-for-byte unchanged from `ebbc2954`;
- the Phase 2D path set is limited to the two READMEs, `SECURITY.md`, this
  contract, and this report;
- `git diff --check` passes; and
- all Phase 2D commit messages have empty bodies and no trailers.

No Gradle, Android SDK, APK, signing, network, or device check was warranted for
this documentation-only phase.

## Deferred work and boundaries

- The user must confirm `io.github.cottenplant.mobyfiles` before the first
  signed distribution.
- A private Moby vulnerability-reporting facility remains to be established and
  verified by the user. Until then, the public fallback intentionally accepts no
  vulnerability details.
- The inherited GitHub workflow is not flavor-aware and its actions are not
  pinned to immutable revisions. CI redesign belongs in a separate contract
  before relying on or publishing its artifacts.
- Reproducible unsigned release assembly, permanent signing and key custody,
  certificate publication, update channels, store metadata, screenshots,
  publication, and signed-device acceptance remain separate contracts.
- Credential-at-rest encryption, Android backup exclusions, cleartext-network
  hardening, and plain FTP compatibility remain behavioral security projects.

No network, external path, Android SDK, Gradle cache, device, personal
infrastructure, authenticated service, signing material, ignored local
configuration, secret-bearing environment value, or private security content
was accessed.

Privacy incidents or near misses: none.
