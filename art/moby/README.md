# Moby Files Android icon source

`launcher_icon.svg` is the canonical legacy launcher-icon source. Regenerate
the five Android density PNGs from the repository root with:

```sh
art/moby/generate-png.sh
```

The script intentionally follows the upstream `art/generate-png.sh` density
set and 78×78 export area. It uses `rsvg-convert` when available and otherwise
falls back to Inkscape; neither path needs network access. The adaptive and
monochrome icons are native Android vector drawables so they do not need raster
generation.

The color roles come from the public Mobyverse `deep` palette as observed on
2026-09-13. The ring follows that public design language. The “crate carrying
files” cargo geometry is an original Moby Files adaptation: it keeps the
Mobyverse rule that only the payload is filled while distinguishing this file
manager from the existing `audiofiler` mark.

All files in this directory are distributed under Material Files' GPL-3.0
license.
