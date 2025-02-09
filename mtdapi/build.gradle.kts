plugins {
    id("java-library")
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization)
    alias(libs.plugins.de.jensklingenberg.ktorfit)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

dependencies {
    implementation(libs.okio)

    implementation(libs.rssparser)

    implementation(libs.ktx.datetime)

    implementation(libs.ktx.serialization.json)
    implementation(libs.ktx.serialization.csv)

    implementation(libs.ktorfit)
    ksp(libs.ktorfit.ksp)
    implementation(libs.ktor.serialization.ktx.json)
    implementation(libs.ktor.client.contentNegotiation)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.logging)

    implementation(libs.slf4j.simple)
}
