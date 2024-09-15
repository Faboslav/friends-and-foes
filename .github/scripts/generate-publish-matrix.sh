#!/bin/bash

allowed_mod_loaders=$1
allowed_versions=$2

echo ${allowed_mod_loaders};
echo ${allowed_versions};

# Convert the allowed mod loaders and versions into arrays
IFS=',' read -r -a allowed_mod_loaders_array <<< "${allowed_mod_loaders//[\[\]\']/}"
IFS=',' read -r -a allowed_versions_array <<< "${allowed_versions//[\[\]\']/}"

# Initialize the matrix JSON with the 'include' key
matrix_content="{\"include\":["

# Extract enabled platforms from gradle.properties
enabled_platforms=$(awk -F= '/enabled_platforms/{print $2}' gradle.properties | tr -d ' ')

# Iterate over each platform and gather versions
for platform in $(echo $enabled_platforms | tr ',' ' '); do
  if [[ " ${allowed_mod_loaders_array[@]} " =~ " ${platform} " ]]; then
    versions=$(awk -F= '/stonecutter_enabled_'$platform'_versions/{print $2}' gradle.properties | tr -d ' ')
    for version in $(echo $versions | tr ',' ' '); do
      if [[ " ${allowed_versions_array[@]} " =~ " ${version} " ]]; then
        # Check if the platform is 'fabric' to append 'quilt' to supported_mod_loaders
        if [[ "$platform" == "fabric" ]]; then
          supported_mod_loaders="\"fabric\",\"quilt\""
        else
          supported_mod_loaders="\"$platform\""
        fi

        # Create each entry with a JSON object for each combination
        matrix_entry="{\"mod_loader\":\"$platform\",\"version\":\"$version\",\"supported_mod_loaders\":[$supported_mod_loaders]},"
        matrix_content+="$matrix_entry"
      fi
    done
  fi
done

# Remove the trailing comma and close the JSON object
matrix_content="${matrix_content%,}]}"
# Output the matrix content for debugging
echo "Generated matrix: $matrix_content"
# Set the output for GitHub Actions
echo "::set-output name=matrix::$matrix_content"