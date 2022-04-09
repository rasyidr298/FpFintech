package id.fp.fintech.di

import id.fp.fintech.ui.main.home.HomeViewModel
import id.fp.fintech.ui.main.markets.MarketViewModel
import id.fp.fintech.ui.onboarding.OnboardingViewModel
import id.fp.fintech.ui.splash.SplashViewModel
import id.fp.core.domain.AppInteractor
import id.fp.core.domain.AppUseCase
import id.fp.fintech.ui.coindetails.CoinDetailsViewModel
import org.koin.dsl.module
import org.koin.android.viewmodel.dsl.viewModel


val useCaseModule = module {
    factory<AppUseCase> { AppInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { MarketViewModel(get()) }
    viewModel { OnboardingViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { CoinDetailsViewModel(get()) }
}