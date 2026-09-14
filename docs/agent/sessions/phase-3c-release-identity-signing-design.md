# Session contract: Phase 3C — release identity and signing design

Status: complete

Working branch: `master`

## Objective

Finalize `io.github.cottenplant.mobyfiles` as the permanent Moby Files Android
application ID and define a secure, reproducible, user-operated release-signing
and key-custody procedure. Keep all private-key creation, custody, backup,
credential entry, artifact signing, and certificate publication outside agent
access and defer the first signed build to a separate contract.

The user has confirmed the Moby Files name and permanent application ID.
Coexistence with any other installed app depends on each app using a distinct
application ID; no other application's identity or implementation is in scope.

## Allowed in this contract

- Read and write this contract, `README.md`, `README_zh-CN.md`,
  `signing.properties.example`, a repository-owned signing and key-custody
  guide, the minimum fail-closed tracked signing configuration needed by that
  guide, and a tracked Phase 3C report.
- Read tracked Gradle, Android, Git ignore, privacy, security, CI, release, and
  prior agent files only as needed to document and verify the signing boundary.
- Replace provisional-identity wording with an explicit statement that
  `io.github.cottenplant.mobyfiles` is the permanent Moby Files application ID.
- Define a dedicated per-app signing identity, offline key-generation ceremony,
  password-entry boundary, backup and recovery requirements, certificate export
  and fingerprint verification, rotation and loss policy, and separation
  between reproducible unsigned assembly and signing.
- Improve examples and tracked signing configuration only where necessary to
  avoid accidental interactive hangs, ambiguous missing values, credentials in
  command arguments, or unintentional signing of a non-Moby variant. Normal
  Moby release signing must remain user-invoked and fail closed.
- Provide commands and checklists for the user to run with placeholders. They
  must prompt for secrets or read them only from ignored user-owned files; no
  real secret value may appear in tracked text, command arguments, logs, or
  agent-visible output.
- Read public, unauthenticated official Android and JDK documentation through
  the browser only to verify current signing, certificate-validity, and key-tool
  guidance. Do not use shell network access or authenticated services.
- Run offline static, text, Gradle-configuration, Git, and diff checks using only
  tracked workspace files and already installed tools that do not require SDK,
  signing material, ignored configuration, or external caches.
- Make small local commits on `master` with concise Conventional Commit subjects
  and no trailers.

## Safety conditions

- Do not create, read, copy, inspect, hash, validate, or modify a keystore,
  private key, signing property file, password, recovery secret, or
  secret-bearing environment value.
- Do not run `keytool`, `apksigner`, `jarsigner`, a Gradle signing task, release
  assembly, or any command that could generate or sign an artifact.
- Do not ask the user to paste a password, private key, keystore, unredacted
  signing configuration, recovery material, or secret-bearing command output.
- A permanent Moby Files signing key must be dedicated to
  `io.github.cottenplant.mobyfiles`; do not reuse another app's signing
  identity.
- Keep keystores, signing properties, passwords, exported recovery material,
  signed artifacts, and local release records ignored and outside tracked Git
  content. Public certificate material may be added only in a later certificate
  publication contract after user verification.
- Keep signing local and user-operated. Do not add CI secrets, hosted signing,
  artifact upload, release automation, or a path that gives an agent access to
  signing material.
- Use no Android device, emulator, SDK outside the repository, network other
  than the allowed public documentation reads, authenticated service, or
  personal infrastructure.

## Explicit non-goals

- No key or keystore generation, password selection, signing, signed artifact,
  certificate fingerprint claim, key rotation, certificate publication, or
  recovery drill.
- No inspection or modification of another app, its application ID, signing
  lineage, source, artifacts, or distribution path.
- No CI artifact upload, provenance attestation, update channel, store metadata,
  screenshot, publication, tag, release, or remote repository mutation.
- No version, dependency, lockfile, verification metadata, wrapper, SDK, NDK,
  CMake, application source, resource, manifest, permission, protocol, storage,
  or runtime behavior change.
