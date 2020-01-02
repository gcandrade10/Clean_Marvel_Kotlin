package com.puzzlebench.clean_marvel_kotlin.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puzzlebench.cmk.domain.model.Character
import com.puzzlebench.cmk.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.cmk.domain.usecase.SaveCharacterRepositoryUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainViewModel(private val getCharacterServiceUseCase: GetCharacterServiceUseCase,
                    private val saveCharacterRepositoryUseCase: SaveCharacterRepositoryUseCase) : ViewModel() {

    private val subscriptions = CompositeDisposable()

    private val characters: MutableLiveData<List<Character>> by lazy {
        MutableLiveData<List<Character>>().also {
            loadCharacters()
        }
    }

    private val events = MutableLiveData<MainEvents>()

    fun getCharacters(): LiveData<List<Character>> {
        return characters
    }

    fun loadCharacters() {
        requestGetCharacters()
    }

    private fun requestGetCharacters() {
        val subscription = getCharacterServiceUseCase.invoke().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ characters ->
            if (characters.isEmpty()) {
                events.value = ShowToastNoItemToShow
            } else {
                saveCharacterRepositoryUseCase.invoke(characters)
                events.value = ShowCharacters(characters)
            }
            events.value = HideLoading

        }, { e ->
            events.value = HideLoading
            events.value = ShowToastNetworkError(e.message.toString())
        })
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        subscriptions.dispose()
        super.onCleared()
    }

    fun getEvents(): MutableLiveData<MainEvents> {
        return events
    }

    fun refresh(){
        requestGetCharacters()
        events.value=Refresh
    }

}