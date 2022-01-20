package com.example.application.billsplitingapp.ui.presentation.products.add_edit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.application.billsplitingapp.ui.presentation.products.components.HorizontalAmountSelector
import com.example.application.billsplitingapp.ui.presentation.products.components.TotalValueAnimated
import com.example.application.billsplitingapp.ui.theme.DisabledGray
import com.example.application.billsplitingapp.utils.Formatter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditProductScreen(
    navController: NavController,
    viewModel: AddEditProductViewModel = hiltViewModel()
) {

    val name = viewModel.name.value

    val amount = viewModel.amount.value

    val value = viewModel.value.value

    val isEditing = viewModel.isEditing.value

    val isTotalBig = remember {
        mutableStateOf(false)
    }

    val composableScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                AddEditProductViewModel.UIEvents.SuccessSavingProduct -> {
                    navController.navigateUp()
                }
                AddEditProductViewModel.UIEvents.ErrorSavingProduct -> {

                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {

            Text(text = "Produto", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { viewModel.onEvent(AddEditProductEvents.EnteredName(it)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = MaterialTheme.colors.primary),
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth(.48f)) {
                    Text(text = "Valor Unitário", style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = value,
                        onValueChange = { viewModel.onEvent(AddEditProductEvents.EnteredValue(it.text)) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = MaterialTheme.colors.primary),
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    TotalValueAnimated(value = viewModel.fullValue, triggered = isTotalBig.value)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Quantidade", style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalAmountSelector(
                        amount = amount,
                        onAmountChange = {
                            viewModel.onEvent(AddEditProductEvents.ChangedAmount(it))
                            composableScope.launch {
                                isTotalBig.value = true
                                delay(250)
                                isTotalBig.value = false
                            }
                        })
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Quem irá dividir esse produto?", style = MaterialTheme.typography.h6)
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { navController.navigateUp() },
                border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.background),
                modifier = Modifier
                    .height(50.dp)
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
                onClick = { viewModel.onEvent(AddEditProductEvents.SaveProduct) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    disabledBackgroundColor = DisabledGray
                ),
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                enabled = name.isNotEmpty() && Formatter.currencyFormatFromString(value.text) > 0f
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


}