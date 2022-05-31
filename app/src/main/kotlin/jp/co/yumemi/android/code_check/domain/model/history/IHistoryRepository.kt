package jp.co.yumemi.android.code_check.domain.model.history

import jp.co.yumemi.android.code_check.domain.model.item.Item
import kotlinx.coroutines.flow.Flow

interface IHistoryRepository {
    suspend fun insert(history: Item)
    suspend fun update(history: Item)
    fun getAll(): Flow<List<Item>>
    suspend fun delete(history: Item)
}