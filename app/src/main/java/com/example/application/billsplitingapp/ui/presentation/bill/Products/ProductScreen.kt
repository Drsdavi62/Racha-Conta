package com.example.application.billsplitingapp.ui.presentation.bill.Products

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.application.billsplitingapp.ui.presentation.bill.BillViewModel

@Composable
fun ProductScreen(viewModel: BillViewModel = hiltViewModel()) {

    Text(text = "Products")
}