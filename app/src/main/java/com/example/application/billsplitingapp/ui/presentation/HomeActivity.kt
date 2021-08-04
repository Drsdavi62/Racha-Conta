package com.example.application.billsplitingapp.ui.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.History
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.application.billsplitingapp.R
import com.example.application.billsplitingapp.allBills.AllBillsActivity
import com.example.application.billsplitingapp.ui.components.DarkModeToggle
import com.example.application.billsplitingapp.ui.components.HomeButton
import com.example.application.billsplitingapp.ui.theme.BillSplitingAppTheme
import com.example.application.billsplitingapp.ui.theme.NovaOvalRegular

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContent {

            val darkTheme = remember {
                mutableStateOf(false)
            }

            BillSplitingAppTheme(darkTheme.value) {
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
                                darkTheme = darkTheme.value,
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                darkTheme.value = !darkTheme.value
                            }
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
                                    val intent =
                                        Intent(this@HomeActivity, AllBillsActivity::class.java)
                                    startActivity(intent)
                                }
                            )
                            HomeButton(
                                icon = Icons.Filled.Add,
                                text = "Adicionar Comanda",
                                width = maxWidth / 2,
                                outline = false,
                                onClick = {

                                }
                            )
                        }
                    }
                }
            }
        }
    }
}