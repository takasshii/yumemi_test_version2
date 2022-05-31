package jp.co.yumemi.android.code_check.ui.history

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.code_check.R

@Composable
fun HistoryScreen(historyViewModel: HistoryViewModel) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val historyList = historyViewModel.historyList.collectAsState()
    
    Scaffold(modifier = Modifier.fillMaxSize()) {
       LazyColumn() {
         items(items = historyList.value) { history ->
             itemCard(history.name)
         }
       }
    }
}

@Composable
fun itemCard(title: String) {
    Row(
        Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_baseline_history_24), contentDescription = null, modifier = Modifier.size(24.dp), tint = Color.Gray)
            Spacer(modifier = Modifier.padding(4.dp))
            Text(text = title, color = Color.Gray)
        }
        Icon(painter = painterResource(id = R.drawable.ic_baseline_close_24), contentDescription = null, modifier = Modifier.size(24.dp), tint = Color.Gray)
    }
}


