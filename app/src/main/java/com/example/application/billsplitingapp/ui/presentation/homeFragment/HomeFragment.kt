package com.example.application.billsplitingapp.ui.presentation.homeFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.example.application.billsplitingapp.BillSplitApp
import com.example.application.billsplitingapp.MainActivity
import com.example.application.billsplitingapp.R
import com.example.application.billsplitingapp.allBills.AllBillsActivity
import com.example.application.billsplitingapp.ui.components.HomeBottomSheet
import com.example.application.billsplitingapp.ui.components.HomeScreen
import com.example.application.billsplitingapp.ui.theme.BillSplitingAppTheme
import com.example.application.billsplitingapp.utils.Constants
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var application: BillSplitApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        application = requireActivity().application as BillSplitApp
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                BillSplitingAppTheme(application.isDark.value) {

                    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
                    )
                    val coroutineScope = rememberCoroutineScope()

                    BottomSheetScaffold(
                        scaffoldState = bottomSheetScaffoldState,
                        sheetContent = {
                            HomeBottomSheet(
                                onCancelClick = {
                                    coroutineScope.launch {
                                        bottomSheetScaffoldState.bottomSheetState.collapse()
                                    }
                                },
                                onDoneClick = { text ->
                                    coroutineScope.launch {
                                        bottomSheetScaffoldState.bottomSheetState.collapse()
                                    }
                                    val id = viewModel.insertBill(text)
                                    val prefs =
                                        PreferenceManager.getDefaultSharedPreferences(application)
                                    val editor = prefs.edit()
                                    editor.putInt(Constants.BILL_ID, id)
                                    editor.apply()
                                    val intent = Intent(requireActivity(), MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    intent.putExtra(Constants.BILL_NAME, text)
                                    startActivity(intent)
                                }
                            )
                        },
                        sheetPeekHeight = 0.dp,
                        sheetShape = RoundedCornerShape(topStartPercent = 10 , topEndPercent = 10)
                    ) {
                        HomeScreen(
                            darkTheme = application.isDark.value,
                            darkThemeToggleClick = {
                                application.toggleDarkTheme()
                            },
                            onHistoryClick = {
                                findNavController().navigate(R.id.goToHistory)
                            },
                            onAddClick = {
                                coroutineScope.launch {
                                    bottomSheetScaffoldState.bottomSheetState.expand()
                                }
                            }
                        )
                    }
                }
            }

        }
    }
}