#!/bin/bash

allowed_mod_loaders=$1
curseforge_fabric_project_id=$2
curseforge_forge_project_id=$3
modrinth_fabric_project_id=$4
modrinth_forge_project_id=$5

IFS=',' read -r -a allowed_mod_loaders_array <<< "${allowed_mod_loaders//[\[\]\']/}"
matrix_content="{\"include\":["
enabled_platforms=$(awk -F= '/enabled_platforms/{print $2}' gradle.properties | tr -d ' ')
minecraft_version=$(awk -F= '/minecraft_version/{print $2; exit}' gradle.properties | tr -d ' ')

for platform in $(echo $enabled_platforms | tr ',' ' '); do
  if [[ " ${allowed_mod_loaders_array[@]} " =~ " ${platform} " ]]; then
  	    if [[ "$platform" == "fabric" ]]; then
        	supported_mod_loaders="\"fabric\",\"quilt\""
        else
        	supported_mod_loaders="\"$platform\""
        fi

        if [[ "$platform" == "fabric" ]]; then
        	curseforge_project_id="$curseforge_fabric_project_id"
        	modrinth_project_id="$modrinth_fabric_project_id"
    	else
        	curseforge_project_id="$curseforge_forge_project_id"
        	modrinth_project_id="$modrinth_forge_project_id"
    	fi

        matrix_entry="{\"mod_loader\":\"$platform\",\"minecraft_version\":\"$minecraft_version\",\"supported_mod_loaders\":[$supported_mod_loaders],\"curseforge_project_id\":\"$curseforge_project_id\",\"modrinth_project_id\":\"$modrinth_project_id\"},"
        matrix_content+="$matrix_entry"
  fi
done

matrix_content="${matrix_content%,}]}"
echo "Generated matrix: $matrix_content"
echo "::set-output name=matrix::$matrix_content"