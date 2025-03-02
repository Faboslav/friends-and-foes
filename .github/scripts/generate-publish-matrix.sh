#!/bin/bash

allowed_mod_loaders=$1
allowed_versions=$2

echo ${allowed_mod_loaders};
echo ${allowed_versions};

IFS=',' read -r -a allowed_mod_loaders_array <<< "${allowed_mod_loaders//[\[\]\']/}"
IFS=',' read -r -a allowed_versions_array <<< "${allowed_versions//[\[\]\']/}"

matrix_content="{\"include\":["
enabled_platforms=$(awk -F= '/stonecutter_enabled_platforms/{print $2}' gradle.properties | tr -d ' ')

for platform in $(echo $enabled_platforms | tr ',' ' '); do
  if [[ " ${allowed_mod_loaders_array[@]} " =~ " ${platform} " ]]; then
    versions=$(awk -F= '/stonecutter_enabled_'$platform'_versions/{print $2}' gradle.properties | tr -d ' ')
    for version in $(echo $versions | tr ',' ' '); do
      if [[ " ${allowed_versions_array[@]} " =~ " ${version} " ]]; then
        if [[ "$platform" == "fabric" ]]; then
          supported_mod_loaders="\"fabric\",\"quilt\""
        else
          supported_mod_loaders="\"$platform\""
        fi

        matrix_entry="{\"mod_loader\":\"$platform\",\"version\":\"$version\",\"supported_mod_loaders\":[$supported_mod_loaders]},"
        matrix_content+="$matrix_entry"
      fi
    done
  fi
done

matrix_content="${matrix_content%,}]}"
echo "Generated matrix: $matrix_content"
echo "::set-output name=matrix::$matrix_content"