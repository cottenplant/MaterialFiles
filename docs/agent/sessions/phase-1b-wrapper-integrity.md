# Session contract: Phase 1B — Gradle wrapper integrity

Status: active

Working branch: `master`

## Objective

Pin the existing Gradle 9.3.1 wrapper distribution to the official SHA-256
already verified during Phase 0B, then prove the wrapper can validate and use
the retained archive from fresh repository-local state without network access.

## Allowed in this contract

- Read and write the Gradle wrapper properties and tracked Phase 1B agent
  documentation in this repository.
- Reuse the retained Gradle 9.3.1 distribution archive under the
  repository-local Phase 0B Gradle home.
- Create a clean detached worktree and fresh Gradle user home only under
  `.gradle/phase-1b/`.
- Execute the existing host shell, checksum utility, and JDK selected by the
  wrapper; do not install or download a toolchain.
- Run the repository Gradle wrapper offline for `--version`; do not configure or
  build the Android project.
- Make small local commits directly to `master` with concise Conventional
  Commit subjects and no trailers.

## Safety conditions

- Use no network, Android SDK, Gradle cache, or task-specific filesystem input
  outside this repository. Host shell, checksum utility, and JDK executable
  access is the only permitted external tool use.
- Explicitly unset `STORE_FILE`, `STORE_PASSWORD`, `KEY_ALIAS`, and
  `KEY_PASSWORD` for every Gradle invocation without printing or inspecting
  their values.
- Do not run an APK build, signing task, installation, or connected-device task.
- Do not inspect credentials, signing material, secret-bearing environment
  values, personal infrastructure, or authenticated services.

## Explicit non-goals

- No Gradle, Android Gradle Plugin, Kotlin, dependency, SDK, or wrapper JAR
  upgrade.
- No dependency locking or verification metadata.
- No app source, application ID, namespace, branding, privacy-policy, signing,
  release, or distribution changes.
- No remote fetch, push, pull request, issue, tag, or release.
- No LAN, VPN, WireGuard, Samba, SSH, SFTP, WebDAV, FTP, Kubernetes, ADB, or
  fastboot access.

## Deliverables

- `distributionSha256Sum` pins the existing Gradle 9.3.1 `-all` distribution to
  its previously verified official checksum.
- A Phase 1B report records archive provenance, checksum comparison, and the
  fresh-state offline wrapper result.

## Acceptance checks

- The retained archive's locally computed SHA-256 equals both the Phase 0B
  record and `distributionSha256Sum`.
- From a clean detached worktree and a fresh repository-local Gradle user home,
  `./gradlew --offline --version` succeeds using the retained archive.
- The detached worktree and local `master` remain clean after verification.
- `git diff --check` passes, commits contain no trailers, and tracked changes
  are limited to this contract, wrapper checksum pin, and Phase 1B report.

## User-run tests

None. This phase does not configure the Android project or produce an APK.

## Dependencies and unresolved decisions

- The retained archive is
  `.gradle/phase-0b/gradle-user-home/wrapper/dists/gradle-9.3.1-all/9ot9r568e8zfvvd4mn8rbu1j0/gradle-9.3.1-all.zip`.
- Phase 0B recorded its official and locally verified SHA-256 as
  `17f277867f6914d61b1aa02efab1ba7bb439ad652ca485cd8ca6842fccec6e43`.
- Dependency locking, dependency verification metadata, identity, Mobyverse
  branding, signing, update channels, privacy documentation, and distribution
  remain deferred to separate contracts.
