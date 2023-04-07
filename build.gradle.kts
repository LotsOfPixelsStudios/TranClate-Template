plugins {
    kotlin("jvm") version "1.7.20"
    id("application")
}

//load local env file
val localEnvFile = File(
    "${System.getProperties().getProperty("user.home")}${File.separator}.gradle",
    "env-timoliacreative.local.gradle.kts"
)
if (localEnvFile.exists()) {
    //set project extras
    apply(from = localEnvFile.path)
} else {
    //gitlab project that has access to the repo
    project.extra.set("gitlab_user", "Job-Token")
    project.extra.set("gitlab_token", System.getenv("CI_JOB_TOKEN") as String)
}

group = "com.timoliacreative"
version = "1"

fun MavenArtifactRepository.authTcGitlab() {
    if (localEnvFile.exists()) {
        credentials {
            username = project.extra["gitlab_user"] as String
            password = project.extra["gitlab_token"] as String
        }
    } else {
        credentials(HttpHeaderCredentials::class) {
            name = project.extra["gitlab_user"] as String
            value = project.extra["gitlab_token"] as String
        }
        authentication {
            create<HttpHeaderAuthentication>("header")
        }
    }
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://git.timoliacreative.de/api/v4/projects/32/packages/maven")
        authTcGitlab()
    }
    maven {
        url = uri("https://git.timoliacreative.de/api/v4/projects/102/packages/maven")
        authTcGitlab()
    }
}

application {
    mainClass.set("com.tcreative.addons.MainKt")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation(group = "com.timoliacreative", name = "tranclate", version = "2.9-dev3")
    implementation(group = "com.timoliacreative", name = "tranclate-std-lib", version = "0.9.0-dev10")

    //test
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}