package me.mm.jw.compose.data.repository

import me.mm.jw.compose.data.database.CookieEntity
import me.mm.jw.compose.data.database.UserDao


class EduRepository(private val userDao: UserDao) {
    suspend fun saveCookies(username: String, cookies: String) {
        userDao.insertCookie(CookieEntity(username, cookies))
    }

    suspend fun getCookies(username: String): String? {
        return userDao.getCookies(username)?.cookies
    }

    suspend fun clearCookies(username: String) {
        userDao.deleteCookies(username)
    }
}