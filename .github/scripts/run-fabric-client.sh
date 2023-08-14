#!/bin/bash

# Start Gradle task in the background
./gradlew fabric:runClient > gradle_output.txt 2>&1 &

# Monitor output for the desired text using a regular expression
TARGET_PATTERN='minecraft:textures/atlas/mob_effects\.png-atlas'
while ! grep -Eq "$TARGET_PATTERN" gradle_output.txt; do
    sleep 1
done

# Stop the Gradle task
pkill -P $$