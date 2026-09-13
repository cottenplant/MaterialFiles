# Session contract: Phase 1C — dependency integrity

Status: active

Working branch: `master`

## Objective

Enable Gradle dependency locking and SHA-256 dependency verification for the
current FOSS build, then prove that the pinned dependency graph and artifacts
support the existing debug build, unit tests, and lint checks without network
access. Keep this supply-chain work separate from application identity,
branding, signing, and distribution.

## Allowed in this contract

- Read and write the root Gradle build file, Gradle-generated dependency lock
  files, `gradle/verification-metadata.xml`, and tracked Phase 1C agent
  documentation in this repository.
- Activate locking for every resolvable root and app project configuration and
  for the root buildscript classpath.
- Bootstrap SHA-256 verification metadata from dependencies already retained
  in the repository-local Phase 0B Gradle user home; do not download or replace
  artifacts.
- Reuse the repository-local Phase 0B Gradle user home without network access.
- Create a clean detached worktree and retain ignored build outputs and logs
  only under `.gradle/phase-1c/`.
- Execute the pinned repository Gradle wrapper and existing host JDK selected by
  that wrapper; do not install or download a toolchain.
- With explicit command-boundary approval, read the existing Android SDK only
  at `/Users/samco/Library/Android/sdk` to configure the Android project and run
  the acceptance tasks.
- Run offline dependency reports, `assembleDebug`, `testDebugUnitTest`, and
  `lintDebug` with strict dependency verification. The debug APK must use the
  repository-owned unsigned-build init script and remains diagnostic only.
- Make small local commits on `master` with concise Conventional Commit
  subjects and no trailers.

## Safety conditions

- Use no network and stop on any offline cache miss rather than authorizing a
  download.
- Run Gradle only from the clean detached Phase 1C worktree, using the
  repository-local Gradle user home and the approved Android SDK path. Do not
  read or populate the default Gradle cache.
- Explicitly unset `STORE_FILE`, `STORE_PASSWORD`, `KEY_ALIAS`, and
  `KEY_PASSWORD` for every Gradle invocation without printing or inspecting
  their values.
- Load `docs/agent/unsigned-debug.init.gradle` for every APK build. It must clear
  debug signing and fail configuration if signing returns.
- Do not run a release build, signing task, installation, or connected-device
  task.
- Do not inspect credentials, signing material, ignored local configuration,
  secret-bearing environment values, personal infrastructure, or authenticated
  services.

## Explicit non-goals

- No dependency, plugin, Gradle, wrapper, SDK, NDK, or CMake upgrade.
- No broad dependency-license or upstream-provenance audit. Cache-derived
  checksums pin the exact artifacts already used by the verified Phase 1A build
  but do not independently establish that those original bytes were benign.
- No app source, application ID, namespace, app name, icon, palette, author,
  privacy-policy, permission, provider, protocol, or feature change.
- No permanent signing, release, update-channel, CI, or distribution change.
- No remote fetch, push, pull request, issue, tag, or release.
- No LAN, VPN, WireGuard, Samba, SSH, SFTP, WebDAV, FTP, Kubernetes, ADB, or
  fastboot access.

## Deliverables

- Root build configuration activates dependency locking for all project
  configurations and the buildscript classpath.
- Gradle-generated lock files pin every external module selected by all
  resolvable root and app configurations.
- `gradle/verification-metadata.xml` requires SHA-256 verification for resolved
  external artifacts and metadata without trusted-artifact bypasses.
- A Phase 1C report records generation commands, lock and verification coverage,
  strict offline acceptance results, and bootstrap trust limitations.

## Acceptance checks

- Gradle generates verification metadata offline while resolving all
  resolvable configurations from the retained repository-local cache.
- Offline root and app dependency reports write lock state for all lock-enabled
  resolvable configurations, including the buildscript classpath.
- A subsequent offline run with strict dependency verification completes
  `assembleDebug`, `testDebugUnitTest`, and `lintDebug` without rewriting lock
  or verification state.
- `apksigner verify` rejects the diagnostic APK as unsigned, and no
  signing-validation task runs.
- The resolved debug runtime contains none of `com.google.firebase`,
  `com.google.android.gms`, or `com.google.android.datatransport`.
- Generated verification metadata uses SHA-256, verifies metadata, verifies no
  signatures, and contains no ignored components or trusted-artifact bypasses.
- The detached worktree and local `master` are clean after verification.
- `git diff --check` passes, commits contain no trailers, and tracked changes
  are limited to this contract, dependency-locking configuration, generated
  integrity files, and the Phase 1C report.

## User-run tests

None. This phase changes build integrity controls only, and the diagnostic APK
must not be installed while it retains upstream identity.

## Dependencies and unresolved decisions

- Gradle execution depends on command-boundary approval for the existing host
  JDK and read-only `/Users/samco/Library/Android/sdk` access.
- The offline run depends on artifacts already present in the repository-local
  Phase 0B cache; a cache miss stops this phase.
- Verification metadata bootstraps trust from the retained artifacts fetched
  over public HTTPS and exercised in prior phases. Independent source,
  signature, reproducible-build, and license review remains future work.
- Application identity, Mobyverse branding, fork privacy documentation,
  signing, update channels, and distribution remain deferred.
