#!/bin/bash

version=$1

parse_properties_file() {
    local file=$1
    while IFS='=' read -r key value; do
        # Trim leading/trailing whitespace from key and value
        key=$(echo "$key" | awk '{$1=$1;print}')
        value=$(echo "$value" | awk '{$1=$1;print}')

        # Skip comments, empty keys, and unwanted keys like "org.gradle.jvmargs"
        if [[ -z "$key" || "$key" =~ ^# || "$key" == "org.gradle.jvmargs" ]]; then
            continue
        fi

        # Convert key to uppercase and replace non-alphanumeric characters with underscores
        key=$(echo "$key" | tr '[:lower:]' '[:upper:]' | tr -c '[:alnum:]' '_')

        # Remove any trailing underscores
        key=$(echo "$key" | sed 's/_$//')

        # Output the key-value pair
        echo "${key}=${value}"
        echo "${key}=${value}" >> "$GITHUB_OUTPUT"
    done < "$file"
}

# Parse the main gradle.properties file
parse_properties_file gradle.properties

# Parse the version-specific gradle.properties file
parse_properties_file versions/"${version}"/gradle.properties