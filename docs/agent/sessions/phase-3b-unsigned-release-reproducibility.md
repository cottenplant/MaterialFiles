# Session contract: Phase 3B — unsigned release reproducibility

Status: active

Working branch: `master`

## Objective

Establish a repository-owned, noninteractive way to assemble the exact Moby
release variant without signing, then determine whether two clean builds of the
same committed source and pinned inputs produce byte-identical APKs. Keep every
artifact diagnostic-only and preserve the existing signed-release configuration
for a later key-custody contract.

## Allowed in this contract

- Read and write this contract, a repository-owned unsigned-release Gradle init
  script, the minimum conditional signing wiring in `app/build.gradle` and
  `signing.gradle`, and a tracked Phase 3B report.
- Read tracked Gradle, Android, signing, dependency-lock, verification-metadata,
  CI, and prior agent files only as needed to define and verify the release
  assembly boundary.
- Add an init-script-only marker that causes project configuration to skip
  release credential lookup and signing-config assignment for the diagnostic
  build. The normal release path must remain unchanged when the marker is absent.
- If reproducibility requires it, make the smallest Gradle-only correction to a
  nondeterministic build input. Do not change application source, resources,
  manifest behavior, dependencies, versions, or identity.
- Create two clean detached worktrees, build outputs, logs, and Gradle state only
  under `.gradle/phase-3b/`, which is already ignored.
- Reuse repository-local Gradle wrapper and dependency state without network
  access or dependency changes.
- With explicit command-boundary approval, read the existing Android SDK only at
  `/Users/samco/Library/Android/sdk` for release assembly and APK inspection.
- Execute the pinned Gradle wrapper, existing host JDK selected by the wrapper,
  and installed SDK tools. Use offline mode, strict dependency verification,
  and locked dependencies.
- Compare artifact bytes and ZIP entry metadata using already installed offline
  host tools, and record sanitized toolchain, task, artifact, and checksum
  evidence.
- Make small local commits on `master` with concise Conventional Commit subjects
  and no trailers.

## Safety conditions

- Run Gradle only from clean detached Phase 3B worktrees with a repository-local
  Gradle user home. Do not read or populate the default Gradle cache.
- Do not read or copy ignored `local.properties`, `signing.properties`, a
  keystore, or any generated output from the main worktree.
- Explicitly unset `STORE_FILE`, `STORE_PASSWORD`, `KEY_ALIAS`, and
  `KEY_PASSWORD` for every Gradle invocation without printing or inspecting
  their values.
- Load the tracked unsigned-release init script for every release assembly. It
  must set the private diagnostic marker before project evaluation, limit the
  task graph to Moby release work, and fail before task execution if the release
  build type has any signing configuration.
- Do not run a signing-validation task, sign an artifact, calculate a certificate
  identity, or access signing material. Confirm each APK is unsigned before
  computing or recording its digest.
- Treat generated APKs and reports as local diagnostics. Do not install, upload,
  retain outside ignored Phase 3B paths, publish, or represent them as releases.
- Use no network, Android device, emulator, authenticated service, or personal
  infrastructure. Stop on an offline cache miss rather than downloading.

## Explicit non-goals

- No signing implementation, key generation, key-custody procedure, certificate
  publication, release secret, or GitHub environment design.
- No CI artifact upload, provenance attestation, update channel, store metadata,
  screenshot, publication, tag, release, or remote repository mutation.
- No confirmation or change of the provisional
  `io.github.cottenplant.mobyfiles` application ID.
- No dependency, plugin, Gradle, wrapper, SDK, NDK, CMake, application version,
  source, resource, manifest, permission, protocol, storage, or runtime behavior
  change.
- No credential-at-rest encryption, Android backup, cleartext-network, or plain
  FTP remediation.
- No LAN, VPN, WireGuard, Samba, SSH, SFTP, WebDAV, FTP, Kubernetes, ADB, or
  fastboot access.

## Deliverables

- A tracked Gradle init script and minimal conditional Gradle wiring that make
  `assembleMobyRelease` unsigned without resolving credentials or weakening the
  repository's normal release-signing design.
- Two clean, offline assemblies of the same committed Moby release source using
  separate worktrees and build directories.
- A Phase 3B report recording source identity, commands in sanitized form,
  signing-task exclusion, unsigned verification, artifact sizes and SHA-256
  digests, byte-comparison results, and any determinism correction.

## Acceptance checks

- The init script sets a private marker before project evaluation, permits only
  Moby release tasks, and fails if signing is present before the task graph runs.
- With the marker present, Gradle neither creates nor assigns the release signing
  configuration. With the marker absent, the tracked signing configuration and
  release build-type assignment remain equivalent to their Phase 3A state.
- Each build uses the pinned wrapper, repository-local Gradle state, offline
  dependency resolution, strict verification, dependency locks, the approved
  SDK, and explicitly unset signing-related environment names.
- Both detached worktrees are clean before and after assembly except for ignored
  build outputs, and both resolve to the same committed source identity.
- Neither build executes a signing-validation task; installed `apksigner verify`
  rejects both APKs as unsigned.
- Both APK manifests identify `io.github.cottenplant.mobyfiles`, use release
  configuration, and contain no upstream application ID as the install package.
- The two APKs have equal sizes and SHA-256 digests and compare byte-for-byte.
  If they initially differ, the report identifies the differing entries and a
  minimal in-scope fix is verified by another two-build comparison.
- Normal release-signing behavior, application/build inputs, dependencies,
  locks, verification metadata, wrapper, CI, and Fastlane metadata remain
  unchanged unless a minimal determinism correction is documented.
- `git diff --check` passes; commits contain no trailers; and Phase 3B tracked
  changes are limited to this contract, the unsigned-release init script,
  conditional diagnostic signing wiring, an optional minimal determinism
  correction, and the Phase 3B report.

## User-run tests

None. The diagnostic APKs are unsigned and must not be installed. Signed-device
acceptance begins only after a separate signing and key-custody contract.

## Dependencies and unresolved decisions

- SDK-backed acceptance requires explicit command-boundary approval for read-only
  `/Users/samco/Library/Android/sdk` access. No SDK installation is authorized.
- Offline acceptance depends on the retained repository-local Gradle and
  dependency state. A cache miss stops this phase for a new contract decision.
- Byte-identical output is an empirical acceptance requirement, not assumed from
  Android Gradle Plugin behavior. Any difference must be localized before the
  phase can complete.
- Signing and key custody, certificate publication, update channels, store
  metadata, publication, and device acceptance remain separate contracts.
