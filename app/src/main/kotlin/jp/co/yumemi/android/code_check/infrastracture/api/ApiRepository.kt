package jp.co.yumemi.android.code_check.infrastracture.api

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.domain.model.api.IApiRepository
import jp.co.yumemi.android.code_check.domain.model.getResources.IGetResources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(
    //インスタンスの生成はApiModule内で行う
    private val httpClient: HttpClient
) : IApiRepository {
    //Httpリスポンスをflowに変換して返す
    override suspend fun getHttpResponse(inputText: String): Flow<HttpResponse> {
        return flow {
            httpClient.get("https://api.github.com/search/repositories") {
                header("Accept", "application/vnd.github.v3+json")
                parameter("q", inputText)
            }
        }
    }
}