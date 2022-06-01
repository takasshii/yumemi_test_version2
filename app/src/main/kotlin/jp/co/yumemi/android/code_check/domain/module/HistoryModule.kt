package jp.co.yumemi.android.code_check.domain.module

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.domain.model.history.IHistoryRepository
import jp.co.yumemi.android.code_check.infrastracture.room.HistoryDAO
import jp.co.yumemi.android.code_check.infrastracture.room.HistoryDatabase
import jp.co.yumemi.android.code_check.infrastracture.room.HistoryRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object HistoryModule {
    @Provides
    @Singleton
    fun provideHistoryDatabase(
        @ApplicationContext context: Context
    ): HistoryDatabase {
        return Room.databaseBuilder(
            context,
            HistoryDatabase::class.java,
            "history.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHistoryDao(db: HistoryDatabase): HistoryDAO {
        return db.historyDAO()
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class HistoryRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindHistoryRepository(
        impl: HistoryRepository
    ): IHistoryRepository
}
