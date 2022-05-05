/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.viewModel

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.domain.model.api.IApiRepository
import jp.co.yumemi.android.code_check.domain.model.getResources.IGetResources
import jp.co.yumemi.android.code_check.domain.model.item.Item
import jp.co.yumemi.android.code_check.view.activity.TopActivity.Companion.lastSearchDate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

/**
 * TwoFragment で使う
 */
@HiltViewModel
class OneViewModel @Inject constructor(
    private val getResourcesRepository: IGetResources,
    private val apiRepository: IApiRepository
) : ViewModel() {

    private val _searchInputText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val searchInputText: LiveData<String>
        get() = _searchInputText

    private val _items: MutableLiveData<List<Item>> by lazy {
        MutableLiveData<List<Item>>()
    }
    val items: LiveData<List<Item>>
        get() = _items

    // 検索結果
    fun searchResults(inputText: String) {
        //一時格納用
        val tempItems = mutableListOf<Item>()

        viewModelScope.launch {
            //apiRepositoryからデータが送られてきたら走る処理
            apiRepository.getHttpResponse(inputText).map {
                //受け取ったデータをJSON型に加工
                JSONObject(it.receive<String>()).optJSONArray("items")!!
            }.onEach {
                //jsonのパース処理
                for (i in 0 until it.length()) {
                    val jsonItem = it.optJSONObject(i)!!
                    val name = jsonItem.optString("full_name")
                    val ownerIconUrl = jsonItem.optJSONObject("owner")!!.optString("avatar_url")
                    val language = jsonItem.optString("language")
                    val stargazersCount = jsonItem.optLong("stargazers_count")
                    val watchersCount = jsonItem.optLong("watchers_count")
                    val forksCount = jsonItem.optLong("forks_count")
                    val openIssuesCount = jsonItem.optLong("open_issues_count")

                    tempItems.add(
                        Item(
                            name = name,
                            ownerIconUrl = ownerIconUrl,
                            language = getResourcesRepository.getStringResources(language),
                            stargazersCount = stargazersCount,
                            watchersCount = watchersCount,
                            forksCount = forksCount,
                            openIssuesCount = openIssuesCount
                        )
                    )
                }
                lastSearchDate = Date()
                //LIVEDataを更新
                _items.value = tempItems.toList()
            }.catch {
                //例外処理

            }.launchIn(viewModelScope)
        }
    }
}

