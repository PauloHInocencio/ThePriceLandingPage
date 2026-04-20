package io.noartcode.theprice.page.i18n

import kotlinx.browser.localStorage

private const val LANGUAGE_KEY = "page:language"

object LanguageStorage {
    fun save(language: Language) {
        localStorage.setItem(LANGUAGE_KEY, language.code)
    }

    fun load(): Language? {
        return localStorage.getItem(LANGUAGE_KEY)?.let { Language.fromCode(it) }
    }
}