plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "1.9.23"
    id("com.google.gms.google-services")
}

android {
    namespace = "com.book.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.book.myapplication"
        minSdk = 24
        targetSdk = 34
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
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.annotation:annotation:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("androidx.compose.runtime:runtime-livedata:1.6.7")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.05.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.05.00"))
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    // Thêm converter Gson nếu bạn muốn sử dụng Gson để chuyển đổi JSON.
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //load data from localhost
    implementation ("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:{latest_version}")
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // load image using link from back-end
    implementation ("com.github.bumptech.glide:compose:1.0.0-beta01")
    implementation("io.coil-kt:coil-compose:2.6.0")

    //data store
    implementation("androidx.datastore:datastore-preferences-core:1.1.1")
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    //grid layout
    implementation("androidx.gridlayout:gridlayout:1.0.0")

    //navigation
    implementation("androidx.navigation:navigation-compose:2.8.0-beta01")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    //single sign on
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation ("com.google.firebase:firebase-auth-ktx")

}