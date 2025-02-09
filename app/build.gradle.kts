@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization)

    alias(libs.plugins.com.google.devtools.ksp)
    alias(libs.plugins.com.google.dagger.hilt.android)
    alias(libs.plugins.androidx.room)

    alias(libs.plugins.org.jetbrains.kotlin.plugin.compose)

    id(libs.plugins.secretsGradlePlugin.get().pluginId)
    id(libs.plugins.androidx.navigation.safeargs.kotlin.get().pluginId)
}

android {
    namespace = "dev.ap5.cunavigator"
    compileSdk = 35

    defaultConfig {
        applicationId = "dev.ap5.cunavigator"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"

        freeCompilerArgs += "-Xwhen-guards"
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    sourceSets {
        getByName("main") {
            java {
                srcDirs("src/main/java")
            }
        }
    }

    ksp {
        arg("room.generateKotlin", "true")
    }
}

secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "local.defaults.properties"
    ignoreList.add("sdk.*")       // Ignore all keys matching the regexp "sdk.*"
}


room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    implementation(libs.kotlin.metadata.jvm)

    implementation(libs.ax.core.ktx)
    implementation(libs.ax.lifecycle.runtime.ktx)
    implementation(libs.ax.activity.compose)
    implementation(platform(libs.ax.compose.bom))
    implementation(libs.ax.compose.ui)
    implementation(libs.ax.compose.ui.graphics)
    implementation(libs.ax.compose.ui.tooling.preview)
    implementation(libs.ax.compose.material3.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ax.test.ext.junit)
    androidTestImplementation(libs.ax.test.espresso.core)
    androidTestImplementation(platform(libs.ax.compose.bom))
    androidTestImplementation(libs.ax.compose.ui.test.junit4)
    debugImplementation(libs.ax.compose.ui.tooling)
    debugImplementation(libs.ax.compose.ui.test.manifest)

    implementation(libs.ktx.datetime)

    implementation(libs.ax.room.ktx)
    ksp(libs.ax.room.compiler)

    implementation(project(":mtdapi"))

    // Kotlin serialization
    implementation(libs.ktx.serialization.json)

    implementation(libs.ktorfit)
    implementation(libs.ktor.serialization.ktx.json)
    implementation(libs.ktor.client.contentNegotiation)
    implementation(libs.ktor.client.okhttp)

    // Compose
    implementation(libs.ax.navigation.compose)
    implementation(libs.ax.lifecycle.viewmodel.compose)

    // Reflection
    implementation(kotlin("reflect"))

    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.android.compiler)

    implementation(libs.gms.playServices.maps)
    implementation(libs.maps.ktx)
    implementation(libs.maps.compose)
    implementation(libs.maps.compose.utils)
    implementation(libs.maps.compose.widgets)

    implementation(libs.ax.compose.material.icons.extended)

    implementation(libs.colormath)
}