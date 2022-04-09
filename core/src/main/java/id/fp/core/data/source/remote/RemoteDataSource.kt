package id.fp.core.data.source.remote

import id.fp.core.data.source.remote.models.CoinDto
import id.fp.core.data.source.remote.models.ExchangeDto
import id.fp.core.data.source.remote.models.MarketChartDto
import id.fp.core.data.source.remote.network.ApiResponse
import id.fp.core.data.source.remote.network.ApiService
import id.fp.core.data.source.remote.response.DataSample
import id.fp.core.domain.model.Sample
import id.fp.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.RequestBody
import java.io.IOException

class RemoteDataSource(private val apiService: ApiService) : IRemoteDataSource {
    var token: String? = ""

    //get response
    fun getResponse(): Flow<ApiResponse<List<Sample>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = apiService.get(token!!)
                if (response.isSuccessful) {
                    emit(ApiResponse.Success(DataMapper.mapSampleRestoMod(response.body()!!.data)))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: IOException) {
                emit(ApiResponse.Error(e.message!!))
            }
        }.flowOn(Dispatchers.IO)
    }
    fun getResponseDb(): Flow<ApiResponse<List<DataSample>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = apiService.get(token!!)
                if (response.isSuccessful) {
                    emit(ApiResponse.Success(response.body()!!.data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: IOException) {
                emit(ApiResponse.Error(e.message!!))
            }
        }.flowOn(Dispatchers.IO)
    }

    //post response
    fun pushResponse(dataSample: HashMap<String, RequestBody>): Flow<ApiResponse<Boolean>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = apiService.post()
                if (response.isSuccessful) {
                    emit(ApiResponse.Success(response.isSuccessful))
                } else {
                    emit(ApiResponse.Error(response.message()))
                }
            } catch (e: IOException) {
                emit(ApiResponse.Error(e.message!!))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun fetchCoins(currency: String): Flow<ApiResponse<List<CoinDto>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = apiService.getAllCoins(currency)
                if (response.isSuccessful) {
                    emit(ApiResponse.Success(response.body() ?: emptyList<CoinDto>()))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: IOException) {
                emit(ApiResponse.Error(e.message!!))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun fetchCoinById(coinId: String): Flow<ApiResponse<CoinDto>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = apiService.getCoinById(coinId)
                if (response.isSuccessful) {
                    response.body()?.let { coin ->
                        emit(ApiResponse.Success(coin))
                    } ?: emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: IOException) {
                emit(ApiResponse.Error(e.message!!))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun fetchExchanges(): Flow<ApiResponse<List<ExchangeDto>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = apiService.getAllExchanges()
                if (response.isSuccessful) {
                    emit(ApiResponse.Success(response.body() ?: emptyList<ExchangeDto>()))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: IOException) {
                emit(ApiResponse.Error(e.message!!))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun fetchMarketChartData(
        coinId: String,
        currency: String,
        days: Int
    ): Flow<ApiResponse<MarketChartDto>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = apiService.getMarketChart(coinId, currency, days)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(ApiResponse.Success(it))
                    } ?: emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: IOException) {
                emit(ApiResponse.Error(e.message!!))
            }
        }.flowOn(Dispatchers.IO)
    }
}
