plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile = file("keystore/mykeystore.jks")
            storePassword = "01654541475"
            keyAlias = "myalias"
            keyPassword = "01654541475"
        }
    }
    namespace = "com.example.monkeeapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.monkeeapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.play.services.cast.framework)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //dependencies cua Dung
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")

    // Also add the dependency for the Google Play services library and specify its version
    implementation("com.google.android.gms:play-services-auth:21.1.0")

    // https://mvnrepository.com/artifact/io.github.chaosleung/pinview
    implementation("io.github.chaosleung:pinview:1.4.4")
    // https://mvnrepository.com/artifact/javax.mail/javax.mail-api
    implementation ("com.sun.mail:android-mail:1.6.0")
    // https://mvnrepository.com/artifact/net.sourceforge.jtds/jtds
    implementation("net.sourceforge.jtds:jtds:1.3.1")

    //dependencies cua Dung

    //dependencies cua Giang
    implementation("com.github.blackfizz:eazegraph:1.2.5l@aar")
    implementation("com.nineoldandroids:library:2.4.0")
    implementation("it.xabaras.android:recyclerview-swipedecorator:1.4")
    //dependencies cua Giang

    //dependencies cua QAnh
    implementation ("com.github.dhaval2404:imagepicker:2.1")
    implementation ("androidx.fragment:fragment:1.5.5")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("me.relex:circleindicator:2.1.6")
    //dependencies cua QAnh

}