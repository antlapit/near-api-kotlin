/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin library project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/7.2/userguide/building_java_projects.html
 */

plugins {
    id("org.jetbrains.kotlin.jvm") // required for Kotlin DSL syntax
}

dependencies {
    implementation(project(":providers-api"))
    implementation(fileTree("lib")) // should be artifact, but borshj is not ready

    // Ktor for making requests
    val ktorVersion = "1.6.2"
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-jackson:$ktorVersion")
    api("io.ktor:ktor-client-logging:$ktorVersion")

    // Jackson for serialization
    val jacksonVersion = "2.10.2"
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:$jacksonVersion")

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation("com.google.guava:guava:30.1.1-jre")

    val komputingKHashSha256Version = "1.1.1"
    testImplementation("com.github.komputing.khash:sha256:${komputingKHashSha256Version}")

    // signing with tweetnacl java port
    val tweetnaclVersion = "1.1.2"
    testImplementation("io.github.instantwebp2p:tweetnacl-java:${tweetnaclVersion}")
}

publishing {
    publications {
        register<MavenPublication>("gpr") {
            groupId = project.rootProject.group.toString()
            artifactId = "providers"
            version = project.rootProject.version.toString()
            from(components["java"])
        }
    }
}
