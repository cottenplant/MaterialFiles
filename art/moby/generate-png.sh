#!/bin/sh
set -eu

script_dir=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
project_dir=$(CDPATH= cd -- "$script_dir/../.." && pwd)
source_svg="$script_dir/launcher_icon.svg"

if command -v rsvg-convert >/dev/null 2>&1; then
    rasterizer=rsvg
elif command -v inkscape >/dev/null 2>&1; then
    rasterizer=inkscape
else
    echo "error: install rsvg-convert or Inkscape to generate launcher PNGs" >&2
    exit 1
fi

for density_size in mdpi:48 hdpi:72 xhdpi:96 xxhdpi:144 xxxhdpi:192; do
    density=${density_size%%:*}
    size=${density_size##*:}
    output_dir="$project_dir/app/src/moby/res/mipmap-$density"
    mkdir -p "$output_dir"
    if test "$rasterizer" = rsvg; then
        rsvg-convert \
            --width="$size" \
            --height="$size" \
            --output="$output_dir/launcher_icon.png" \
            "$source_svg"
    else
        inkscape \
            --export-area-page \
            --export-background-opacity=0 \
            --export-filename="$output_dir/launcher_icon.png" \
            --export-width="$size" \
            --export-height="$size" \
            "$source_svg"
    fi
done
