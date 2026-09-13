# Session contract: Phase 1A — lint closure

Status: complete

Working branch: `master`

## Objective

Close the sole Phase 1A verification gap by obtaining the public lint-tool
artifacts authorized by the user, running `lintDebug`, and recording the
reproducible result without changing app source, dependencies, identity,
branding, signing, or behavior.

## Allowed in this contract

- Read and write tracked Phase 1A agent documentation and ignored verification
  files under `.gradle/phase-1a/` in this repository.
- Make public, unauthenticated downloads limited to Maven coordinates
  `androidx.graphics:graphics-shapes-desktop:1.0.1` and
  `com.android.tools.lint:lint-gradle:32.1.0`, and record artifact provenance
  and checksums without accessing any authenticated service.
- Use one additional public, unauthenticated Gradle network pass to obtain only
  the remaining fixed-version dependencies required for `lintDebug`, including
  transitives exposed by the lint tool graph, as authorized by the user.
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
- Use offline mode for the final acceptance run. Network access ends after the
  one authorized dependency-resolution pass and must not be used for the
  reproducibility run.
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
  belonging to the authorized Maven coordinates.
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

- The user authorized downloading
  `androidx.graphics:graphics-shapes-desktop:1.0.1` and, after the first offline
  run exposed it, `com.android.tools.lint:lint-gradle:32.1.0` for this closure.
- The user subsequently authorized a one-time download of all remaining
  blockers required to develop, build, and compile this lint path.
- The authorized files were downloaded from Google Maven and recorded in the
  Phase 1A report. The graphics-shapes JAR matched the size and SHA-256
  published in its Gradle metadata, and both JARs passed archive validation.
- The first offline run passed the original artifact resolution point and
  reached `:app:lintAnalyzeDebug`, which exposed the second authorized
  lint-only artifact, `com.android.tools.lint:lint-gradle:32.1.0`.
- The second offline run resolved `lint-gradle` and stopped at five uncached
  runtime dependencies: `intellij-core:32.1.0`, `kotlin-compiler:32.1.0`,
  `uast:32.1.0`, `lint:32.1.0`, and `groovy:3.0.22`. They were not downloaded
  before the user expanded the one-time authorization.
- The one-time Gradle network pass downloaded the remaining five blockers and
  four newly exposed transitives from public Google Maven and Maven Central.
  The 23 unique downloaded payloads and their SHA-256 checksums are recorded in
  `docs/agent/baselines/phase-1a-lint-artifacts.sha256`.
- Online `lintDebug` succeeded with 0 errors and 873 warnings. A forced
  `--offline --rerun-tasks` acceptance run then succeeded with all 36 tasks
  executed and the same lint result, closing the verification gap.
- Android SDK access was approved at the command boundary and limited to the
  existing `/Users/samco/Library/Android/sdk` path.
- Identity, Mobyverse branding, privacy documentation, signing, update channel,
  and distribution remain deferred.
