package me.mm.jw.compose
import android.app.Application
import androidx.room.Room
import me.mm.jw.compose.data.database.AppDatabase


class App : Application() {
    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "edu_database"
        ).build()
    }
}