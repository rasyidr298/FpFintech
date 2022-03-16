package id.fp.core.domain

import id.candlekeeper.core.data.Resource
import id.fp.core.data.NetworkBoundResource
import id.fp.core.data.source.local.LocalDataSource
import id.fp.core.data.source.remote.RemoteDataSource
import id.fp.core.data.source.remote.network.ApiResponse
import id.fp.core.data.source.remote.response.DataSample
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

}