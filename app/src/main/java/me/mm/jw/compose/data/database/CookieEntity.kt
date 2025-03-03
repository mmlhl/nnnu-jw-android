package me.mm.jw.compose.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cookies")
data class CookieEntity(
    @PrimaryKey val username: String,
    val cookies: String // 存储cookie的JSON字符串
)