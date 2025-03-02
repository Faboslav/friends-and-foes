#!/bin/bash

matrix_content="{\"include\":["
enabled_platforms=$(awk -F= '/stonecutter_enabled_platforms/{print $2}' gradle.properties | tr -d ' ')

for platform in $(echo $enabled_platforms | tr ',' ' '); do
  if [[ "$platform" == "forge" ]]; then
    mod_loader_alias="lexforge"
  else
    mod_loader_alias="$platform"
  fi

  versions=$(awk -F= '/stonecutter_enabled_'$platform'_versions/{print $2}' gradle.properties | tr -d ' ')
  for version in $(echo $versions | tr ',' ' '); do
    yacl_version=$(awk -F= '/yacl_version/{print $2}' versions/"${version}"/gradle.properties | tr -d ' ')
    matrix_entry="{\"mod_loader\":\"$platform\",\"mod_loader_alias\":\"$mod_loader_alias\",\"version\":\"$version\",\"yacl_version\":\"$yacl_version\"},"
    matrix_content+="$matrix_entry"
  done
done

matrix_content="${matrix_content%,}]}"
echo "Generated matrix: $matrix_content"
echo "::set-output name=matrix::$matrix_content"