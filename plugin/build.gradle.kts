import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Apply the Java Gradle plugin development plugin to add support for developing Gradle plugins
    `java-gradle-plugin`

    // Apply the Kotlin JVM plugin to add support for Kotlin.
    embeddedKotlin("jvm")

    `kotlin-dsl`

    id("com.gradle.plugin-publish") version "1.2.2"

    `maven-publish`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform(kotlin("bom")))

    // Use the Kotlin JDK 8 standard library.
    implementation(kotlin("stdlib"))

    // Use the Kotlin test library.
    testImplementation(kotlin("test"))

    // Use the Kotlin JUnit integration.
    testImplementation(kotlin("test-junit"))

    implementation(kotlin("gradle-plugin"))
}

group = "org.lwjgl"
version = "0.0.36"

publishing {
    publications.create<MavenPublication>("maven") {
        artifactId = rootProject.name
    }
}

gradlePlugin {
    website = "https://github.com/LWJGL/lwjgl3-gradle"
    vcsUrl = "https://github.com/LWJGL/lwjgl3-gradle"
    // Define the plugin
    plugins.create("lwjgl") {
        id = "org.lwjgl.plugin"
        implementationClass = "org.lwjgl.LwjglPlugin"
        displayName = "Lwjgl Gradle util"
        description = "Easier Lwjgl dependency management"
        tags = listOf("lwjgl", "dependency", "easy", "management")
    }
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}


// Add a source set for the functional test suite
//val functionalTestSourceSet = sourceSets.create("functionalTest") {
//}
//
//gradlePlugin.testSourceSets(functionalTestSourceSet)
//configurations["functionalTestImplementation"].extendsFrom(configurations["testImplementation"])
//
//// Add a task to run the functional tests
//val functionalTest by tasks.registering(Test::class) {
//    testClassesDirs = functionalTestSourceSet.output.classesDirs
//    classpath = functionalTestSourceSet.runtimeClasspath
//}
//
//tasks.check {
//    // Run the functional tests as part of `check`
//    dependsOn(functionalTest)
//}