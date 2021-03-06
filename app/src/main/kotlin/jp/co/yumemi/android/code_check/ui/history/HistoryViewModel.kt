package jp.co.yumemi.android.code_check.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.domain.model.history.IHistoryRepository
import jp.co.yumemi.android.code_check.infrastracture.room.DBResult
import jp.co.yumemi.android.code_check.infrastracture.room.History
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: IHistoryRepository,
) : ViewModel() {
    private val _historyList = MutableStateFlow<List<History>>(emptyList())
    val historyList: StateFlow<List<History>> get() = _historyList

    init {
        fetchHistoryList()
    }

    private fun fetchHistoryList() {
        viewModelScope.launch {
            historyRepository.getAll().onEach { result ->
                when (result) {
                    is DBResult.Process, is DBResult.Empty -> {

                    }
                    is DBResult.Error -> {

                    }
                    is DBResult.Success -> {
                        _historyList.emit(result.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun deleteHistory(history: History) {
        viewModelScope.launch(Dispatchers.IO) {
            historyRepository.delete(history)
        }
    }
}