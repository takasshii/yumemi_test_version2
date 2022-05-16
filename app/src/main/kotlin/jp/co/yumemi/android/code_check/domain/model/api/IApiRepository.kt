package jp.co.yumemi.android.code_check.domain.model.api

import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.infrastracture.api.Result
import kotlinx.coroutines.flow.Flow

interface IApiRepository {
    //Flow型を返す
    suspend fun getHttpResponse(inputText: String): Flow<Result<HttpResponse>>
}