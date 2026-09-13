# Phase 2C privacy transparency

Recorded: 2026-09-13

## Implementation and isolation

The final implementation was validated at
`f6278a1d29c9c3b73a7692772ee13f195876a3e5` in the clean detached Phase 2C
worktree. Its commits are:

- `e6caeebe` defines the Phase 2C contract;
- `9586038c` replaces the inherited upstream privacy policy;
- `b77c4f2e` makes About-screen source and privacy links
  distribution-configurable while preserving upstream behavior;
- `a5321c38` adds the user-confirmed public Moby source and privacy URLs;
- `1689eeca` adds the required app resource import found by the first compile;
  and
- `f6278a1d` uses the existing Android KTX URI extension for the new URLs so the
  lint-warning baseline remains unchanged.

The Moby privacy notice now describes the application as a network-capable file
manager rather than repeating the removed upstream Firebase service policy. It
does not change application behavior, protocol implementation, storage access,
permissions, credential handling, backup configuration, or network security.

Shared About-screen code reads two distribution resources. The upstream source
set defines its existing source URL and an empty privacy URL, so its visible
source link is unchanged and its privacy row remains hidden. The Moby overlay
defines:

```text
https://github.com/cottenplant/MaterialFiles
https://github.com/cottenplant/MaterialFiles/blob/master/PRIVACY.md
```

The user confirmed the canonical public repository URL before these values were
added. A non-empty privacy URL makes the existing row visible. Source and policy
links still create an external view intent only after the user selects the row;
there is no startup or background request.

## Privacy audit evidence

Phase 1A removed Firebase Analytics, Crashlytics, Google Play Services, Google
DataTransport, their Gradle plugins and configuration, the Crashlytics
initializer, and the former nonfree policy link. Phase 2C source inspection
found no remaining Firebase, Crashlytics, Google measurement, DataTransport,
analytics, telemetry, Sentry, Bugsnag, or ACRA marker under `app/src/main`.
The dependency-group guard remains active for `com.google.firebase`,
`com.google.android.gms`, and `com.google.android.datatransport`.

No dependency declaration, lock, or verification metadata changed from Phase
2B. The final strict offline build resolved both debug variants successfully,
so no forbidden dependency group was encountered. The previously verified Moby
runtime boundary therefore remains applicable.

The notice records the material user-data and network boundaries observed in
tracked source:

- local and remote storage definitions are retained through
  `ParcelValueSettingLiveData` in ordinary Android shared preferences;
- passwords, SFTP private keys, and private-key passwords are fields in those
  saved parcel values; Base64 is serialization, not separate encryption;
- the manifest enables Android backup and supplies no data-exclusion rules, so
  the notice warns that a configured Android backup transport may receive app
  data, including saved remote credentials;
- the app requests broad file-management, package-query, APK-install, and
  network capabilities, with optional root or Shizuku access;
- opening LAN SMB discovery starts SMB browser queries and private-subnet
  address probes;
- configured FTP, SFTP, SMB, and WebDAV clients communicate with user-selected
  endpoints, and the optional FTP server listens after the user starts it;
- the network security configuration permits cleartext traffic and trusts both
  system and user-added certificate authorities; and
- source code writes some exception details to Android's system log, but no
  automatic developer log-upload path was found.

The notice distinguishes those user-directed connections and OS/app boundaries
from developer telemetry. It does not claim that Android, backup transports,
document providers, browsers, remote hosts, or receiving apps are controlled by
the fork maintainers.

Credential-at-rest encryption, Android backup exclusions, and cleartext-network
hardening are deliberately not changed here. They require separate behavioral
and compatibility contracts.

## Offline acceptance

The first combined acceptance run at `a5321c38` reached both flavor Kotlin
compile tasks and failed because the new `R` references in the About subpackage
needed an explicit import. No APK was produced, and no signing-validation task
ran. The import was added in `1689eeca`.

A complete rerun at `1689eeca` then passed all 107 tasks but reported 873 lint
warnings, one above the Phase 2B count. The only added diagnostic was `UseKtx`
on the new `Uri.parse` call. The new source and privacy URL parsing was changed
to `String.toUri()` in `f6278a1d`; the pre-existing three `UseKtx` diagnostics
on upstream author constants were left unchanged.

With signing-related environment names unset, strict dependency verification,
the repository-local Phase 0B Gradle user home, the approved read-only Android
SDK, the unsigned-build init script, and `--rerun-tasks`, the final command set
passed offline with all 107 tasks executed:

```text
assembleMobyDebug testMobyDebugUnitTest lintMobyDebug assembleUpstreamDebug
```

`testMobyDebugUnitTest` reported `NO-SOURCE`. Lint completed with 0 errors and
872 warnings, matching the Phase 2B count. No lint diagnostic points at either
new URL call or either distribution URL resource. Existing SDK XML, Gradle,
Kotlin, and Java source/target warnings remain unchanged in character.

Merged-resource and Build Tools 36.0.0 inspection confirmed:

| Variant | Source URL | Privacy URL |
| --- | --- | --- |
| Moby debug | `https://github.com/cottenplant/MaterialFiles` | `https://github.com/cottenplant/MaterialFiles/blob/master/PRIVACY.md` |
| upstream debug | `https://github.com/zhanghai/MaterialFiles` | empty |

The final diagnostic artifacts are:

| Variant | Package | Bytes | SHA-256 |
| --- | --- | ---: | --- |
| Moby debug | `io.github.cottenplant.mobyfiles` | 22,854,039 | `3c49c880bbe7ac595de9460d90cc620dc518073d0b65de559eb40306bdb2b1a4` |
| upstream debug | `me.zhanghai.android.files` | 22,832,279 | `f799d7cb6e1761f5f3e3b120a995ea403ede729820c2cde6987168b76c254454` |

Both report version 39 (`1.7.4`), minimum SDK 23, target SDK 34, and compile SDK
36. `apksigner verify --verbose` rejected each APK with `DOES NOT VERIFY` and
`Missing META-INF/MANIFEST.MF`. No signing-validation task ran, and neither APK
was installed.

Static comparison with `02e1feec` confirmed that `app/build.gradle`,
`app/gradle.lockfile`, `gradle/verification-metadata.xml`,
`app/src/main/AndroidManifest.xml`, and `signing.gradle` are unchanged. There is
no application ID, source namespace, authority, permission, intent action,
shortcut, provider, dependency, build-tool, signing, protocol, or storage
behavior change in Phase 2C.

## Deferred work and boundaries

- The user should test the source and privacy rows on the signed Moby build in a
  later contract. No diagnostic APK from this phase should be installed.
- `io.github.cottenplant.mobyfiles` still requires final confirmation before
  the first signed distribution.
- Saved remote credentials and SFTP private-key material are not separately
  encrypted in app storage and are not excluded from Android backup. Security
  hardening and migration behavior require a separate contract.
- Cleartext traffic and the optional plain FTP client/server remain available.
  Any transport-security change requires protocol-specific compatibility work.
- The inherited 872 lint warnings, optional offline UTP/JaCoCo configurations,
  permanent signing, update channels, CI, and distribution remain out of scope.
- The `origin/master` reflog recorded an external push to `02e1feec` before this
  phase, contrary to the Phase 2B handoff. Phase 2C did not fetch, inspect, or
  mutate the remote.

No network, Android device, personal infrastructure, authenticated service,
signing material, secret-bearing environment value, ignored local
configuration, or cache outside the repository-local Gradle home was accessed.
The only approved host path read was the existing Android SDK path. No remote
URL was opened.

Privacy incidents or near misses: none.
