package jp.co.yumemi.android.code_check.infrastracture.room

import jp.co.yumemi.android.code_check.domain.model.history.IHistoryRepository
import jp.co.yumemi.android.code_check.domain.model.item.Item
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryRepository @Inject constructor(
    private val historyDAO: HistoryDAO
): IHistoryRepository {
    override suspend fun insert(history: Item) {
        TODO("Not yet implemented")
    }

    override suspend fun update(history: Item) {
        TODO("Not yet implemented")
    }

    override fun getAll(): Flow<List<Item>> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(history: Item) {
        TODO("Not yet implemented")
    }

}