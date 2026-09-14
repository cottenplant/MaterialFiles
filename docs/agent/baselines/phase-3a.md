# Phase 3A CI hardening

Recorded: 2026-09-14

## Implementation

The Phase 3A workflow implementation was completed at
`393bec5fa3a04790271b9db124715d5299adf344`. Its commits are:

- `98e6925a` defines the Phase 3A contract; and
- `393bec5f` replaces the inherited workflow with a least-privilege,
  flavor-aware verification job.

The workflow now runs on pushes and pull requests. It explicitly grants only
`contents: read`, cancels superseded runs for the same workflow and ref, uses
the fixed `ubuntu-24.04` runner label, and limits the build job to 45 minutes.
Checkout does not persist credentials. The inherited submodule request was
removed because the repository has neither a tracked `.gitmodules` file nor a
gitlink.

The Java step is consistently named and configured for Temurin 21. The Gradle
step uses the checked-in wrapper with `--no-daemon`, strict dependency
verification, and explicit tasks for:

- the Moby debug build (`assembleMobyDebug`);
- Moby unit tests (`testMobyDebugUnitTest`);
- Moby debug lint (`lintMobyDebug`); and
- the retained upstream comparison build (`assembleUpstreamDebug`).

Those task names previously completed offline in the Phase 2A through 2C
acceptance work. Phase 3A does not change application or Gradle configuration.

The inherited `actions/upload-artifact` step and obsolete pre-flavor
`app/build/outputs/apk/debug/app-debug.apk` path were removed. The workflow
therefore produces no retained or downloadable CI artifact and remains a source
verification boundary rather than a release path.

## Action provenance

Public, unauthenticated inspection was limited to the two official GitHub action
repositories. Their official release pages identified the current releases and
linked them to these full commits:

| Action | Release | Pinned commit |
| --- | --- | --- |
| [`actions/checkout`](https://github.com/actions/checkout/releases/tag/v7.0.1) | `v7.0.1` | [`3d3c42e5aac5ba805825da76410c181273ba90b1`](https://github.com/actions/checkout/commit/3d3c42e5aac5ba805825da76410c181273ba90b1) |
| [`actions/setup-java`](https://github.com/actions/setup-java/releases/tag/v6.0.1) | `v6.0.1` | [`de7274f081f381c8f8158605e0321c36c376e2e6`](https://github.com/actions/setup-java/commit/de7274f081f381c8f8158605e0321c36c376e2e6) |

Both official commit pages reported GitHub-verified signatures. The workflow
uses the full 40-character revisions rather than mutable major-version tags;
the release versions remain as review comments beside each pin.

## Offline acceptance

Static checks at the final Phase 3A tree confirmed:

- Ruby's installed YAML parser accepts `.github/workflows/android.yml`;
- both and only `uses:` entries contain full 40-character commit revisions and
  adjacent semantic release comments;
- push and pull-request triggers, `contents: read`, concurrency cancellation,
  the fixed runner label, timeout, JDK 21, non-persisted checkout credentials,
  strict dependency verification, and all four explicit Gradle tasks are
  present;
- the workflow contains no artifact upload, secret reference, write permission,
  mutable action tag, `ubuntu-latest`, or submodule request;
- no tracked `.gitmodules` file or gitlink exists;
- application and build configuration, dependency locks and verification
  metadata, wrapper files, app sources and resources, public documentation,
  signing configuration, and Fastlane metadata are unchanged from `17e69612`;
- the Phase 3A path set is limited to the workflow, its contract, and this
  report;
- `git diff --check` passes; and
- all Phase 3A commit messages have empty bodies and no trailers.

No Gradle, Android SDK, APK, signing, device, emulator, or remote GitHub Actions
run was used. A local Android build would repeat previously verified tasks while
requiring access excluded by this contract; hosted-runner behavior can only be
confirmed after the user publishes the commits.

## Deferred work and boundaries

- The user should inspect one published push run and one pull-request run,
  confirming successful tasks, read-only token permissions, concurrency
  cancellation, and the absence of downloadable artifacts.
- GitHub-hosted runner labels do not pin immutable virtual-machine contents.
  The explicit `ubuntu-24.04` label reduces drift but does not eliminate it.
- Fresh CI runners must download the checksum-pinned Gradle distribution and
  strictly verified locked dependencies. Full reproducibility remains separate
  work.
- The user must still confirm `io.github.cottenplant.mobyfiles` before the first
  signed distribution and establish a private vulnerability-reporting channel.
- Reproducible unsigned release assembly, permanent signing and key custody,
  certificate publication, update channels, store metadata, screenshots,
  publication, and signed-device acceptance remain separate contracts.
- Credential-at-rest encryption, Android backup exclusions, cleartext-network
  hardening, and plain FTP compatibility remain behavioral security projects.

No local SDK or cache outside the repository, Android device, personal
infrastructure, authenticated service, signing material, ignored local
configuration, secret-bearing environment value, or private security content
was accessed. Public network reads were restricted to official, unauthenticated
GitHub pages for the two pinned actions. No remote repository state was mutated.

Privacy incidents or near misses: none.
