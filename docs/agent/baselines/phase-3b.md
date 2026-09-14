# Phase 3B unsigned release reproducibility

Recorded: 2026-09-14

## Implementation

Phase 3B established an explicit agent-operated path for assembling the exact
`mobyRelease` variant without signing. The implementation commits are:

- `162110d7` defines the Phase 3B contract;
- `102d4b7b` adds the initial unsigned-release safeguard;
- `5e561eff` amends the contract after Android variant finalization prevented
  the initial safeguard from affecting packaging;
- `d9562601` adds pre-evaluation conditional signing isolation;
- `2a3eeecf` corrects task-graph closure scope;
- `c5052267` distinguishes release symbol stripping from debug variants;
- `810ce6b3` authorizes the localized native reproducibility correction; and
- `df977a72` normalizes native debug source prefixes so linker build IDs remain
  stable across checkout locations.

`docs/agent/unsigned-release.init.gradle` sets a private Boolean marker before
project evaluation. `signing.gradle` does not create a release signing
configuration when that exact marker is present, and `app/build.gradle` does not
assign one to the release build type. The task-graph guard then requires Moby
release work, rejects upstream and Moby debug variants, and fails if release
signing is present.

The marker is absent during normal builds. In that path, the inherited signing
configuration body and `release` build-type assignment execute as before Phase
3B. Phase 3B does not provide, weaken, inspect, or test a permanent signing key.

## Build method and toolchain

The final comparison used two clean detached worktrees at
`df977a723f041ad0abe70d8b2d1a0b73e399e341`. Both used a Phase 3B-local
copy-on-write clone of the retained repository-local dependency cache. Every
build task executed independently in each worktree; project outputs were not
shared.

The sanitized command shape was:

```text
unset STORE_FILE STORE_PASSWORD KEY_ALIAS KEY_PASSWORD
ANDROID_HOME=<approved-sdk> ANDROID_SDK_ROOT=<approved-sdk> \
GRADLE_USER_HOME=<repository>/.gradle/phase-3b/gradle-user-home \
./gradlew --offline --no-daemon --dependency-verification strict \
  --init-script docs/agent/unsigned-release.init.gradle \
  :app:assembleMobyRelease --console=plain
```

The relevant toolchain was:

| Component | Value |
| --- | --- |
| Gradle wrapper | 9.3.1, checksum-pinned |
| Android Gradle Plugin | 9.1.0 |
| Kotlin Gradle Plugin | 2.3.20 |
| Build JVM | Eclipse Temurin 21.0.12.1+1 LTS, AArch64 |
| Compile SDK / Build Tools | 36 / 37.0.0 |
| NDK | 28.1.13356709 |
| CMake | 3.22.1 |
| Native release configuration | `RelWithDebInfo`, four retained ABIs |

Both final builds completed 65 executed tasks in 1 minute 21 seconds. Their
logs are retained under ignored `.gradle/phase-3b/logs/` paths:

| Build | Log SHA-256 |
| --- | --- |
| `build-i` | `c2b5d74c3ec167a684717d50da2e562b5563edfde0f0b5e4950107605fd80171` |
| `build-j` | `8d817b74d43ff8f8552e6101d2ab0216cf83c3945384af9ca2976ed9cbfa2d9f` |

Neither log contains a signing-validation or signing task. Both contain
`packageMobyRelease`, `assembleMobyRelease`, R8 minification, resource shrinking,
and release lint-vital tasks. The normal
`writeMobyReleaseSigningConfigVersions` metadata task ran; it neither validated
nor applied a signing key.

## Reproducibility diagnosis and correction

Before the native correction, two successful clean builds at `c5052267` were
unsigned but not byte-identical:

| Build | Size | SHA-256 |
| --- | ---: | --- |
| `build-g` | 9,925,056 bytes | `3b7905ed446b23a2f2d143f13dbedaa02c844aadaf273e5aea458f7014876468` |
| `build-h` | 9,925,052 bytes | `fc535b70e86b78978790d46fc644ebb9ecc6fb22116380451977e58acb001e9c` |

Decompressing and comparing every APK entry localized all content differences
to the fork-built `libhiddenapi.so` and `libsyscall.so` for each of the four
ABIs. Each library had equal size and differed only in its 20-byte GNU build-ID
note; one pair had 19 unequal bytes because one byte happened to match. NDK
`llvm-readelf` confirmed the differing notes, while inspection of the unstripped
libraries showed checkout-specific absolute paths in native debug data.

`app/CMakeLists.txt` now supplies:

