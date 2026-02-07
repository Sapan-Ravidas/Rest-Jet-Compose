package com.sapan.restjet.ui.compose

import android.text.TextUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.sapan.restjet.db.entity.CollectionData
import com.sapan.restjet.data.HttpMethod
import com.sapan.restjet.data.RequestState
import com.sapan.restjet.data.ResponseState
import com.sapan.restjet.db.entity.SavedRequestData
import com.sapan.restjet.ui.theme.DarkGreen
import com.sapan.restjet.ui.theme.Typography
import com.sapan.restjet.ui.theme.card_content_gap
import com.sapan.restjet.ui.theme.card_corner_radius
import com.sapan.restjet.ui.theme.card_leading_icon_size
import com.sapan.restjet.ui.theme.card_trailing_icon_size
import com.sapan.restjet.ui.theme.request_card_content_vertical_gap


@Composable
fun IconStar() {
    Icon(
        modifier = Modifier.size(card_leading_icon_size),
        imageVector = Icons.Default.Star,
        contentDescription = null,
    )
}
@Composable
fun CollectionCard(
    collectionInfo: CollectionData,
    onCollectionClicked: (CollectionData) -> Unit,
    onCollectionDeleteClicked: (CollectionData) -> Unit
) {
    Card(
        modifier = Modifier.padding(card_content_gap).fillMaxWidth(),
        shape = RoundedCornerShape(card_corner_radius)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(card_content_gap)
        ) {

            IconStar()

            Spacer(
                modifier = Modifier.size(card_content_gap)
            )

            Column(
                modifier = Modifier.weight(1f).clickable {
                    onCollectionClicked(collectionInfo)
                }
            ) {
                Text(
                    text = collectionInfo.title,
                    style = Typography.titleMedium
                )

                Text(
                    text = collectionInfo.description ?: "no description",
                    style = Typography.bodySmall
                )
            }

            Spacer(
                modifier = Modifier.size(card_content_gap)
            )

            DeleteIconButton(
                onClick = {
                    onCollectionDeleteClicked(collectionInfo)
                },
                modifier = Modifier.size(card_trailing_icon_size)
                )

        }
    }
}


@Composable
fun RequestCard(
    requestState: RequestState,
    responseState: ResponseState
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val params = requestState.queryParameters.entries.joinToString(separator = "&") {
        "${it.key}=${it.value}"
    }

    val responseCodeColor = when {
            responseState.statusCode.startsWith("2") -> DarkGreen
            responseState.statusCode.startsWith("4") ||
                 responseState.statusCode.startsWith("5") -> Color.Red
            else -> Color.Blue
        }

    Card(
        modifier = Modifier.padding(card_content_gap).fillMaxWidth(),
        shape = RoundedCornerShape(card_corner_radius)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(card_content_gap)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(card_content_gap)
            ) {

                IconStar()

                Spacer(
                    modifier = Modifier.size(card_content_gap)
                )

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(request_card_content_vertical_gap)
                ) {
                    Text(text = "base_url: ${requestState.baseUrl}", style = Typography.bodySmall)
                    Text(text = "path: ${requestState.pathUrl}", style = Typography.titleSmall)
                    Text(text = "query: $params", style = Typography.bodySmall)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = requestState.action.name, style = Typography.titleMedium)
                        Text(text = responseState.statusCode, style = Typography.titleMedium, color = responseCodeColor)
                    }
                    if (!responseState.error.isNullOrEmpty()) {
                        Text(
                            text = responseState.error!!,
                            style = Typography.bodySmall,
                            color = Color.Red
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.size(card_content_gap)
                )
                if (expanded) {
                    ShrinkButton { expanded = !expanded }
                } else {
                    ExpandButton { expanded = !expanded }
                }
            }

            if (expanded) {
                Column {
                    Surface(
                        tonalElevation = 8.dp,
                        modifier = Modifier.fillMaxWidth().padding(2.dp),
                        color = Color.White,
                        shape = RoundedCornerShape(card_corner_radius)
                    ) {
                        Column {
                            if (!TextUtils.isEmpty(requestState.body)) {
                                Text(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    text = "Request Body",
                                    style = Typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Text(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    text = requestState.body,
                                    style = Typography.bodySmall
                                )
                            }

                            if (responseState.responseHeaders.isNotEmpty()) {
                                Text(
                                    modifier = Modifier.padding(top = 16.dp, start = 8.dp),
                                    text = "Response Headers",
                                    style = Typography.bodySmall,
                                    fontWeight = FontWeight.SemiBold
                                )
                                for (pair in responseState.responseHeaders) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                                    ) {
                                        Text(text = pair, style = Typography.bodySmall)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SavedRequestItem(
    request: SavedRequestData,
    onClick: (SavedRequestData) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.surfaceContainerLow)
        .border(width = 0.4.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
        .padding(8.dp)
        .clickable { onClick(request) }
    ) {
        Text(
            text = "${request.httpMethod} - ",
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1
        )
        Text(
            text = request.name,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1
        )

    }
}

@Preview
@Composable
fun SavedRequestItemPreview() {
    SavedRequestItem(
        request = SavedRequestData(id = 1, collectionId = 1, name = "test", httpMethod = "GET", baseUrl = "https://jsonplaceholder.typicode.com", pathUrl = "/posts", headers = "", queryParams = "", requestBody = "", savedAt = ""),
        onClick = {}
    )
}


@Preview(showBackground = true)
@Composable
fun RequestCardPreview() {
    RequestCard(RequestState().copy(
        baseUrl = "https://jsonplaceholder.typicode.com",
        pathUrl = "posts",
        queryParameters = mapOf(
            "userId" to "1"
        ),
        action = HttpMethod.GET,
        body = """
            {
                "title": "foo",
                "body": "bar",
                "userId": 1
            }
        """.trimIndent(),
        headers = mapOf(
            "content-type" to "application/json"
        )

    ), ResponseState().copy(
        statusCode = "400",
        responseHeaders = listOf(
            "Date: Mon 02 Jan 2026"
        )
    ))
}
