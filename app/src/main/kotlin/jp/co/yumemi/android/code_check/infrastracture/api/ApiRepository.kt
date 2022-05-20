package jp.co.yumemi.android.code_check.infrastracture.api

import jp.co.yumemi.android.code_check.domain.model.api.ApiService
import jp.co.yumemi.android.code_check.domain.model.api.IApiRepository
import jp.co.yumemi.android.code_check.domain.model.item.Item
import kotlinx.coroutines.flow.*
import retrofit2.Response
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
    private val apiService: ApiService
) : IApiRepository {
    //Httpリスポンスをflowに変換して返す
    override suspend fun getHttpResponse(
        header: String,
        inputText: String
    ): Flow<Result<Response<Item>>> =
        flow {
            val result = try {
                val httpResponse: Response<Item> = apiService.fetchRepositoryData(header, inputText)
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