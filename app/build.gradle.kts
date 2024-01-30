plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    // 구글서비스 그래들 플러그인 추가
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.firebasecrud"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.firebasecrud"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    // 뷰 바인딩 설정
    // findViewById없이 뷰 객체를 사용하기 위함.
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // 파이어베이스 BoM 임포트
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))

    // 스와이프 기능을 사용하기 위한 라이브러리 추가.
    implementation("it.xabaras.android:recyclerview-swipedecorator:1.4")
}