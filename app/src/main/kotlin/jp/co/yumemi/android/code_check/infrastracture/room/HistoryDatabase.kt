package jp.co.yumemi.android.code_check.infrastracture.room

import androidx.room.Database
import androidx.room.RoomDatabase
import jp.co.yumemi.android.code_check.domain.model.item.Item


@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun historyDAO(): HistoryDAO
}