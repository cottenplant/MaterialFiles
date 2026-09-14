# Session contract: Phase 3E — repository identity migration

Status: complete

Working branch: `master`

## Objective

Migrate the active Moby Files repository identity from the former public GitHub
location `cottenplant/MaterialFiles` to the user-confirmed renamed repository
`cottenplant/MobyFiles`. Update the README signing status after the completed
Phase 3D ceremony, repoint the local `origin`, and verify the public links that
the active fork documentation and app expose before any APK is signed.

## Allowed in this contract

- Read and write this contract, the English and Chinese root READMEs,
  `SECURITY.md`, `PRIVACY.md`, the Moby identity resource, and a tracked Phase
  3E report.
- Read tracked agent reports, signing documentation, repository metadata, and
  active public-link sources only as needed to verify the rename, signing-status
  wording, and unchanged release boundary.
- Replace active fork-facing URLs under `cottenplant/MaterialFiles` with their
  corresponding `cottenplant/MobyFiles` locations.
- State that the permanent signing key passed the private Phase 3D ceremony and
  recovery gates while making clear that no Moby release has yet been signed.
- Change the existing local `origin` fetch and push URL to
  `git@github.com:cottenplant/MobyFiles.git`; leave `upstream` unchanged.
- Use public, unauthenticated web requests only to verify the external links in
  the active English and Chinese READMEs, `SECURITY.md`, `PRIVACY.md`,
  `docs/release-signing.md`, and the Moby identity resource. Do not authenticate,
  follow account-specific links, submit forms, or mutate a remote service.
- Execute offline text, XML, relative-link, Git, and diff checks using only
  tracked workspace files and already installed tools that require no SDK,
  dependency download, signing material, or external cache.
- Make small local commits on `master` with concise Conventional Commit subjects
  and no trailers.

## Safety conditions

- Treat historical contracts and baseline reports as time-stamped evidence. Do
  not rewrite an old URL when it records the repository location or migration
  state that was true during an earlier phase.
- Do not fetch, pull, push, tag, publish a release, open or modify an issue or
  pull request, trigger a workflow, or otherwise mutate GitHub.
- Do not access credentials, signing material, certificate values, ceremony
  records, ignored local configuration, secret-bearing environment values,
  personal infrastructure, Android devices, or files outside the repository.
- Do not claim that an APK, signed release, update channel, device-qualified
  build, private security channel, or public certificate exists.
- Do not change application or signing identity. The permanent application ID
  remains `io.github.cottenplant.mobyfiles`, and Phase 3D remains verified only
  by sanitized human attestations.

## Explicit non-goals

- No APK or app bundle assembly, signing, verification, installation, device
  acceptance, distribution, certificate publication, tag, or release.
- No source behavior, Java/Kotlin namespace, application ID, app name, branding,
  manifest, permission, version, dependency, lockfile, verification metadata,
  wrapper, build, signing, CI, or Fastlane change.
- No private-reporting facility, update channel, store listing, release notes,
  screenshot, compatibility claim, or support-policy expansion.
- No historical-document rewrite solely to remove superseded URL strings.
- No Android SDK, Gradle, JDK, signing tool, device, emulator, LAN, VPN, or
  personal-protocol access.

## Deliverables

- Active source, privacy, and issue-tracker links that target
  `https://github.com/cottenplant/MobyFiles`.
- English and Chinese README status text that records successful private key
  ceremony and recovery without claiming that the key has signed a release.
- A local `origin` that fetches from and pushes to the renamed SSH repository;
  the upstream remote remains unchanged.
- A Phase 3E report recording the migration, public-link results, static
  acceptance, verification limits, and deferred release work.

## Acceptance checks

- The Moby identity resource uses the renamed repository root and privacy URL,
  and the public issue links in `SECURITY.md` and `PRIVACY.md` use the renamed
  issue tracker.
- Both READMEs say that the permanent key passed the private Phase 3D ceremony
  and recovery gates and has not yet been used to sign a Moby release.
- Active public surfaces contain no fork-facing
  `github.com/cottenplant/MaterialFiles` URL. Historical agent records remain
  unchanged and are excluded from this assertion.
- Every repository-relative Markdown link in the active public documents points
  to an existing tracked file.
- Every external HTTP(S) link in the active public documents and Moby identity
  resource is checked through public unauthenticated access; redirects and any
  unverifiable result are recorded rather than silently treated as success.
- `origin` has identical fetch and push URLs at the renamed SSH location, while
  `upstream` remains `https://github.com/zhanghai/MaterialFiles.git`.
- Application/build inputs and all other protected paths are unchanged except
  for the authorized Moby identity resource.
- `git diff --check` passes; commits contain no bodies or trailers; and Phase 3E
  tracked changes are limited to this contract, the two READMEs, `SECURITY.md`,
  `PRIVACY.md`, the Moby identity resource, and the Phase 3E report.

## User-run tests

After the commits are published, the user should confirm from a browser that
the renamed repository, privacy notice, issue tracker, and private
vulnerability-reporting availability appear as intended. Do not share
authenticated URLs, account details, vulnerability content, or credentials.

No APK, device, signing, or network-protocol test belongs to this phase.

## Dependencies and unresolved decisions

- The user reports that GitHub has already renamed the public repository to
  `cottenplant/MobyFiles`. Public unauthenticated checks can establish link
  reachability but cannot verify account-only settings or repository ownership.
- GitHub may redirect old URLs after a rename. Active fork surfaces will still
  use the canonical new URL so they do not depend on redirect continuity.
- Whether GitHub private vulnerability reporting is enabled remains a user-run
  account-level check; the existing safe public no-details fallback remains.
- The first signed release candidate, certificate/provenance publication,
  device acceptance, developer verification, update channels, and distribution
  require later contracts.

## Result

Phase 3E completed on 2026-09-15. Commit `748c9aaa` migrated the active fork
URLs and updated both READMEs with the Phase 3D signing-key status without
claiming a signed release. The local `origin` uses the renamed SSH repository
for fetch and push, while `upstream` remains unchanged.

All 11 external links in the active public documents and Moby identity resource
had already been confirmed reachable through public unauthenticated access in
the interrupted Phase 3E session. The resumed session did not repeat those
network checks. Offline acceptance, the verification limits, deferred release
work, and the anonymous-cookie privacy near miss are recorded in
`docs/agent/baselines/phase-3e.md`. No signed-release-candidate contract was
started.
