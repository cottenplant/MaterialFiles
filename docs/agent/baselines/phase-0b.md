# Phase 0B reproducible baseline

Recorded: 2026-09-13

## Source identities

| Item | Immutable identity |
| --- | --- |
| Public upstream | `https://github.com/zhanghai/MaterialFiles.git` |
| Fetched `upstream/master` | `fc1250038496ebf4d4c139f62d16f0071f2c995a` |
| Pre-change `origin/master` | `fc1250038496ebf4d4c139f62d16f0071f2c995a` |
| Local `master` build | `1fb2084eedcecbbd85bb4b306e5d768f78efff43` |
| Lightweight `v1.7.4` tag and commit | `61a3cffede303d159ee9ad805319b89c21c3aa04` |
| dav4jvm revision | `02fe1a95e6b86e323bec3784d7d2fe2d4081dde6` |

The fetched upstream and origin baselines were identical. The local build commit
adds only the Phase 0A/0B agent files and the dav4jvm full-SHA fix on top of that
source baseline. The dav4jvm SHA was resolved through GitHub's public commit API,
used as the JitPack version, and successfully resolved during the build.

## Toolchain matrix

| Component | Local `master` | `v1.7.4` |
| --- | --- | --- |
| Gradle wrapper | 9.3.1 | 8.7 |
| Gradle distribution SHA-256 | `17f277867f6914d61b1aa02efab1ba7bb439ad652ca485cd8ca6842fccec6e43` | `194717442575a6f96e1c1befa2c30e9a4fc90f701d7aee33eb879b79e7ff05c0` |
| Gradle embedded Kotlin | 2.2.21 | 1.9.22 |
| Android Gradle Plugin | 9.1.0 | 8.3.2 |
| Kotlin Gradle Plugin | 2.3.20 | 2.0.0 |
| Google Services plugin | 4.4.4 | 4.4.2 |
| Crashlytics Gradle plugin | 3.0.6 | 3.0.2 |
| Build JVM | Temurin 21.0.12.1+1 LTS, AArch64 | Same |
| Android platform | API 36, revision 2, extension 17 | API 34, revision 3, extension 7 |
| Build Tools | 37.0.0 | 34.0.0 |
| NDK | 28.1.13356709 | 26.3.11579264 |
| CMake selected for all ABIs | 3.22.1 | 3.22.1 |
| Application min/target SDK | 23 / 34 | 21 / 34 |
| Core library desugaring | 2.1.5 | Not configured |

Both cached Gradle archive hashes matched the corresponding official
`services.gradle.org` checksum endpoint. Neither wrapper currently pins its
distribution with `distributionSha256Sum`; adding that protection is deferred
because it was outside this contract's permitted source changes.

Builds selected CMake 3.22.1 for `arm64-v8a`, `armeabi-v7a`, `x86`, and `x86_64`.
The builds installed only the exact missing packages below in the approved SDK:

- Android SDK Build Tools 37.0.0 and NDK 28.1.13356709 for local `master`.
- Android SDK Platform 34 revision 3, Build Tools 34.0.0, and NDK
  26.3.11579264 for `v1.7.4`.

## Resolved dependencies

The complete selected `debugRuntimeClasspath` component sets are committed as
sorted, deduplicated `group:module:version` snapshots:

- [Local master components](phase-0b-master-debug-runtime-components.txt): 200
  components; SHA-256
  `bb998c26df5f5d7132d9bd9899bae4c5754ee4892780db31b2d9df50acb9c6d3`.
- [v1.7.4 components](phase-0b-v1.7.4-debug-runtime-components.txt): 183
  components; SHA-256
  `f048cde3e848511c9afe97f8fa92bfad7d207c0ac5698b99c02bc8424e3cf148`.

The unabridged Gradle reports remain under the ignored
`.gradle/phase-0b/logs/` directory:

| Report | SHA-256 |
| --- | --- |
| `master-debug-runtime-dependencies.txt` | `5b7dc541e0413e42d7ae8e4a311d8b2aac36c84c79148aa3ca83457411cc5f61` |
| `v1.7.4-debug-runtime-dependencies.txt` | `87f373980533cb6822163dd80e9975ec36f3c7a0739978181b37674688b7b48d` |

These are resolution snapshots, not dependency locks. In particular, the source
still enables the `NONFREE`-marked Firebase Analytics and Crashlytics sections
when built directly. The diagnostic APKs are therefore not FOSS distribution
artifacts; removal of those sections belongs to a later contract.

## Unsigned build artifacts

Both builds used the tracked `docs/agent/unsigned-debug.init.gradle` init script,
which clears the debug signing configuration and fails configuration if signing
is restored. Signing-related environment names were explicitly unset. Neither
successful unsigned build executed `validateSigningDebug`, and the matching SDK
`apksigner verify` command rejected each artifact as unsigned.

| Source | Result | Size | SHA-256 |
| --- | --- | ---: | --- |
| Local `master` | `.gradle/phase-0b/worktrees/master/app/build/outputs/apk/debug/app-debug-unsigned.apk` | 27,140,809 bytes | `e061a51517f3b740b4d38f0d70fd9352e4970e82da63c229d67e3dacbaf3be4c` |
| `v1.7.4` | `.gradle/phase-0b/worktrees/v1.7.4/app/build/outputs/apk/debug/app-debug-unsigned.apk` | 23,807,921 bytes | `69b79d9d5ccef677adee7eea05c4e9cc70a87c3e96f4616c14b0a392287dc55f` |

Both detached worktrees were clean before and after their builds; all generated
outputs are ignored. The local `master` unsigned rebuild was run with Gradle
offline after the approved dependency bootstrap. The pristine `v1.7.4` build
performed its approved public dependency and SDK downloads.

## Warnings and deferred risks

- Both builds warned that the available Android SDK tooling understands SDK XML
  through version 3 while version 4 metadata is present. It did not prevent SDK
  installation or either build.
- Both builds emitted existing deprecation warnings. Java 21 also warned that
  source/target level 8 is obsolete. No warning was changed or suppressed.
- This contract built `assembleDebug` only. Lint, tests, release minification,
  signing, device installation, and protocol smoke tests were not run.
- The wrappers lack declared distribution checksums, and the resolved dependency
  snapshots are not enforced as locks.
- The unsigned APKs retain upstream package identity and non-FOSS conditional
  sections. They must not be installed or distributed.

## Privacy incident and boundaries

Before the unsigned-build amendment, the first local-`master` `assembleDebug`
completed `validateSigningDebug` and `packageDebug`. Android Gradle Plugin likely
read or created its default debug keystore outside the approved paths. No
keystore path, contents, or derived value was inspected, printed, copied, or
hashed. Work stopped immediately.

After the user authorized unsigned builds, Gradle `clean` removed the prior APK;
the incident build log remains at
`.gradle/phase-0b/logs/master-assemble-debug.log` with SHA-256
`55bde2338e3ce83110d01bc1d33647f3632588d8d1064365c2b78f8116abb3b3`.
The replacement unsigned-build logs have SHA-256
`6060b95b03ad8cf1847b4a4b0631031e86d820b6ec5a12526bf61f7ba245d8e4`
for local `master` and
`635a1b929ea2c9b0bd60e5210b1633168bb7058480fac856269828b027ddb954`
for `v1.7.4`.

No LAN, VPN, WireGuard, Samba, SSH, SFTP, WebDAV, FTP, Kubernetes, ADB,
fastboot, or Android device access occurred. Apart from the likely default debug
keystore access described above, no credential or secret value was accessed,
inspected, printed, copied, or hashed.
