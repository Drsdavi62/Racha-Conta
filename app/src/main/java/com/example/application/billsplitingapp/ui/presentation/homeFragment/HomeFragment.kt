package com.example.application.billsplitingapp.ui.presentation.homeFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.application.billsplitingapp.BillSplitApp
import com.example.application.billsplitingapp.R
import com.example.application.billsplitingapp.ui.components.HomeBottomSheet
import com.example.application.billsplitingapp.ui.components.HomeScreen
import com.example.application.billsplitingapp.ui.theme.BillSplitingAppTheme
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
                        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
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
                                    Toast.makeText(requireContext(), text, Toast.LENGTH_LONG)
                                        .show()
                                }
                            )
                        },
                        sheetPeekHeight = 0.dp
                    ) {
                        HomeScreen(
                            darkTheme = application.isDark.value,
                            darkThemeToggleClick = application::toggleDarkTheme,
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