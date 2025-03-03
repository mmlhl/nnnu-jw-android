package me.mm.jw.compose
import android.app.Application
import androidx.room.Room
import me.mm.jw.compose.data.database.AppDatabase
import me.mm.jw.compose.util.ConfigManager


class App : Application() {
    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        ConfigManager.init(this)
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "edu_database"
        ).build()
    }
}