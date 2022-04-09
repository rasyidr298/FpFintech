package id.fp.fintech.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.fp.core.domain.AppUseCase
import id.fp.core.domain.model.OnBoardingState
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val appUseCase: AppUseCase
) : ViewModel() {

    private val _state = MutableLiveData<OnBoardingState>()
    val state: LiveData<OnBoardingState>
        get() = _state

    fun getOnBoardingSlide() {
        viewModelScope.launch {
            val list = appUseCase.getOnBoardingParts()
            _state.value = OnBoardingState.COMPLET(list)
        }
    }

    fun setOnboardingCompleted() {
        viewModelScope.launch {
            appUseCase.setOnboardingCompleted(true)
        }
    }
}
