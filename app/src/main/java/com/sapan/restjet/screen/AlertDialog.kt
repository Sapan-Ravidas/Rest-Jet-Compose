package com.sapan.restjet.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sapan.restjet.ui.compose.ButtonDefault
import com.sapan.restjet.ui.compose.TextInputField
import com.sapan.restjet.ui.theme.Typography

@Composable
fun PopupDialog(
    onDismissRequest: () -> Unit = {},
    onConfirm: (String) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var filename by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        title = {
            Text("Provide Filename", style = Typography.titleSmall)
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            ButtonDefault(
                onClick = {
                    onConfirm(filename)
                },
                text = "Done"
            )
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(text = "Cancel")
            }
        },
        text = {
            Column{
                Text(
                    text = "Enter filename to save the request as:"
                )

                TextInputField(
                    label = "Filename",
                    value = filename,
                    onValueChange = {
                        filename = it
                    }
                )
            }
        }
    )
}

@Preview
@Composable
fun AlertDialogPreview() {
    PopupDialog()
}

