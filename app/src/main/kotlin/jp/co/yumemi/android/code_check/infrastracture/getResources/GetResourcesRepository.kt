package jp.co.yumemi.android.code_check.infrastracture.getResources

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.domain.model.getResources.IGetResources
import javax.inject.Inject
import javax.inject.Singleton

class GetResourcesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) : IGetResources {
    override suspend fun getStringResources(language: String): String {
        return context.getString(R.string.written_language, language)
    }
}