#!/bin/bash

./gradlew $1:runClient > gradle_output.txt 2>&1 &

TARGET_PATTERN='minecraft:textures/atlas/mob_effects\.png-atlas'
TIMEOUT=600
ELAPSED=0

while [ $ELAPSED -lt $TIMEOUT ]; do
    if grep -Eq "$TARGET_PATTERN" gradle_output.txt; then
        pkill -P $$
        exit 0
    fi

    sleep 1
    ELAPSED=$((ELAPSED + 1))
done

if [ $ELAPSED -ge $TIMEOUT ]; then
    exit 1
fi
