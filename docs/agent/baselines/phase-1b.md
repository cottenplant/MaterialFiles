# Phase 1B Gradle wrapper integrity

Recorded: 2026-09-13

## Source identity and change

The wrapper checksum implementation was validated at
`8df6abf5230537c90c59d7c5202361cc3aa9a842` in a clean detached worktree.
Local `master` was then a documentation-only descendant at
`f7d3e7184d475bf16739f88ef04500c89ffb7e5d`.

`gradle/wrapper/gradle-wrapper.properties` now pins the existing
`gradle-9.3.1-all.zip` distribution with:

```properties
distributionSha256Sum=17f277867f6914d61b1aa02efab1ba7bb439ad652ca485cd8ca6842fccec6e43
```

No Gradle version, distribution URL, wrapper JAR, Android build configuration,
application source, or dependency changed.

## Checksum and offline wrapper verification

Phase 0B obtained the checksum from Gradle's official public checksum endpoint
and verified the retained repository-local archive against it. Phase 1B
recomputed SHA-256 for that archive and obtained the same value:

`17f277867f6914d61b1aa02efab1ba7bb439ad652ca485cd8ca6842fccec6e43`

The archive was copied into a fresh Gradle user home under
`.gradle/phase-1b/`. Before the wrapper ran, that home contained only the ZIP:
there was no expanded distribution or `.ok` marker to reuse. The copied ZIP's
locally computed SHA-256 also matched the pinned value.

With the four signing-related environment names unset, this command succeeded
from the clean detached worktree:

```text
GRADLE_USER_HOME=<repository>/.gradle/phase-1b/gradle-user-home ./gradlew --offline --version
```

The wrapper reported Gradle 9.3.1, revision
`44f4e8d3122ee6e7cbf5a248d7e20b4ca666bda3`, and created the expected `.ok`
marker beside the retained ZIP. It did not configure the Android project or run
an Android task. No APK or other application artifact was produced.

## Deferred work

- Dependency locking and dependency verification metadata remain separate
  supply-chain contracts.
- The broad dependency-license audit and remediation of 873 non-fatal lint
  warnings remain deferred.
- Application identity, Mobyverse branding, fork privacy documentation,
  signing, update channels, and distribution remain deferred.

## Privacy and boundary report

Verification used no network, Android SDK, device, personal infrastructure,
signing material, authenticated service, or Gradle cache outside this
repository. It used the existing host shell, checksum utility, and JDK only as
permitted by the contract.

An initial command form using `env -u` was rejected by the repository safety
policy before execution because the `env` utility is conservatively blocked.
No environment value was read or printed. The successful invocation instead
used shell-local `unset` operations.

Privacy incidents or near misses: none.
