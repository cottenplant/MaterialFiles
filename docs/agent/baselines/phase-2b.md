# Phase 2B visual branding

Recorded: 2026-09-13

## Implementation and isolation

The visual-branding implementation was validated at
`efdaa2c432dfe1b60cff6f8b4f1de5e869e47674` in the clean detached Phase 2B
worktree. Its commits are:

- `55707161` defines the Phase 2B contract;
- `0ddd972d` adds the Moby palette, launcher icon set, splash resources, and
  reproducible raster source;
- `85b73113` preserves the base splash-theme parent on Android 12 and later;
  and
- `efdaa2c4` replaces private Material-library palette overrides with public
  theme attributes.

All runtime changes are under `app/src/moby/res/`. The upstream source set,
Java/Kotlin namespace, application ID wiring, permissions, authorities,
intent actions, shortcuts, NIO2 provider design, Gradle files, dependency lock,
and verification metadata did not change.

## Palette provenance and theme mapping

The public Mobyverse README and `mobyverse/palettes.py` were consulted within
the contract's read-only reference scope. The `deep` source palette observed on
2026-09-13 is:

| Role | Value |
| --- | --- |
| background | `#0D1117` |
| foreground | `#93C5FD` |
| muted | `#6B6190` |
| lime | `#A3E635` |
| periwinkle | `#8B8CFF` |
| whale | `#A5B4FC` |
| pink | `#EC4899` |
| orange | `#F97316` |
| yellow | `#FDE047` |

The Moby flavor packages these values as named source colors. Material 2 uses
an accessible deep-derived indigo (`#5153A5`) for the light primary, lime for
the dark primary, and the deep background for the dark surface. Static Material
3 themes use public `Theme.Material3.*.NoActionBar` parents and public theme
attributes. Their semantic primary, secondary, tertiary, container, surface,
outline, and fixed roles are derived from the source palette while preserving
readable foreground/background pairs.

WCAG relative-luminance checks gave 6.71:1 for the representative Material 2
light primary/on-primary pair, 12.55:1 for the dark primary/deep-surface pair,
and at least 4.84:1 across the static Material 3 primary, secondary, tertiary,
container, and surface foreground/background pairs checked in both light and
dark modes.

On API 31 and later, the Material 3 path deliberately retains the app's
platform dynamic-color parents, so wallpaper-derived colors take precedence.
The static Moby roles apply where dynamic color does not. This keeps the
existing user-visible behavior on the target Android version instead of
silently disabling it.

## Cargo icon and splash

The launcher mark is an original crate carrying three files. It follows the
public Mobyverse palette and two-color ring language but does not copy existing
Mobyverse glyph geometry or generator code. No explicit Mobyverse license file
was present in the permitted public reference locations, so the implementation
uses only factual palette values and original geometry. The new source and
resources are distributed under this repository's GPL-3.0 license.

The adaptive foreground uses a 108 by 108 viewport. Its outer ring has radius
31 and 2.25-unit stroke width, remaining within the platform's centered 66-unit
safe zone. A deep background layer, full-color foreground, and separate
single-color monochrome vector support adaptive and Android 13 themed icons.
The legacy SVG exports a 78 by 78 area with transparent corners around the deep
medallion.

`art/moby/generate-png.sh` regenerated the density set twice with the existing
host `rsvg-convert`; the second pass produced no tracked diff. `file` reported
RGBA, non-interlaced PNGs at the expected dimensions. Source and packaged APK
resource hashes matched:

| Asset | Dimensions | SHA-256 |
| --- | ---: | --- |
| canonical SVG | 78 by 78 export area | `be1b9929104dd7407f11457a1d12865877da883ac68209cf23d344a3224915db` |
| mdpi PNG | 48 by 48 | `ac75ea15ca66ce164ab1366bd9a3bd8fe09bf01af64b6916ccb6c4bfc620e9e6` |
| hdpi PNG | 72 by 72 | `05d4fb5c64325c9896476fe38aa6aad08253b00d6990eb84a5428ca28b3d3ed5` |
| xhdpi PNG | 96 by 96 | `e52baad7d76153ba4d910f1129813026e877abe3fd417b0a7718cd6414a44f90` |
| xxhdpi PNG | 144 by 144 | `8895f008e1df122d6bc3a5b7086ea973881a564362c01215fb7c475c1342f94f` |
| xxxhdpi PNG | 192 by 192 | `5076344f45963d8db392e3b11d086fe5d91d0bdc74fdabeb18e892f49333e0b1` |

