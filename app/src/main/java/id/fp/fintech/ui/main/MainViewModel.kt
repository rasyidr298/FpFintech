package id.fp.fintech.ui.main

import id.fp.core.data.source.remote.network.ApiResponse
import id.fp.core.domain.IAppUseCase
import id.fp.core.domain.model.Sample
import okhttp3.RequestBody
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.candlekeeper.core.data.Resource

class MainViewModel(private val iAppUseCase: IAppUseCase) : ViewModel() {

    fun getSample(): LiveData<ApiResponse<List<Sample>>> {
        return iAppUseCase.getSample().asLiveData()
    }

    fun getSampleDb(): LiveData<Resource<List<Sample>>> {
        return iAppUseCase.getSampleDb().asLiveData()
    }

    fun searchSample(search: String): LiveData<List<Sample>> {
        return iAppUseCase.searchSample(search).asLiveData()
    }

    fun pushSample(dataMonitor: HashMap<String, RequestBody>): LiveData<ApiResponse<Boolean>> {
        return iAppUseCase.postSample(dataMonitor).asLiveData()
    }
}