package com.example.application.billsplitingapp.ui.presentation.homeFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.application.billsplitingapp.R
import com.example.application.billsplitingapp.allBills.AllBillsActivity
import com.example.application.billsplitingapp.ui.components.DarkModeToggle
import com.example.application.billsplitingapp.ui.components.HomeBottomSheet
import com.example.application.billsplitingapp.ui.components.HomeButton
import com.example.application.billsplitingapp.ui.components.HomeScreen
import com.example.application.billsplitingapp.ui.theme.BillSplitingAppTheme
import com.example.application.billsplitingapp.ui.theme.NovaOvalRegular
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

                val darkTheme = remember {
                    mutableStateOf(false)
                }

                val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                    bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
                )
                val coroutineScope = rememberCoroutineScope()

                BillSplitingAppTheme(darkTheme.value) {
                    BoxWithConstraints(modifier = Modifier.background(color = MaterialTheme.colors.background)) {

                        val maxWidth = maxWidth
                        BottomSheetScaffold(
                            scaffoldState = bottomSheetScaffoldState,
                            sheetContent = {
                                HomeBottomSheet(
                                    maxWidth = maxWidth,
                                    onCancelClick = {
                                        coroutineScope.launch {
                                            bottomSheetScaffoldState.bottomSheetState.collapse()
                                        }
                                    },
                                    onDoneClick = { text ->
                                        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
                                    }
                                )
                            },
                            sheetPeekHeight = 0.dp
                        ) {
                            HomeScreen(
                                darkTheme = darkTheme.value,
                                darkThemeToggleClick = {
                                   darkTheme.value = !darkTheme.value
                                },
                                maxWidth = maxWidth,
                                navController = findNavController(),
                                coroutineScope = coroutineScope,
                                bottomSheetScaffoldState = bottomSheetScaffoldState
                            )
                        }
                    }
                }
            }

        }
    }
}