```text
-fdebug-prefix-map=${CMAKE_SOURCE_DIR}=.
```

This normalizes the checkout-dependent debug source prefix before linking. It
does not remove GNU build IDs or change application runtime code. After the
correction, independently built `arm64-v8a` native libraries retained matching
build IDs; the complete APK comparison below establishes the same result for
every packaged entry and ABI.

## Final artifact verification

The final outputs were:

| Build | Size | SHA-256 |
| --- | ---: | --- |
| `build-i` | 9,925,052 bytes | `170ccdd4754604304946c0a56fea33f2934a5f1dd0a7cb606bd229394ebdfd72` |
| `build-j` | 9,925,052 bytes | `170ccdd4754604304946c0a56fea33f2934a5f1dd0a7cb606bd229394ebdfd72` |

`cmp` returned zero: the APKs are byte-for-byte identical. The installed Build
Tools 37.0.0 `apksigner verify` command rejected each artifact before its digest
was computed:

```text
DOES NOT VERIFY
ERROR: Missing META-INF/MANIFEST.MF
```

The identical APK's `aapt dump badging` output reports package
`io.github.cottenplant.mobyfiles`, version code 39, version name 1.7.4, label
`Moby Files`, compile SDK 36, minimum SDK 23, target SDK 34, and the four expected
ABIs. It contains no debug application marker. Release task names, R8
minification, resource shrinking, lint-vital execution, and the release output
path independently confirm release configuration. Because the APKs are
byte-identical, this inspection applies to both manifests.

The init-script guard also rejected dry-run requests for
`assembleUpstreamRelease` and `assembleMobyDebug` before task execution. The two
final worktrees remained clean except for ignored Gradle and build outputs and
still resolved to the same full source commit after acceptance.

## Failed diagnostic iterations

The empirical work identified three implementation issues before the final
comparison:

- At `102d4b7b`, clearing the release build type after evaluation was too late
  to alter Android Gradle Plugin's finalized variant. Packaging stopped because
  the captured signing configuration had no `storeFile`. No signing-validation
  task ran and no key was read.
- At `d9562601`, a closure-scope error stopped configuration before any task.
- At `2a3eeecf`, the guard mistook the legitimate
  `stripMobyReleaseDebugSymbols` task for a debug variant and stopped before any
  task.

These failures produced no installable, signed, uploaded, or published artifact.
They were retained only as ignored local diagnostic evidence.

## Static acceptance and scope

Final static checks confirmed:

- Phase 3B changes are limited to its contract and report, the unsigned-release
  init script, conditional signing wiring in `app/build.gradle` and
  `signing.gradle`, and the native debug-prefix correction in
  `app/CMakeLists.txt`;
- application source, resources, manifests, identity values, versions,
  dependencies, locks, verification metadata, wrapper, CI, public
  documentation, and Fastlane metadata are unchanged from Phase 3A;
- the normal signing statements are unchanged inside the marker-absent branch;
- `git diff --check` passes; and
- every Phase 3B commit has an empty body and no trailer.

## Deferred work and boundaries

- The reproducibility result covers two clean builds on the same host with the
  same retained verified dependency cache and pinned toolchain. A cross-host or
  independent clean-room reproduction remains unproven.
- The APKs are unsigned diagnostics and must not be installed or distributed.
- The provisional `io.github.cottenplant.mobyfiles` application ID still needs
  user confirmation before permanent signing.
- Permanent signing and key custody, certificate publication, update channels,
  provenance attestations, store metadata, screenshots, publication, and signed
  GrapheneOS device acceptance remain separate contracts.
- Credential-at-rest encryption, Android backup exclusions, cleartext-network
  hardening, and plain FTP compatibility remain behavioral security projects.
- Existing SDK XML-version, Gradle deprecation, Java 8 source/target, and Kotlin
  compiler warnings remain unchanged and out of scope.

No network, Android device, emulator, personal infrastructure, authenticated
service, signing material, ignored main-worktree configuration, or
secret-bearing value was accessed. SDK access was limited to the explicitly
approved `/Users/samco/Library/Android/sdk` path. Signing-related environment
names were unset without inspecting their prior values.

Privacy incidents: none.

Filesystem-boundary near miss: the host `strings` wrapper unexpectedly attempted
to create an `xcrun_db-*` cache file under `/tmp`. The sandbox denied every
attempt with `Operation not permitted`; no file was created and no sensitive
data was read or exposed. Subsequent ELF inspection used NDK `llvm-readelf` from
the approved SDK path.
