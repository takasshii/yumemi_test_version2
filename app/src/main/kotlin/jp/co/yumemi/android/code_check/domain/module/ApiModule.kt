package jp.co.yumemi.android.code_check.domain.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import jp.co.yumemi.android.code_check.domain.model.api.IApiRepository
import jp.co.yumemi.android.code_check.infrastracture.api.ApiRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    //HttpClientのインスタンスを生成する
    fun provideHttpClient(
    ): HttpClient {
        return HttpClient(Android)
    }
}

//各リポジトリの結びつけ
@Module
@InstallIn(SingletonComponent::class)
abstract class JsonRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindJsonRepository(
        impl: ApiRepository
    ): IApiRepository
}