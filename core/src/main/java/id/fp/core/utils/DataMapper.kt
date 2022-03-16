package id.fp.core.utils

import id.fp.core.data.source.local.entity.SampleEntity
import id.fp.core.data.source.remote.response.DataSample
import id.fp.core.domain.model.Sample


object DataMapper {

    //sample
    fun mapSampleRestoMod(input: List<DataSample>): List<Sample> {
        return input.map {
            Sample(
                it.id,
                it.name!!,
                it.youtube_url
            )
        }
    }
    fun mapSampleEntToMod(input: List<SampleEntity>): List<Sample> {
        return input.map {
            Sample(
                it.id!!,
                it.name!!,
                it.youtubeUrl
            )
        }
    }
    fun mapSampleResToEnt(input: List<DataSample>): List<SampleEntity> {
        return input.map {
            SampleEntity(
                it.id!!,
                it.name!!,
                it.youtube_url
            )
        }
    }
}