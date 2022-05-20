package jp.co.yumemi.android.code_check.domain.model.api

import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.domain.model.item.Item
import jp.co.yumemi.android.code_check.infrastracture.api.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface IApiRepository {
    //Flow型を返す
    suspend fun getHttpResponse(header: String, inputText: String): Flow<Result<Response<Item>>>
}

interface ApiService {
    @GET("search/repositories")
    suspend fun fetchRepositoryData(
        @Header("Accept") header: String,
        @Query("q") inputText: String
    ): Response<Item>
}