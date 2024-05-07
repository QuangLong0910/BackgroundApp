package quanglong.kotlin.backgroundapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import quanglong.kotlin.backgroundapp.database.dao.BackgroundDao
import quanglong.kotlin.backgroundapp.model.Background


@Database(entities = [Background::class], version = 1)
abstract class BackgroundDatabase : RoomDatabase() {
    abstract fun getBackground(): BackgroundDao

    companion object {
        @Volatile
        private var instance: BackgroundDatabase? = null
        fun getInstance(context: Context): BackgroundDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(
                        context,
                        BackgroundDatabase::class.java,
                        "QuizThisDatabase"
                    )
                        .build()
            }
            return instance!!
        }

    }

}