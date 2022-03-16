package id.fp.core.data.source.remote.network

import id.fp.core.BuildConfig
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
}