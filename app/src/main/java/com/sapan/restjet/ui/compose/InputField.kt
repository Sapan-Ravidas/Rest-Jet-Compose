package com.sapan.restjet.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sapan.restjet.ui.theme.input_field_out_label_font_size

@Composable
fun TextInputField(
    label: String,
    onValueChange: (String) -> Unit,
    labelColor: Color = Color.Black,
    textColor: Color = Color.Gray,
    borderColor: Color = Color.Gray,
    modifier: Modifier = Modifier,
) {
    var value by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            onValueChange = { newValue: String ->
                value = newValue
                onValueChange(newValue)
            },
            label = { Text(text = label, color = textColor) },
            trailingIcon = {
                if (value.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            value = ""
                            onValueChange(value)
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear",
                            tint = borderColor
                        )
                    }
                }
            }
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = label,
            fontSize = input_field_out_label_font_size,
            color = labelColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextInputFieldPreview() {
    TextInputField("label", {},
         modifier = Modifier.height(200.dp))
}