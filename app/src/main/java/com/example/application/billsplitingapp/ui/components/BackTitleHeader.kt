package com.example.application.billsplitingapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController

@Composable
fun BackTitleHeader(
    title: String,
    navController: NavController,
    modifier: Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        val (back, titleRef) = createRefs()

        IconButton(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.constrainAs(back) {
                start.linkTo(parent.start)
                centerVerticallyTo(parent)
            }
        ) {
            Icon(
                Icons.Filled.ChevronLeft,
                contentDescription = "Go Back",
                tint = MaterialTheme.colors.secondary,
                modifier = Modifier.size(60.dp)
            )
        }

        Text(
            text = "Histórico",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(titleRef) {
                centerTo(parent)
            },
            color = MaterialTheme.colors.onBackground
        )
    }
}