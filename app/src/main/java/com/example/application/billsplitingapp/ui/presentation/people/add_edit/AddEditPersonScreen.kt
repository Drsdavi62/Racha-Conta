package com.example.application.billsplitingapp.ui.presentation.people.add_edit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.RemoveDone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.application.billsplitingapp.ui.components.simpleVerticalScrollbar
import com.example.application.billsplitingapp.ui.presentation.products.components.CheckboxItem
import com.example.application.billsplitingapp.ui.presentation.products.components.HorizontalAmountSelector
import com.example.application.billsplitingapp.ui.presentation.products.components.TotalValueAnimated
import com.example.application.billsplitingapp.ui.theme.DisabledGray
import com.example.application.billsplitingapp.utils.Formatter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditPersonScreen(
    navController: NavController,
    viewModel: AddEditProductViewModel = hiltViewModel()
) {

    val name = viewModel.name.value

    val isEditing = viewModel.isEditing.value

    val products = viewModel.products.value

    var selectedProducts = viewModel.selectedProducts

    val composableScope = rememberCoroutineScope()

    val listState = rememberLazyListState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                AddEditProductViewModel.UIEvents.SuccessSavingPerson -> {
                    navController.navigateUp()
                }
                AddEditProductViewModel.UIEvents.ErrorSavingPerson -> {

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
        Column(Modifier.fillMaxHeight(.9f)) {

            Text(text = "Nome", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { viewModel.onEvent(AddEditPersonEvents.EnteredName(it)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = MaterialTheme.colors.primary),
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Selecione os produtos",
                    style = MaterialTheme.typography.h6,
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        viewModel.onEvent(AddEditPersonEvents.ToggleAllProducts)
                    }
                ) {
                    Icon(
                        imageVector = if (selectedProducts.size == products.size) Icons.Default.RemoveDone else Icons.Default.DoneAll,
                        contentDescription = "Select all",
                        tint = MaterialTheme.colors.secondary
                    )
                }
            }

            BoxWithConstraints() {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.simpleVerticalScrollbar(
                        listState,
                        listTotalHeight = maxHeight
                    )
                ) {
                    items(products) { product ->
                        CheckboxItem(
                            checked = selectedProducts.contains(product),
                            text = product.name
                        ) {
                            viewModel.onEvent(AddEditPersonEvents.ToggledProductSelection(product))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            OutlinedButton(
                onClick = { navController.navigateUp() },
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
                onClick = { viewModel.onEvent(AddEditPersonEvents.SavePerson) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    disabledBackgroundColor = DisabledGray
                ),
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                enabled = name.isNotEmpty()
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