Android 12 and later use the deep background, cargo foreground, and matching
icon background for the platform splash. The API-specific style explicitly
inherits `Base.Theme.MaterialFiles`, preserving the existing app theme after
the splash. Earlier Android versions retain the existing upstream launch-window
behavior rather than receiving a persistent branded window background.

Build Tools 36.0.0 resource-table inspection confirmed that the Moby APK
contains all nine source colors and derived semantic roles, the full-color and
monochrome vectors, five legacy densities, adaptive XML, static theme parents,
API 31 dynamic parents, and the three platform splash attributes.

## Offline acceptance

With signing-related environment names unset, strict dependency verification,
the repository-local Phase 0B Gradle user home, the approved read-only Android
SDK, and the unsigned-build init script, this task set passed offline with all
64 tasks executed:

```text
assembleMobyDebug testMobyDebugUnitTest lintMobyDebug
```

`testMobyDebugUnitTest` reported `NO-SOURCE`. Lint completed with 0 errors and
872 existing warnings, with no diagnostic pointing into `app/src/moby`. After
replacing each detached-worktree prefix with `WORKTREE`, the complete Phase 2A
and Phase 2B lint XML files had the same SHA-256:

```text
3005b6030edc31ef12cb627dfa491a31437dfc57f58f223f3aed3bdf4f672c47
```

This confirms that the final public-attribute implementation introduced no new
lint diagnostic. The initial implementation exposed private Material-library
palette resources and generated 47 additional `PrivateResource` warnings; that
approach was removed before final validation.

`assembleUpstreamDebug` also passed offline with all 51 tasks executed. The
upstream APK remained `me.zhanghai.android.files`, labelled `Material Files`,
contained no `moby_` resource, and its five packaged legacy launcher PNG hashes
exactly matched `app/src/main/res`. Its adaptive launcher and existing theme
resources remained selected.

The final diagnostic artifacts were:

| Variant | Bytes | SHA-256 |
| --- | ---: | --- |
| Moby debug | 22,852,403 | `fe4edf8a1cc263ffc5b0d7ed62fd7e050d8421937dda7b642461d5c956b96a7f` |
| upstream debug | 22,830,643 | `f678e8c1067999cc357d53b35eb65ec3e010045aaf47e5f3e7ca98d8d2b7e19a` |

`aapt2` reported the Moby package as `io.github.cottenplant.mobyfiles`, version
39 (`1.7.4`), minimum SDK 23, target SDK 34, compile SDK 36, and label `Moby
Files` in every packaged locale. The package-derived custom permission,
authorities, and actions remained those verified in Phase 2A; no identity
source changed in this phase.

`apksigner verify --verbose` rejected the Moby APK with `DOES NOT VERIFY` and
`Missing META-INF/MANIFEST.MF`. No signing-validation task ran, and neither APK
was installed.

The strict Moby debug runtime dependency report completed successfully and
contained none of `com.google.firebase`, `com.google.android.gms`, or
`com.google.android.datatransport`. Neither `app/gradle.lockfile` nor
`gradle/verification-metadata.xml` changed from Phase 2A.

Existing SDK XML, Gradle deprecation, Kotlin, and Java source/target warnings
remain unchanged in character. `git diff --check` passed, the implementation
commits contain no trailers, and both the detached worktree and local `master`
were clean after verification.

## Deferred work and boundaries

- Device rendering remains a user-run check after permanent signing is designed
  in a later contract. It should cover launcher masks, Android 13 themed-icon
  tinting, light/dark cold-start transitions, dynamic color, and readable
  contrast on the GrapheneOS Pixel 9 Pro.
- Permanent signing, installation, upgrade testing, screenshots, store assets,
  privacy documentation, update channels, CI, and distribution remain separate
  contracts.
- The inherited lint/compiler warnings and optional offline UTP/JaCoCo
  configurations remain out of scope.

No network, dependency download, SDK installation or modification, Android
device, personal infrastructure, authenticated service, signing material,
secret-bearing environment value, or ignored local configuration was accessed.
Reference access stayed within the permitted public Mobyverse design sources;
no deployment or infrastructure material was inspected.

Privacy incidents or near misses: none.
