package com.example.application.billsplitingapp.ui.presentation.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.example.application.billsplitingapp.BillSplitApp
import com.example.application.billsplitingapp.MainActivity
import com.example.application.billsplitingapp.models.PersonModel
import com.example.application.billsplitingapp.ui.components.BackTitleHeader
import com.example.application.billsplitingapp.ui.components.DeleteButtonRow
import com.example.application.billsplitingapp.ui.components.HistoryCardItem
import com.example.application.billsplitingapp.ui.theme.BillSplitingAppTheme
import com.example.application.billsplitingapp.utils.Constants
import com.example.application.billsplitingapp.utils.addAllDistinct

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

                        val selectedIdList: MutableList<Int> = remember { mutableStateListOf() }

                        val list = viewModel.list.observeAsState(listOf()).value

                        val selectionMode = remember {
                            mutableStateOf(false)
                        }

                        Box(modifier = Modifier.fillMaxWidth()) {

                            if (selectionMode.value) {
                                DeleteButtonRow(
                                    isAllSelected = selectedIdList.size >= list.size,
                                    onCheckedChange = { checked ->
                                        if (checked) {
                                            selectedIdList.addAllDistinct(list.map { it.id })
                                        } else {
                                            selectedIdList.clear()
                                            selectionMode.value = false
                                        }
                                    },
                                    onDeleteClick = {

                                    }
                                )
                            } else {
                                BackTitleHeader(
                                    title = "Histórico",
                                    navController = findNavController(),
                                    modifier = Modifier.padding(vertical = 16.dp)
                                )
                            }
                        }


                        LazyColumn() {
                            itemsIndexed(list) { index, bill ->
                                HistoryCardItem(
                                    index = index,
                                    bill = bill,
                                    personList = viewModel.getRelationList(list),
                                    selectionMode = selectionMode.value,
                                    isSelected = selectedIdList.contains(bill.id),
                                    onClick = {
                                        val prefs =
                                            PreferenceManager.getDefaultSharedPreferences(
                                                application
                                            )
                                        val editor = prefs.edit()
                                        editor.putInt(Constants.BILL_ID, bill.id)
                                        editor.apply()
                                        val intent =
                                            Intent(requireActivity(), MainActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        intent.putExtra(Constants.BILL_NAME, bill.name)
                                        startActivity(intent)
                                    },
                                    onLongPress = { selected, id ->
                                        if (!selectionMode.value) {
                                            selectionMode.value = true
                                        }
                                        if (selected) {
                                            selectedIdList.add(id)
                                        } else {
                                            selectedIdList.remove(id)
                                        }
                                        if (selectedIdList.isEmpty()) {
                                            selectionMode.value = false
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}