- No cross-host reproducibility claim or GrapheneOS installation, coexistence,
  upgrade, backup, restore, or protocol test.
- No credential-at-rest encryption, Android backup, cleartext-network, or plain
  FTP remediation.
- No LAN, VPN, WireGuard, Samba, SSH, SFTP, WebDAV, FTP, Kubernetes, ADB, or
  fastboot access.

## Deliverables

- Public documentation that records `io.github.cottenplant.mobyfiles` as the
  permanent Moby Files application ID.
- A tracked signing and key-custody guide with a user-run key ceremony, dedicated
  key policy, credential boundary, redundant-backup and recovery policy,
  unsigned-input verification, signed-output verification, certificate
  fingerprint workflow, rotation/loss policy, and sanitized release record.
- A secret-free signing-properties example and, if required, minimal fail-closed
  tracked signing configuration aligned with the guide.
- A Phase 3C report recording source decisions, documentation provenance,
  static acceptance results, and deferred user actions without recording any
  signing secret or real certificate identity.

## Acceptance checks

- Tracked public documentation consistently identifies
  `io.github.cottenplant.mobyfiles` as permanent and explains that Android
  coexistence depends on distinct application IDs, not display names.
- The signing guide requires a dedicated Moby Files key, strong user-chosen
  passwords entered without command-line exposure, an appropriately long
  certificate validity, at least two protected backups in separate locations,
  a tested recovery procedure, and no source-control or CI custody of secrets.
- The guide starts from the byte-identical unsigned `mobyRelease` procedure,
  binds release records to a full source commit and unsigned APK SHA-256, and
  requires post-signing package, certificate, signature-scheme, and artifact
  digest verification before installation or publication.
- User-run commands use placeholders, do not put passwords in arguments, and
  distinguish public certificate output from private key material.
- Signing configuration fails clearly when required non-secret or secret inputs
  are absent, cannot silently sign an upstream or debug variant under the Moby
  release procedure, and preserves the guarded unsigned Phase 3B path.
- No tracked or staged file resembles a keystore, signing property file, signed
  APK, password, private key, recovery secret, or real certificate output.
- Dependencies, locks, verification metadata, wrapper, CI, app source and
  resources, manifest behavior, versions, and the Phase 3B unsigned release
  path remain unchanged unless the report documents a minimal authorized
  fail-closed signing correction.
- `git diff --check` passes; commits contain no trailers; and Phase 3C tracked
  changes are limited to this contract and its stated deliverables.

## User-run tests

After Phase 3C is complete, the user should review the signing and custody guide
and decide where the offline primary keystore and two protected backups will be
kept. Do not generate a real key yet. The key ceremony, certificate fingerprint
confirmation, signed build, and GrapheneOS coexistence/upgrade checks require a
new contract and explicit user operation.

## Dependencies and unresolved decisions

- The user's confirmation resolves the public naming decision and establishes
  `io.github.cottenplant.mobyfiles` for permanent use.
- Android package coexistence with any other app depends on distinct application
  IDs. This remains a later user-run device check; the agent will not inspect
  another app to prove it.
- Exact key-generation options and certificate validity were checked against
  current official Android and JDK guidance; the resulting fixed profile and
  source links are recorded in the guide and Phase 3C report.
- Storage media, password-manager choice, physical locations, responsible
  person, and recovery custodian are personal operational decisions. The guide
  will define requirements and placeholders without requesting their values.
- Certificate publication, signed-release construction, reproducibility after
  signing, update channels, distribution, and device acceptance remain separate
  contracts.

## Result

Phase 3C completed on 2026-09-14. The permanent Moby Files application ID is now
recorded as `io.github.cottenplant.mobyfiles`, and the repository documents a
dedicated, user-operated signing and key-custody policy that separates
reproducible unsigned assembly from private-key use. No signing key, signing
credential, certificate identity, or signed artifact was created or accessed.
Implementation decisions, official documentation provenance, static acceptance,
the corrected privacy near miss, and deferred user actions are recorded in
`docs/agent/baselines/phase-3c.md`.
