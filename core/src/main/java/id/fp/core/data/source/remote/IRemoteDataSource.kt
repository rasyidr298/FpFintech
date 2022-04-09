package id.fp.core.data.source.remote

import id.fp.core.data.source.remote.models.CoinDto
import id.fp.core.data.source.remote.models.ExchangeDto
import id.fp.core.data.source.remote.models.MarketChartDto
import id.fp.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {
    fun fetchCoins(currency: String): Flow<ApiResponse<List<CoinDto>>>

    fun fetchCoinById(coinId: String): Flow<ApiResponse<CoinDto>>

    fun fetchExchanges(): Flow<ApiResponse<List<ExchangeDto>>>

    fun fetchMarketChartData(
        coinId: String,
        currency: String,
        days: Int
    ): Flow<ApiResponse<MarketChartDto>>
}