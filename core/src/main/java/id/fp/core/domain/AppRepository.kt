package id.fp.core.domain

import id.fp.core.data.Resource
import id.fp.core.data.NetworkBoundResource
import id.fp.core.data.source.local.LocalDataSource
import id.fp.core.data.source.remote.RemoteDataSource
import id.fp.core.data.source.remote.mapper.CoinMapper.toDomain
import id.fp.core.data.source.remote.mapper.ExchangeMapper.toDomain
import id.fp.core.data.source.remote.network.ApiResponse
import id.fp.core.data.source.remote.response.DataSample
import id.fp.core.domain.model.Coin
import id.fp.core.domain.model.Exchange
import id.fp.core.domain.model.OnBoardingPart
import id.fp.core.domain.model.Sample
import id.fp.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.RequestBody


interface IAppRepository {
    //sample
    fun getSampleDb(): Flow<Resource<List<Sample>>>
    fun getSample(): Flow<ApiResponse<List<Sample>>>
    fun searchSample(search: String): Flow<List<Sample>>
    fun postSample(dataSample: HashMap<String, RequestBody>): Flow<ApiResponse<Boolean>>

    fun fetchCoins(currency: String): Flow<Resource<List<Coin>>>
    fun fetchCoinById(id: String): Flow<Resource<Coin>>
    fun fetchExchanges(): Flow<Resource<List<Exchange>>>
    fun fetchMarketChartData(coinId: String, currency: String, days: Int): Flow<Resource<List<List<Number>>>>

    suspend fun setOnboardingCompleted(isCompleted: Boolean)
    fun getOnBoardingParts(): List<OnBoardingPart>
}

class AppRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IAppRepository {

    //sample
    override fun getSample(): Flow<ApiResponse<List<Sample>>> {
        return remoteDataSource.getResponse()
    }

    override fun searchSample(search: String): Flow<List<Sample>> {
        return localDataSource.searchSample(search).map {
            DataMapper.mapSampleEntToMod(it)
        }
    }

    override fun getSampleDb(): Flow<Resource<List<Sample>>> =
        object : NetworkBoundResource<List<Sample>, List<DataSample>>() {
            override fun loadFromDB(): Flow<List<Sample>> {
                return localDataSource.getSample().map {
                    DataMapper.mapSampleEntToMod(it)
                }
            }

            override fun shouldFetch(data: List<Sample>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<DataSample>>> {
                return remoteDataSource.getResponseDb()
            }

            override suspend fun saveCallResult(data: List<DataSample>) {
                val result = DataMapper.mapSampleResToEnt(data)
                localDataSource.insertSample(result)
            }
        }.asFlow()

    override fun postSample(dataSample: HashMap<String, RequestBody>): Flow<ApiResponse<Boolean>> {
        return remoteDataSource.pushResponse(dataSample)
    }

    override fun fetchCoins(currency: String): Flow<Resource<List<Coin>>> {
        return remoteDataSource.fetchCoins(currency).map {
            try {
                when (it) {
                    is ApiResponse.Error -> {
                        Resource.Error(it.errorMessage)
                    }
                    is ApiResponse.Empty -> {
                        Resource.Error("No result found")
                    }
                    is ApiResponse.Success -> {
                        Resource.Success(it.data.toDomain())
                    }
                    is ApiResponse.Loading -> Resource.Loading()
                }

            } catch (e: Exception) {
                Resource.Error(e.message!!)
            }
        }
    }

    override fun fetchCoinById(id: String): Flow<Resource<Coin>> {
        return remoteDataSource.fetchCoinById(id).map {
            try {
                when (it) {
                    is ApiResponse.Error -> {
                        Resource.Error(it.errorMessage)
                    }
                    is ApiResponse.Empty -> {
                        Resource.Error("No result found")
                    }
                    is ApiResponse.Success -> {
                        Resource.Success(it.data.toDomain())
                    }
                    is ApiResponse.Loading -> Resource.Loading()
                }

            } catch (e: Exception) {
                Resource.Error(e.message!!)
            }
        }
    }

    override fun fetchExchanges(): Flow<Resource<List<Exchange>>> {
        return remoteDataSource.fetchExchanges().map {
            try {
                when (it) {
                    is ApiResponse.Error -> {
                        Resource.Error(it.errorMessage)
                    }
                    is ApiResponse.Empty -> {
                        Resource.Error("No result found")
                    }
                    is ApiResponse.Success -> {
                        Resource.Success(it.data.toDomain())
                    }
                    is ApiResponse.Loading -> Resource.Loading()
                }

            } catch (e: Exception) {
                Resource.Error(e.message!!)
            }
        }
    }

    override fun fetchMarketChartData(
        coinId: String,
        currency: String,
        days: Int
    ): Flow<Resource<List<List<Number>>>> {
        return remoteDataSource.fetchMarketChartData(coinId, currency, days).map {
            try {
                when (it) {
                    is ApiResponse.Error -> {
                        Resource.Error(it.errorMessage)
                    }
                    is ApiResponse.Empty -> {
                        Resource.Error("No result found")
                    }
                    is ApiResponse.Success -> {
                        Resource.Success(it.data.prices)
                    }
                    is ApiResponse.Loading -> Resource.Loading()
                }

            } catch (e: Exception) {
                Resource.Error(e.message!!)
            }
        }
    }

    override suspend fun setOnboardingCompleted(isCompleted: Boolean) {
        localDataSource.setOnboardingCompleted(isCompleted)
    }

    override fun getOnBoardingParts(): List<OnBoardingPart> {
        return localDataSource.getOnBoardingList()
    }
}