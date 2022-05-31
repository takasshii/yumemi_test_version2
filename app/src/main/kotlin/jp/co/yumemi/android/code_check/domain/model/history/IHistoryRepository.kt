package jp.co.yumemi.android.code_check.domain.model.history

import jp.co.yumemi.android.code_check.infrastracture.room.DBResult
import jp.co.yumemi.android.code_check.infrastracture.room.History
import kotlinx.coroutines.flow.Flow

interface IHistoryRepository {
    suspend fun insert(history: History)
    suspend fun getAll(): Flow<DBResult<List<History>>>
    suspend fun delete(history: History)
}