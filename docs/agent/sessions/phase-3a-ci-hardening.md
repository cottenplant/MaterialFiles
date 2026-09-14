# Session contract: Phase 3A — CI hardening

Status: complete

Working branch: `master`

## Objective

Replace the inherited Android workflow with a Moby-owned verification boundary
that is flavor-aware, least-privilege, and pinned to reviewable action revisions.
Keep CI diagnostic-only: it may compile and inspect the tracked source, but it
must not publish an APK, sign an artifact, or imply a release channel.

## Allowed in this contract

- Read and write this contract, `.github/workflows/android.yml`, and a tracked
  Phase 3A report.
- Read tracked Gradle configuration, dependency locks and verification metadata,
  prior agent reports, and public repository documentation only as needed to
  identify supported flavor tasks and verify the CI boundary.
- Add pull-request verification alongside push verification, explicit read-only
  workflow permissions, a fixed hosted-runner label, bounded execution time,
  and cancellation of superseded runs.
- Pin first-party GitHub actions to full commit revisions with nearby release-tag
  comments, disable persisted checkout credentials, and remove inherited
  checkout or artifact behavior that the repository does not need.
- Run explicit Moby and upstream diagnostic build tasks, Moby unit tests, and
  Moby lint with the pinned Gradle wrapper and strict dependency verification.
- Read public, unauthenticated pages in the official `actions/checkout` and
  `actions/setup-java` GitHub repositories only to verify release-tag and commit
  provenance. Do not use shell network access or authenticated services.
- Execute offline YAML, text, Git, and diff checks using only tracked workspace
  files and already installed host tools that do not read outside the repository.
- Make small local commits on `master` with concise Conventional Commit subjects
  and no trailers.

## Safety conditions

- Give the workflow only `contents: read`; do not add write permissions, secrets,
  credentials, attestations, deployments, publication, or release automation.
- Do not persist the GitHub token in the checkout and do not expose tokens or
  repository secrets to Gradle commands.
- Treat all generated APKs and reports as ephemeral diagnostics. Do not upload,
  sign, retain, publish, or install them.
- Use strict dependency verification and the checked-in dependency locks. Do not
  weaken verification, add repositories, or introduce mutable dependency inputs.
- Do not access a local Android SDK, Gradle cache outside the repository, Android
  device, emulator, personal infrastructure, signing material, ignored local
  configuration, or secret-bearing environment values.
- Do not run or mutate GitHub Actions, remote branches, checks, pull requests,
  repository settings, secrets, tags, or releases.

## Explicit non-goals

- No application source, resource, manifest, dependency, lockfile, verification
  metadata, Gradle configuration, wrapper, signing configuration, Fastlane
  metadata, version, package identity, branding, or runtime behavior change.
- No release build, reproducibility claim, artifact upload, signing, certificate,
  provenance attestation, update channel, store metadata, screenshot, publication,
  tag, or release.
- No permanent signing or key-custody design and no confirmation of the
  provisional `io.github.cottenplant.mobyfiles` application ID.
- No private vulnerability-reporting setup or repository-settings change.
- No remote fetch, push, pull request, issue, workflow dispatch, or CI observation.
- No Android SDK, Gradle execution, device, emulator, LAN, VPN, or protocol test.

## Deliverables

- A flavor-aware Android workflow that verifies the Moby debug build, Moby unit
  tests, Moby lint, and the retained upstream comparison build.
- Immutable action references, least-privilege permissions, non-persisted
  checkout credentials, a fixed runner image, and bounded/concurrent execution.
- No CI artifact upload or other distribution path.
- A Phase 3A report recording action provenance, workflow design, static
  acceptance results, and all intentionally deferred runtime verification.

## Acceptance checks

- The workflow runs for pushes and pull requests with top-level
  `permissions: contents: read` and no broader permission.
- The runner label and Java version are explicit, and the step name agrees with
  the configured Java version.
- Every `uses:` reference is a full 40-character commit SHA whose adjacent
  comment identifies an official release tag verified from the action's
  official repository.
- Checkout sets `persist-credentials: false` and does not request submodules when
  this repository has no tracked `.gitmodules` file or gitlink.
- The Gradle command uses `--no-daemon` and strict dependency verification, and
  explicitly covers `assembleMobyDebug`, `testMobyDebugUnitTest`,
  `lintMobyDebug`, and `assembleUpstreamDebug`.
- The workflow has a finite job timeout, cancels superseded runs for the same
  workflow and ref, and contains no artifact upload, signing, publication,
  secret reference, write permission, or unpinned action.
- Application/build configuration, dependency locks and verification metadata,
  wrapper files, app sources and resources, documentation, signing configuration,
  and Fastlane metadata are unchanged.
- The workflow parses as YAML with an available offline parser;
  `git diff --check` passes; commits contain no trailers; and tracked changes are
  limited to this contract, the workflow, and the Phase 3A report.

## User-run tests

After the user publishes these commits, they should inspect one push run and one
pull-request run in GitHub Actions. Confirm that all four Gradle tasks complete,
the token permissions are read-only, superseded runs cancel as expected, and no
APK or other artifact is offered for download. Share only sanitized logs if a
failure needs diagnosis.

## Dependencies and unresolved decisions

- Static local acceptance can establish workflow structure and task names from
  prior verified reports, but actual hosted-runner behavior cannot be confirmed
  without a user-published GitHub Actions run.
- The workflow necessarily resolves the pinned Gradle distribution and verified
  dependencies on a fresh hosted runner. Dependency locks and strict verification
  constrain those downloads; full build reproducibility remains separate work.
- Hosted runner images are selected by GitHub labels rather than immutable image
  digests. This phase fixes the label to reduce drift but cannot make the runner
  image content immutable.
- Reproducible unsigned release assembly, signing and key custody, certificate
  publication, update channels, store metadata, and device acceptance remain
  separate contracts.

## Result

Phase 3A completed on 2026-09-14. The Android workflow now verifies explicit
Moby and upstream diagnostic tasks on pushes and pull requests with read-only
permissions, immutable action revisions, non-persisted checkout credentials, a
fixed runner label, and bounded concurrent execution. It no longer uploads an
unsigned APK or implies a distribution path. The implementation and static
acceptance evidence are recorded in
`docs/agent/baselines/phase-3a.md`.
