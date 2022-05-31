package jp.co.yumemi.android.code_check.infrastracture.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import jp.co.yumemi.android.code_check.domain.model.item.Content

class HistoryEntity {
}

@Entity
data class History(
    @PrimaryKey(autoGenerate = true)
    val history: Content
) {}