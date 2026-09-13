# Security policy

## Supported versions

Moby Files has not published a signed release yet. Security work currently
targets the tracked `master` branch. A versioned support policy will be defined
before a public release; this file does not promise support for automated build
artifacts or third-party packages.

## Reporting a Moby Files vulnerability

If this repository offers GitHub's private **Report a vulnerability** control,
use it for confidential reports. No other private Moby Files reporting channel
has been established in the tracked repository.

If private reporting is unavailable, open a minimal request for private contact
in the fork's [public issue tracker](https://github.com/cottenplant/MaterialFiles/issues).
State only that you may have found a security issue and whether it appears
specific to Moby Files. Do not include vulnerability details, exploit steps,
passwords, private keys, tokens, private hostnames or addresses, confidential
file data, screenshots, or unredacted logs in the public issue. Keep sensitive
details private until a confidential channel is agreed.

Once a private channel is available, a useful report includes:

- the affected Moby Files version or commit;
- the Android and device version, without private device identifiers;
- clear reproduction steps and the expected versus observed behavior;
- the security impact and any known preconditions; and
- the smallest sanitized logs or proof of concept needed to reproduce it.

## Upstream vulnerabilities

If the issue reproduces in an unmodified build of
[upstream Material Files](https://github.com/zhanghai/MaterialFiles), follow the
[upstream security policy](https://github.com/zhanghai/MaterialFiles/security/policy).
Upstream reporting channels belong to that project and should not be used for a
Moby-only change. If the origin is uncertain, use the Moby Files no-details
contact request above and state that uncertainty.

For privacy questions that are not vulnerabilities, see the
[Moby Files privacy notice](PRIVACY.md).
