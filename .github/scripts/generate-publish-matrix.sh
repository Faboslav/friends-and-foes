#!/bin/bash

allowed_mod_loaders=$1
allowed_versions=$2
curseforge_fabric_project_id=$3
curseforge_forge_project_id=$4
modrinth_fabric_project_id=$5
modrinth_forge_project_id=$6

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
          curseforge_project_id="$curseforge_fabric_project_id"
          modrinth_project_id="$modrinth_fabric_project_id"
        else
          supported_mod_loaders="\"$platform\""
          curseforge_project_id="$curseforge_forge_project_id"
          modrinth_project_id="$modrinth_forge_project_id"
        fi

        matrix_entry="{\"mod_loader\":\"$platform\",\"version\":\"$version\",\"supported_mod_loaders\":[$supported_mod_loaders],\"curseforge_project_id\":\"$curseforge_project_id\",\"modrinth_project_id\":\"$modrinth_project_id\"},"
        matrix_content+="$matrix_entry"
      fi
    done
  fi
done

matrix_content="${matrix_content%,}]}"
echo "Generated matrix: $matrix_content"
echo "::set-output name=matrix::$matrix_content"