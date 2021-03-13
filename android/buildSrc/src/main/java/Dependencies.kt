@file:Suppress("MayBeConstant")

object ApplicationId {
    val id = "com.backbase.assignment"
}

object Modules {
    val app = ":app"
}

object Releases {
    val versionCode = 1
    val versionName = "1.0"
}

object Versions {
    val gradle = "4.1.0"
    val kotlin = "1.4.10"
    val kotlinCoroutines = "1.3.2"
    val lifecycle = "2.2.0"
    val ktx = "1.3.2"

    val design = "1.3.0"
    val appcompat = "1.2.0"
    val constraint = "2.0.4"
    val recycler = "1.1.0"
    val timber = "4.7.1"
    val retrofit = "2.6.0"
    val retrofitSerialization = "0.8.0"
    val jsonSerialization = "1.1.0"
    val okhttp = "4.9.0"
    val okhttpLogger = "4.9.0"

    val picasso = "2.71828"

    val koin = "2.0.1"
    val koinAndroid = "2.0.1"

    val junit = "4.13"
    val runner = "1.3.0"

    val compileSdk = 28
    val minSdk = 23
    val targetSdk = 29
}

object Libraries {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    val kotlinCoroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

    val koin = "org.koin:koin-core:${Versions.koin}"
    val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    val ktx = "androidx.core:core-ktx:${Versions.ktx}"
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitSerialization =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofitSerialization}"
    val jsonSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.jsonSerialization}"
    val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    val okhttpLogger = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLogger}"
    val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
}

object SupportLibraries {
    val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val design = "com.google.android.material:material:${Versions.design}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
    val recycler = "androidx.recyclerview:recyclerview:${Versions.recycler}"
}

object TestLibraries {
    val instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    val koin = "org.koin:koin-test:${Versions.koin}"
    val junit = "junit:junit:${Versions.junit}"
    val runner = "androidx.test:runner:${Versions.runner}"
}
