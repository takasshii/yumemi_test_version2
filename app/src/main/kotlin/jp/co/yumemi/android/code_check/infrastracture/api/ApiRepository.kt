package jp.co.yumemi.android.code_check.infrastracture.api

import android.content.Context
import android.util.Log
import android.util.Log.e
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.domain.model.api.IApiRepository
import jp.co.yumemi.android.code_check.domain.model.getResources.IGetResources
import jp.co.yumemi.android.code_check.domain.model.item.Item
import kotlinx.coroutines.flow.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

//結果を返すクラス
sealed class Result<out R> {
    //読み込み中の時
    object Process : Result<Nothing>()

    //成功した場合
    data class Success<out T>(val data: T) : Result<T>()

    //失敗した場合
    data class Error(val exception: Throwable) : Result<Nothing>()
}

@Singleton
class ApiRepository @Inject constructor(
    //インスタンスの生成はApiModule内で行う
    private val httpClient: HttpClient
) : IApiRepository {
    //Httpリスポンスをflowに変換して返す
    override suspend fun getHttpResponse(inputText: String): Flow<Result<HttpResponse>> =
        flow {
            val result = try {
                val httpResponse: HttpResponse =
                    httpClient.get("https://api.github.com/search/repositories") {
                        header("Accept", "application/vnd.github.v3+json")
                        parameter("q", inputText)
                    }
                //成功した場合、結果を返す
                Result.Success(data = httpResponse)
            } catch (e: Throwable) {
                //失敗した場合、エラーメッセージを返す
                Result.Error(exception = e)
            }
            emit(result)
        }.onStart {
            //開始時にロードを通知する
            emit(Result.Process)
        }

}

interface ApiService {
    @GET("https://api.github.com/search/repositories")
    suspend fun fetchRepositoryData(
        @Header("Accept") header: String,
        @Query("q") inputText: String
    ):Response<Item>
}