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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.People
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.application.billsplitingapp.BillSplitApp
import com.example.application.billsplitingapp.ui.components.BackTitleHeader
import com.example.application.billsplitingapp.ui.components.HistoryCardItem
import com.example.application.billsplitingapp.ui.components.IconText
import com.example.application.billsplitingapp.ui.theme.BillSplitingAppTheme
import com.example.application.billsplitingapp.utils.Formatter

class HistoryFragment : Fragment() {

    private lateinit var viewModel: HistoryViewModel
    private lateinit var application: BillSplitApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        application = requireActivity().application as BillSplitApp
        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
    }

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                BillSplitingAppTheme(application.isDark.value) {
                    Column(modifier = Modifier.background(color = MaterialTheme.colors.background)) {

                        val list = viewModel.list.observeAsState(listOf()).value

                        BackTitleHeader(
                            title = "Histórico",
                            navController = findNavController(),
                            modifier = Modifier.padding(vertical = 16.dp)
                        )

                        LazyColumn() {
                            itemsIndexed(list) { index, bill ->
                                HistoryCardItem(index = index, bill = bill, personList = viewModel.getRelationList(list)) {
                                    application::toggleDarkTheme
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}