plugins { id("com.gradleup.shadow") version "8.3.5" }

dependencies {
    implementation(project(":core")); implementation(project(":api")); implementation(project(":config")); implementation(project(":database")); implementation(project(":registry")); implementation(project(":assets")); implementation(project(":animation")); implementation(project(":gui")); implementation(project(":world")); implementation(project(":ai")); implementation(project(":admin")); implementation(project(":gameplay")); implementation(project(":content"))
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")
}

tasks {
    shadowJar { archiveBaseName.set("project-eclipse"); archiveClassifier.set(""); mergeServiceFiles(); relocate("com.zaxxer.hikari","dev.modichamiya.eclipse.libs.hikari"); relocate("org.sqlite","dev.modichamiya.eclipse.libs.sqlite"); relocate("com.fasterxml.jackson","dev.modichamiya.eclipse.libs.jackson"); relocate("org.yaml.snakeyaml","dev.modichamiya.eclipse.libs.snakeyaml") }
    build { dependsOn(shadowJar) }
}
