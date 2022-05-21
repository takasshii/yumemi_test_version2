/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.search

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.domain.model.api.IApiRepository
import jp.co.yumemi.android.code_check.domain.model.getResources.IGetResources
import jp.co.yumemi.android.code_check.domain.model.item.ParcelizeItem
import jp.co.yumemi.android.code_check.infrastracture.api.Result
import jp.co.yumemi.android.code_check.TopActivity.Companion.lastSearchDate
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * TwoFragment で使う
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getResourcesRepository: IGetResources,
    private val apiRepository: IApiRepository
) : ViewModel() {

    //入力欄の保持用
    private val _searchInputText: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val searchInputText: LiveData<String>
        get() = _searchInputText

    //検索結果の表示用
    private val _items: MutableLiveData<List<ParcelizeItem>> by lazy {
        MutableLiveData<List<ParcelizeItem>>()
    }
    val items: LiveData<List<ParcelizeItem>>
        get() = _items

    //エラー内容の表示用
    private val _errorContent: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val errorContent: LiveData<String>
        get() = _errorContent

    //loadingの通知
    private val _loadingCircle: MutableLiveData<Boolean> by lazy {
        //初期値はfalse
        MutableLiveData<Boolean>().also { mutableLiveData ->
            mutableLiveData.value = false
        }
    }
    val loadingCircle: LiveData<Boolean>
        get() = _loadingCircle


    // 検索結果を表示するために呼ばれる関数
    fun searchResults(inputText: String) {
        val tempItems = mutableListOf<ParcelizeItem>()

        //非同期処理で実行
        viewModelScope.launch {
            //apiRepositoryからデータが送られてきたら走る処理
            apiRepository.getHttpResponse("application/vnd.github.v3+json", inputText)
                .onEach { result ->
                    when (result) {
                        is Result.Process -> {
                            //ここでロード画面を見せる
                            _loadingCircle.value = true
                        }
                        is Result.Error -> {
                            //ロード画面消す
                            _loadingCircle.value = false
                            //エラー表示
                            notifyError(result.exception)
                            //空白のItemを返す
                            _items.value = tempItems.toList()
                        }
                        is Result.Success -> {
                            //ロード画面消す
                            _loadingCircle.value = false
                            //jsonの処理
                            result.data.body()?.let {
                                it.items.forEach {
                                    tempItems.add(
                                        ParcelizeItem(
                                            name = it.name,
                                            ownerIconUrl = it.owner.avatarUrl,
                                            language = getResourcesRepository.getStringResources(it.language),
                                            stargazersCount = it.stargazersCount,
                                            watchersCount = it.watchersCount,
                                            forksCount = it.forksCount,
                                            openIssuesCount = it.openIssuesCount
                                        )
                                    )
                                }
                            }
                            lastSearchDate = Date()
                            //LIVEDataを更新
                            _items.value = tempItems.toList()
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }

    //エラー表示用
    private fun notifyError(exception: Throwable) {
        _errorContent.value = "エラーが発生しました。検索し直してください。\n エラー内容: $exception"
    }
}

