package quanglong.kotlin.backgroundapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Background")
class Background(var image: String = "", var status: Boolean) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
