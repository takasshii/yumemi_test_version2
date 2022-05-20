package jp.co.yumemi.android.code_check.domain.model.getResources

interface IGetResources {
    suspend fun getStringResources(language: String?): String
}