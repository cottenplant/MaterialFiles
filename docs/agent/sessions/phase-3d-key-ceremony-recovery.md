# Session contract: Phase 3D — key ceremony and recovery verification

Status: active

Working branch: `master`

## Objective

Create the first permanent Moby Files app-signing key under the fixed Phase 3C
profile, establish the required protected backups, and prove that each backup
can recover the same signing identity. The release owner performs every
secret-bearing operation privately. The agent may record only sanitized human
pass/fail attestations and must not access the key, certificate identity,
credentials, private records, storage paths, tool output, or signing environment.

This phase makes the key eligible for later release-signing work. It does not
authorize building, signing, installing, publishing, or distributing an APK.

## Allowed in this contract

- Read and write this contract and a tracked Phase 3D report.
- Read tracked signing, identity, Git-ignore, security, and prior agent files
  only as needed to verify that the Phase 3C policy and repository boundary
  remain unchanged.
- Correct `docs/release-signing.md` only if the private ceremony exposes a
  safety-critical ambiguity in its generic commands. Stop and document the
  issue before making any correction; do not use private values in the change.
- Give the user the already tracked ceremony and recovery checklist with
  placeholders and receive only the sanitized result vocabulary defined here.
- Allow the user, outside the agent session and without terminal capture, to use
  a trusted JDK 21 `keytool`, private encrypted storage, backup media, and a
  password manager to complete the ceremony and recovery drill.
- Run offline static, text, Git, and diff checks using only tracked repository
  files and already installed tools that do not require an SDK, keystore,
  certificate, credentials, ignored configuration, external caches, or private
  storage.
- Make small local commits on `master` with concise Conventional Commit subjects
  and no trailers.

## Safety conditions

- The agent must not create, read, inspect, copy, move, hash, validate, or modify
  a keystore, private key, public certificate, fingerprint, signing property
  file, password, recovery secret, ceremony record, backup, or restored copy.
- The agent must not run `keytool`, `apksigner`, `jarsigner`, Gradle, an Android
  build or signing task, or any command against the user's private ceremony or
  backup environment.
- Do not ask for or accept command transcripts, screenshots, file paths,
  filenames containing private location details, certificate fingerprints,
  certificate dates or serial numbers, distinguished-name output, password
  details, hostnames, device identifiers, media labels, or backup locations.
- The user must not paste secret-bearing or certificate-bearing output into the
  chat, repository, issue tracker, shell arguments, or build logs. If a failure
  needs diagnosis, report only the failed checklist identifier and a sanitized
  paraphrase that contains none of the excluded data.
- Use exactly the Phase 3C profile: application ID
  `io.github.cottenplant.mobyfiles`, PKCS#12, alias `moby-files-release`, one
  RSA-3072 key pair, SHA-256 with RSA, 10,950-day validity, and certificate
  subject `CN=Moby Files`. Stop rather than substituting another profile.
- Generate a new dedicated key at a destination the user privately confirms did
  not exist. Do not reuse, import, rotate, or replace another app's key.
- Passwords must be strong and unique, entered only through local prompts, kept
  separate from all keystore copies, and omitted from commands and captured
  output.
- Keep one offline primary keystore and at least two byte-identical backups on
  separately encrypted media in separate physical locations. Test each backup
  through an isolated restore before declaring the ceremony complete.
- Keep all signing and recovery material outside every source checkout, Git
  worktree, synced folder, agent-visible path, and CI system. Disconnect private
  media when each operation is complete.
- Use no network, Android SDK, Android device, emulator, authenticated service,
  source-hosting mutation, or personal infrastructure through the agent.

## Explicit non-goals

- No APK or app bundle assembly, signing, verification, installation,
  coexistence test, upgrade test, device acceptance, or distribution.
- No certificate, fingerprint, expiry, public key, release record, provenance,
  key attestation, or developer-verification publication.
- No Gradle signing integration, signing-property creation, CI secret, hosted
  signing, hardware-token integration, rotation lineage, or compromise drill.
- No application source, resource, manifest, permission, identity, version,
  dependency, lockfile, verification metadata, wrapper, build, signing, CI, or
  Fastlane change.
- No fetch, push, tag, release, pull request, issue, workflow run, remote
  repository mutation, or external publication.
