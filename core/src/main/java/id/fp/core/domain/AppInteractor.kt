package id.fp.core.domain

import id.candlekeeper.core.data.Resource
import id.fp.core.data.source.remote.network.ApiResponse
import id.fp.core.domain.model.Sample
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody


interface IAppUseCase {
    //sample
    fun getSampleDb(): Flow<Resource<List<Sample>>>
    fun getSample(): Flow<ApiResponse<List<Sample>>>
    fun searchSample(search: String): Flow<List<Sample>>
    fun postSample(dataSample: HashMap<String, RequestBody>): Flow<ApiResponse<Boolean>>
}

class AppInteractor(private val iAppRepository: IAppRepository) : IAppUseCase {
    override fun getSampleDb(): Flow<Resource<List<Sample>>> {
        return iAppRepository.getSampleDb()
    }

    override fun getSample(): Flow<ApiResponse<List<Sample>>> {
        return iAppRepository.getSample()
    }

    override fun searchSample(search: String): Flow<List<Sample>> {
        return iAppRepository.searchSample(search)
    }

    override fun postSample(dataSample: HashMap<String, RequestBody>): Flow<ApiResponse<Boolean>> {
        return iAppRepository.postSample(dataSample)
    }
}