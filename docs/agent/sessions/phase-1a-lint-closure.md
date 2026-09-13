# Session contract: Phase 1A — lint closure

Status: active

Working branch: `master`

## Objective

Close the sole Phase 1A verification gap by obtaining the exact public
`androidx.graphics:graphics-shapes-desktop:1.0.1` artifact, running
`lintDebug`, and recording the reproducible result without changing app source,
dependencies, identity, branding, signing, or behavior.

## Allowed in this contract

- Read and write tracked Phase 1A agent documentation and ignored verification
  files under `.gradle/phase-1a/` in this repository.
- Make one public, unauthenticated download limited to Maven coordinate
  `androidx.graphics:graphics-shapes-desktop:1.0.1` and record artifact
  provenance and checksums without accessing any authenticated service.
- Reuse the repository-local Phase 0B Gradle user home and a clean detached
  worktree under `.gradle/phase-1a/`; do not read or populate the default Gradle
  cache.
- Run the repository Gradle wrapper offline after the authorized artifact is
  available locally.
- With the required command-boundary approval, read the existing Android SDK
  only at `/Users/samco/Library/Android/sdk` for `lintDebug`.
- Make small local commits directly to `master` with concise Conventional
  Commit subjects and no trailers.

## Safety conditions

- Run Gradle only in a clean detached worktree.
- Explicitly unset `STORE_FILE`, `STORE_PASSWORD`, `KEY_ALIAS`, and
  `KEY_PASSWORD` for every Gradle invocation without printing or inspecting
  their values.
- Use offline mode for the acceptance run; network access ends after obtaining
  the exact authorized artifact.
- Do not run an APK build, release-signing task, installation, or connected
  device task.
- Do not connect to personal infrastructure or inspect credentials, signing
  material, secret-bearing environment values, or caches outside the approved
  repository-local Gradle user home.

## Explicit non-goals

- No app source, build configuration, dependency declaration, application ID,
  namespace, branding, signing, release, or distribution changes.
- No dependency upgrades, broad cache population, SDK installation, wrapper
  changes, dependency locking, or license audit.
- No remote fetch, push, pull request, issue, tag, or release.
- No LAN, VPN, WireGuard, Samba, SSH, SFTP, WebDAV, FTP, Kubernetes, ADB, or
  fastboot access.

## Deliverables

- Recorded provenance and cryptographic checksums for every downloaded file
  belonging to the authorized Maven coordinate.
- A completed offline `lintDebug` result at the current `master` source commit.
- An updated Phase 1A report that closes the lint gap or precisely records any
  source-independent lint failure.

## Acceptance checks

- The detached validation worktree is clean apart from ignored build products.
- `lintDebug` runs offline using the repository-local Gradle user home and
  completes successfully, or its existing diagnostics are recorded without
  expanding source-change scope.
- `git diff --check` passes, commits contain no trailers, and tracked changes
  are limited to this contract and the Phase 1A report.

## User-run tests

None. The diagnostic APK must not be installed because it retains upstream
identity, and this contract does not build or modify an APK.

## Dependencies and unresolved decisions

- The user authorized downloading only
  `androidx.graphics:graphics-shapes-desktop:1.0.1` for this closure.
- Android SDK access remains subject to the repository's explicit
  command-boundary approval rule.
- Identity, Mobyverse branding, privacy documentation, signing, update channel,
  and distribution remain deferred.
