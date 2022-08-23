package com.example.application.billsplitingapp.ui.presentation.people.list

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.application.billsplitingapp.domain.model.Person
import com.example.application.billsplitingapp.ui.components.SwipeToDeleteBackground
import com.example.application.billsplitingapp.ui.components.SwipeToDeleteList
import com.example.application.billsplitingapp.ui.presentation.bill.PeopleScreens
import com.example.application.billsplitingapp.ui.presentation.people.components.PersonItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PeopleScreen(
    navController: NavController,
    viewModel: PeopleViewModel = hiltViewModel(),
    billId: Int
) {

    viewModel.fetchPeople(billId)

    val people = viewModel.people.value

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        color = MaterialTheme.colors.primary.copy(alpha = .3f),
        shape = RoundedCornerShape(20.dp)
    ) {
        SwipeToDeleteList(
            list = people,
            key = {_, person: Person -> person.id},
            onDeleteBySwipe = { person ->
                viewModel.deletePerson(person)
            }
        ) { index: Int, person: Person ->
            Column {
                PersonItem(person = person, onEditClick = {
                    navController.navigate(PeopleScreens.AddEditPersonScreen.route + "/?billId=${billId}&personId=${person.id}")
                })

                if (index < people.lastIndex) {
                    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    Canvas(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)) {
                        drawLine(
                            color = Color.Black,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            pathEffect = pathEffect
                        )
                    }
                }
            }
        }
    }
}