package jp.co.yumemi.android.code_check.viewModel

import jp.co.yumemi.android.code_check.domain.model.api.IApiRepository
import jp.co.yumemi.android.code_check.domain.model.getResources.IGetResources
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.mockito.Mock
import io.mockk.coEvery
import jp.co.yumemi.android.code_check.domain.model.item.Item
import kotlinx.coroutines.flow.flow

@RunWith(RobolectricTestRunner::class)
class OneViewModelTest {

    @Mock
    lateinit var getResourcesRepository: IGetResources

    @Mock
    lateinit var apiRepository: IApiRepository

    private val mockRepository = mockk<IApiRepository> {
        coEvery { getHttpResponse(any()) } returns flow {
            Item(
                name = "JetBrains/kotlin",
                ownerIconUrl = "",
                language = "Koltin",
                stargazersCount = 38530,
                watchersCount = 38530,
                forksCount = 38530,
                openIssuesCount = 38530
            )
        }
    }


    @Test
    fun getSearchInputText() {
    }

    @Test
    fun getItems() {
    }

    @Test
    fun getErrorContent() {
    }

    @Test
    fun getLoadingCircle() {
    }

    @Test
    fun searchResults() {
    }
}