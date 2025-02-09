// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        classpath(libs.javapoet)
        classpath(libs.secretsGradlePlugin)
        classpath(libs.ax.navigation.safeArgs.gradle)
    }
}

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.com.google.devtools.ksp) apply false
    alias(libs.plugins.org.jetbrains.kotlin.plugin.compose) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
}
true // Needed to make the Suppress annotation work for the plugins block