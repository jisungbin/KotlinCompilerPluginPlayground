import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.22"
    id("com.google.devtools.ksp") version "1.8.22-1.0.11"
    application
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    google()
}

application {
    mainClass.set("MainKt")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    implementation("team.duckie.quackquack.util:util-backend-kotlinc:2.0.0-alpha01")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:1.8.22")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.5.0")

    implementation("com.squareup:kotlinpoet:1.14.2")

    implementation("com.google.auto.service:auto-service-annotations:1.1.1")
    ksp("dev.zacsweers.autoservice:auto-service-ksp:1.0.0")

    testImplementation("junit:junit:4.13.2")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.9.3")
}
