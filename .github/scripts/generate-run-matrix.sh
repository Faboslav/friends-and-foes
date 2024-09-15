#!/bin/bash

# Initialize the matrix JSON with the 'include' key
matrix_content="{\"include\":["

# Extract enabled platforms from gradle.properties
enabled_platforms=$(awk -F= '/stonecutter_enabled_platforms/{print $2}' gradle.properties | tr -d ' ')

# Iterate over each platform and gather versions
for platform in $(echo $enabled_platforms | tr ',' ' '); do
  versions=$(awk -F= '/stonecutter_enabled_'$platform'_versions/{print $2}' gradle.properties | tr -d ' ')
  for version in $(echo $versions | tr ',' ' '); do
    # Create each entry with a JSON object for each combination
    matrix_entry="{\"mod_loader\":\"$platform\",\"version\":\"$version\",\"script\":\"client\"},"
    matrix_content+="$matrix_entry"
    matrix_entry="{\"mod_loader\":\"$platform\",\"version\":\"$version\",\"script\":\"server\"},"
    matrix_content+="$matrix_entry"
  done
done

# Remove the trailing comma and close the JSON object
matrix_content="${matrix_content%,}]}"
# Output the matrix content for debugging
echo "Generated matrix: $matrix_content"
# Set the output for GitHub Actions
echo "::set-output name=matrix::$matrix_content"