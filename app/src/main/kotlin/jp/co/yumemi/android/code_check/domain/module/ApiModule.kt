package jp.co.yumemi.android.code_check.domain.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.yumemi.android.code_check.domain.model.api.ApiService
import jp.co.yumemi.android.code_check.domain.model.api.IApiRepository
import jp.co.yumemi.android.code_check.infrastracture.api.ApiRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    //HttpClientのインスタンスを生成する
    fun provideMoshi(): Moshi {
        return Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    //HttpClientのインスタンスを生成する
    fun provideRetrofit(moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideGitHubService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
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