package com.sapan.restjet.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sapan.restjet.data.JsonNode

@Composable
fun JsonViewerCard(
    node: JsonNode,
    modifier: Modifier = Modifier
) {
    val node = remember { node }
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            JsonViewer(node = node)
        }
    }
}

@Composable
fun JsonViewer(
    node: JsonNode,
    indent: Int = 0
) {
    when (node) {
        is JsonNode.JsonObjectNode -> ExpandableJsonNode(
            label = node.key ?: "{}",
            children = node.children,
            indent = indent
        )
        is JsonNode.JsonArrayNode -> ExpandableJsonNode(
            label = node.key ?: "[]",
            children = node.children,
            indent = indent
        )
        is JsonNode.JsonPrimitiveNode -> Text(
            text = "${node.key?.let { "\"$it\": " } ?: ""}${formatPrimitiveValue(node.value)}",
            modifier = Modifier.padding(start = indent.dp),
            style = TextStyle(fontSize = 14.sp)
        )
    }
}

@Composable
fun ExpandableJsonNode(
    label: String,
    children: List<JsonNode>,
    indent: Int
) {
    var expanded by remember { mutableStateOf(true) } // Start expanded by default

    Column(
        modifier = Modifier.padding(start = indent.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = if (expanded) "▼ " else "▶ ",
                style = TextStyle(fontSize = 12.sp)
            )
            Text(
                text = label,
                style = TextStyle(fontSize = 14.sp)
            )
        }

        if (expanded) {
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                children.forEach { child ->
                    JsonViewer(child, indent)
                }
            }
        }
    }
}

private fun formatPrimitiveValue(value: String): String {
    return when {
        value == "null" -> "null"
        value == "true" || value == "false" -> value
        value.toDoubleOrNull() != null -> value
        else -> "\"$value\""
    }
}