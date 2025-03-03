package me.mm.jw.compose.data.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertCookie(cookie: CookieEntity)

    @Query("SELECT * FROM cookies WHERE username = :username")
    suspend fun getCookies(username: String): CookieEntity?

    @Query("DELETE FROM cookies WHERE username = :username")
    suspend fun deleteCookies(username: String)
}