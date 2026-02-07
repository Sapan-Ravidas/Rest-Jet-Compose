package com.sapan.restjet.screen

import android.text.TextUtils
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapan.restjet.ui.compose.ButtonDefault
import com.sapan.restjet.ui.compose.TextInputField
import com.sapan.restjet.ui.theme.Typography

@Composable
fun KeyValueInputScreen(
    onSubmit: (String, String) -> Unit,
    onDismiss: () -> Unit,
    title: String = "TITLE",
    keyLabel: String = "KEY",
    valueLabel: String = "VALUE",
    modifier: Modifier = Modifier,
) {
    var key by rememberSaveable { mutableStateOf("") }
    var value by rememberSaveable { mutableStateOf("") }
    var keyErrorMsg by remember { mutableStateOf<String?>(null) }
    var valueErrorMsg by remember { mutableStateOf<String?>(null) }

    val colorScheme = MaterialTheme.colorScheme

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            ButtonDefault(
                onClick = {
                    if (TextUtils.isEmpty(key)) {
                        keyErrorMsg = "Key cannot be empty"
                    }
                    if (TextUtils.isEmpty(value)) {
                        valueErrorMsg = "Value cannot be empty"
                    }
                    if (key != "" && value != "") {
                        onSubmit(key, value)
                    }
                          },
                text = "ADD"
            )
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = "CANCEL", color = colorScheme.primary)
            }
        },
        title = {
            Text(text = title, style = Typography.titleMedium)
        },
        text = {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                TextInputField(
                    label = if (keyErrorMsg != null) keyErrorMsg!! else keyLabel,
                    value = key,
                    onValueChange = {
                        key = it
                        Log.d("COLLECTION_SCREEN", "key=$key")
                    },
                    borderColor = colorScheme.secondary,
                    textColor = colorScheme.onSecondaryContainer,
                    labelColor = if (keyErrorMsg != null) Color.Red else colorScheme.onSecondaryContainer
                )

                TextInputField(
                    label = if (valueErrorMsg != null) valueErrorMsg!! else valueLabel,
                    value = value,
                    onValueChange = {
                        value = it
                    },
                    borderColor = colorScheme.secondary,
                    textColor = colorScheme.onSecondaryContainer,
                    labelColor = if (valueErrorMsg != null) Color.Red else colorScheme.onSecondaryContainer
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun KeyValueInputScreenPreview() {
    KeyValueInputScreen({_, _ -> }, {})
}