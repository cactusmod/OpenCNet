plugins {
    id("java")
    id("java-library")
    id("com.gradleup.shadow") version "8.3.0"
}

repositories {
    mavenCentral()
}

val targetDir = file("$rootDir/.dist")
val targetJavaVersion = 21

allprojects {
    group = "com.dwarslooper.cactus.ocn"
    version = "1.0"

    apply(plugin = "java")
    apply(plugin = "java-library")
    apply(plugin = "com.gradleup.shadow")

    repositories {
        mavenCentral()
        maven("https://oss.sonatype.org/content/groups/public/")
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = targetJavaVersion.toString()
        targetCompatibility = targetJavaVersion.toString()
        options.encoding = "UTF-8"
        options.release.set(targetJavaVersion)
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
}

subprojects {
    afterEvaluate {
        listOf(
            "jar" to "${project.name}-${project.version}.jar",
            "shadowJar" to "${project.name}-${project.version}-deps.jar"
        ).forEach { (taskName, fileName) ->
            tasks.named(taskName) {
                // outputs.upToDateWhen { false } -> forces rebuild
                (this as Jar).apply {
                    archiveFileName.set(fileName)
                    destinationDirectory.set(targetDir)
                }
            }
        }

        dependencies {
            implementation("io.netty", "netty-all", "4.1.114.Final")
            implementation("org.apache.logging.log4j", "log4j-core", "2.20.0")
            implementation("org.apache.logging.log4j", "log4j-api", "2.20.0")
        }

        tasks.register("prepareKotlinBuildScriptModel") {

        }
    }
}