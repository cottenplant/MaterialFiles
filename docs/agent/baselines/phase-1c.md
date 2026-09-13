# Phase 1C dependency integrity

Recorded: 2026-09-13

## Source identity and controls

The dependency-integrity implementation was validated at
`51a78a3aecc2e1b181e00acbb9dd8fcc99869bd9` in the clean detached Phase 1C
worktree. Its implementation commits are:

- `a521b18cf8d6af50f22be3418e6448205307a01a` activates locking for all
  project configurations and the root buildscript classpath.
- `31f01170e05f21c54ad7f88e8b7012ce1174c6c8` adds the initial Gradle-generated
  project locks and verification metadata.
- `7891b1a0d135100ff36b8d21a6894f9a282fa29a` adds checksums for artifacts
  resolved only by the build and lint tasks.
- `51a78a3aecc2e1b181e00acbb9dd8fcc99869bd9` requires `LockMode.STRICT` and
  records the task-created `androidApis` configuration as empty.

No dependency declaration, selected version, repository, plugin version,
Gradle version, wrapper file, Android source, or application behavior changed.

## Lock coverage

Gradle generated two lock files:

| Lock file | Locked module-version entries | Configuration coverage | SHA-256 |
| --- | ---: | --- | --- |
| `buildscript-gradle.lockfile` | 111 | Root `classpath` | `c9ad0cf657d7701a1a7f0b09b3db143598c2cb0580670ca576fd069a86c30944` |
| `app/gradle.lockfile` | 170 | 35 app configurations | `d93db1f52ce4beedb63f1fa3744b1448e9d64020c8735255d23cbda32c135c9d` |

The app lock covers debug and release compile/runtime classpaths, Android-test
and unit-test classpaths, lint classpaths, core-library desugaring, Kotlin
compiler and plugin classpaths, and AGP support configurations. The root
project has no external project dependency configuration, so Gradle did not
create a root `gradle.lockfile`; its buildscript dependencies are locked in the
separate buildscript lock file.

An initial dependency-report pass did not encounter AGP's task-created
`androidApis` configuration. A strict-mode probe identified the missing state,
and running the real acceptance task set with `--write-locks` added only
`androidApis` to the generated `empty=` entry. It added no module. A subsequent
strict run without `--write-locks` passed.

The final offline `dependencies app:dependencies --write-locks` regeneration
was byte-for-byte idempotent and left the worktree clean. Its retained log is
`.gradle/phase-1c/logs/strict-lock-idempotence-final.log`, SHA-256
`2dc29bbcf4fd6772abe9192290bfee2793b2f3ce864e47fc085437721939544d`.

## Verification coverage and provenance

`gradle/verification-metadata.xml` has SHA-256
`c3161605d1c247e648085521eeb0c6c2c4afd89f75654977b5337f8dc370ff6f`
and contains:

- 373 components;
- 638 artifacts or metadata files;
- exactly 638 SHA-256 entries.

It sets `verify-metadata` to `true` and `verify-signatures` to `false`. It has no
MD5, SHA-1, or SHA-512 entries, trusted-artifact bypasses, ignored components,
or entries for `com.google.firebase`, `com.google.android.gms`, or
`com.google.android.datatransport`.

The initial offline `--write-verification-metadata sha256 help` pass resolved
all configurations Gradle could discover during configuration and generated
checksums for 362 components and 616 files. The first strict acceptance run
then stopped on AGP's task-created macOS `aapt2` JAR and POM, as expected for a
detached configuration. Re-running the exact build, test, and lint task set in
offline metadata-writing mode added 11 components and 22 file checksums. The
next strict run accepted all resolved files without changing metadata.

Twenty of those 22 task-specific checksums exactly match the Google Maven and
Maven Central payloads already recorded in the Phase 1A lint download manifest.
The remaining two are the retained Phase 0B macOS `aapt2` JAR and POM; local
SHA-256 computation matched their new verification entries:

| File | SHA-256 |
| --- | --- |
| `aapt2-9.1.0-14792394-osx.jar` | `b58cb80ac24aa343d02316fa50a662e2710b2f9ea14fc7da6d08d9cd801cafa3` |
| `aapt2-9.1.0-14792394.pom` | `038bb533d0332d08cdf556892a4094a922767f0e5610d4dec1c08ffee5af06ab` |

Overall, 21 of the 23 checksums in the Phase 1A lint manifest are present in
the verification file. Gradle requested only the `.module` metadata for
`graphics-shapes-desktop:1.0.1` in this graph; its previously downloaded JAR
and POM were not requested and therefore were not added. Strict verification
will fail and require review if a future task begins consuming either file.

These checksums pin the repository-local artifacts fetched over public HTTPS
and already exercised in earlier phases. They detect future substitution but
do not independently prove the original artifacts' source or benignness.
Signature verification, source reproduction, and the broad dependency-license
audit remain deferred.

## Strict offline acceptance

With signing-related environment names unset, the repository-local Phase 0B
Gradle user home, the approved existing Android SDK, strict dependency
verification, strict lock mode, and the unsigned-build init script, this task
set passed offline with all 64 tasks executed:

```text
assembleDebug testDebugUnitTest lintDebug
```

`testDebugUnitTest` reported `NO-SOURCE`. `lintDebug` completed with 0 errors
and 872 warnings. Compared with Phase 1A's 873 warnings, the only issue-count
difference is the absence of the single `AndroidGradlePluginVersion` advisory;
the source-diagnostic issue counts are unchanged. Existing SDK XML,
deprecation, Kotlin, and Java source/target warnings remain unresolved.

The final acceptance log is retained at
`.gradle/phase-1c/logs/strict-offline-acceptance-complete.log`, SHA-256
`4abd8bf507f955a43f48bf36093339e5458942788198a449772f7b008c0fbdb5`.
It contains no `validateSigning` task. The resulting diagnostic APK is
22,829,779 bytes with SHA-256
`b7aa24cb61d3c47730c1991fa5f3931b426110da47f1e7891712c87d9e9bac51`.
Build Tools 37.0.0 `apksigner verify --verbose` rejected it with `DOES NOT
VERIFY`, confirming it is unsigned.

The final lock and verification files and strict dependency reports contain no
Firebase, Google Play services, or Google data transport group. No device or
network-service test is required, and the APK must not be installed while it
retains upstream identity.

## Deferred work and boundaries

- Dependency signature/source reproduction and the broad license audit remain
  separate supply-chain work.
- The existing 872 non-fatal lint warnings are not changed or suppressed.
- Application identity, Mobyverse branding, fork privacy documentation,
  permanent signing, update channels, CI, and distribution remain separate
  contracts.

No network, dependency download, SDK installation or modification, Android
device, personal infrastructure, authenticated service, signing material, or
ignored local configuration was accessed. External access was limited to the
existing host JDK and read-only Android SDK path explicitly approved at each
Gradle or `apksigner` boundary.

Privacy incidents or near misses: none.
