package io.noartcode.theprice.page.i18n

sealed class Platform(val id: String) {
    data object Android : Platform("android")
    data object IOS : Platform("ios")
    data object Both : Platform("both")

    companion object {
        val all = listOf(Android, IOS, Both)
    }
}

fun Platform.displayName(strings: Strings): String = when (this) {
    Platform.Android -> strings.platformAndroid
    Platform.IOS -> strings.platformIOS
    Platform.Both -> strings.platformBoth
}
