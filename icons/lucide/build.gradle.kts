import mihon.gradle.tasks.GenerateVectorDrawablesTask

plugins {
    alias(mihonx.plugins.android.library)
    alias(mihonx.plugins.compose)

    alias(mihonx.plugins.spotless)

    alias(libs.plugins.valkyrie)
}

android {
    namespace = "mihon.icons.lucide"
}

spotless {
    format("svg") {
        target("src/**/*.svg")
        trimTrailingWhitespace()
        endWithNewline()
    }
}

valkyrie {
    packageName = "mihon.icons.lucide"
    generateAtSync = true

    imageVector {
        suppressUnusedReceiverWarning = true
    }

    iconPack {
        name = "Lucide"
        targetSourceSet = "main"

        nested {
            name = "Icons"
            sourceFolder = "icons"
            autoMirror = false
        }

        // https://github.com/ComposeGears/Valkyrie/pull/1019
        nested {
            name = "AutoMirrored"
            sourceFolder = "autoMirrored"
            autoMirror = true
        }
    }
}

androidComponents {
    onVariants { variant ->
        val resSource = variant.sources.res ?: return@onVariants

        val variantName = variant.name.replaceFirstChar { it.uppercase() }
        val task = tasks.register<GenerateVectorDrawablesTask>("generate${variantName}VectorDrawables") {
            svgFiles.from(
                listOf(
                    "icons/book_open",
                    "icons/check",
                    "icons/x",
                    "icons/glasses",
                    "icons/refresh_cw",
                    "icons/share_2",
                    "icons/puzzle",
                    "icons/folder",
                    "icons/pause",
                    "icons/image",
                    "icons/play",
                    "icons/triangle_alert",
                ).map { layout.projectDirectory.file("src/main/valkyrieResources/$it.svg") },
            )
        }
        resSource.addGeneratedSourceDirectory(task) { it.outputDir }
    }
}

dependencies {
    api(libs.androidx.compose.ui)
}
