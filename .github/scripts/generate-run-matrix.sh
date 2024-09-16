#!/bin/bash

matrix_content="{\"include\":["
enabled_platforms=$(awk -F= '/enabled_platforms/{print $2}' gradle.properties | tr -d ' ')
version=$(awk -F= '/minecraft_version/{print $2; exit}' gradle.properties | tr -d ' ')

for platform in $(echo $enabled_platforms | tr ',' ' '); do
  if [[ "$platform" == "forge" ]]; then
    mod_loader_alias="lexforge"
  else
    mod_loader_alias="$platform"
  fi

  matrix_entry="{\"mod_loader\":\"$platform\",\"mod_loader_alias\":\"$mod_loader_alias\",\"version\":\"$version\"},"
  matrix_content+="$matrix_entry"
done

matrix_content="${matrix_content%,}]}"
echo "Generated matrix: $matrix_content"
echo "::set-output name=matrix::$matrix_content"