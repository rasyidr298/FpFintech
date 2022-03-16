package id.fp.fintech.di

import id.fp.core.domain.AppInteractor
import id.fp.core.domain.IAppUseCase
import id.fp.fintech.ui.main.MainViewModel
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel


val useCaseModule = module {
    factory<IAppUseCase> { AppInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}