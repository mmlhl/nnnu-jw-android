package me.mm.jw.compose.data.model
sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val error: EduError) : NetworkResult<Nothing>()

    fun isSuccess() = this is Success
    fun isError() = this is Error
}
// 在 EduService.kt 或单独的工具类中
suspend inline fun <T> handleNetworkResult(
    result: NetworkResult<String>,
    crossinline parser: suspend (String) -> T
): NetworkResult<T> {
    return when (result) {
        is NetworkResult.Success -> {
            try {
                NetworkResult.Success(parser(result.data))
            } catch (e: Exception) {
                NetworkResult.Error(EduError.PARSE_ERROR)
            }
        }
        is NetworkResult.Error -> {
            if (result.error == EduError.NETWORK_ERROR) result
            else NetworkResult.Error(result.error) // 保留原始错误
        }
    }
}
enum class EduError {
    NETWORK_ERROR,
    LOGIN_EXPIRED,
    PARSE_ERROR,
    UNKNOWN_ERROR
}