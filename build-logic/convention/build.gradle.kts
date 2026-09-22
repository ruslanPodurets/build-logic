plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    implementation(
        "org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin:${libs.versions.kotlin.get()}"
    )
}

gradlePlugin {
    plugins {
        create("androidLibrary") {
            id = "convention.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        create("kotlinJvm") {
            id = "convention.kotlin.jvm"
            implementationClass = "KotlinJvmConventionPlugin"
        }
    }
}