import com.github.erizo.gradle.JcstressPluginExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    val kotlinVersion = "1.3.0-rc-131"
    val jcstressVersion = "0.8.2"

    repositories {
        mavenCentral()
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.github.erizo.gradle:jcstress-gradle-plugin:$jcstressVersion")
    }
}

plugins {
    java
}

apply {
    plugin("kotlin")
    plugin("jcstress")
}

group = "io.github.anti-social"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "0.30.1-eap13")
    compile("org.jetbrains.kotlinx", "kotlinx-coroutines-jdk8", "0.30.1-eap13")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

configure<JcstressPluginExtension> {
    jcstressDependency = "org.openjdk.jcstress:jcstress-core:0.4"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}