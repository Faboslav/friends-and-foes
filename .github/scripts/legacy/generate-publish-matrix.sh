#!/bin/bash

allowed_mod_loaders=$1

IFS=',' read -r -a allowed_mod_loaders_array <<< "${allowed_mod_loaders//[\[\]\']/}"
matrix_content="{\"include\":["
enabled_platforms=$(awk -F= '/enabled_platforms/{print $2}' gradle.properties | tr -d ' ')

for platform in $(echo $enabled_platforms | tr ',' ' '); do
  if [[ " ${allowed_mod_loaders_array[@]} " =~ " ${platform} " ]]; then
  	    if [[ "$platform" == "fabric" ]]; then
        	supported_mod_loaders="\"fabric\",\"quilt\""
        else
        	supported_mod_loaders="\"$platform\""
        fi

        matrix_entry="{\"mod_loader\":\"$platform\",\"supported_mod_loaders\":[$supported_mod_loaders]},"
        matrix_content+="$matrix_entry"
  fi
done

matrix_content="${matrix_content%,}]}"
echo "Generated matrix: $matrix_content"
echo "::set-output name=matrix::$matrix_content"