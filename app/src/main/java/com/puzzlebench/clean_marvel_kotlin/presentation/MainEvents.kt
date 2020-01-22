package com.puzzlebench.clean_marvel_kotlin.presentation

import com.puzzlebench.cmk.domain.model.Character

sealed class MainEvents

object ShowToastNoItemToShow : MainEvents() {
    override fun hashCode(): Int = javaClass.hashCode()
    override fun equals(other: Any?) = equalz(other)
}

object HideLoading : MainEvents() {
    override fun hashCode(): Int = javaClass.hashCode()
    override fun equals(other: Any?) = equalz(other)
}

object Refresh : MainEvents() {
    override fun hashCode(): Int = javaClass.hashCode()
    override fun equals(other: Any?) = equalz(other)
}

class ShowToastNetworkError(val message: String) : MainEvents() {
    override fun hashCode(): Int = javaClass.hashCode()
    override fun equals(other: Any?) = equalz(other)
}

class ShowCharacters(val characters: List<Character>) : MainEvents() {
    override fun hashCode(): Int = javaClass.hashCode()
    override fun equals(other: Any?) = equalz(other)
}

inline fun <reified T : MainEvents> T.equalz(other: Any?) = when {
    this === other -> true
    other !is T -> false
    else -> true
}