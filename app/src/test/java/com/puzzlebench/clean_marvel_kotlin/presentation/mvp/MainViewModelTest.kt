package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.puzzlebench.clean_marvel_kotlin.presentation.*
import com.puzzlebench.cmk.domain.model.Character
import com.puzzlebench.cmk.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.cmk.domain.usecase.SaveCharacterRepositoryUseCase
import io.github.plastix.rxschedulerrule.RxSchedulerRule
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MainViewModelTest : KoinTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var schedulerRule = RxSchedulerRule()

    @Mock
    private lateinit var observer: Observer<MainEvents>

    private lateinit var viewModel: MainViewModel
    private val getCharacterServiceUseCase = Mockito.mock(GetCharacterServiceUseCase::class.java)
    private val saveCharacterRepositoryUseCase = Mockito.mock(SaveCharacterRepositoryUseCase::class.java)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        startKoin {
            modules(listOf(viewModelModule, useCaseModule))
        }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        viewModel = MainViewModel(
                getCharacterServiceUseCase,
                saveCharacterRepositoryUseCase).apply { getEvents().observeForever(observer) }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun `test Load Characters happy path`() {
        // Given
        val itemsCharacters = listOf(1..5).map {
            Mockito.mock(Character::class.java)
        }
        val observable = Single.just(itemsCharacters)
        given(getCharacterServiceUseCase.invoke()).willReturn(observable)
        doNothing().`when`(saveCharacterRepositoryUseCase).invoke(itemsCharacters)

        // When
        viewModel.loadCharacters()

        // Then
        verify(saveCharacterRepositoryUseCase, Mockito.times(1)).invoke(itemsCharacters)
        verify(observer).onChanged(ShowCharacters(itemsCharacters))
        verify(observer).onChanged(HideLoading)
    }

    @Test
    fun `test Load Characters empty`() {
        // Given
        val itemsCharacters = emptyList<Character>()
        val observable = Single.just(itemsCharacters)
        given(getCharacterServiceUseCase.invoke()).willReturn(observable)

        // When
        viewModel.loadCharacters()

        // Then
        verify(observer).onChanged(ShowToastNoItemToShow)
        verify(observer).onChanged(HideLoading)
    }

    @Test
    fun `test Load Characters network error`() {
        // Given
        val errorMessage = "Problem getting the data"
        given(getCharacterServiceUseCase.invoke()).willReturn(Single.error(Exception(errorMessage)))

        // When
        viewModel.loadCharacters()

        // Then
        verify(observer).onChanged(ShowToastNetworkError(errorMessage))
    }
}