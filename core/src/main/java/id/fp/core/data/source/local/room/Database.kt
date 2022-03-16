package id.fp.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.fp.core.data.source.local.entity.SampleEntity

@Database(
    entities = [SampleEntity::class], version = 9, exportSchema = false
)

abstract class Database : RoomDatabase() {
    abstract fun dao(): Dao
}