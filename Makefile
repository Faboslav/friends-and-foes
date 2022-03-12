help: ## Prints help for targets with comments
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'

build-project: ## Builds project
	./gradlew build --stacktrace --info

refresh: ## Refresh dependencies
	./gradlew --refresh-dependencies

gen-sources: ## Generate sources
	./gradlew genSources