group = "antlapit.near.api"
version = "1.0.0-SNAPSHOT"

subprojects {

    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    repositories {
        // Use Maven Central for resolving dependencies.
        mavenCentral()

        // Use jitpack for Kotlin Komputing projects
        maven(url = "https://jitpack.io")
    }

    dependencies {
        // Align versions of all Kotlin components
        implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

        // Use the Kotlin JDK 8 standard library.
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        // Use the Kotlin test library.
        testImplementation("org.jetbrains.kotlin:kotlin-test")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")

        // Test logging
        val logbackVersion = "1.2.5"
        testImplementation("ch.qos.logback:logback-classic:$logbackVersion")

        // Use the Kotlin JUnit integration.
        testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
        val kotestVersion = "5.0.0.M3"
        testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
        testImplementation("io.kotest:kotest-framework-datatest:$kotestVersion")
        testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
        testImplementation("io.kotest:kotest-property:$kotestVersion")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    sourceSets {
        main {
            java.srcDirs("src/main/kotlin")
        }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/antlapit/near-api-kotlin")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }

}

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.5.31"

    // Apply the java-library plugin for API and implementation separation.
    `java-library`

    `maven-publish`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}
