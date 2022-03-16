package id.fp.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sample(
    var id: Int? = 0,
    var name: String = "",
    var youtubeUrl: String? = ""
) : Parcelable