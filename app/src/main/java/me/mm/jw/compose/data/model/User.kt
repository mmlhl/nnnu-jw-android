package me.mm.jw.compose.data.model

import me.mm.jw.compose.App
import me.mm.jw.compose.data.repository.EduRepository
import me.mm.jw.compose.data.service.EduService


class User(val username: String, val password: String) {
    private val eduService = EduService(this, EduRepository(App.database.userDao()))

    suspend fun login(): NetworkResult<String> = eduService.login()
    suspend fun getTodaySchedule(): NetworkResult<Schedule> = eduService.getTodaySchedule()
    suspend fun getTomorrowSchedule(): NetworkResult<Schedule> = eduService.getTomorrowSchedule()
    suspend fun getProfile(): NetworkResult<Schedule> = eduService.getProfile()

    suspend fun User.getWeekSchedule(): NetworkResult<Schedule> {
        return eduService.fetchCustomData("http://edu.example.com/schedule/week") { html ->
            // TODO: 解析一周课表
            DailySchedule(listOf("Chemistry"), "WeekSchedule")
        }
    }
    internal suspend fun get(url: String): NetworkResult<String> = eduService.get(url)
    internal suspend fun post(url: String, params: Map<String, String>): NetworkResult<String> =
        eduService.post(url, params)
}