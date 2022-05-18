package jp.co.yumemi.android.code_check.repository

import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.domain.model.api.IApiRepository
import jp.co.yumemi.android.code_check.infrastracture.api.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart


class FakeApiRepository : IApiRepository {

    var getActivityWasCalled = false

    override suspend fun getHttpResponse(inputText: String): Flow<Result<HttpResponse>> {
        getActivityWasCalled = true
        return flow {
            val result = try {
                //成功した場合、結果を返す
                Result.Success(data = )
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
}
