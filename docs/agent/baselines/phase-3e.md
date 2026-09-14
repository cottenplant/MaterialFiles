# Phase 3E repository identity migration

Recorded: 2026-09-15

## Result

The Phase 3E repository identity migration was implemented by commit
`748c9aaacfe9cf4565daaf7e0402bb0b38569c09`.

- Active source, privacy, and issue-tracker URLs now use the renamed
  `cottenplant/MobyFiles` GitHub repository.
- Both public READMEs record that the permanent signing key passed the private
  Phase 3D ceremony and recovery gates on the basis of sanitized human
  attestations, while explicitly stating that it has not signed a Moby release.
- The local `origin` fetch and push URLs are both
  `git@github.com:cottenplant/MobyFiles.git`.
- `upstream` remains `https://github.com/zhanghai/MaterialFiles.git`.

The Java/Kotlin namespace, application ID, signing identity, release status,
and upstream relationship are unchanged.

## Public-link verification

The interrupted Phase 3E session checked the 11 distinct external URLs exposed
by the English and Chinese READMEs, `SECURITY.md`, `PRIVACY.md`,
`docs/release-signing.md`, and the Moby identity resource through public,
unauthenticated access. The user confirmed that all 11 had been found reachable:

| URL | Result |
| --- | --- |
| `https://developer.android.com/build/configure-app-module` | Reachable |
| `https://developer.android.com/developer-verification/guides/faq` | Reachable |
| `https://developer.android.com/studio/publish/app-signing` | Reachable |
| `https://developer.android.com/tools/apksigner` | Reachable |
| `https://developer.android.com/tools/zipalign` | Reachable |
| `https://docs.oracle.com/en/java/javase/21/docs/specs/man/keytool.html` | Reachable |
| `https://github.com/cottenplant/MobyFiles` | Reachable |
| `https://github.com/cottenplant/MobyFiles/blob/master/PRIVACY.md` | Reachable |
| `https://github.com/cottenplant/MobyFiles/issues` | Reachable |
| `https://github.com/zhanghai/MaterialFiles` | Reachable |
| `https://github.com/zhanghai/MaterialFiles/security/policy` | Reachable |

No link was reported as unverifiable. The resumed session relied on the
completed reachability results and performed no additional network request,
redirect check, or response-header capture. These checks do not establish
repository ownership, account-only settings, or private vulnerability-reporting
availability.

## Offline static acceptance

Repository-only checks at Phase 3E closure confirmed:

- the active public surfaces contain no fork-facing
  `github.com/cottenplant/MaterialFiles` URL;
- the Moby identity resource contains the renamed source and privacy URLs, and
  the public privacy and security documents contain the renamed issue URL;
- both READMEs contain the Phase 3D ceremony/recovery status and the explicit
  not-yet-signed limitation;
- every repository-relative Markdown link in the active public documents names
  an existing tracked file;
- the active public documents and Moby identity resource contain exactly the 11
  distinct external URLs recorded above;
- the local `origin` fetch and push URLs are identical and use the renamed SSH
  repository, while `upstream` is unchanged;
- the application ID remains `io.github.cottenplant.mobyfiles`;
- the identity XML parses successfully with `xmllint`;
- a content-silent tracked-file scan found no common HTTP status-line or
  response-header signatures, including cookie-header signatures;
- implementation changes from the contract commit are limited to `README.md`,
  `README_zh-CN.md`, `SECURITY.md`, `PRIVACY.md`, and
  `app/src/moby/res/values/identity.xml`;
- application and build inputs and all other protected paths are unchanged;
- `git diff --check` passes; and
- the implementation commit has a concise Conventional Commit subject and no
  message body or trailer.

No Gradle, JDK, Android SDK, APK build, signing tool, device, emulator, private
storage, or personal-infrastructure check was appropriate or performed. The
resumed session made no network request.

## Deferred work and boundaries

- The user should confirm after publication that the renamed repository,
  privacy notice, issue tracker, and private vulnerability-reporting
  availability appear as intended.
- The first signed release candidate requires a separate contract. No such
  contract was created or started in Phase 3E.
- APK assembly, signing, verification, installation, GrapheneOS device
  acceptance, certificate and provenance publication, developer-verification
  decisions, update channels, distribution, tags, releases, and remote
  repository mutation remain deferred.
- The key ceremony and recovery status continues to rely on the sanitized Phase
  3D human attestations; no key or certificate identity was exposed for
  repository verification.

No signing material, certificate identity, user/account credential,
secret-bearing environment value, ignored local configuration, Android device,
personal infrastructure, or unrelated private data was accessed.

Privacy incidents: none.

Privacy near miss: during the interrupted Phase 3E session, a header-based
public `curl` check displayed anonymous cookie values generated by GitHub. They
were not user or account credentials, were not retained or written to tracked
files, and do not indicate credential rotation. The values are intentionally
not reproduced. The resumed session avoided further header-based checks, used
the already completed reachability results, and confirmed without emitting
file contents that no response-header signatures entered tracked files.
