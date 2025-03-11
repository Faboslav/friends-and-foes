#!/bin/bash

# Variables
MOD_SLUG="resourceful-lib"
MOD_VERSION="3.4.5"
LOADER="neoforge"

# Fetch version information from Modrinth API
API_URL="https://api.modrinth.com/v2/project/${MOD_SLUG}/version"
VERSION_DATA=$(curl -s "$API_URL")

# Sanitize VERSION_DATA by removing any control characters
SANITIZED_DATA=$(echo "$VERSION_DATA" | tr -d '\000-\031')

# Check if SANITIZED_DATA is not empty
if [ -z "$SANITIZED_DATA" ]; then
    echo "No data received from Modrinth API."
    exit 1
fi

# Extract the download URL for the specified loader and version
DOWNLOAD_URL=$(echo "$SANITIZED_DATA" | jq -r --arg loader "$LOADER" \
    '.[] | select(.version_number == "3.4.5") | select(.loaders | index($loader)) | .files[] | select(.primary == true) | .url')

# Output the download URL
if [ -n "$DOWNLOAD_URL" ]; then
    echo "$DOWNLOAD_URL"
else
    echo "No matching file found for ${MOD_SLUG} version ${MOD_VERSION} with loader ${LOADER}."
    exit 1
fi