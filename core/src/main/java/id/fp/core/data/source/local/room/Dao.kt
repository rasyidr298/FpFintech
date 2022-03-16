package id.fp.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.fp.core.data.source.local.entity.SampleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    //sample Dao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSample(insert: List<SampleEntity>)

    @Query("SELECT * FROM sampleEntities ORDER BY name ASC")
    fun getSample(): Flow<List<SampleEntity>>

    @Query("SELECT * FROM sampleEntities WHERE name LIKE '%' || :search || '%'")
    fun searchSample(search: String): Flow<List<SampleEntity>>
}