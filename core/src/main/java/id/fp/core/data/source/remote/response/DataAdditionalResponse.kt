package id.fp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SampleResponse(
    @field:SerializedName("data")
    val data: List<DataSample>
)

data class DataSample(
    @field:SerializedName("id_url_tutorial")
    var id: Int? = 0,

    @field:SerializedName("name")
    var name: String? = "",

    @field:SerializedName("youtube_url")
    var youtube_url: String? = ""
)