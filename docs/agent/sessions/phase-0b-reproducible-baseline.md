# Session contract: Phase 0B — reproducible baseline

Status: complete

Working branch: `master`

## Objective

Establish reproducible source and build baselines for current Material Files and
the `v1.7.4` release, then repair the current dav4jvm dependency coordinate with
its immutable full commit SHA. Keep all caches and clean build worktrees inside
this repository.

## Allowed in this contract

- Read and write tracked files and Git metadata in this repository.
- Add the public `upstream` remote with the exact HTTPS URL
  `https://github.com/zhanghai/MaterialFiles.git` and fetch its branches and
  tags read-only.
- Make unauthenticated public downloads needed by the Gradle wrapper and build
  from GitHub, Gradle distribution servers, Google Maven, Maven Central, and
  JitPack.
- Read and write only `/Users/samco/Library/Android/sdk` outside the repository
  to inventory and install exact missing Android platform, Build Tools, NDK, or
  CMake packages. Any required Android SDK repository download must be public
  and unauthenticated.
- Execute the repository Gradle wrapper, the existing host JDK selected by that
  wrapper, and Android SDK tools needed for inventory or installation.
- Store Gradle state and temporary clean worktrees only under
  `.gradle/phase-0b/`, which is covered by the existing `.gitignore`.
- Edit the dav4jvm coordinate, project agent safety configuration, this contract,
  the repository-owned unsigned-build init script, and a Phase 0B baseline
  report.
- Make small local commits directly to `master` with concise Conventional Commit
  subjects and no trailers.

## Safety conditions

- Run Gradle only in clean temporary worktrees. Do not read or copy any ignored
  `local.properties`, `signing.properties`, keystore, or build output from the
  main worktree.
- Explicitly unset `STORE_FILE`, `STORE_PASSWORD`, `KEY_ALIAS`, and
  `KEY_PASSWORD` for every Gradle invocation, without printing or inspecting
  their values.
- Load `docs/agent/unsigned-debug.init.gradle` for every APK build. It must clear
  the debug signing configuration and fail configuration if signing remains
  enabled.
- Use a repository-local Gradle user home; do not read or populate the default
  user Gradle cache.
- Do not run a release-signing task, install an APK, or connect to an Android
  device or personal network service.
- Treat produced upstream debug APKs as diagnostic artifacts only. They retain
  upstream identity and conditional non-FOSS source sections and are not fork
  distribution artifacts.

## Explicit non-goals

- No naming, branding, application ID, signing, release, or distribution work.
- No source changes other than the dav4jvm full-SHA resolution fix and the
  safety/documentation changes named above.
- No dependency upgrades, Gradle migrations, permission changes, or provider
  behavior changes.
- No LAN, VPN, WireGuard, Samba, SSH, SFTP, WebDAV, FTP, Kubernetes, ADB, or
  fastboot access.
- No push, pull request, issue, tag creation, release, or other remote mutation.
- No reading of credentials, signing material, secret-bearing environment
  values, local agent settings, or caches outside the approved SDK directory.

## Deliverables

- Public `upstream` remote and fetched upstream branch/tag references.
- Exact immutable commit IDs for the pre-change local baseline, fetched upstream
  default branch, peeled `v1.7.4` tag, and selected dav4jvm revision.
- Recorded Gradle, Android Gradle Plugin, Kotlin, JDK, Android SDK, Build Tools,
  NDK, CMake, and resolved dependency versions used by each build.
- dav4jvm dependency coordinate using the verified full commit SHA.
- Clean unsigned `assembleDebug` builds of current `master` and pristine
  `v1.7.4`, with artifact paths, sizes, and SHA-256 digests recorded in the
  baseline report.
- Build logs and Gradle caches retained only in ignored `.gradle/phase-0b/`
  paths for local diagnosis.

## Acceptance checks

- The upstream URL is exact and only read-only fetch operations occurred.
- Baseline and tag commit IDs resolve locally and are recorded as full SHA-1s.
- The dav4jvm full SHA is verified against its public Git repository and resolves
  successfully through JitPack.
- Both clean worktrees are clean before and after their builds except for ignored
  build products.
- Both unsigned debug APK builds succeed using the repository-local Gradle user
  home, without a signing-validation task, and `apksigner verify` confirms that
  neither artifact is signed.
- The baseline report contains toolchain/dependency versions and artifact
  SHA-256 digests for both builds.
- `git diff --check` passes and committed Phase 0B changes are limited to the
  contract, baseline report, safety filter, unsigned-build init script, and
  dav4jvm coordinate.

## User-run tests

No device or network-service smoke test belongs to this baseline contract. The
user should not install these diagnostic upstream APKs.

## Dependencies and unresolved decisions

- Network fetches and downloads require the user's explicit approval at the
  command boundary.
- Android SDK inspection and installation require explicit access approval for
  `/Users/samco/Library/Android/sdk`.
- Exact fetched source/tag SHAs, the dav4jvm full SHA, host JDK version, and any
  missing SDK package versions will be filled into the baseline report after
  approved inspection.
- Naming, package identity, branding, FOSS preprocessing, signing, and delivery
  remain deferred to later contracts.

## Resume amendment: unsigned diagnostic builds

The first current-`master` diagnostic build completed `validateSigningDebug` and
`packageDebug`, so Android Gradle Plugin likely read or created its default debug
keystore outside the approved paths. Work stopped immediately. No keystore path,
contents, or derived value was inspected, printed, copied, or hashed.

On 2026-09-13 the user authorized resuming with unsigned diagnostic builds. The
prior APK output will be removed by Gradle's project-local `clean` task while its
repository-local build log is retained as the incident record. All subsequent
APK builds must use the tracked unsigned-build init script and be verified
unsigned before an artifact digest is computed.
