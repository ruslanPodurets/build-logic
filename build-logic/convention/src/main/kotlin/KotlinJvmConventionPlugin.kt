import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

class KotlinJvmConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.apply("java-library")
        target.pluginManager.apply("org.jetbrains.kotlin.jvm")

        val libs = target.extensions
            .getByType<VersionCatalogsExtension>()
            .named("libs")

        val javaVersion = libs.findVersion("java")
            .get()
            .requiredVersion

        target.extensions.configure<JavaPluginExtension> {
            val version = JavaVersion.toVersion(javaVersion.toInt())

            sourceCompatibility = version
            targetCompatibility = version
        }

        target.extensions.configure<KotlinJvmProjectExtension> {
            compilerOptions {
                jvmTarget.set(JvmTarget.fromTarget(javaVersion))
            }
        }
    }
}