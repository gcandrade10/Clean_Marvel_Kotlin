package com.puzzlebench.clean_marvel_kotlin.presentation

import com.puzzlebench.cmk.domain.model.Character

sealed class MainEvents

object ShowToastNoItemToShow : MainEvents()
object HideLoading : MainEvents()
object Refresh : MainEvents()
class ShowToastNetworkError(val message: String) : MainEvents()
class ShowCharacters(val characters: List<Character>) : MainEvents()

