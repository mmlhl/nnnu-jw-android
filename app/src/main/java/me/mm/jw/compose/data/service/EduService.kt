package me.mm.jw.compose.data.service

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.CompletableDeferred
import me.mm.jw.compose.data.model.DailySchedule
import me.mm.jw.compose.data.model.EduError
import me.mm.jw.compose.data.model.NetworkResult
import me.mm.jw.compose.data.model.Profile
import me.mm.jw.compose.data.model.Schedule
import me.mm.jw.compose.data.model.User
import me.mm.jw.compose.data.model.handleNetworkResult
import me.mm.jw.compose.data.repository.EduRepository

class EduService(private val user: User, private val repository: EduRepository) {
    private val client = HttpClient()
    private var cookies: String? = null
    private val cookiesInitialized = CompletableDeferred<Unit>()

    private suspend fun initializeCookies() {
        cookies = repository.getCookies(user.username)
        cookiesInitialized.complete(Unit)
    }

    private suspend fun ensureCookiesInitialized() {
        if (!cookiesInitialized.isCompleted) {
            initializeCookies()
        }
        cookiesInitialized.await()
    }

    private suspend fun updateCookies(response: HttpResponse) {
        val setCookie = response.headers["Set-Cookie"]
        if (setCookie != null) {
            cookies = setCookie
            repository.saveCookies(user.username, setCookie)
        }
    }

    suspend fun get(url: String): NetworkResult<String> {
        ensureCookiesInitialized()
        return try {
            val response = client.get(url) {
                cookies?.let { header(HttpHeaders.Cookie, it) }
            }
            updateCookies(response)
            NetworkResult.Success(response.bodyAsText())
        } catch (e: Exception) {
            NetworkResult.Error(EduError.NETWORK_ERROR)
        }
    }

    suspend fun post(url: String, params: Map<String, String>): NetworkResult<String> {
        ensureCookiesInitialized()
        return try {
            val response = client.post(url) {
                cookies?.let { header(HttpHeaders.Cookie, it) }
                contentType(ContentType.Application.FormUrlEncoded)
                setBody(params.map { "${it.key}=${it.value}" }.joinToString("&"))
            }
            updateCookies(response)
            NetworkResult.Success(response.bodyAsText())
        } catch (e: Exception) {
            NetworkResult.Error(EduError.NETWORK_ERROR)
        }
    }

    suspend fun login(): NetworkResult<String> {
        val result = post("http://edu.example.com/login", mapOf(
            "username" to user.username,
            "password" to user.password
        ))
        return when (result) {
            is NetworkResult.Success -> {
                if (result.data.contains("登录成功")) result
                else NetworkResult.Error(EduError.LOGIN_EXPIRED)
            }
            is NetworkResult.Error -> result
        }
    }

    suspend fun getTodaySchedule(): NetworkResult<Schedule> {
        val result = get("http://edu.example.com/schedule/today")
        return handleNetworkResult(result) { html ->
            // TODO: 解析今日课表HTML到DailySchedule
            DailySchedule(listOf("Math"), "DailySchedule")
        }
    }

    suspend fun getTomorrowSchedule(): NetworkResult<Schedule> {
        val result = get("http://edu.example.com/schedule/tomorrow")
        return handleNetworkResult(result) { html ->
            // TODO: 解析明日课表HTML到DailySchedule
            DailySchedule(listOf("Physics"), "DailySchedule")
        }
    }

    suspend fun getProfile(): NetworkResult<Schedule> {
        val result = get("http://edu.example.com/profile")
        return handleNetworkResult(result) { html ->
            // TODO: 解析个人信息HTML到Profile
            Profile("张三", "123456", "Profile")
        }
    }

    // 添加新 API 的模板方法
    suspend inline fun <T> fetchCustomData(url: String, crossinline parser: suspend (String) -> T): NetworkResult<T> {
        val result = get(url)
        return handleNetworkResult(result, parser)
    }
}