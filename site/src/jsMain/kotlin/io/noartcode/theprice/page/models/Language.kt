package io.noartcode.theprice.page.models

import io.noartcode.theprice.page.i18n.Strings

sealed class Language(val code: String, val displayName: String, val flag: String) {
    data object PortugueseBR : Language("pt-BR", "Português", "🇧🇷")
    data object English : Language("en", "English", "🇺🇸")

    companion object {
        val all = listOf(PortugueseBR, English)
        fun fromCode(code: String) = all.find { it.code == code } ?: English
    }
}

fun Language.strings(): Strings = when (this) {
    Language.PortugueseBR -> Strings.PortugueseBR
    Language.English -> Strings.English
}