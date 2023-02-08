object google {

    object accompanist {
        private val version = "0.29.0-alpha"
        val accompanist = "com.google.accompanist:accompanist-insets:$version"
    }

    object dagger {
        private val version = "2.44.2"
        val hilt = "com.google.dagger:hilt-android:$version"
        object hiltAndroid {
            private val version = "2.44"
            val compiler = "com.google.dagger:hilt-android-compiler$version"
        }
    }
}