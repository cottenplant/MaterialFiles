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

All three Phase 1A detached worktrees remained clean apart from ignored build
products and worktree-local verification configuration.

## Lint closure and existing warnings

The follow-up lint-closure contract downloaded only the POM, Gradle module
metadata, and JAR for
`androidx.graphics:graphics-shapes-desktop:1.0.1` from Google's public Maven
endpoint at
`https://dl.google.com/dl/android/maven2/androidx/graphics/graphics-shapes-desktop/1.0.1/`.
The downloaded files were staged in the ignored repository-local verification
area with this provenance:

| File | Size | SHA-256 |
| --- | ---: | --- |
| `graphics-shapes-desktop-1.0.1.pom` | 2,194 bytes | `f1c2b64dca4c7306275f442176712740eb021b78af0f8a316c577be63debc6e7` |
| `graphics-shapes-desktop-1.0.1.module` | 3,431 bytes | `d2523d73abbe2f1e787106ad4e1f7acee30a7c62370f85bb4d782700f0fd0701` |
| `graphics-shapes-desktop-1.0.1.jar` | 88,885 bytes | `f305c1e1ac3eb7d20e54550f48e07b56a96951473dd3fe7bb8913ed0cd3a5e91` |

The JAR size and SHA-256 match Google's Gradle module metadata, and `unzip -t`
reported no archive errors.

After the first offline run exposed the next cache miss, the user also
authorized `com.android.tools.lint:lint-gradle:32.1.0`. Google Maven publishes
a POM and JAR, but no Gradle `.module` file, at
`https://dl.google.com/dl/android/maven2/com/android/tools/lint/lint-gradle/32.1.0/`.
The downloaded files have this locally computed provenance:

| File | Size | SHA-256 |
| --- | ---: | --- |
| `lint-gradle-32.1.0.pom` | 2,276 bytes | `8086683af428d7e9f8a2ba80dbbbbf33eb1019cd036ee8fb9b97cdab39d51179` |
| `lint-gradle-32.1.0.jar` | 66,373 bytes | `0abdb1b1cd9a0bb7b9488500646a016f2c053bb31ba427645d6bf563feb93821` |

`unzip -t` reported no archive errors for this JAR. Across both initial
exact-coordinate authorizations, no coordinate other than the two named by the
user was downloaded.

The first offline `lintDebug` run at docs-only descendant commit
`fe3281e91bf522da184154cedf4190f74b25c068`, whose app and build source matches
the final validation commit, then passed the original dependency resolution
point, compiled the debug sources, generated the lint models, and reached
`:app:lintAnalyzeDebug`. It stopped there because the next lint-only tool
artifact, `com.android.tools.lint:lint-gradle:32.1.0`, was not cached for offline
use.

After the second authorized download, another offline run resolved
`lint-gradle` and identified five uncached runtime dependencies declared by its
POM:

- `com.android.tools.external.com-intellij:intellij-core:32.1.0`;
- `com.android.tools.external.com-intellij:kotlin-compiler:32.1.0`;
- `com.android.tools.external.org-jetbrains:uast:32.1.0`;
- `com.android.tools.lint:lint:32.1.0`; and
- `org.codehaus.groovy:groovy:3.0.22`.

The user then authorized one Gradle network pass for all remaining lint
blockers. It downloaded those five artifacts plus the newly exposed
`lint-api:32.1.0`, `lint-checks:32.1.0`, `play-sdk-proto:32.1.0`, and
`httpclient:4.5.6` transitives. Android and AndroidX payloads came from Google
Maven; Groovy and Apache HttpClient came from Maven Central. The other two
direct runtime dependencies declared by `lint-gradle` were already cached.

[The lint artifact manifest](phase-1a-lint-artifacts.sha256) records SHA-256
checksums and repository-relative provenance for all 23 unique downloaded
payloads. Four files staged during the two exact-coordinate downloads were
fetched again by Gradle with identical hashes. No authenticated repository or
other network service was accessed. These are host-side lint tool artifacts;
they do not alter the app dependency declarations, runtime snapshot, or APK.

The online `lintDebug` run succeeded with 36 actionable tasks: 5 executed and
31 up-to-date. It reported 0 errors and 873 warnings. A final acceptance run
then used `--offline --rerun-tasks`; all 36 tasks executed and `lintDebug`
succeeded again in one minute with the same 0-error, 873-warning result. The
acceptance run used only the repository-local Gradle home, ignored local Maven
staging area, and approved Android SDK. Signing environment names were unset
for every Gradle invocation. The Phase 1A lint verification gap is closed.

The existing Android SDK XML-version warning, Gradle deprecations, Kotlin source
warnings, and Java 8 source/target deprecation warnings remain. They were not
changed or suppressed.

## Deferred work

- Do not install or distribute this diagnostic APK: it still uses upstream name,
  package identity, iconography, and version metadata.
- App identity, Mobyverse branding, signing, update channels, fork privacy
  documentation, and distribution remain separate later contracts.
- Remediating the 873 non-fatal lint warnings is outside Phase 1A and remains
  deferred.
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
device access occurred. The original Phase 1A validation used no network. The
follow-up made only the authorized unauthenticated HTTPS downloads from Google
Maven and Maven Central recorded above; the final acceptance run was offline.
The only approved host path read was the existing Android SDK path named above.
No additional privacy incident or near miss occurred during lint closure.
