package me.mm.jw.compose.data.model

interface Schedule {
    val type: String // 用于区分课表或个人信息
}

data class DailySchedule(
    val courses: List<String>,
    override val type: String = "DailySchedule"
) : Schedule

data class Profile(
    val name: String,
    val studentId: String,
    override val type: String = "Profile"
) : Schedule