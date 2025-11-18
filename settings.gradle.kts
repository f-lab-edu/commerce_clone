pluginManagement {
    repositories {
//        google()
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

rootProject.name = "commerce"
include(":app")

include(":feature:home")
include(":feature:category")
include(":feature:login")
include(":feature:product")
include(":feature:search")

include(":core:android")
include(":core:database")
include(":core:navigation")
include(":core:domain")
include(":core:auth")
include(":feature:mypage")
include(":feature:cart")
include(":core:notification")
include(":feature:like")
