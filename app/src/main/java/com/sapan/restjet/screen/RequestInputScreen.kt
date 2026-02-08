package com.sapan.restjet.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sapan.restjet.data.Action
import com.sapan.restjet.data.ButtonContent
import com.sapan.restjet.data.HttpMethod
import com.sapan.restjet.data.RequestState
import com.sapan.restjet.data.httpMethods
import com.sapan.restjet.ui.compose.ButtonDefault
import com.sapan.restjet.ui.compose.ConnectedButtonGroup
import com.sapan.restjet.ui.compose.HttpMethodDropDown
import com.sapan.restjet.ui.compose.TextInputField
import com.sapan.restjet.ui.theme.Typography
import com.sapan.restjet.viewmodel.RequestResponseViewModel
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.sapan.restjet.events.UIEvent

@Composable
fun RequestInputScreen(
    viewModel: RequestResponseViewModel = hiltViewModel(),
    onSendRequest: () -> Unit = {},
    onSaveRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val lifeCycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifeCycleOwner) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UIEvent.NavigateToResponseScreen -> {
                    onSendRequest()
                }
            }
        }
    }

    val requestState by viewModel.requestState.collectAsStateWithLifecycle()
    val responseState by viewModel.responseState.collectAsStateWithLifecycle()

    // local ui state
    var showHeaderDialog by rememberSaveable { mutableStateOf(false) }
    var showParamsDialog by rememberSaveable { mutableStateOf(false) }
    var showMethodDropDown by rememberSaveable { mutableStateOf(false) }
    var selectedHttpMethod by rememberSaveable { mutableStateOf("GET") }
    var baseUrlError by rememberSaveable { mutableStateOf<String?>(null) }

    val buttons = listOf(
        ButtonContent(
            action = Action.SELECT_REQUEST_TYPE,
            text = selectedHttpMethod,
            icon = Icons.Default.ArrowDropDown,
            iconContentDescription = "select https method",
            dropDownItems = HttpMethod.entries.map { it.string },
            onClick = { showMethodDropDown = true },
        ),
        ButtonContent(
            action = Action.ADD_HEADER,
            text = "Header",
            icon = Icons.Default.Add,
            iconContentDescription = "add headers",
            onClick = {
                showHeaderDialog = true
            }
        ),
        ButtonContent(
            action = Action.ADD_QUERY_PARAM,
            text = "Params",
            icon = Icons.Default.Add,
            iconContentDescription = "add query parameters",
            onClick = {
                showParamsDialog = true
            }
        ),
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.8f),
                shape = RoundedCornerShape(12.dp)
            )
            .wrapContentSize()

    ) {
        if (responseState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Text(
            text = "SAVE",
            color = Color.Black.copy(alpha = 0.8f),
            style = Typography.titleSmall,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .clickable{
                    onSaveRequest()
                }
        )

        Column(
            modifier = modifier.fillMaxSize()
                        .wrapContentSize(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "REQUEST",
                color = Color.Black.copy(alpha = 0.8f),
                style = Typography.titleSmall
            )

            TextInputField(
                label = "BASE_URL",
                value = requestState.baseUrl,
                onValueChange = {
                    viewModel.updateBaseUrl(it)
                },
            )

            TextInputField(
                label = "PATH",
                value = requestState.pathUrl,
                onValueChange = {
                    viewModel.updatePathUrl(it)
                }
            )

            TextInputField(
                label = "REQUEST_BODY",
                value = requestState.body,
                onValueChange = {
                    viewModel.updateBody(body = it)
                },
                modifier = Modifier
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.5f))
            )

            ConnectedButtonGroup(
                buttons = buttons,
            )

            if (showMethodDropDown) {
                HttpMethodDropDown(
                    onItemClick = { method ->
                        selectedHttpMethod = method
                        showMethodDropDown = false
                    },
                    onDismissRequest = {
                        showMethodDropDown = false
                    },
                    items = httpMethods,
                    expanded = showMethodDropDown
                )
            }

            if (baseUrlError != null) {
                Text(
                    text = baseUrlError!!,
                    style = Typography.bodySmall,
                    color = Color.Red
                )
            }

            ButtonDefault(
                onClick = {
                    if (requestState.baseUrl.isEmpty()) {
                        baseUrlError = "Base URL cannot be empty"
                        return@ButtonDefault
                    }
                    viewModel.updateHttpMethod(HttpMethod.fromString(selectedHttpMethod))
                    viewModel.sendRequest()
                },
                text = "SEND"
            )

        } // end column

        /**
         *
         */
        if (showHeaderDialog) {
            KeyValueInputScreen(
                onSubmit = { key, value ->
                    if (viewModel.addHeader(key, value)) {
                        showHeaderDialog = false
                    }
                },
                onDismiss = {
                    showHeaderDialog = false
                },
                title = "HEADER",
                keyLabel = "key",
                valueLabel = "value",
            )
        }

        if (showParamsDialog) {
            KeyValueInputScreen(
                onSubmit = { key, value ->
                    if (viewModel.addQueryParams(key, value)) {
                        showParamsDialog = false
                    }
                },
                onDismiss = {
                    showParamsDialog = false
                },
                title = "QUERY PARAMS",
                keyLabel = "key",
                valueLabel = "value",
            )
        }
    } // end box
}

@Preview
@Composable
fun RequestInputScreenPreview() {
    RequestInputScreen(
        onSendRequest = {},
        onSaveRequest = {

        }
    )
}

