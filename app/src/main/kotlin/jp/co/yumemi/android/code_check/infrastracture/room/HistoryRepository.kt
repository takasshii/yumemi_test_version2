package jp.co.yumemi.android.code_check.infrastracture.room

import jp.co.yumemi.android.code_check.domain.model.history.IHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

//結果を返すクラス
sealed class DBResult<out R> {
    //読み込み中の時
    object Process : DBResult<Nothing>()

    //成功した場合
    data class Success<out T>(val data: T) : DBResult<T>()

    //失敗した場合
    data class Error(val exception: Throwable) : DBResult<Nothing>()
}

class HistoryRepository @Inject constructor(
    private val historyDAO: HistoryDAO
): IHistoryRepository {
    override suspend fun insert(history: History) {
        historyDAO.insert(history)
    }

    override suspend fun getAll(): Flow<DBResult<List<History>>> =
        flow {
            val result = try {
                val historyList: List<History> = historyDAO.getAll()
                //成功した場合、結果を返す
                DBResult.Success(data = historyList)
            } catch (e: Throwable) {
                //失敗した場合、エラーメッセージを返す
                DBResult.Error(exception = e)
            }
            emit(result)
        }.onStart {
            //開始時にロードを通知する
            emit(DBResult.Process)
        }



    override suspend fun delete(history: History) {
        historyDAO.deleteSelected(history)
    }
}