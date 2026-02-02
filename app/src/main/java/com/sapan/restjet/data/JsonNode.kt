package com.sapan.restjet.data

import org.json.JSONArray
import org.json.JSONObject

sealed class JsonNode(val key: String?) {
    class JsonObjectNode(key: String?, val children: List<JsonNode>) : JsonNode(key)
    class JsonArrayNode(key: String?, val children: List<JsonNode>) : JsonNode(key)
    class JsonPrimitiveNode(key: String?, val value: String) : JsonNode(key)
}

fun parseJson(jsonString: String): JsonNode {
    val json = jsonString.trim()
    return if (json.startsWith("{")) {
        parseObject(null, JSONObject(json))
    } else if (json.startsWith("[")) {
        parseArray(null, JSONArray(json))
    } else {
        JsonNode.JsonPrimitiveNode(null, json)
    }
}

private fun parseObject(key: String?, jsonObject: JSONObject): JsonNode.JsonObjectNode {
    val children = mutableListOf<JsonNode>()
    val keys = jsonObject.keys()
    while (keys.hasNext()) {
        val childKey = keys.next()
        val value = jsonObject.get(childKey)
        children.add(parseValue(childKey, value))
    }
    return JsonNode.JsonObjectNode(key, children)
}

private fun parseArray(key: String?, jsonArray: JSONArray): JsonNode.JsonArrayNode {
    val children = mutableListOf<JsonNode>()
    for (i in 0 until jsonArray.length()) {
        val value = jsonArray.get(i)
        children.add(parseValue("[$i]", value))
    }
    return JsonNode.JsonArrayNode(key, children)
}

private fun parseValue(key: String?, value: Any): JsonNode {
    return when (value) {
        is JSONObject -> parseObject(key, value)
        is JSONArray -> parseArray(key, value)
        else -> JsonNode.JsonPrimitiveNode(key, value.toString())
    }
}
