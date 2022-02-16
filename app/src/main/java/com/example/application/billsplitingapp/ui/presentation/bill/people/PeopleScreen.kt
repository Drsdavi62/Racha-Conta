package com.example.application.billsplitingapp.ui.presentation.bill.people

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.application.billsplitingapp.ui.presentation.bill.BillViewModel

@Composable
fun PeopleScreen(
    viewModel: PeopleViewModel = hiltViewModel(),
    billId: Int
) {

    viewModel.fetchPeople(billId)

    val people = viewModel.people.value

    Column {
        LazyColumn() {
            items(people) { person ->
                Text(text = person.name)
            }
        }

        Button(onClick = {
            viewModel.load(billId)
        }) {
            Text(text = "Load")
        }
    }
}