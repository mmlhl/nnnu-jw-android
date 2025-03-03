package me.mm.jw.compose.data.database
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CookieEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}