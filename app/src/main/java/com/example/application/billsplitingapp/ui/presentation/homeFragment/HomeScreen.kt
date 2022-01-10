package com.example.application.billsplitingapp.ui.presentation.homeFragment

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.preference.PreferenceManager
import com.example.application.billsplitingapp.MainActivity
import com.example.application.billsplitingapp.R
import com.example.application.billsplitingapp.ui.Screen
import com.example.application.billsplitingapp.ui.components.DarkModeToggle
import com.example.application.billsplitingapp.ui.components.HomeBottomSheet
import com.example.application.billsplitingapp.ui.components.HomeButton
import com.example.application.billsplitingapp.ui.theme.NovaOvalRegular
import com.example.application.billsplitingapp.utils.Constants
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                HomeViewModel.UIEvent.ErrorCreatingBill -> {

                }
                is HomeViewModel.UIEvent.SuccessfullyCreatedBill -> {
                    event.id?.let { billId ->
//                        val id = event.id
//                        val prefs =
//                            PreferenceManager.getDefaultSharedPreferences(context)
//                        val editor = prefs.edit()
//                        editor.putInt(Constants.BILL_ID, id)
//                        editor.apply()
//                        val intent = Intent(context, MainActivity::class.java)
//                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//                        intent.putExtra(Constants.BILL_NAME, "Bill name")
//                        context.startActivity(intent)
                        navController.navigate(Screen.Bill.route + "/?billId=$billId")
                    }
                }
            }
        }
    }

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
                    if (text.isEmpty()) return@HomeBottomSheet
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                    viewModel.createBill(text)
                }
            )
        },
        sheetPeekHeight = 0.dp,
        sheetShape = RoundedCornerShape(topStartPercent = 10, topEndPercent = 10)
    ) {
        BoxWithConstraints(modifier = Modifier.background(color = MaterialTheme.colors.background)) {
            val maxWidth = maxWidth

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Racha\n  Conta",
                        style = MaterialTheme.typography.h2,
                        color = MaterialTheme.colors.secondary,
                        modifier = Modifier
                            .padding(start = 16.dp),
                        fontFamily = NovaOvalRegular
                    )
                }


                Image(
                    painter = painterResource(id = R.drawable.fast_food),
                    contentDescription = "Fast food",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    contentScale = ContentScale.FillWidth
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    HomeButton(
                        icon = Icons.Filled.History,
                        text = "Histórico",
                        width = maxWidth / 2,
                        outline = true,
                        onClick = {
                            navController.navigate(Screen.History.route)
                        }
                    )
                    HomeButton(
                        icon = Icons.Filled.Add,
                        text = "Adicionar Comanda",
                        width = maxWidth / 2,
                        outline = false,
                        onClick = {
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