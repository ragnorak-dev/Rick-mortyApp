plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.ragnorak.rick_morty.character_details"
    compileSdk = 35

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    detekt {
        config.setFrom(files("$rootDir/detekt-compose-config.yml"))
        buildUponDefaultConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(project(Modules.coreNavigation))
    implementation(project(Modules.coreApi))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.corePersistence))
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.material3)
    implementation(libs.compose.animation)
    implementation(libs.coil.compose)
    implementation(libs.hilt.android)
    implementation(libs.hilt.compose)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.testing.kotlin.coroutines)
    testImplementation(libs.turbine)
    debugImplementation(libs.ui.tooling)

    detektPlugins(libs.detekt.formatting)
}