package jp.co.yumemi.android.code_check.infrastracture.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDAO {
    @Insert
    fun insert(history: History)

    @Query("select * from history")
    fun getAll(): List<History>

    @Delete
    fun deleteSelected(history: History)
}