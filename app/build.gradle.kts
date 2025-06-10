plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("com.google.gms.google-services")
}

android {
    namespace = "br.senai.sp.jandira.reporterdomeubairromac"
    compileSdk = 35

    defaultConfig {
        applicationId = "br.senai.sp.jandira.reporterdomeubairromac"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    composeOptions{
        kotlinCompilerExtensionVersion = "1.5.10"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.common.ktx)
    // implementation(libs.play.services.location) // Removido, já que você tem uma versão explícita abaixo
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //ampliar icones disponiveis
    implementation("androidx.compose.material:material-icons-extended-android:1.7.8")

    //Dependencia de navegação entre telas
    implementation("androidx.navigation:navigation-compose:2.8.9")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")

    //gson - converter
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    //coil
    implementation("io.coil-kt:coil-compose:2.7.0")

    // Você tem essas dependências duplicadas com versões diferentes em libs.versions.toml ou em outro lugar.
    // É melhor manter uma única declaração e usar a versão mais recente.
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0") // Atualizado para uma versão mais recente
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0") // Atualizado para uma versão mais recente
    implementation ("androidx.activity:activity-compose:1.9.0") // Atualizado para uma versão mais recente
    implementation ("androidx.compose.ui:ui:1.6.8") // Atualizado para uma versão mais recente
    implementation ("androidx.compose.material3:material3:1.2.1") // Atualizado para uma versão mais recente

    implementation ("com.github.Drjacky:ImagePicker:2.3.22")

    implementation ("com.squareup.okhttp3:okhttp:4.9.3")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.3")

    implementation ("androidx.core:core-ktx:1.12.0") // Já está em libs.androidx.core.ktx, mantenha um

    implementation("com.google.firebase:firebase-storage-ktx:20.3.0") // Atualizado para uma versão mais recente
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.1") // Atualizado para uma versão mais recente

    // Maps - Versões consolidadas e mais recentes
    implementation ("com.google.android.gms:play-services-location:21.0.1") // Para APIs de localização
    implementation("com.google.android.gms:play-services-maps:18.2.0") // Para o Maps SDK
    implementation ("com.google.maps.android:maps-compose:6.6.0") // Versão atual do Compose Maps

    implementation(platform("com.google.firebase:firebase-bom:33.15.0"))

    implementation(platform("com.google.firebase:firebase-bom:33.15.0"))
}