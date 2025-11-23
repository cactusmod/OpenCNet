plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":commons"))
    implementation(project(":server"))
    implementation(project(":client"))
}