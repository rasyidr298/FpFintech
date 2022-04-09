package id.fp.core.data.source.remote.network

import id.fp.core.BuildConfig
import id.fp.core.data.source.remote.models.CoinDto
import id.fp.core.data.source.remote.models.ExchangeDto
import id.fp.core.data.source.remote.models.MarketChartDto
import id.fp.core.data.source.remote.response.SampleResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    /*---- Data POST----*/
    @POST("token")
    suspend fun post(
        @Header("Authorization") token: String = BuildConfig.API_KEY
    ): Response<SampleResponse>


    /*---- Data GET----*/
    @GET("app_status")
    suspend fun get(
        @Header("Authorization") token: String
    ): Response<SampleResponse>

    //region Coins
    @GET("coins/markets")
    suspend fun getAllCoins(
        @Query("vs_currency") currency: String
    ): Response<List<CoinDto>>

    @GET("coins/{id}")
    suspend fun getCoinById(
        @Path(value = "id", encoded = true) coinId: String
    ): Response<CoinDto>

    @GET("coins/{id}/market_chart")
    suspend fun getMarketChart(
        @Path(value = "id", encoded = true) coinId: String,
        @Query("vs_currency") currency: String,
        @Query("days") days: Int
    ): Response<MarketChartDto>

    //endregion

    //region Exchange
    @GET("exchanges")
    suspend fun getAllExchanges(): Response<List<ExchangeDto>>
}