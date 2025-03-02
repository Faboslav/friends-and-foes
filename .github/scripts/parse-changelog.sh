#!/bin/bash

tag=$1
changelog=$(cat CHANGELOG.md)

# Extract the changelog section for the specified tag
list=$(echo "$changelog" | sed -n "/^## $tag/,/^## [0-9]/p" | sed -e '1d;$d')

# Remove the first blank line if it exists
list=$(echo "$list" | sed -e '1{/^$/d;}')

echo "$list" > RELEASE_CHANGELOG.md