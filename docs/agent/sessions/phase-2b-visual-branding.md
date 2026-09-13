# Session contract: Phase 2B — visual branding

Status: complete

Working branch: `master`

## Objective

Give the `moby` distribution flavor a distinct, reproducible visual identity
using the public Mobyverse `deep` palette and cargo glyph. Apply that identity
to the app's default palette, legacy and adaptive launcher icons, themed icon,
and Android splash screen without changing the upstream flavor, source
namespace, application behavior, or NIO2 provider design.

## Allowed in this contract

- Read and write this contract, a tracked Phase 2B report, flavor-specific app
  resources, and repository-local deterministic asset-generation sources or
  provenance records needed for the branding.
- Read existing app source and resources only as needed to understand theme,
  launcher-icon, splash-screen, About-screen, and flavor-overlay behavior.
- Read only the public README, public design assets, palette source, cargo-glyph
  generator source, and directly associated public license or attribution files
  under `../cottenplant/mobyverse/`. Do not inspect any other part of that
  repository.
- Reimplement or generate Android resources from those public design inputs in
  a license-compatible, attributed, deterministic way; do not copy unrelated
  application or infrastructure code.
- Add `moby` resource overlays and the smallest necessary flavor-specific theme
  wiring. Keep upstream-owned resources unchanged unless a shared indirection
  is strictly necessary, in which case preserve the upstream rendering.
- Reuse the repository-local Phase 0B Gradle user home without network access.
- Create a clean detached worktree and retain ignored outputs and logs only
  under `.gradle/phase-2b/`.
- Execute the pinned repository Gradle wrapper and existing host JDK selected by
  that wrapper; do not install or download a toolchain.
- With explicit command-boundary approval, read the existing Android SDK only
  at `/Users/samco/Library/Android/sdk` for acceptance checks.
- Run offline build, lint, unit-test, resource, manifest, APK, image, dependency,
  and signing-state checks. Any APK must use the repository-owned unsigned-build
  init script and remains diagnostic only.
- Make small local commits on `master` with concise Conventional Commit subjects
  and no trailers.

## Safety conditions

- Use no network and stop on any offline cache miss rather than authorizing a
  download.
- Run Gradle only from a clean detached Phase 2B worktree, using the
  repository-local Gradle user home and approved Android SDK path. Do not read
  or populate the default Gradle cache.
- Explicitly unset `STORE_FILE`, `STORE_PASSWORD`, `KEY_ALIAS`, and
  `KEY_PASSWORD` for every Gradle invocation without printing or inspecting
  their values.
- Load `docs/agent/unsigned-debug.init.gradle` for every APK build. It must clear
  debug signing and fail configuration if signing returns.
- Do not run a release build, signing task, installation, connected-device
  task, emulator, or screenshot automation.
- Do not inspect credentials, signing material, ignored local configuration,
  secret-bearing environment values, personal infrastructure, or authenticated
  services.

## Explicit non-goals

- No application ID, Java/Kotlin namespace, app name, authority, permission,
  intent-action, shortcut-target, or provider-design change.
- No branding change to the `upstream` distribution flavor.
- No dependency, plugin, Gradle, wrapper, SDK, NDK, CMake, or dependency-lock
  change.
- No permission, storage, protocol, network, analytics, telemetry, advertising,
  or feature change.
- No comprehensive UI redesign, typography change, translation change,
  screenshot production, store-listing asset, banner, shortcut-icon, or
  notification-icon redesign.
- No permanent signing, release, update-channel, CI, or distribution change.
- No remote fetch, push, pull request, issue, tag, or release.
- No LAN, VPN, WireGuard, Samba, SSH, SFTP, WebDAV, FTP, Kubernetes, ADB, or
  fastboot access.

## Deliverables

- Flavor-isolated Moby palette resources derived from the public `deep` palette
  and used by the Moby app's default theme where platform dynamic color does not
  intentionally take precedence.
- A cargo-glyph launcher icon set covering legacy raster launchers, adaptive
  icon background and foreground layers, and Android 13 themed icons.
- An Android 12+ splash screen whose background and icon match the Moby launcher
  identity, with a compatible launch preview on supported earlier versions.
- Deterministic, repository-local source or commands for generated raster icon
  assets, plus recorded source provenance and checksums.
- A Phase 2B report recording the design mapping and offline acceptance results.

## Acceptance checks

- The Moby debug variant assembles offline with strict dependency verification
  and existing dependency locks; offline unit tests and lint complete.
- The upstream debug variant still assembles offline and resolves its existing
  launcher icon, colors, and theme resources rather than the Moby overlays.
- Merged Moby resources and diagnostic APK contain the intended palette,
  adaptive icon layers, monochrome icon, density-complete legacy icons, and
  Android 12+ splash attributes.
- Icon source dimensions, alpha handling, adaptive safe-zone placement, and PNG
  outputs are validated deterministically; generated assets reproduce without a
  tracked diff.
- `apksigner verify` rejects the diagnostic APK as unsigned, and no
  signing-validation task runs.
- The resolved Moby debug runtime contains none of `com.google.firebase`,
  `com.google.android.gms`, or `com.google.android.datatransport`.
- Existing application ID, authorities, custom permissions, intent actions, and
  source namespace remain unchanged from Phase 2A.
- The detached worktree and local `master` are clean after verification.
- `git diff --check` passes, commits contain no trailers, and tracked changes
  are limited to this contract, Moby visual-branding resources/generation, and
  the Phase 2B report.

## User-run tests

After permanent signing is designed in a later contract, the user should verify
on the GrapheneOS Pixel 9 Pro that the launcher icon renders correctly in the
selected launcher shape, the themed icon follows wallpaper tint when enabled,
the cold-start splash transition is visually coherent in light and dark modes,
and the default Moby palette has readable contrast. No APK produced in this
phase should be installed.

## Dependencies and unresolved decisions

- The exact `deep` palette values and public cargo design language were taken
  only from the permitted public Mobyverse files and are recorded in the Phase
  2B report. No explicit license file was present in that permitted reference
  scope, so no Mobyverse code or existing glyph geometry was copied.
- Android SDK access requires explicit approval at the build command boundary.
- Offline acceptance depends on artifacts already present in the
  repository-local Phase 0B cache; a cache miss stops this phase.
- Material 3 dynamic color may intentionally override static brand colors at
  runtime. This phase will document the current behavior and will not disable a
  user-visible dynamic-color preference merely to force branding.
- Permanent signing, install/upgrade testing, screenshots, store metadata,
  privacy documentation, update channels, and distribution remain deferred.

## Result

Phase 2B completed on 2026-09-13. The implementation and offline acceptance
evidence are recorded in `docs/agent/baselines/phase-2b.md`. Pre-Android 12
devices retain the existing upstream launch-window behavior; the branded
platform splash applies on Android 12 and later.
