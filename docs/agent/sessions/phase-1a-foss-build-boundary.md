# Session contract: Phase 1A — FOSS build boundary

Status: active

Working branch: `master`

## Objective

Make the fork's direct Android build fully FOSS by removing the upstream
Firebase Analytics and Crashlytics path and adding a build-time dependency
guard against its reintroduction. Preserve the existing application identity,
branding, NIO2 provider design, and functional behavior outside telemetry and
crash reporting.

## Allowed in this contract

- Read and write tracked files and Git metadata in this repository.
- Remove Firebase/Crashlytics plugins, dependencies, configuration, manifest
  entries, source, service configuration, and user-interface wiring.
- Add a Gradle dependency-resolution guard for the known Firebase, Google Play
  services, and Google data transport groups removed in this phase.
- Add this contract, a resolved FOSS runtime-component snapshot, and a Phase 1A
  verification report.
- Reuse the repository-local Phase 0B Gradle user home without network access.
- Create a clean detached worktree and retain ignored build outputs and logs
  only under `.gradle/phase-1a/`.
- With explicit command approval, read the existing Android SDK only at
  `/Users/samco/Library/Android/sdk` to compile and inspect the unsigned APK.
- Execute the repository Gradle wrapper offline, the existing host JDK selected
  by the wrapper, and read-only Android SDK inspection tools.
- Make small local commits directly to `master` with concise Conventional Commit
  subjects and no trailers.

## Safety conditions

- Run Gradle only in a clean temporary worktree.
- Explicitly unset `STORE_FILE`, `STORE_PASSWORD`, `KEY_ALIAS`, and
  `KEY_PASSWORD` for every Gradle invocation without printing or inspecting
  their values.
- Load `docs/agent/unsigned-debug.init.gradle` for every APK build. It must clear
  the debug signing configuration and fail configuration if signing returns.
- Use the repository-local Gradle user home in offline mode; do not read or
  populate the default user Gradle cache.
- Do not run a release-signing task, install an APK, or connect to an Android
  device or personal network service.
- Treat the resulting APK as a diagnostic artifact only because it retains the
  upstream application ID and branding.

## Explicit non-goals

- No application ID, Java/Kotlin namespace, app name, icon, palette, author,
  signing, release, or distribution changes.
- No NIO2 provider, storage, protocol, permission, or other feature changes.
- No dependency upgrades, wrapper checksum pinning, dependency locking, Gradle
  migration, CI changes, or broad license audit.
- No rewrite of upstream README, fastlane metadata, or historical privacy
  policy; fork documentation belongs to the identity/branding phase.
- No network access, dependency downloads, SDK installation, remote fetch,
  push, pull request, issue, tag, or release.
- No LAN, VPN, WireGuard, Samba, SSH, SFTP, WebDAV, FTP, Kubernetes, ADB, or
  fastboot access.
- No reading of credentials, signing material, secret-bearing environment
  values, local agent settings, or caches outside the approved repository-local
  Gradle user home and Android SDK path.

## Deliverables

- Direct builds no longer apply Google Services or Crashlytics plugins and no
  longer resolve or package Firebase Analytics, Crashlytics, Google Play
  services, or Google data transport components.
- The Crashlytics initializer, Firebase manifest metadata, and
  `google-services.json` are absent from the distributable source path.
- A dependency-resolution guard fails the build if a removed telemetry group is
  reintroduced transitively.
- A sorted, deduplicated `debugRuntimeClasspath` component snapshot and Phase 1A
  report record the verification result and diagnostic APK identity.

## Acceptance checks

- Offline `assembleDebug`, `testDebugUnitTest`, and `lintDebug` complete in the
  clean worktree, or any pre-existing lint/test failure is recorded without
  expanding the source-change scope.
- `apksigner verify` rejects the diagnostic APK as unsigned, and no
  signing-validation task runs.
- The resolved debug runtime contains none of
  `com.google.firebase`, `com.google.android.gms`, or
  `com.google.android.datatransport`.
- APK class/resource and merged-manifest inspection finds no Firebase,
  Crashlytics, Google measurement, or Google data transport payload.
- The detached worktree is clean apart from ignored build products.
- `git diff --check` passes, commits contain no trailers, and Phase 1A changes
  remain limited to this contract, the FOSS boundary, and its verification
  artifacts.

## User-run tests

No device or network-service test belongs to this phase. The user should not
install the diagnostic APK because it still uses upstream identity.

## Dependencies and unresolved decisions

- Android SDK access requires explicit approval at the build command boundary.
- The offline build depends on artifacts already present in the approved
  repository-local Phase 0B cache; a cache miss stops the build rather than
  authorizing a download.
- Final name, application ID, Mobyverse branding, signing strategy, update
  channel, privacy documentation, and distribution mechanism remain deferred.
