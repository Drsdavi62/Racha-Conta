package com.example.application.billsplitingapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.application.billsplitingapp.ui.theme.DisabledGray

@Composable
fun AddEditButtonRow(
    isSaveEnabled: Boolean,
    isEditing: Boolean,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        OutlinedButton(
            onClick = onCancelClick,
            border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.background),
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(.5f)
        ) {
            Text(
                text = "Cancelar",
                color = MaterialTheme.colors.secondary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = onSaveClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                disabledBackgroundColor = DisabledGray
            ),
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            enabled = isSaveEnabled
        ) {
            Text(
                text = if (isEditing) "Salvar" else "Adicionar",
                color = MaterialTheme.colors.onSecondary,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6
            )
        }
    }
}