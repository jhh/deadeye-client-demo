plugins {
    application
}

repositories {
    mavenCentral()
    maven(url = "https://dl9vkdsodilqp.cloudfront.net/repo")
    maven(url = "https://frcmaven.wpi.edu/artifactory/release/")
}

dependencies {
    val wpiVersion = "2022.3.1"
    implementation("org.strykeforce:deadeye:22.0.0")
    implementation("edu.wpi.first.ntcore:ntcore-java:$wpiVersion")
    implementation("edu.wpi.first.ntcore:ntcore-jni:$wpiVersion:osxx86-64")
    implementation("edu.wpi.first.ntcore:ntcore-jni:$wpiVersion:windowsx86-64")
    implementation("edu.wpi.first.ntcore:ntcore-jni:$wpiVersion:linuxx86-64")
    implementation("edu.wpi.first.wpiutil:wpiutil-java:$wpiVersion")

    // used by DemoTargetData
    implementation("com.squareup.okio:okio:3.0.0")
    implementation("com.squareup.moshi:moshi:1.13.0")
    implementation("org.jetbrains:annotations:22.0.0")
    implementation("org.slf4j:slf4j-api:1.7.35")
    implementation("ch.qos.logback:logback-classic:1.2.10")
}

application {
    mainClass.set("Main")
}
