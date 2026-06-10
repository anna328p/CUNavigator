// Top-level build file where you can add configuration options common to all sub-projects/modules.
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
	dependencies {
		classpath(libs.javapoet)
		classpath(libs.secretsGradlePlugin)
		classpath(libs.ax.navigation.safeArgs.gradle)
	}
}

plugins {
	alias(libs.plugins.com.android.application) apply false
	alias(libs.plugins.org.jetbrains.kotlin.android) apply false
	alias(libs.plugins.com.google.devtools.ksp) apply false
	alias(libs.plugins.org.jetbrains.kotlin.plugin.compose) apply false
	alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
}

allprojects {
	tasks.withType<KotlinCompile>().configureEach {
		compilerOptions {
			languageVersion = KotlinVersion.KOTLIN_2_4
			apiVersion = KotlinVersion.KOTLIN_2_4
			jvmTarget = JvmTarget.JVM_17

			moduleName = project.name

			freeCompilerArgs.add("-Xcontext-sensitive-resolution")
			freeCompilerArgs.add("-Xallow-reified-type-in-catch")
			freeCompilerArgs.add("-Xallow-contracts-on-more-functions")
			freeCompilerArgs.add("-Xallow-condition-implies-returns-contracts")
			freeCompilerArgs.add("-Xallow-holdsin-contract")
			freeCompilerArgs.add("-Xwhen-expressions=indy")
			freeCompilerArgs.add("-Xreturn-value-checker=full")
			freeCompilerArgs.add("-Xname-based-destructuring=complete")
			freeCompilerArgs.add("-Xcollection-literals")
			freeCompilerArgs.add("-Xintrinsic-const-evaluation")
			freeCompilerArgs.add("-Xallow-returns-result-of")

			extraWarnings = true
		}
	}
}