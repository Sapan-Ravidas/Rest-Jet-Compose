package com.sapan.restjet.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sapan.restjet.data.parseJson
import com.sapan.restjet.ui.compose.JsonViewerCard
import com.sapan.restjet.ui.compose.RequestCard
import com.sapan.restjet.ui.theme.Typography
import com.sapan.restjet.viewmodel.RequestResponseViewModel

@Composable
fun ResponseScreen(
    viewModel: RequestResponseViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val responseState by viewModel.responseState.collectAsStateWithLifecycle()
    val requestState by viewModel.requestState.collectAsStateWithLifecycle()
    val jsonNode = parseJson(responseState.responseBody)
    // val jsonNode = parseJson(jsonString)

    SideEffect {
        Log.d("SAPAN", "jsonNode $jsonNode")
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "RESPONSE",
            style = Typography.titleLarge
        )

        RequestCard(
            requestState = requestState,
            responseState = responseState
        )

        JsonViewerCard(
            node = jsonNode,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResponseScreenPreview() {
    ResponseScreen()
}