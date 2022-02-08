plugins {
    application
}

repositories {
    mavenCentral()
    maven(url = "https://dl9vkdsodilqp.cloudfront.net/repo")
    maven(url = "https://frcmaven.wpi.edu/artifactory/release/")
}

dependencies {
    implementation("org.strykeforce:deadeye:21.0.0")
    implementation("edu.wpi.first.ntcore:ntcore-java:2021.3.1")
    implementation("edu.wpi.first.ntcore:ntcore-jni:2021.3.1:osxx86-64")
    implementation("edu.wpi.first.ntcore:ntcore-jni:2021.3.1:windowsx86-64")
    implementation("edu.wpi.first.ntcore:ntcore-jni:2021.3.1:linuxx86-64")
    implementation("edu.wpi.first.wpiutil:wpiutil-java:2021.3.1")

    // used by DemoTargetData
    implementation("com.squareup.okio:okio:2.10.0")
    implementation("com.squareup.moshi:moshi:1.12.0")
    implementation("org.jetbrains:annotations:21.0.1")
}

application {
    mainClass.set("Main")
}