- No LAN, VPN, WireGuard, Samba, SSH, SFTP, WebDAV, FTP, Kubernetes, ADB, or
  fastboot access.

## Deliverables

- This tracked Phase 3D contract, committed before the real ceremony begins.
- A user-owned private ceremony record containing the actual public certificate
  fingerprint and expiry, stored outside the repository and never shown to the
  agent.
- One offline primary keystore and at least two protected, byte-identical backup
  copies, all outside agent and repository access.
- A successful user-operated restore test for each backup that confirms the
  fixed profile and the same public certificate fingerprint as the private
  ceremony record.
- A tracked Phase 3D report containing only the sanitized attestations defined
  below, static repository checks, and deferred work.

## User-operated procedure

Follow `docs/release-signing.md`, from **Fixed key profile** through **Backup and
recovery gate**, without sharing commands or output with the agent. Before the
ceremony, privately decide the primary medium, two encrypted backup media and
separate locations, password custody, offline recovery custody, and responsible
recovery person or process.

The user may report only these identifiers as `PASS`, `FAIL`, or `NOT RUN`:

| ID | Sanitized assertion |
| --- | --- |
| `D1` | The environment was offline and encrypted; terminal capture and shell tracing were disabled; the JDK 21 `keytool` was trusted by the user. |
| `D2` | A previously nonexistent destination outside repositories, worktrees, synced folders, and agent-visible paths was used. |
| `D3` | One new dedicated PKCS#12 key was generated through local password prompts with the exact fixed Phase 3C profile. |
| `D4` | The exported public certificate was inspected privately; its profile, SHA-256 fingerprint, and expiry were recorded in the private ceremony record. |
| `D5` | The primary and at least two encrypted backups were confirmed byte-identical and assigned to separate physical locations; password recovery is separate. |
| `D6` | Each backup was independently restored in isolation and produced the expected alias, profile, expiry, and SHA-256 certificate fingerprint. |
| `D7` | Temporary restored copies were safely disposed of, backup media were returned to custody, and the primary medium was disconnected. |
| `D8` | No secret, private path, certificate-bearing output, private record, signing material, or personal-system detail entered Git, chat, logs, CI, or agent access. |

Return only the eight identifier/status pairs, for example `D1 PASS`, and a
statement that no excluded data is included. Do not return evidence values.
Every identifier must be `PASS` before Phase 3D can complete. A `FAIL` or
`NOT RUN` result leaves the phase active and stops later signing work.

## Acceptance checks

- The Phase 3C signing guide and fixed profile remain unchanged unless a
  separately committed, generic safety correction is documented.
- The user reports `PASS` for `D1` through `D8` without disclosing any excluded
  value or output.
- The private ceremony record, primary key, backups, password recovery, and
  restored copies never enter the repository or agent-visible filesystem.
- No tracked or staged path resembles a keystore, signing property file, public
  certificate, signed artifact, recovery record, password, or private key.
- Application/build inputs, identity, versions, dependencies, locks,
  verification metadata, wrapper, CI, source, resources, manifests, signing
  configuration, Fastlane metadata, and Phase 3B/3C files remain unchanged
  except for an authorized generic signing-guide safety correction.
- `git diff --check` passes; commits contain no trailers; and Phase 3D tracked
  changes are limited to this contract, an optional signing-guide correction,
  and the Phase 3D report.

## User-run tests

The ceremony and restore operations in `D1` through `D8` are the user-run tests.
They must happen outside agent observation. No APK should be built, signed, or
installed during this phase.

## Dependencies and unresolved decisions

- Primary and backup media, physical locations, password-manager choice,
  password and recovery custody, and the recovery operator are private user
  decisions. The agent neither needs nor accepts their values.
- The user is responsible for establishing trust in the offline environment and
  JDK before reporting `D1 PASS`; the agent cannot inspect that environment.
- Phase completion relies on explicit human attestations rather than
  agent-verifiable key evidence. The tracked report must state this limitation.
- The first signed build requires a separate contract that reauthorizes the
  guarded Phase 3B unsigned path, signing, non-secret APK inspection, and signed
  reproducibility checks.
- Certificate publication, device acceptance, developer-verification decisions,
  update channels, distribution, and remote publication remain separate work.
