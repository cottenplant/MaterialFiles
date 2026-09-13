# Session contract: Phase 2D — public repository identity

Status: complete

Working branch: `master`

## Objective

Make the public repository describe Moby Files rather than presenting inherited
Material Files release, download, maintainer, and security-contact information
as though it belonged to this fork. Preserve clear upstream attribution and
license notices while stating that this repository currently publishes source,
not an installable Moby release.

## Allowed in this contract

- Read and write this contract, the root English and Chinese READMEs,
  `SECURITY.md`, and a tracked Phase 2D report.
- Read tracked build configuration, manifests, prior agent reports, privacy
  documentation, and repository metadata only as needed to verify public claims
  about the fork name, application ID, platform boundary, provenance, release
  status, privacy boundary, and support ownership.
- Replace inherited fork-facing badges, download links, first-person upstream
  maintainer claims, and upstream security contact guidance with accurate Moby
  source-only status and fork-specific reporting guidance.
- Retain direct links to the upstream Material Files project and clearly credit
  its architecture, implementation, translations, assets, history, and
  copyright.
- Execute offline text, link-target, Git, and diff checks using only tracked
  workspace files.
- Make small local commits on `master` with concise Conventional Commit subjects
  and no trailers.

## Safety conditions

- Use no network and do not open any public link.
- Do not inspect remote configuration beyond already known, non-secret tracked
  or previously confirmed public repository locations.
- Do not inspect credentials, signing material, ignored local configuration,
  secret-bearing environment values, personal infrastructure, authenticated
  services, or files outside the repository.
- Do not claim that a private vulnerability-reporting channel, signed artifact,
  release, update path, compatibility result, or support commitment exists
  unless tracked evidence establishes it.
- Do not direct fork-specific reports to the upstream maintainer. Distinguish
  issues introduced by Moby from issues reproducible in unmodified upstream.

## Explicit non-goals

- No application source, resource, manifest, dependency, build configuration,
  signing configuration, workflow, Fastlane metadata, version, or generated
  asset change.
- No application ID, namespace, app name, branding, privacy-policy, permission,
  protocol, provider, storage, credential, network, or runtime behavior change.
- No permanent signing, release artifact, update channel, CI redesign, store
  listing, screenshot, publication, tag, or release.
- No comprehensive contributor, translation, build, installation, or end-user
  support guide.
- No remote fetch, push, pull request, issue, tag, or release.
- No Android SDK, Gradle, device, emulator, LAN, VPN, or protocol access.

## Deliverables

- English and Chinese repository landing pages that identify Moby Files as a
  source-only FOSS fork, describe its current differences and boundaries, and
  do not offer upstream packages as Moby downloads.
- Clear upstream Material Files attribution and links without speaking in the
  upstream author's first person.
- A fork-specific security policy that removes the inherited upstream email
  address, gives a safe no-secrets public contact fallback, and routes genuinely
  upstream issues to upstream policy.
- A Phase 2D report recording the claim audit and acceptance results.

## Acceptance checks

- Both READMEs consistently name Moby Files, identify
  `io.github.cottenplant.mobyfiles` as provisional until the first signed
  distribution, and state that no Moby APK or release is currently published.
- Neither README presents Google Play, F-Droid, upstream GitHub releases,
  upstream CI, Transifex, or upstream maintainer statements as fork services.
- Privacy and security links resolve to tracked repository files, and upstream
  links are labeled as upstream.
- `SECURITY.md` does not expose or direct fork reports to the inherited upstream
  email address; it warns reporters not to place vulnerability details or
  secrets in a public issue.
- GPL-3.0-or-later terms, Hai Zhang's upstream copyright, and upstream project
  attribution remain clear.
- App sources, resources, build/signing configuration, dependency locks,
  verification metadata, workflows, and Fastlane metadata are unchanged.
- `git diff --check` passes, commits contain no trailers, and tracked changes
  are limited to this contract, the two READMEs, `SECURITY.md`, and the Phase 2D
  report.

## User-run tests

None. This documentation-only phase produces no APK and does not require a
device or network. The user should review public links after publication without
sharing authenticated URLs or credentials.

## Dependencies and unresolved decisions

- `io.github.cottenplant.mobyfiles` remains provisional until the user confirms
  it before the first signed distribution. The documentation must not imply
  that an update-compatible release lineage already exists.
- No private Moby vulnerability-reporting address or verified GitHub private
  vulnerability-reporting facility is available in tracked evidence. Until the
  user establishes one, reporters need a public, no-details request-for-contact
  fallback.
- CI, reproducible unsigned release assembly, permanent key custody, artifact
  signing, certificate publication, update channels, store metadata, and device
  acceptance remain separate contracts.

## Result

Phase 2D completed on 2026-09-13. The repository landing pages now identify the
source-only Moby Files fork in English and Chinese, preserve upstream
attribution without presenting upstream services as Moby services, and direct
fork vulnerability reports through a fork-owned, no-secrets disclosure path.
The implementation and offline acceptance evidence are recorded in
`docs/agent/baselines/phase-2d.md`.
