# Material Files fork: agent rules of engagement

These rules apply to every agent working in this repository. They supplement the
user's instructions and never weaken system, sandbox, or approval constraints.

## Mission

Maintain a privacy-respecting, fully FOSS Material Files distribution for a
GrapheneOS Pixel 9 Pro on Android 17. Preserve the upstream NIO2 provider design,
keep technical changes suitable for upstream discussion, and isolate personal
branding, package identity, signing, and distribution changes.

Priorities, in order:

1. User safety, privacy, and secret protection.
2. Correctness and reproducibility.
3. Security maintenance.
4. Small, reviewable, upstream-friendly changes.
5. Delivery speed.

## Filesystem scope

- The hard filesystem boundary is `/Users/samco/codex-lab/foss`.
- Launch Codex from `/Users/samco/codex-lab/foss/MaterialFiles`, not from the
  multi-repository parent. This keeps the automatic workspace-write root limited
  to this Git repository and activates its project-scoped `.codex/` layer.
- Never inspect, search, enumerate, or write outside that directory without a
  specific user approval for an exact path and purpose.
- `MaterialFiles/` is the only writable project unless a session contract says
  otherwise.
- `../File-Manager/` and `../General-Discussion/` are read-only references.
- `../cottenplant/mobyverse/` is read-only. Its README, public design assets, and
  palette/glyph generator sources may be consulted for branding. Do not inspect
  deployment manifests, inventories, local agent settings, virtual environments,
  caches, or infrastructure configuration.
- Do not follow symlinks or path references that escape the boundary.

## Secrets and personal systems

Never read, print, copy, hash, summarize, or pass to a command:

- `~/.ssh`, SSH keys, `known_hosts`, or SSH configuration;
- WireGuard configuration, kubeconfig, cluster credentials, Docker credentials,
  cloud credentials, password stores, browser profiles, or keychains;
- signing keystores, signing property files, recovery material, tokens, cookies,
  `.env` files, or secret-bearing environment variables;
- personal hostnames, private IP inventories, DNS search domains, or mounted
  private shares.

Do not run `env`, `printenv`, shell profile scripts, or broad home-directory
searches. Do not ask the user to paste private keys or unredacted credentials.

## Network and device boundary

- Never scan, probe, discover, mount, or connect to the user's LAN, VPN,
  WireGuard peers, Samba shares, SFTP servers, WebDAV servers, FTP servers,
  Kubernetes cluster, or other personal infrastructure.
- Never use SSH, SCP, SFTP, SMB clients, port scanners, mDNS discovery, ping,
  traceroute, netcat, `kubectl`, `helm`, `k9s`, or WireGuard tooling against the
  user's systems.
- Provide LAN, protocol, GrapheneOS, and ADB tests as commands/checklists for the
  user to run. Use placeholders such as `<test-host>` and request only sanitized
  results.
- ADB or fastboot access to a real device requires explicit approval in the
  current session contract. The default is a user-operated test handoff.
- Public, unauthenticated upstream documentation and source may be read when a
  session contract requires it. Shell network access, dependency downloads,
  SDK installation, and authenticated services require user approval.

## Git and remote services

- Commit directly to local `master` unless the user explicitly selects another
  branch for the session contract.
- Make changes small and commits atomic. Use concise Conventional Commit
  subjects and do not add commit-message trailers. Never mix phases or unrelated
  fixes.
- Never push, force-push, publish a tag or release, open or modify a pull
  request/issue, or mutate any GitHub/GitLab state.
- The user owns publication, remote review, and remote integration.
- Do not rewrite history, delete branches, discard changes, or clean untracked
  files without explicit approval.
- Treat all pre-existing changes as user-owned. Stop if they overlap the task.

## Change policy

- Read the active session contract before editing.
- Do not expand scope because a nearby improvement looks useful. Record it for a
  later contract.
- Keep upstream-compatible changes separate from personal distribution changes.
- Preserve the Java/Kotlin namespace unless a contract explicitly says otherwise;
  personal package identity should normally use an application ID/flavor.
- Keep the build FOSS: no analytics, telemetry, advertising, proprietary runtime
  services, or silent network activity.
- Pin dependencies and CI actions to immutable versions where feasible. Record
  provenance and checksums for manually supplied artifacts.
- Do not weaken TLS, SSH host verification, Android permissions, sandboxing, or
  certificate checks to make tests pass.
- Preserve GPL-3.0 notices and attribution. Reimplement external UX ideas unless
  copying code is deliberate, license-compatible, attributed, and contractually
  approved.

## Commands and approvals

Allowed without extra approval when they stay inside the repository:

- read-only source inspection;
- `git status`, `git diff`, `git log`, `git show`, and local branch inspection;
- edits authorized by the active contract;
- offline formatting, static analysis, and tests that use only workspace files.

Ask before:

- accessing an SDK, cache, or toolchain outside the workspace;
- downloading dependencies or installing software;
- adding or fetching a remote;
- running a destructive or history-rewriting command;
- accessing a connected Android device;
- widening filesystem, network, or repository scope.

If an action is prohibited rather than merely approval-gated, hand it back to the
user instead of asking to bypass the prohibition.

## Session contracts

Every implementation session must have a short contract under
`docs/agent/sessions/` specifying:

- objective and working branch (normally `master`);
- allowed reads, writes, network access, and tools;
- explicit non-goals;
- deliverables and acceptance checks;
- user-run device or network tests;
- dependencies and unresolved decisions.

Stop when the contract is complete. Propose a new contract before beginning the
next major phase.

## Required handoff

Every final handoff must report:

- branch and commit status;
- files changed;
- checks run and their results;
- work deliberately deferred to the user;
- unresolved risks;
- privacy incidents or near misses, explicitly saying `none` when there were none.

If a privacy boundary may have been crossed, stop work immediately, preserve the
evidence without exposing sensitive content, and tell the user exactly what
happened.
