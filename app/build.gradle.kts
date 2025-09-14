plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android )
    alias(libs.plugins.kotlin.kapt)
}


android {
    namespace = "com.example.buylistapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.buylistapp"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }


}



dependencies {

    val roomVersion = "2.7.2"
    implementation("androidx.room:room-runtime:$roomVersion")
    //annotationProcessor("androidx.room:room-compiler:$roomVersion")
    implementation(libs.room.ktx)
    kapt("androidx.room:room-compiler:$roomVersion")
    //kapt(libs.room.compiler)

    //compose?
    /*
    implementation (libs.ui)
    implementation (libs.androidx.ui.tooling.preview)
    implementation (libs.androidx.material3)*/

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.0")

    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}