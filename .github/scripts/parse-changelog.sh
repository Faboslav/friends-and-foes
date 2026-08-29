#!/bin/bash

tag="$1"

awk -v tag="$tag" '

	$0 == "## " tag {
		found = 1
		next
	}
	found && /^## / {
		exit
	}
	found {
		print
	}

' CHANGELOG.md | sed -e '1{/^$/d;}' > RELEASE_CHANGELOG.md

cat .github/assets/socials.md >> RELEASE_CHANGELOG.md