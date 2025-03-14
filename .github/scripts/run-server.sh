#!/bin/bash

mod_loader=$1
version=$2

mkdir -p $mod_loader/versions/$version/run && echo "eula=true" > $mod_loader/versions/$version/run/eula.txt

./gradlew $mod_loader:$version:runServer --no-daemon --args="nogui" 2>&1 | tee gradle_server_output.txt &

SUCCESS_PATTERN='For help, type "help"'
ERROR_PATTERNS=(
    'For more details see the full crash report file'
    ' end of report '
    'Failed download after 3 attempts'
    'Error: Exception in thread'
)
TIMEOUT=1800
ELAPSED=0

while [ $ELAPSED -lt $TIMEOUT ]; do
    if grep -Eq "$SUCCESS_PATTERN" gradle_server_output.txt; then
        pkill -P $$
        exit 0
    fi

    for ERROR_PATTERN in "${ERROR_PATTERNS[@]}"; do
        if grep -Eq "$ERROR_PATTERN" gradle_server_output.txt; then
            pkill -P $$
            exit 1
        fi
    done

    sleep 1
    ELAPSED=$((ELAPSED + 1))
done

if [ $ELAPSED -ge $TIMEOUT ]; then
    exit 1
fi