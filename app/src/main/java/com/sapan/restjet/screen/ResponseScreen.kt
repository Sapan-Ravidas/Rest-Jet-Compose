package com.sapan.restjet.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sapan.restjet.data.parseJson
import com.sapan.restjet.ui.compose.JsonViewerCard
import com.sapan.restjet.ui.compose.RequestCard
import com.sapan.restjet.ui.theme.Typography
import com.sapan.restjet.viewmodel.RequestResponseViewModel

@SuppressLint("RestrictedApi")
@Composable
fun ResponseScreen(
    viewModel: RequestResponseViewModel = hiltViewModel(),
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val responseState by viewModel.responseState.collectAsStateWithLifecycle()
    val requestState by viewModel.requestState.collectAsStateWithLifecycle()
    val responseBody = rememberUpdatedState(responseState.responseBody)
    val jsonNode = remember(responseBody) {
        derivedStateOf { parseJson(responseBody.value) }
    }

    SideEffect {
        Log.d("RESPONSE_SCREEN", "response body: ${responseBody.value}")
    }

    BackHandler {
        viewModel.clearResponse()
        navController.popBackStack()
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
            node = jsonNode.value,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ResponseScreenPreview() {
    ResponseScreen(
        navController = rememberNavController()
    )
}