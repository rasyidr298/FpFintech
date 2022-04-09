package id.fp.fintech.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.fp.core.domain.AppUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SplashViewModel(
    private val appUseCase: AppUseCase
) : ViewModel() {
    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean>
        get() = _state

    fun isOnboardingCompleted(): Boolean {
        viewModelScope.launch {
            _state.value.let { _ ->
                appUseCase.isOnboardingCompleted().collect {
                    _state.value = it
                }
            }
        }
        return _state.value == true
    }
}
