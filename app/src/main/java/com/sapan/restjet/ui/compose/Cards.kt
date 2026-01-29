package com.sapan.restjet.ui.compose

import android.widget.TextView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapan.restjet.data.CollectionInfo
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
    collectionInfo: CollectionInfo = CollectionInfo("header", "description")
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
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = collectionInfo.title,
                    style = Typography.titleMedium
                )

                Text(
                    text = collectionInfo.description,
                    style = Typography.bodySmall
                )
            }

            Spacer(
                modifier = Modifier.size(card_content_gap)
            )

            DeleteIconButton(
                onClick = {},
                modifier = Modifier.size(card_trailing_icon_size)
                )

        }
    }
}


@Composable
fun RequestCard(
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

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
                    Text(text = "base_url: ", style = Typography.bodySmall)
                    Text(text = "path: ", style = Typography.titleSmall)
                    Text(text = "query: ", style = Typography.bodySmall)
                    Text(text = "GET", style = Typography.titleMedium)
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
                Surface(
                    tonalElevation = 8.dp,
                    modifier = Modifier.fillMaxWidth().padding(2.dp),
                    color = Color.White,
                    shape = RoundedCornerShape(card_corner_radius)
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "json request_body"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RequestCardPreview() {
    RequestCard()
}

@Preview(showBackground = true)
@Composable
fun CollectionCardPreview() {
    CollectionCard()
}