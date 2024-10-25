import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.application")
    id("kotlin-android")
}
android {
    namespace = "com.sparkleside"
    compileSdk = 34
    
    defaultConfig {
        applicationId = "com.sparkleside"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
      
        vectorDrawables { 
            useSupportLibrary = true
        }
        resConfigs("ar-rTN", "es-rES", "in-rID", "night", "pt-rBR", "pt-rPT", "zh-rCN")
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        viewBinding = true
        buildConfig= true
    }
    
    signingConfigs {
        create("release") {
            // temporary keystore
            // todo: sign in actions with secrets
            storeFile = file(layout.buildDirectory.dir("../release_key.jks"))
            storePassword = "release_temp"
            keyAlias = "release_temp"
            keyPassword = "release_temp"
        }
        getByName("debug") {
            storeFile = file(layout.buildDirectory.dir("../testkey.keystore"))
            storePassword = "testkey"
            keyAlias = "testkey"
            keyPassword = "testkey"
        }
    }
    
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation(platform("io.github.Rosemoe.sora-editor:bom:0.23.4"))
    implementation("io.github.Rosemoe.sora-editor:editor:0.23.4")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("androidx.activity:activity:1.6.0-alpha05")
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    implementation("com.blankj:utilcodex:1.31.1")
    
    implementation(project(":peekandpop"))
    implementation(project(":maskable"))
    implementation(project(":ui-utils"))
    
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.0.9")
}
