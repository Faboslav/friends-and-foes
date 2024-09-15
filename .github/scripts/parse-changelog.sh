#!/bin/bash

tag=$1

changelog=$(cat CHANGELOG.md)

list=$(echo "$changelog" | sed -n "/^## $tag/,/^## [0-9]/p" | sed -e '1d;$d' -e '/^$/d')

echo "$list" > RELEASE_CHANGELOG.md