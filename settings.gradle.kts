rootProject.name = "walking_skeleton"

plugins {
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "1.1.0"
}

gitHooks {
    createHooks()

    hook("pre-commit") {
        tasks("spotlessApply")
    }
}