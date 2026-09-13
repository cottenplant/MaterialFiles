# Session contract: Phase 2A — fork identity

Status: active

Working branch: `master`

## Objective

Give the personal FOSS distribution a distinct Android application identity and
the user-facing name `Moby Files` without changing the Java/Kotlin namespace or
upstream provider design. Keep visual branding, signing, and distribution in
later contracts.

## Allowed in this contract

- Read and write this contract, the app Gradle build configuration, manifest
  identity references, app-name resources, and a tracked Phase 2A report.
- Read existing source and resources only as needed to identify package-name,
  authority, permission, and app-label coupling.
- Read only the public README, design assets, palette source, and glyph
  generator sources under `../cottenplant/mobyverse/` to establish the public
  naming convention. Do not inspect any other part of that repository.
- Add a dedicated personal-distribution product flavor or equivalent Gradle
  application-ID layer while retaining `me.zhanghai.android.files` as the
  source namespace.
- Reuse the repository-local Phase 0B Gradle user home without network access.
- Create a clean detached worktree and retain ignored outputs and logs only
  under `.gradle/phase-2a/`.
- Execute the pinned repository Gradle wrapper and existing host JDK selected by
  that wrapper; do not install or download a toolchain.
- With explicit command-boundary approval, read the existing Android SDK only
  at `/Users/samco/Library/Android/sdk` for acceptance checks.
- Run offline build, unit-test, lint, manifest, APK identity, and dependency
  guard checks. Any APK must use the repository-owned unsigned-build init
  script and remains diagnostic only.
- Make small local commits on `master` with concise Conventional Commit
  subjects and no trailers.

## Safety conditions

- Use no network and stop on any offline cache miss rather than authorizing a
  download.
- Run Gradle only from the clean detached Phase 2A worktree, using the
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

- No Java/Kotlin namespace or source-package rename.
- No `moby-deep` palette, icon, cargo glyph, splash-screen, screenshot, or other
  visual-branding change. The user selected this direction preliminarily for a
  separate Phase 2B contract.
- No dependency, plugin, Gradle, wrapper, SDK, NDK, or CMake change.
- No permission, provider behavior, protocol, storage, network, analytics,
  telemetry, advertising, or feature change.
- No permanent signing, release, update-channel, CI, or distribution change.
- No remote fetch, push, pull request, issue, tag, or release.
- No LAN, VPN, WireGuard, Samba, SSH, SFTP, WebDAV, FTP, Kubernetes, ADB, or
  fastboot access.

## Deliverables

- A dedicated personal-distribution application ID that cannot collide with the
  upstream Material Files package.
- The installed application and launcher label use `Moby Files`.
- Manifest authorities and custom permissions derived from the application ID
  remain unique and internally consistent without hard-coded fork package
  strings in source.
- Existing FOSS dependency guards remain effective for the fork variant.
- A Phase 2A report records the identity design, affected package-derived
  identifiers, and offline acceptance results.

## Acceptance checks

- The fork debug variant assembles offline with strict dependency verification
  and dependency locking.
- Offline unit tests and lint complete for the fork debug variant.
- The merged manifest and diagnostic APK use the fork application ID, `Moby
  Files` label, fork-derived authorities, and fork-derived custom permissions;
  they do not expose the upstream application ID as an install identity.
- `apksigner verify` rejects the diagnostic APK as unsigned, and no
  signing-validation task runs.
- The resolved fork debug runtime contains none of `com.google.firebase`,
  `com.google.android.gms`, or `com.google.android.datatransport`.
- Existing upstream-oriented source namespace references remain unchanged.
- The detached worktree and local `master` are clean after verification.
- `git diff --check` passes, commits contain no trailers, and tracked changes
  are limited to this contract, fork identity/name wiring, and the Phase 2A
  report.

## User-run tests

None. The diagnostic APK remains unsigned and must not be installed. Device
coexistence and upgrade behavior are deferred until signing is designed.

## Dependencies and unresolved decisions

- The stable application ID should follow an established public Mobyverse
  naming convention if one exists. If the permitted public materials do not
  establish one clearly, implementation pauses for the user's choice rather
  than inventing a durable identity.
- Gradle execution depends on command-boundary approval for the existing host
  JDK and read-only `/Users/samco/Library/Android/sdk` access.
- Offline acceptance depends on artifacts already present in the
  repository-local Phase 0B cache; a cache miss stops this phase.
- `Moby Files` and the `moby-deep` palette direction are preliminary user
  decisions. This phase applies only the name; visual branding remains Phase
  2B.
- Privacy documentation, permanent signing, update channels, and distribution
  remain deferred.
