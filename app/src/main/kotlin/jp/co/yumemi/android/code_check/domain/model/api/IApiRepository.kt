package jp.co.yumemi.android.code_check.domain.model.api

import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow

interface IApiRepository {
    //Flow型を返す
    suspend fun getHttpResponse(inputText: String): Flow<HttpResponse>
}