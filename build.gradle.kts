import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
}

group = "io.github.si1kn"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("commons-io:commons-io:2.11.0")
    implementation("org.ow2.asm:asm:9.4")




}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType(Jar::class) {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Manifest-Version"] = "1.0"
        attributes["Premain-Class"] = "io.github.si1kn.injector.AgentMain"
        attributes["Can-Redefine-Classes"] = "true"
    }

    from(configurations.runtimeClasspath.get().map {if (it.isDirectory) it else zipTree(it)})
}
