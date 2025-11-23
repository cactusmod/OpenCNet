pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}


rootProject.name = "opencnet"

include("commons")
include("server")
include("client")