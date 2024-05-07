package quanglong.kotlin.backgroundapp.database.repository

import android.app.Application
import androidx.lifecycle.LiveData
import quanglong.kotlin.backgroundapp.database.BackgroundDatabase
import quanglong.kotlin.backgroundapp.database.dao.BackgroundDao
import quanglong.kotlin.backgroundapp.model.Background


class BackgroundRepository(app: Application) {
    private val backgroundDao: BackgroundDao

    init {
        val backgroundDatabase: BackgroundDatabase = BackgroundDatabase.getInstance(app)
        backgroundDao = backgroundDatabase.getBackground()
    }

    suspend fun insertBackground(background: Background) =
        backgroundDao.insertBackground(background)

    suspend fun updateBackground(background: Background) =
        backgroundDao.updateBackground(background)

    suspend fun deleteBackground(background: Background) =
        backgroundDao.deleteBackground(background)

    fun getAll(): LiveData<List<Background>> = backgroundDao.getAllBackground()
}