plugins {
    id("java-library")
    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization)
    alias(libs.plugins.de.jensklingenberg.ktorfit)
    alias(libs.plugins.androidx.room)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
        freeCompilerArgs.add("-Xwhen-guards")
        extraWarnings.set(true)
    }
}

room {
    schemaDirectory("$projectDir/schemas")
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

    implementation(libs.ax.room.runtime)
    ksp(libs.ax.room.compiler)
    implementation(libs.ax.room.ktx)
    implementation(libs.ax.sqlite)
    implementation(libs.ax.sqlite.bundled)
}
