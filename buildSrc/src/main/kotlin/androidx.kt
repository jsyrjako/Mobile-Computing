object androidx {
    object core{
        private val version = "1.9.0"
        val ktx = "androidx.core:core-ktx:$version"
    }

    object compose {
        private val version = "1.3.1"
        object ui {
            val ui = "androidx.compose.ui:ui:$version"
            val preview = "androidx.compose.ui:ui-tooling-preview:$version"
            val test_junit4 = "androidx.compose.ui:ui-test-junit4:$version"
            val tooling = "androidx.compose.ui:ui-tooling:$version"
            val test_manifest = "androidx.compose.ui:ui-test-manifest:$version"
        }
        val material = "androidx.compose.material:material:$version"
        val material_icons = "androidx.compose.material:material-icons-extended:$version"
    }

    object lifecycle {
        private val version = "2.5.1"
        val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
    }

    object activity {
        private val version = "1.6.1"
        val activity = "androidx.activity:activity-compose:$version"
    }

    object navigation {
        private val version = "2.6.0-alpha04"
        val navigation = "androidx.navigation:navigation-compose:$version"
        object hilt {
            private val version = "1.0.0"
            val compose = "androidx.hilt:hilt-navigation-compose:$version"
        }
    }

    object constraintlayout {
        private val version = "1.1.0-alpha06"
        val constraintlayout = "androidx.constraintlayout:constraintlayout-compose:$version"
    }

    object room {
        private val version = "2.5.0"
        val room = "androidx.room:room-ktx:$version"
        val runtime = "androidx.room:room-runtime:$version"
        val compiler = "androidx.room:room-compiler:$version"
    }

    object test_ext {
        private val version = "1.1.5"
        val junit = "androidx.test.ext:junit:$version"
    }
    object test_espresso {
        private val version = "3.5.1"
        val espresso_core = "androidx.test.espresso:espresso-core:$version"
    }

}