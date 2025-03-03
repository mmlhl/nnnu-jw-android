package me.mm.jw.compose.util

import android.content.Context
import kotlinx.serialization.json.Json
import java.io.IOException

object ConfigManager {
    private lateinit var config: EduConfig
    private var currentEnvironment: String = ""

    // 初始化配置
    fun init(context: Context) {
        try {
            val jsonString = context.assets.open("config.json").bufferedReader().use { it.readText() }
            config = Json.decodeFromString<EduConfig>(jsonString)
            currentEnvironment = config.defaultEnvironment
        } catch (e: IOException) {
            config = EduConfig()
            currentEnvironment = config.defaultEnvironment
        }
    }

    // 切换环境
    fun switchEnvironment(environment: String) {
        if (config.environments.containsKey(environment)) {
            currentEnvironment = environment
        } else {
            throw IllegalArgumentException("Unknown environment: $environment")
        }
    }

    // 当前 baseUrl
    val baseUrl: String
        get() = config.environments[currentEnvironment] ?: config.environments[config.defaultEnvironment]!!

    // 普通登录相关
    /** 传统登录验证码地址*/
    val normalLoginServletUrl: String get() = "$baseUrl${config.normalLogin.servlet}"
    /** 传统登录地址*/
    val normalLoginUrl: String get() = "$baseUrl${config.normalLogin.login}"

    // 统一身份认证登录相关
    /** 统一登录baseUrl*/
    val tyLoginBaseUrl: String get() = config.tyLogin.baseUrl
    /** 统一登录主页面*/
    val tyLoginIndexUrl: String get() = config.tyLogin.loginIndex
    /** 统一登录的Host*/
    val tyLoginHost: String get() = config.tyLogin.host
    /** 统一登录获取Csrf-Key和Csrf-Value的地址*/
    val tyLoginCsrfJsUrl: String get() = "${config.tyLogin.baseUrl}${config.tyLogin.csrfJs}${System.currentTimeMillis()}"
    /** login post请求地址*/
    val tyLoginUrl: String get() = "${config.tyLogin.baseUrl}${config.tyLogin.login}"

    /** 当前学期*/
    val season: String get() = config.season

    // 端点 URL
    /** 全部成绩Url*/
    val allScoreUrl: String get() = "$baseUrl${config.endpoints.allScore}"
    /** 全部成绩pdf下载链接*/
    val allScorePDFUrl: String get() = "$baseUrl${config.endpoints.allScorePDF}"
    /** 培养计划*/
    val trainPlanUrl: String get() = "$baseUrl${config.endpoints.trainPlan}"
    /** 网页版成绩*/
    val webScoreUrl: String get() = "$baseUrl${config.endpoints.webScore}"
    /** 考试安排*/
    val examScheduleUrl: String get() = "$baseUrl${config.endpoints.examSchedule}"
    val tomorrowScheduleUrl: String get() = "$baseUrl${config.endpoints.tomorrowSchedule}"
    /** 个人信息*/
    val profileUrl: String get() = "$baseUrl${config.endpoints.profile}"
    val weekScheduleUrl: String get() = "$baseUrl${config.endpoints.weekSchedule}"
    /** 选课列表*/
    val courseSelectUrl: String get() = "$baseUrl${config.endpoints.courseSelect}"

    // 当前环境名称和可用环境
    val currentEnvironmentName: String get() = currentEnvironment
    val availableEnvironments: List<String> get() = config.environments.keys.toList()
}