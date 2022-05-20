package jp.co.yumemi.android.code_check.domain.model.item

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class Item(
    val items: List<Content>
)

@JsonClass(generateAdapter = true)
data class Content(
    val name: String,
    @Json(name = "owner")
    val owner: Owner,
    val language: String?,
    @Json(name = "stargazers_count")
    val stargazersCount: Long,
    @Json(name = "watchers_count")
    val watchersCount: Long,
    @Json(name = "forks_count")
    val forksCount: Long,
    @Json(name = "open_issues_count")
    val openIssuesCount: Long,
)

@JsonClass(generateAdapter = true)
data class Owner(
    @Json(name = "avatar_url")
    val avatarUrl: String
)

@Parcelize
data class ParcelizeItem(
    val name: String,
    val ownerIconUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
) : Parcelable