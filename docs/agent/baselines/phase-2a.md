# Phase 2A fork identity

Recorded: 2026-09-13

## Source identity and design

The fork-identity implementation was validated at
`eb83f4bb2b5acf6e6b8d27f3c41fbb47b4d4c0ab` in the clean detached Phase 2A
worktree. Its commits are:

- `a670618c` defines the Phase 2A contract;
- `29f0b219` adds the isolated Moby Files application identity; and
- `eb83f4bb` records generated lock coverage for both distribution flavors.

The app now has one `distribution` flavor dimension:

| Flavor | Application ID | User-facing name | Purpose |
| --- | --- | --- | --- |
| `upstream` | `me.zhanghai.android.files` | Material Files | Preserve the upstream identity path for review and regression checks. |
| `moby` | `io.github.cottenplant.mobyfiles` | Moby Files | Personal FOSS distribution identity. |

The Java/Kotlin namespace, source package declarations, component class names,
and NIO2 provider design remain `me.zhanghai.android.files`. No namespace or
source tree was renamed.

The public Mobyverse README establishes `deep` as a palette in the shared design
system, but it defines no Android reverse-domain namespace. The public fork
remote establishes `cottenplant` as the durable GitHub identity, so this phase
uses `io.github.cottenplant.mobyfiles`. This ID may still be changed before a
signed build is distributed.

## Package-derived identity

Package-coupled values now resolve per variant instead of capturing or
hard-coding the upstream application ID:

- provider authorities are `${applicationId}.app_provider` and
  `${applicationId}.file_provider` in the manifest;
- AndroidX contributes `${applicationId}.androidx-startup` and
  `${applicationId}.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION`;
- internal open-file and downloads actions use generated `BuildConfig` values
  derived from `APPLICATION_ID`;
- manifest intent actions use the built-in `${applicationId}` placeholder; and
- static shortcut target packages and actions use flavor-overlaid, explicitly
  non-translatable identity resources.

The distribution app-name resource aliases the existing translated upstream
name for the `upstream` flavor. The `moby` overlay replaces that alias with the
non-translatable brand name `Moby Files`, preventing locale-specific upstream
names from reappearing in the launcher or About screen.

Build Tools 37.0.0 `aapt2` inspection of the Moby diagnostic APK reported:

- package `io.github.cottenplant.mobyfiles`;
- application label `Moby Files` for the default configuration and every
  packaged locale;
- providers `io.github.cottenplant.mobyfiles.app_provider` and
  `io.github.cottenplant.mobyfiles.file_provider`;
- AndroidX startup authority
  `io.github.cottenplant.mobyfiles.androidx-startup`;
- custom permission
  `io.github.cottenplant.mobyfiles.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION`;
- Moby-derived open-file, downloads, and FTP-management intent actions; and
- compiled static-shortcut target/action resources containing only the Moby
  application ID.

An equivalent `aapt2` check of the upstream diagnostic APK reported package
`me.zhanghai.android.files` and application label `Material Files`, confirming
that the personal identity remains isolated to its flavor.

## Dependency integrity

Offline Gradle lock generation added configuration coverage for the `moby` and
`upstream` variants without changing any selected module or version. The
normalized module/version key list has the same SHA-256 before and after Phase
2A:

```text
5954642589c680d9454ffc0c090cf480735477affa42a42ebc2044ab37d8d572
```

The generated `app/gradle.lockfile` contains 170 external module/version
entries across 87 lock-enabled configurations and has SHA-256
`4ac2e1068f468b968d36acfe33e9a6a6294578d473d2fc0358c31e7112982183`.
The final offline lock-regeneration pass was byte-idempotent and left the
detached worktree clean.

`gradle/verification-metadata.xml` did not change and retains SHA-256
`c3161605d1c247e648085521eeb0c6c2c4afd89f75654977b5337f8dc370ff6f`.
The strict Moby runtime dependency report completed successfully and contained
none of `com.google.firebase`, `com.google.android.gms`, or
`com.google.android.datatransport`; those groups are also absent from the lock
and verification files.

As in earlier dependency-report passes, Gradle printed `FAILED` for optional,
non-resolvable UTP and JaCoCo configurations whose artifacts are not present in
the retained offline cache. The report and lock-writing tasks still completed
successfully, and no build, unit-test, or lint acceptance task consumed those
configurations.

## Offline acceptance

With signing-related environment names unset, strict dependency verification,
the repository-local Phase 0B Gradle user home, the approved existing Android
SDK, and the unsigned-build init script, this Moby task set passed offline with
all 64 tasks executed:

```text
assembleMobyDebug testMobyDebugUnitTest lintMobyDebug
```

`testMobyDebugUnitTest` reported `NO-SOURCE`. `lintMobyDebug` reported 0 errors
and 872 warnings. The sorted lint issue-ID list has SHA-256
`911e688c8827d0bf19a85ef308606758f7ad32c49be5cb5e3630b466b9cc556d`,
exactly matching the retained Phase 1C lint result; Phase 2A introduced no new
lint diagnostic.

`assembleUpstreamDebug` also passed offline with 51 executed tasks. Existing SDK
XML, Gradle deprecation, Kotlin, and Java source/target warnings remain
unresolved and unchanged in character.

The Moby diagnostic APK is 22,830,775 bytes with SHA-256
`24f42f60353b0885c07ccb053262ffe2983997a6a1e396cbd649f902f33ecf8c`.
Build Tools 37.0.0 `apksigner verify --verbose` rejected it with `DOES NOT
VERIFY`; no signing-validation task ran. Neither diagnostic APK was installed.

The detached worktree and local `master` were clean after verification.
`git diff --check` passed, and the Phase 2A commits contain no trailers.

## Deferred work and boundaries

- The `moby-deep` palette, cargo glyph, adaptive icon, splash screen, and other
  visual branding remain Phase 2B work.
- `io.github.cottenplant.mobyfiles` should be treated as changeable only until
  the first signed distribution; changing it later would create a different app
  and break the update path.
- Permanent signing, device coexistence/upgrade testing, privacy documentation,
  update channels, CI, and distribution remain separate contracts.
- Existing lint and compiler warnings remain out of scope.

No network, dependency download, SDK installation or modification, Android
device, personal infrastructure, authenticated service, signing material, or
ignored local configuration was accessed. External access was limited to the
existing host JDK and read-only Android SDK path approved at each command
boundary. The permitted public Mobyverse README was read only for naming and
palette context; no deployment or infrastructure material was inspected.

Privacy incidents or near misses: none.
