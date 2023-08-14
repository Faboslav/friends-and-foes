#!/bin/bash

./gradlew $1:runClient > gradle_client_output.txt 2>&1 &

SUCCESS_PATTERN='minecraft:textures/atlas/mob_effects\.png-atlas'
ERROR_PATTERN='Execution failed for task'
TIMEOUT=1800
ELAPSED=0

while [ $ELAPSED -lt $TIMEOUT ]; do
    if grep -Eq "$SUCCESS_PATTERN" gradle_client_output.txt; then
        pkill -P $$
        exit 0
    fi

    if grep -Eq "$ERROR_PATTERN" gradle_client_output.txt; then
        pkill -P $$
        exit 1
    fi

    sleep 1
    ELAPSED=$((ELAPSED + 1))
done

if [ $ELAPSED -ge $TIMEOUT ]; then
    exit 1
fi
