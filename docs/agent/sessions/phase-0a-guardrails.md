# Session contract: Phase 0A — guardrails

Status: complete

Branch: `codex/phase-0-guardrails`

## Objective

Establish enforceable project-scoped Codex defaults and written rules of
engagement before changing application source, dependencies, build tooling, or
remote repository state.

## Allowed in this contract

- Read Material Files source and repository metadata inside the workspace.
- Read only Mobyverse's public README, palette source, and generated branding
  assets for a preliminary design recommendation.
- Add `AGENTS.md`, `.codex/config.toml`, `.codex/rules/privacy.rules`, and this
  contract to Material Files.
- Create the local feature branch named above.
- Run offline syntax and policy validation against the new files.

## Explicit non-goals

- No application, Gradle, CI, dependency, package ID, or branding changes.
- No remote fetch, push, pull request, issue, tag, or release.
- No SDK installation or dependency download.
- No Android device, LAN, VPN, Samba, SFTP, WebDAV, FTP, or cluster access.
- No reading of local agent settings, credentials, signing material, or SSH data.

## Deliverables

- Repository-scoped agent policy with privacy and Git boundaries.
- A requirement to launch future sessions from the Material Files Git root so
  sibling repositories are not part of the writable sandbox.
- Workspace-write sandbox configuration with shell networking disabled.
- Secret-bearing environment variables filtered from child processes.
- Command rules forbidding remote mutation and personal-infrastructure access.
- A repeatable session-contract format for later phases.

## Acceptance checks

- TOML parses successfully.
- Codex exec-policy rules pass their inline tests when a compatible Codex CLI is
  available; otherwise validation is handed back without installing anything.
- `git diff --check` passes.
- Only the intended guardrail files are changed on the feature branch.

## Deferred contract: Phase 0B — reproducible baseline

Phase 0B should request approval for only the exact public upstream fetch and
Android SDK/cache paths it needs. Its scope should be:

1. Add the public `upstream` remote over HTTPS and fetch tags.
2. Pin baseline commit identifiers and record the dependency/toolchain matrix.
3. Repair the dav4jvm full-SHA dependency resolution in an isolated commit.
4. Build current master and v1.7.4 from clean worktrees.
5. Produce user-run commands for any device or network smoke tests.

## Decisions intentionally deferred

- Final application name and application ID.
- Mobyverse cargo glyph and palette.
- Obtainium versus a later Accrescent submission.
- Signing-key creation and storage.
