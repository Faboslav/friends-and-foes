#!/bin/bash

mkdir -p $1/run && echo "eula=true" > $1/run/eula.txt

./gradlew $1:runServer --no-daemon --args="nogui" 2>&1 | tee gradle_server_output.txt &

SUCCESS_PATTERN='For help, type "help"'
ERROR_PATTERNS=(
    'For more details see the full crash report file'
    ' end of report '
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