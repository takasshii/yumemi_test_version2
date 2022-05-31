package jp.co.yumemi.android.code_check.infrastracture.room

import androidx.room.*
import com.squareup.moshi.Json
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDAO {
    @Insert
    fun insert(json: Json)

    @Query("select * from history where id = 1")
    fun getAll(): Flow<Json>

    @Update
    fun updateJson(json: Json)

    @Delete
    fun deleteSelected(json: Json)
}