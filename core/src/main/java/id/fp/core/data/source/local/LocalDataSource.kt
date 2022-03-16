package id.fp.core.data.source.local

import id.fp.core.data.source.local.entity.SampleEntity
import id.fp.core.data.source.local.room.Dao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class LocalDataSource(private val mDao: Dao) {

    //content feature
    suspend fun insertSample(insert: List<SampleEntity>) = mDao.insertSample(insert)

    fun getSample(): Flow<List<SampleEntity>> = mDao.getSample()

    fun searchSample(search: String): Flow<List<SampleEntity>> =
        mDao.searchSample(search).flowOn(Dispatchers.Default).conflate()
}