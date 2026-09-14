# Phase 3D key ceremony and recovery verification

Recorded: 2026-09-15

## Result

The release owner completed the private, user-operated Moby Files key ceremony
and recovery gate defined by the Phase 3D contract. The user returned only the
permitted sanitized statuses and confirmed that no excluded data was included:

| Check | Status |
| --- | --- |
| `D1` | `PASS` |
| `D2` | `PASS` |
| `D3` | `PASS` |
| `D4` | `PASS` |
| `D5` | `PASS` |
| `D6` | `PASS` |
| `D7` | `PASS` |
| `D8` | `PASS` |

On the basis of those attestations, the permanent key passed the required
offline-environment, new-destination, fixed-profile, private-record,
protected-backup, independent-restore, cleanup, and privacy gates. It is now
eligible for use under a separate contract that expressly authorizes the first
signed release candidate.

## Verification boundary

Phase completion relies on the release owner's human attestations. The agent
did not access or independently verify the signing key, keystore, public
certificate, fingerprint, expiry, credentials, private ceremony record,
storage paths, backup media, restored copies, private environment, or command
output. No signing or recovery tool was run through the agent, and no evidence
value was requested or recorded in the repository.

## Offline static acceptance

Repository-only checks at Phase 3D closure confirmed:

- the fixed Phase 3C application ID and signing profile remain unchanged;
- `docs/release-signing.md` is unchanged from the committed Phase 3D starting
  point;
- no application, build, identity, version, dependency, lock, verification,
  wrapper, CI, source, resource, manifest, signing-configuration, or Fastlane
  path changed during Phase 3D;
- no new tracked or staged path resembles a keystore, signing property file,
  certificate output, signed artifact, recovery record, password, or private
  key;
- Phase 3D closure changes are limited to its contract and this report;
- `git diff --check` passes; and
- the closure commit uses a concise Conventional Commit subject without a
  message body or trailer.

No Gradle, JDK, Android SDK, `keytool`, signing tool, APK build, network, device,
emulator, private storage, or personal-infrastructure check was appropriate or
performed by the agent.

## Deferred work and boundaries

- A new contract must authorize the first signed release candidate, including
  version selection, two guarded byte-identical unsigned builds, detached
  signing, non-secret artifact inspection, sanitized release records, and
  signed-output reproducibility checks.
- Installation, GrapheneOS owner-device acceptance, coexistence, authenticated
  upgrade testing, private pilot distribution, public release, certificate and
  provenance publication, developer-verification decisions, and update-channel
  policy remain separate gates.
- The release owner reported that the public GitHub repository was renamed to
  `cottenplant/MobyFiles`. Updating the local `origin`, tracked source/privacy
  URLs, and stale source-only signing statements requires a separate contract.
- Credential-at-rest protection, Android backup handling, cleartext networking,
  and plain FTP remain documented behavioral security decisions for later
  work.
- The Phase 3A published push and pull-request CI checks remain user-run.

No signing material, certificate-bearing output, ignored signing
configuration, secret-bearing environment value, private path, backup detail,
Android device, personal infrastructure, or unrelated private data was
accessed.

Privacy incidents or near misses: none.
