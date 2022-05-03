package jp.co.yumemi.android.code_check.domain.model.api

import io.ktor.client.statement.*

interface IApiRepository {
    suspend fun getStringResources(): HttpResponse
}