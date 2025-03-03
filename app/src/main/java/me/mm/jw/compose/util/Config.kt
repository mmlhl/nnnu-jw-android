package me.mm.jw.compose.util

import kotlinx.serialization.Serializable

@Serializable
data class EduConfig(
    val environments: Map<String, String> = mapOf(
        "intranet" to "http://jw.nnnu.edu.cn/jsxsd/",
        "extranet" to "http://jw-nnnu-edu-cn.atrust.nnnu.edu.cn/jsxsd/"
    ),
    val defaultEnvironment: String = "extranet",
    val normalLogin: NormalLogin = NormalLogin(),
    val tyLogin: TyLogin = TyLogin(),
    val season: String = "2024-2025-1",
    val endpoints: Endpoints = Endpoints()
) {
    @Serializable
    data class NormalLogin(
        val servlet: String = "verifycode.servlet",
        val login: String = "xk/LoginToXk"
    )

    @Serializable
    data class TyLogin(
        val baseUrl: String = "https://sso.nnnu.edu.cn/",
        val loginIndex: String = "https://sso.nnnu.edu.cn/login?service=http:%2F%2Fjw.nnnu.edu.cn%2Fsso.jsp",
        val host: String = "sso.nnnu.edu.cn",
        val csrfJs: String = "public/deploy/deploy.js?",
        val login: String = "login"
    )

    @Serializable
    data class Endpoints(
        val allScore: String = "kscj/cjcx_list?xsfs=all",
        val allScorePDF: String = "kscj/printXscjk",
        val trainPlan: String = "pyfa/pyfa_query",
        val webScore: String = "kscj/cjcx_list?xsfs=all&kksj=",
        val examSchedule: String = "xsks/xsksap_list",
        val tomorrowSchedule: String = "/schedule/tomorrow",
        val profile: String = "yxszzy/yxszzy_grxx_ck",
        val weekSchedule: String = "/schedule/week",
        val courseSelect: String = "kscj/cjcx_list?xsfs=all"
    )
}