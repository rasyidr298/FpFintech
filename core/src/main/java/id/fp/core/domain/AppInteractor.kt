package id.fp.core.domain

import id.fp.core.data.Resource
import id.fp.core.data.source.remote.network.ApiResponse
import id.fp.core.domain.model.Coin
import id.fp.core.domain.model.Exchange
import id.fp.core.domain.model.OnBoardingPart
import id.fp.core.domain.model.Sample
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody


interface AppUseCase {
    //sample
    fun getSampleDb(): Flow<Resource<List<Sample>>>
    fun getSample(): Flow<ApiResponse<List<Sample>>>
    fun searchSample(search: String): Flow<List<Sample>>
    fun postSample(dataSample: HashMap<String, RequestBody>): Flow<ApiResponse<Boolean>>

    fun getCoins(currency: String): Flow<Resource<List<Coin>>>
    fun getCoinById(coinId: String): Flow<Resource<Coin>>
    fun getExchanges(): Flow<Resource<List<Exchange>>>
    fun getMarketChart(coinId: String, currency: String, days: Int): Flow<Resource<List<List<Number>>>>
    fun isOnboardingCompleted(): Flow<Boolean>
    suspend fun setOnboardingCompleted(isCompleted: Boolean)
    fun getOnBoardingParts(): List<OnBoardingPart>
}

class AppInteractor(private val repository: IAppRepository) : AppUseCase {
    override fun getSampleDb(): Flow<Resource<List<Sample>>> {
        return repository.getSampleDb()
    }

    override fun getSample(): Flow<ApiResponse<List<Sample>>> {
        return repository.getSample()
    }

    override fun searchSample(search: String): Flow<List<Sample>> {
        return repository.searchSample(search)
    }

    override fun postSample(dataSample: HashMap<String, RequestBody>): Flow<ApiResponse<Boolean>> {
        return repository.postSample(dataSample)
    }

    override fun isOnboardingCompleted(): Flow<Boolean> {
        return flow {
            emit(false)
        }
    }

    override suspend fun setOnboardingCompleted(isCompleted: Boolean) {
        repository.setOnboardingCompleted(isCompleted)
    }

    override fun getOnBoardingParts(): List<OnBoardingPart> {
        return repository.getOnBoardingParts()
    }

    override fun getCoins(currency: String): Flow<Resource<List<Coin>>> {
        return repository.fetchCoins(currency)
    }

    override fun getCoinById(coinId: String): Flow<Resource<Coin>> {
        return repository.fetchCoinById(coinId)
    }

    override fun getExchanges(): Flow<Resource<List<Exchange>>> {
        return repository.fetchExchanges()
    }

    override fun getMarketChart(coinId: String, currency: String, days: Int): Flow<Resource<List<List<Number>>>> {
        return repository.fetchMarketChartData(coinId, currency, days)
    }
}