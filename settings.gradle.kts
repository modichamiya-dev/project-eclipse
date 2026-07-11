pluginManagement {
    repositories { gradlePluginPortal(); maven("https://repo.papermc.io/repository/maven-public/") }
}
rootProject.name = "project-eclipse"
include("core", "api", "config", "database", "plugin", "assets", "registry", "animation", "gui", "world", "ai", "admin", "gameplay", "content")
