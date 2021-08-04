package com.example.application.billsplitingapp.ui.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.application.billsplitingapp.ui.components.BackTitleHeader
import com.example.application.billsplitingapp.ui.theme.BillSplitingAppTheme
import com.example.application.billsplitingapp.utils.Formatter

class HistoryFragment : Fragment() {

    private lateinit var viewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                BillSplitingAppTheme(true) {
                    Column(modifier = Modifier.background(color = MaterialTheme.colors.background)) {

                        val list = viewModel.list.observeAsState(listOf()).value

                        BackTitleHeader(
                            title = "Histórico",
                            navController = findNavController(),
                            modifier = Modifier.padding(vertical = 16.dp)
                        )

                        LazyColumn() {
                            itemsIndexed(list) { index, bill ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    shape = RoundedCornerShape(20.dp),
                                    border = BorderStroke(2.dp, MaterialTheme.colors.primary),
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(16.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(bottom = 8.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = bill.name,
                                                style = MaterialTheme.typography.h5,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = Formatter.currencyFormat(bill.value),
                                                style = MaterialTheme.typography.h6
                                            )
                                        }
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            val personList = viewModel.getRelationList(list)

                                            Text(
                                                text = personList[index].joinToString(", ") { it.name },
                                                style = MaterialTheme.typography.body1
                                            )
                                            Text(
                                                text = bill.date,
                                                style = MaterialTheme.typography.body2
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}