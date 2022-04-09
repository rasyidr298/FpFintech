package id.fp.core.data.source.remote.models

import com.google.gson.annotations.SerializedName

class MarketChartDto(
        @SerializedName("prices")
        val prices: List<List<Float>>,
)


