package com.sapan.restjet.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sapan.restjet.data.JsonNode
import org.json.JSONObject


val jsonString = """
    {
        "user": {
            "id": 1,
            "name": "Sapan",
            "skills": ["kotlin", "java", "android"]
        }
    }
""".trimIndent()

val rootNode = JSONObject(jsonString)

@Composable
fun JsonViewer(
    node: JsonNode,
    indent: Int = 0
) {
    when (node) {
        is JsonNode.JsonObjectNode -> ExpandableJsonNode(
            label = node.key ?: "{object}",
            children = node.children,
            indent = indent
        )
        is JsonNode.JsonArrayNode -> ExpandableJsonNode(
            label = node.key ?: "[array]",
            children = node.children,
            indent = indent
        )
        is JsonNode.JsonPrimitiveNode -> Text(
            text = "${node.key ?: ""}: ${node.value}",
            modifier = Modifier.padding(start = indent.dp)
        )
    }
}

@Composable
fun ExpandableJsonNode(
    label: String,
    children: List<JsonNode>,
    indent: Int
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(start = indent.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable{ expanded = !expanded }
        ) {
            Text(
                text = if (expanded) "▼ $label" else "▶ $label"
            )
            if (expanded) {
                children.forEach { child ->
                    JsonViewer(child, indent + 16)
                }
            }
        }
    }
}