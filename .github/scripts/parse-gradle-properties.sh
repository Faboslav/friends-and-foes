#!/bin/bash

version=${1:-}

parse_properties_file() {
    local file=$1
    while IFS='=' read -r key value; do
        key=$(echo "$key" | awk '{$1=$1;print}')
        value=$(echo "$value" | awk '{$1=$1;print}')

        if [[ -z "$key" || "$key" =~ ^# || "$key" == "org.gradle.jvmargs" ]]; then
            continue
        fi

        key=$(echo "$key" | tr '[:lower:]' '[:upper:]' | tr -c '[:alnum:]' '_')
        key=$(echo "$key" | sed 's/_$//')

        echo "${key}=${value}"
        echo "${key}=${value}" >> "$GITHUB_OUTPUT"
    done < "$file"
}

parse_properties_file gradle.properties

if [[ -n "$version" ]]; then
    parse_properties_file "versions/${version}/gradle.properties"
fi