# Phase 1A FOSS build boundary

Recorded: 2026-09-13

## Source identity and scope

The final clean validation commit is
`f4fc5a8ae59531afb2f60161a20b6f5e752bdb95` on local `master`.
Phase 1A removed only the upstream Firebase Analytics and Crashlytics path and
added a fail-closed dependency-group guard. The application ID, Java/Kotlin
namespace, branding, permissions, NIO2 providers, and protocol behavior remain
unchanged.

The source change removed:

- Google Services and Crashlytics Gradle plugins and configuration;
- Firebase Analytics, Crashlytics NDK, and Firebase BOM dependencies;
- the Crashlytics application initializer and manifest metadata;
- the upstream Firebase service configuration; and
- the nonfree privacy-policy link wiring from the About screen.

The build now rejects dependencies in `com.google.firebase`,
`com.google.android.gms`, and `com.google.android.datatransport` from every
app configuration. An ignored test init script created a resolvable project
configuration containing a cached Firebase component. Resolution failed with
the expected `Non-FOSS dependency is forbidden` guard message, proving that the
guard fails before repository resolution.

## Resolved runtime

[The Phase 1A snapshot](phase-1a-foss-debug-runtime-components.txt) contains 148
sorted, deduplicated `debugRuntimeClasspath` components. Its SHA-256 is
`f4c8edd626c2f7d82c28ddbf5c1f0159f1676c3a45c5e233f8881e3b0e16df45`.

Compared by exact component identity with the 200-component Phase 0B snapshot,
Phase 1A removes 52 components and adds none. The removed set includes all 16
Firebase components, all 11 Google Play Services components, all 3 Google data
transport components, and 22 dependencies that were reachable only through
that path. The final graph contains zero components from any guarded group.

The component list was regenerated at the final validation commit and matched
the tracked snapshot exactly after excluding its three provenance comments.

## Offline build and artifact inspection

The final build used the Phase 0B repository-local Gradle user home in offline
mode and the existing Android SDK at `/Users/samco/Library/Android/sdk`. Signing
environment names were explicitly unset, and
`docs/agent/unsigned-debug.init.gradle` disabled debug signing.

`clean assembleDebug testDebugUnitTest` succeeded. The unit-test task reported
`NO-SOURCE`. No `validateSigningDebug` task ran.

The diagnostic artifact is:

| Source | Result | Size | SHA-256 |
| --- | --- | ---: | --- |
| `f4fc5a8ae59531afb2f60161a20b6f5e752bdb95` | `.gradle/phase-1a/worktree-final/app/build/outputs/apk/debug/app-debug-unsigned.apk` | 22,829,787 bytes | `d6d6cb7aea1fed1a1a6aa144fb48aaa452ad56b59af908a0f9144c24b031d103` |

Android Build Tools 37.0.0 `apksigner verify` rejected the APK with a missing
signature-manifest error, confirming that it is unsigned. Inspection of APK
entry names, uncompressed DEX streams, resources, and the merged debug manifest
found none of the Firebase, Crashlytics, Google measurement, Google service
resource, or Google data transport identifiers used by the verification check.

Both Phase 1A detached worktrees remained clean apart from ignored build
products.

## Incomplete check and existing warnings

`lintDebug` could not run offline because
`androidx.graphics:graphics-shapes-desktop:1.0.1`, a lint-only transitive
artifact of Material Components, was not present in the repository-local cache.
The contract required a cache miss to stop rather than authorize a download, so
no network access occurred. This is a verification gap, not a compile or runtime
dependency failure.

The existing Android SDK XML-version warning, Gradle deprecations, Kotlin source
warnings, and Java 8 source/target deprecation warnings remain. They were not
changed or suppressed.

## Deferred work

- Do not install or distribute this diagnostic APK: it still uses upstream name,
  package identity, iconography, and version metadata.
- App identity, Mobyverse branding, signing, update channels, fork privacy
  documentation, and distribution remain separate later contracts.
- Completing lint requires a later contract that permits the exact public Maven
  artifact download or provides it with recorded provenance.
- Wrapper checksum pinning, dependency locking, and a broad dependency-license
  audit remain deferred.

## Privacy and boundary report

During initial review, a broad `git diff` printed the deleted tracked upstream
Firebase service configuration, including an API credential-like value, into
the local tool transcript. Work stopped immediately. The user confirmed that
the value was not their credential and authorized resumption. The value was not
repeated, copied, hashed, or sent to another service.

No signing material or signing-related value was inspected. No LAN, VPN,
WireGuard, SSH, Samba, SFTP, WebDAV, FTP, Kubernetes, ADB, fastboot, or Android
device access occurred. No network access or dependency download occurred in
Phase 1A. The only approved host path read was the existing Android SDK path
named above.
