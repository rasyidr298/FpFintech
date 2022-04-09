package id.fp.fintech.ui.coindetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.fp.core.data.Resource
import id.fp.core.domain.AppUseCase
import id.fp.core.domain.model.DataState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CoinDetailsViewModel(
        private val appUseCase: AppUseCase,
) : ViewModel() {

    private val _chartState = MutableLiveData<DataState<List<List<Number>>>>()
    val chartState: LiveData<DataState<List<List<Number>>>>
        get() = _chartState

    fun getMarketChart(coinId: String, currency: String, days: Int) {
        viewModelScope.launch {
            _chartState.value.let { _ ->
                appUseCase.getMarketChart(coinId, currency, days).collect {
                    when(it) {
                        is Resource.Error -> _chartState.value = DataState.Error(Exception(it.message))
                        is Resource.Loading -> _chartState.value = DataState.Loading
                        is Resource.Success -> _chartState.value = DataState.Success(it.data)
                    }
                }
            }
        }
    }

}