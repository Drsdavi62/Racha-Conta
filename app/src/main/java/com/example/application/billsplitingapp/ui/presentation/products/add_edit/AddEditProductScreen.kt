package com.example.application.billsplitingapp.ui.presentation.products.add_edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.application.billsplitingapp.ui.presentation.bill.BillViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditProductScreen(
    navController: NavController,
    viewModel: AddEditProductViewModel = hiltViewModel()
) {

    val name = remember {
        mutableStateOf("Hamburguer")
    }

    val amount = remember {
        mutableStateOf("2")
    }

    val value = remember {
        mutableStateOf("35")
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {
            navController.navigateUp()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = name.value,
            onValueChange = {
                name.value = it
            },
            label = { Text(text = "Nome")}
        )

        TextField(
            value = amount.value,
            onValueChange = {
                amount.value = it
            },
            label = { Text(text = "Quantidade")},

        )

        TextField(
            value = value.value,
            onValueChange = {
                value.value = it
            },
            label = { Text(text = "Preco")}
        )

        Button(onClick = {
            viewModel.insertProduct(name.value, amount.value.toInt(), value.value.toFloat(), emptyList())
        }) {
            Text(text = "Salvar")
        }
    }
}