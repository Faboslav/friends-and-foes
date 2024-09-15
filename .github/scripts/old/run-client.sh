#!/bin/bash

./gradlew $1:runClient --no-daemon 2>&1 | tee gradle_client_output.txt &

SUCCESS_PATTERN='minecraft:textures/atlas/mob_effects\.png-atlas'
ERROR_PATTERNS=(
    'For more details see the full crash report file'
    ' end of report '
    'Failed download after 3 attempts'
)
TIMEOUT=1800
ELAPSED=0

while [ $ELAPSED -lt $TIMEOUT ]; do
    if grep -Eq "$SUCCESS_PATTERN" gradle_client_output.txt; then
        pkill -P $$
        exit 0
    fi

    for ERROR_PATTERN in "${ERROR_PATTERNS[@]}"; do
        if grep -Eq "$ERROR_PATTERN" gradle_client_output.txt; then
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
