package com.example.translator.locale

data class AutocompleteLocale(
    val language: String,
    val locale: String
) {
    companion object {
        val locales: MutableList<AutocompleteLocale> = mutableListOf(
            AutocompleteLocale("Romanian", "ro"),
            AutocompleteLocale("English", "en"),
        )
    }

    override fun toString(): String {
        return language
    }
}