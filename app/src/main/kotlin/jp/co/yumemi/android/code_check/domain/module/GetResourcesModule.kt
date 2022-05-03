package jp.co.yumemi.android.code_check.domain.module

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.domain.model.getResources.IGetResources
import jp.co.yumemi.android.code_check.infrastracture.getResources.GetResourcesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GetResourcesRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindGetResourcesRepository(
        impl: GetResourcesRepository
    ): IGetResources
}

