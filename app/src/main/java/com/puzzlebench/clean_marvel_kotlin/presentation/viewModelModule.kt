package com.puzzlebench.clean_marvel_kotlin.presentation

import com.puzzlebench.cmk.data.mapper.repository.CharacterMapperRepository
import com.puzzlebench.cmk.data.repository.CharacterDataRepository
import com.puzzlebench.cmk.data.repository.source.CharacterDataSource
import com.puzzlebench.cmk.data.repository.source.CharacterDataSourceImpl
import com.puzzlebench.cmk.data.service.CharacterServicesImpl
import com.puzzlebench.cmk.domain.repository.CharacterRepository
import com.puzzlebench.cmk.domain.service.CharacterServices
import com.puzzlebench.cmk.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.cmk.domain.usecase.SaveCharacterRepositoryUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(), get()) }

}

val useCaseModule = module {
    single<CharacterServices> { CharacterServicesImpl() }
    single { GetCharacterServiceUseCase(get()) }

    single<CharacterDataSource> { CharacterDataSourceImpl() }
    single { CharacterMapperRepository() }
    single<CharacterRepository> { CharacterDataRepository(get(), get()) }
    single { SaveCharacterRepositoryUseCase(get()) }
}