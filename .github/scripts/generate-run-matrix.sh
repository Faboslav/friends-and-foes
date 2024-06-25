#!/bin/bash

matrix_content="{\"include\":["
enabled_platforms=$(awk -F= '/enabled_platforms/{print $2}' gradle.properties | tr -d ' ')

for platform in $(echo $enabled_platforms | tr ',' ' '); do
  versions=$(awk -F= '/minecraft_version/{print $2}' gradle.properties | tr -d ' ')
  for version in $(echo $versions | tr ',' ' '); do
    matrix_entry="{\"mod_loader\":\"$platform\",\"version\":\"$version\",\"script\":\"client\"},"
    matrix_content+="$matrix_entry"
    matrix_entry="{\"mod_loader\":\"$platform\",\"version\":\"$version\",\"script\":\"server\"},"
    matrix_content+="$matrix_entry"
  done
done

matrix_content="${matrix_content%,}]}"
echo "Generated matrix: $matrix_content"
echo "::set-output name=matrix::$matrix_content"