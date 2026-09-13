# Session contract: Phase 2C — privacy transparency

Status: complete

Working branch: `master`

## Objective

Give the `moby` distribution accurate, accessible privacy and source provenance
without changing application behavior. Replace the fork's inherited privacy
claims about Google Play Services, Firebase Analytics, Crashlytics, automatic
log collection, and third-party service providers with a factual description of
the verified FOSS build and the app's user-directed local and network features.
Preserve upstream About-screen links and behavior in the `upstream` flavor.

## Allowed in this contract

- Read and write this contract, a tracked Phase 2C report, `PRIVACY.md`, and the
  smallest necessary app resources and About-screen source needed for
  flavor-specific repository and privacy-policy links.
- Read tracked manifests, dependency declarations, dependency locks, prior agent
  reports, and relevant application source to audit permissions, external links,
  logging, telemetry, crash reporting, storage access, network protocols, local
  server behavior, credential persistence, and user-directed network activity.
- Add flavor-overridable string resources or equivalent small indirection while
  preserving the upstream flavor's current visible links and hidden privacy row.
- Add Moby-only About-screen privacy-policy visibility and point Moby source and
  privacy links at public, unauthenticated fork locations once those locations
  are confirmed from non-secret repository metadata or by the user.
- Reuse the repository-local Phase 0B Gradle user home without network access.
- Create a clean detached worktree and retain ignored outputs and logs only
  under `.gradle/phase-2c/` if build verification needs one.
- Execute offline formatting, static source/resource checks, and, if needed, the
  pinned Gradle wrapper with the existing host JDK selected by that wrapper.
- With explicit command-boundary approval, read the existing Android SDK only
  at `/Users/samco/Library/Android/sdk` for acceptance checks.
- Make small local commits on `master` with concise Conventional Commit subjects
  and no trailers.

## Safety conditions

- Use no network and stop on any offline cache miss rather than authorizing a
  download.
- Do not open any About-screen or policy URL, contact any protocol endpoint, or
  test against personal infrastructure.
- If Gradle is needed, run it only from a clean detached Phase 2C worktree with
  the repository-local Gradle user home. Explicitly unset `STORE_FILE`,
  `STORE_PASSWORD`, `KEY_ALIAS`, and `KEY_PASSWORD` without reading their values.
- Load `docs/agent/unsigned-debug.init.gradle` for every APK build. It must clear
  debug signing and fail configuration if signing returns.
- Do not run a release build, signing task, installation, connected-device task,
  emulator, or screenshot automation.
- Do not inspect credentials, signing material, ignored local configuration,
  secret-bearing environment values, personal infrastructure, or authenticated
  services.
- Keep the privacy notice factual and technical; do not present it as legal
  advice or make unsupported promises about Android, remote hosts, other apps,
  or third-party document providers.

## Explicit non-goals

- No application ID, Java/Kotlin namespace, app name, authority, permission,
  intent-action, shortcut-target, launcher, palette, splash, or provider-design
  change.
- No branding or About-screen behavior change to the `upstream` distribution
  flavor.
- No dependency, plugin, Gradle, wrapper, SDK, NDK, CMake, dependency-lock, or
  verification-metadata change.
- No storage, protocol, credential-handling, network, analytics, telemetry,
  advertising, crash-reporting, logging, or other runtime feature change.
- No terms of service, warranty, regulatory-compliance claim, age policy,
  translation campaign, store listing, screenshot, or release announcement.
- No permanent signing, release, update-channel, CI, or distribution change.
- No remote fetch, push, pull request, issue, tag, or release.
- No LAN, VPN, WireGuard, Samba, SSH, SFTP, WebDAV, FTP, Kubernetes, ADB, or
  fastboot access.

## Deliverables

- A fork-specific `PRIVACY.md` that accurately describes developer data
  collection, on-device data, user-directed network features, credentials,
  Android/document-provider boundaries, external links, logs, retention,
  security limits, changes, and contact/source location.
- Flavor-isolated About-screen source and privacy links: Moby exposes its policy
  and fork source; upstream retains its existing source link and hidden policy
  row.
- Static checks demonstrating that documented telemetry/crash-reporting claims,
  links, permissions, and protocol scope agree with tracked source and the
  previously verified dependency boundary.
- A Phase 2C report recording the audit evidence and acceptance results.

## Acceptance checks

- Static inspection finds no Firebase, Google Play Services, Google
  DataTransport, analytics, advertising, or automatic developer crash-reporting
  integration in the Moby runtime or application source.
- The notice does not imply that remote-storage clients, the optional FTP server,
  Android document providers, explicit external links, or user-shared diagnostics
  are local-only or controlled by the distributor.
- Moby's About screen resolves its fork source and privacy URLs and makes the
  privacy row visible; upstream resolves the original source URL and keeps the
  privacy row hidden.
- Relevant offline resource compilation or Moby/upstream debug assembly and lint
  pass if source or resources require build validation. Any diagnostic APK is
  unsigned and is not installed.
- Application IDs, namespace, authorities, permissions, actions, provider
  design, dependency locks, and verification metadata are unchanged.
- The detached worktree and local `master` are clean after verification.
- `git diff --check` passes, commits contain no trailers, and tracked changes are
  limited to this contract, privacy documentation, flavor-isolated About-screen
  link wiring, and the Phase 2C report.

## User-run tests

After permanent signing is designed, the user should open About in both Moby
light and dark themes, confirm the source and privacy rows launch the intended
public pages, and verify that returning from the browser preserves app state. No
APK produced in this phase should be installed.

## Dependencies and unresolved decisions

- The canonical public fork repository URL and privacy-policy URL must be
  confirmed without exposing authenticated remote configuration. If no stable
  public URL exists yet, link activation is deferred rather than guessed.
- `io.github.cottenplant.mobyfiles` still requires final confirmation before the
  first signed distribution, but no signing or identity change is needed here.
- Offline acceptance depends on artifacts already present in the
  repository-local Phase 0B cache; a cache miss stops Gradle verification.
- The source audit describes the current tracked revision and cannot guarantee
  the behavior or policy of Android, user-selected document providers, remote
  servers, browsers, or future builds.
- The `origin/master` remote-tracking reflog records an external push to
  `02e1feec` despite the Phase 2B handoff stating that nothing was pushed. This
  phase will not inspect, fetch, or mutate the remote.

## Result

Phase 2C completed on 2026-09-13. Moby now exposes its confirmed public source
and privacy notice from About, the upstream flavor retains its original source
link and hidden privacy row, and the implementation and offline acceptance
evidence are recorded in `docs/agent/baselines/phase-2c.md`.
