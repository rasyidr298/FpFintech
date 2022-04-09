package id.fp.fintech.ui.main.markets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.fp.core.data.Resource
import id.fp.core.domain.AppUseCase
import id.fp.core.domain.model.Coin
import id.fp.core.domain.model.DataState
import id.fp.core.domain.model.Exchange
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MarketViewModel(
    private val appUseCase: AppUseCase
) : ViewModel() {

    private val _coinState = MutableLiveData<DataState<List<Coin>>>()
    val coinState: LiveData<DataState<List<Coin>>>
        get() = _coinState

    private val _exchangeState = MutableLiveData<DataState<List<Exchange>>>()
    val exchangeState: LiveData<DataState<List<Exchange>>>
        get() = _exchangeState

    fun getCoins(currency: String) {
        viewModelScope.launch {
            _coinState.value.let { _ ->
                appUseCase.getCoins(currency).onEach {
                    when(it) {
                        is Resource.Error -> _coinState.value = DataState.Error(Exception(it.message))
                        is Resource.Loading -> _coinState.value = DataState.Loading
                        is Resource.Success -> _coinState.value = DataState.Success(it.data)
                    }
                }.launchIn(viewModelScope)
            }
        }
    }


    fun getExchanges() {
        viewModelScope.launch {
            _exchangeState.value.let { _ ->
                appUseCase.getExchanges().onEach {
                    when(it) {
                        is Resource.Error -> _exchangeState.value = DataState.Error(Exception(it.message))
                        is Resource.Loading -> _exchangeState.value = DataState.Loading
                        is Resource.Success -> _exchangeState.value = DataState.Success(it.data)
                    }
                }.launchIn(viewModelScope)
            }
        }
    }

}