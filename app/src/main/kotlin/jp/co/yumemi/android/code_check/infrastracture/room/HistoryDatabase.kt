package jp.co.yumemi.android.code_check.infrastracture.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [History::class], version = 1, exportSchema = false)
abstract class HistoryDatabase: RoomDatabase() {
    abstract fun historyDAO(): HistoryDAO
}