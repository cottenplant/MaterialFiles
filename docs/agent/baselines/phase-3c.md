# Phase 3C release identity and signing design

Recorded: 2026-09-14

## Implementation

Phase 3C finalized the Moby Files install identity and established a
repository-owned signing and key-custody policy without creating or accessing a
real signing identity. The implementation commits are:

- `3620c945` defines the Phase 3C contract; and
- `b46e0685` records the permanent application ID and signing policy.

The public English and Chinese READMEs now identify
`io.github.cottenplant.mobyfiles` as permanent. They explain that Android uses
the application ID and signing certificate for installation and updates, while
the visible app name does not determine coexistence. The existing Java/Kotlin
namespace remains unchanged.

`docs/release-signing.md` is the human release owner's procedure. It does not
claim that a key, signed artifact, or release exists. The tracked
`signing.properties.example` remains secret-free and points readers to the
detached signing procedure rather than presenting Gradle-integrated signing as
the canonical Moby Files release path.

## Signing and custody decisions

The first Moby Files signing identity is specified as a dedicated key that must
not be reused by another app:

| Field | Policy |
| --- | --- |
| Application ID | `io.github.cottenplant.mobyfiles` |
| Keystore | PKCS#12, outside the repository |
| Alias | `moby-files-release` |
| Key | RSA, 3072 bits |
| Self-signed certificate | SHA-256 with RSA, `CN=Moby Files` |
| Validity | 10,950 days (30 years) |
| Password entry | Local prompt only; absent from command arguments and logs |

The non-personal certificate subject avoids embedding a name, address,
organization, or email in distributed APKs. The 30-year validity exceeds
Android's recommendation of at least 25 years. Explicit algorithm and size
values avoid dependence on a JDK's changing key-generation defaults.

The policy requires one primary keystore and at least two verified,
separately encrypted backups in different physical locations. Password recovery
must remain separate from the keystore copies. A restored copy of each backup
must reproduce the expected public certificate fingerprint before the key is
eligible for release use, with another recovery test at least annually.
Keystore paths, passwords, backup locations, personal system identifiers, and
recovery details are expressly excluded from release records and agent output.

## Planned release boundary

The inherited Gradle-integrated signing path is unchanged. It is not the
planned canonical Moby Files release path. The documented path separates:

1. two independent, clean, offline, byte-identical unsigned `mobyRelease`
   assemblies;
2. package, version, alignment, unsigned-state, and digest verification;
3. one detached, user-operated `apksigner` invocation that writes a new file;
4. removal of the primary key medium; and
5. signature-scheme, signer-certificate, package, alignment, and signed-digest
   verification without the private key.

The existing Phase 3B init script rejects upstream and Moby debug work, skips
credential resolution, and fails if release signing is configured. It and its
outputs remain diagnostic-only under Phase 3B. The signing guide requires a
later signed-release contract to reauthorize the same guarded build method
before an output can become a release input.

The signing command omits password values and preserves the canonical unsigned
APK by requiring `apksigner --out`. It disables the optional V4 sidecar while
allowing `apksigner` to select embedded schemes from the manifest's minimum SDK.
The guide requires `zipalign -c -P 16` before signing and again as a non-mutating
check after signing. Any post-signing archive modification is prohibited.

The sanitized release-record schema contains source, toolchain, unsigned and
signed artifact digests, package/version data, public certificate fingerprint
and expiry, signature schemes, alignment result, and human gate results. It
excludes secrets and personal infrastructure details.

## Official documentation provenance

The following public, unauthenticated primary documentation was reviewed in the
browser on 2026-09-14:

- Android's application-module guide states that the application ID uniquely
  identifies an app on a device and should not change after publication:
  <https://developer.android.com/build/configure-app-module>.
- Android's app-signing guide describes certificate continuity for updates,
  recommends at least 25 years of key validity, warns that a lost self-managed
  key cannot be recovered, and distinguishes public certificates from private
  keys: <https://developer.android.com/studio/publish/app-signing>.
- Android's `apksigner` reference documents separate output, password-input,
  verification, certificate-printing, signature-scheme, and rotation options:
  <https://developer.android.com/tools/apksigner>.
- Android's `zipalign` reference requires alignment before `apksigner` and
  documents the 16 KiB shared-library check:
  <https://developer.android.com/tools/zipalign>.
- The JDK 21 `keytool` reference documents PKCS#12, key-pair and certificate
  export operations, and recommends prompting rather than placing passwords on
  a command line:
  <https://docs.oracle.com/en/java/javase/21/docs/specs/man/keytool.html>.
- Android's developer-verification FAQ describes the changing 2026–2027
  package-registration boundary and its relationship to signing-key ownership:
  <https://developer.android.com/developer-verification/guides/faq>.

No shell network access, authenticated account, package-registration action, or
remote repository operation occurred.

## Static acceptance

Static checks at `b46e0685` confirmed:

- `app/build.gradle` still declares the Moby application ID as
  `io.github.cottenplant.mobyfiles`, matching both READMEs and the signing guide;
- no provisional identity wording remains in the public READMEs;
- no password value appears in `signing.properties.example`;
- no tracked path at `HEAD` ends in `.jks`, `.keystore`, `.p12`, `.pfx`, `.apk`,
  `.aab`, `.pem`, or `.pk8`;
- the signing guide has balanced Markdown code fences and all local references
  resolve to tracked files;
- `app/build.gradle`, `app/CMakeLists.txt`, `signing.gradle`, the Phase 3B init
  script, CI, dependencies, locks, verification metadata, wrapper files,
  application source and resources, and manifests are unchanged from
  `a1688dfa`;
- Phase 3C implementation paths before this report are limited to its contract,
  the signing guide, both public READMEs, and the signing-properties example;
- `git diff --check a1688dfa..b46e0685` passes; and
- both implementation commits have empty bodies and no trailers.

No Gradle, Android SDK, key tool, signing tool, APK build, device, or emulator
check was appropriate because this phase changes documentation and an inert
example only. The proven Phase 3B build and signing behavior remains untouched.

## Deferred work and boundaries

- The user must choose custody media, physical locations, password management,
  and recovery responsibility without sharing those choices with an agent.
- Real key generation, backup creation, recovery testing, certificate
  fingerprint confirmation, and permanent certificate publication require a
  separate user-operated contract.
- The first signed APK, signed-output reproducibility test, version decision,
  GrapheneOS installation, coexistence, upgrade, backup/restore, and protocol
  acceptance remain separate phases.
- The currently diagnostic-only Phase 3B unsigned path must be explicitly
  promoted or reauthorized before it supplies a signed release input.
- Cross-host clean-room unsigned reproducibility remains unproven.
- The applicability of Android developer verification to future Moby Files
  channels and target devices must be reassessed using then-current rules in a
  distribution contract.
- Certificate publication, update channels, provenance publication, store
  metadata, screenshots, release publication, and remote repository mutation
  remain deferred.
- The Phase 3A published push and pull-request CI checks remain user-run.

No signing material, ignored signing configuration, secret-bearing environment
value, SDK outside the repository, Android device, emulator, personal
infrastructure, or unrelated app identity was accessed.

Privacy incidents: none.

Privacy near miss: the initial unpushed local contract revision included an
unnecessary private-context detail. Work stopped, the user explicitly approved
an amend, and replacement commit `3620c945` removed it before implementation
continued. The superseded commit is not reachable from `master`; no fetch, push,
publication, or external disclosure occurred.
