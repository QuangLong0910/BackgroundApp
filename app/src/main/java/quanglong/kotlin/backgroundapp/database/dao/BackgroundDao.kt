package quanglong.kotlin.backgroundapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import quanglong.kotlin.backgroundapp.model.Background


@Dao
interface BackgroundDao {
    @Insert
    suspend fun insertBackground(background: Background)

    @Update
    suspend fun updateBackground(background: Background)

    @Delete
    suspend fun deleteBackground(background: Background)

    @Query("select * from Background")
    fun getAllBackground(): LiveData<List<Background>>
}