package com.example.application.billsplitingapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.application.billsplitingapp.R
import com.example.application.billsplitingapp.ui.theme.NovaOvalRegular
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    darkTheme: Boolean,
    darkThemeToggleClick: () -> Unit,
    maxWidth: Dp,
    navController: NavController,
    coroutineScope: CoroutineScope,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Racha\n  Conta",
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .padding(start = 16.dp),
                fontFamily = NovaOvalRegular
            )
            DarkModeToggle(
                darkTheme = darkTheme,
                modifier = Modifier.padding(end = 8.dp),
                onClick = darkThemeToggleClick
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
                    navController.navigate(R.id.goToHistory)
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