package com.example.application.billsplitingapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Store
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun HomeBottomSheet(
    onDoneClick: (String) -> Unit,
    onCancelClick: () -> Unit
) {
    val textValue = rememberSaveable {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        val maxWidth = maxWidth

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp)
        ) {

            Text(text = "Digite o nome do restaurante", style = MaterialTheme.typography.h6)

            Spacer(modifier = Modifier.padding(8.dp))

            BottomSheetInput(textValue = textValue) {
                keyboardController?.hide()
                onDoneClick(textValue.value)
                textValue.value = ""
            }

            Spacer(modifier = Modifier.padding(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = {
                        keyboardController?.hide()
                        textValue.value = ""
                        onCancelClick()
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.width(maxWidth / 2 - 40.dp)
                ) {
                    Text(text = "Cancelar")
                }
                Button(
                    onClick = {
                        keyboardController?.hide()
                        onDoneClick(textValue.value)
                        textValue.value = ""
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.width(maxWidth / 2 - 40.dp)
                ) {
                    Text(text = "Criar")
                }
            }
        }
    }
}

@Composable
fun BottomSheetInput(
    textValue: MutableState<String>,
    onDoneClick: () -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = textValue.value,
        onValueChange = { textValue.value = it },
        label = {
            Text(text = "Restaurante")
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Store,
                contentDescription = "Restaurant"
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            capitalization = KeyboardCapitalization.Words,
        ),
        keyboardActions = KeyboardActions(onDone = {
            onDoneClick()
        })
    )
}

