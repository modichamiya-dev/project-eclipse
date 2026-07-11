plugins { base }

allprojects {
    group = "dev.modichamiya.eclipse"
    version = "0.1.0-SNAPSHOT"
    repositories { mavenCentral(); maven("https://repo.papermc.io/repository/maven-public/") }
}

subprojects {
    apply(plugin = "java-library")
    extensions.configure<JavaPluginExtension> {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
        withSourcesJar()
    }
    tasks.withType<JavaCompile>().configureEach { options.encoding = "UTF-8"; options.release.set(21) }
    tasks.withType<Test>().configureEach { useJUnitPlatform() }
    dependencies { "testImplementation"("org.junit.jupiter:junit-jupiter:5.11.4") }
}
