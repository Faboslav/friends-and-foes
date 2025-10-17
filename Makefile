help: ## Prints help for targets with comments
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

build-project: ## Builds project
	./gradlew build

refresh: ## Refresh dependencies
	./gradlew --refresh-dependencies

gen-sources: ## Generate sources
	./gradlew genSources

run-fabric-client: ## Runs fabric client
	./gradlew fabric:1.21.4:runClient

run-forge-client: ## Runs forge client
	./gradlew forge:runClient

run-neoforge-client: ## Runs neoforge client
	./gradlew neoforge:runClient

run-fabric-server: ## Runs fabric server
	./gradlew fabric:1.21.4:runServer

run-forge-server: ## Runs forge server
	./gradlew forge:runServer

run-neoforge-server: ## Runs neoforge server
	./gradlew neoforge:1.21.4:runServer

run-data: ## Runs datagen
	./gradlew runData

run-validate-aw: ##
	./gradlew common:1.21.9:validateAccessWidener

clean-cache: ## Cleans cache
	./gradlew --stop
	rm -rf $GRADLE_HOME/caches/transforms-*
	rm -rf $GRADLE_HOME/caches/build-cache-*
	./gradlew clean

nuke: ## Nuke the project
	./gradlew --stop
	rm -rf $GRADLE_HOME/caches/transforms-*
	rm -rf $GRADLE_HOME/caches/build-cache-*
	find . -type d \( -name ".idea" -o -name ".kotlin" -o -name ".gradle" -o -name "build" -o -name "run" \) -exec rm -rf {} +