# Convention Plugins

Reusable Gradle convention plugins for Android/Kotlin projects.

## Available Plugins

### `convention.android.library`

Use for Android Library modules.

```kotlin
plugins {
    id("convention.android.library")
}
```

The plugin applies:

- `com.android.library`

and configures:

- `compileSdk`;
- `minSdk`;
- Java source/target compatibility.

### `convention.kotlin.jvm`

Use for pure Kotlin/JVM modules that do not use the Android Gradle Plugin.

```kotlin
plugins {
    id("convention.kotlin.jvm")
}
```

The plugin applies:

- `java-library`;
- `org.jetbrains.kotlin.jvm`

and configures:

- Java source/target compatibility;
- Kotlin JVM target.

---

## Required Project Configuration

Both convention plugins require a Gradle Version Catalog named `libs.versions.toml`.

The catalog must contain the following versions.

### Required by `convention.android.library`

```toml
[versions]
compileSdk = "37"
minSdk = "29"
java = "17"
```

`compileSdk` and `minSdk` must be valid integer SDK versions.

`java` must be a valid Java version supported by `JavaVersion` and Kotlin's `JvmTarget`.

### Required by `convention.kotlin.jvm`

```toml
[versions]
java = "17"
```

---

A minimal `gradle/libs.versions.toml` for the convention plugins is:

```toml
[versions]
compileSdk = "37"
minSdk = "29"
java = "17"
```

The values above are examples. The plugins do not require these exact numbers; they require the corresponding version keys.

---

## Required Gradle Plugin Dependencies

The `build-logic` project must be able to resolve the plugins used internally by the convention plugins:

```text
com.android.library
org.jetbrains.kotlin.jvm
```

The convention plugin implementation applies them automatically.

The consuming modules do **not** need to apply these plugins separately.

For example, an Android library module only needs:

```kotlin
plugins {
    id("convention.android.library")
}
```

not:

```kotlin
plugins {
    id("com.android.library")
    id("convention.android.library")
}
```

---

## How to Add `build-logic` to a Project

Include the `build-logic` build from the root `settings.gradle.kts`:

```kotlin
pluginManagement {
    includeBuild("build-logic")
}
```

Then the convention plugins can be used by project modules.

### Android Library Module

```kotlin
plugins {
    id("convention.android.library")
}

android {
    namespace = "com.example.feature"
}

dependencies {
    // Module-specific dependencies
}
```

### Pure Kotlin/JVM Module

```kotlin
plugins {
    id("convention.kotlin.jvm")
}

dependencies {
    // Module-specific dependencies
}
```

---

## Module Usage

| Module type     | Plugin                       |
| --------------- | ---------------------------- |
| Android Library | `convention.android.library` |
| Pure Kotlin/JVM | `convention.kotlin.jvm`      |

Use `convention.android.library` for modules that require the Android Gradle Plugin.

Use `convention.kotlin.jvm` for pure Kotlin/JVM modules that do not require Android.

---

## Design Principle

The plugin IDs are intentionally project-independent:

```text
convention.android.library
convention.kotlin.jvm
```

They are intended to be reusable across multiple projects without renaming the plugins for each project.

The convention plugins contain only generic Gradle/build configuration. They do not contain product-specific, client-specific, environment-specific, or business logic.
