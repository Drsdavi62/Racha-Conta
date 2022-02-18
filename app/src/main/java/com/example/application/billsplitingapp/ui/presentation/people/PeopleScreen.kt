package com.example.application.billsplitingapp.ui.presentation.people

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.application.billsplitingapp.ui.presentation.people.components.PersonItem

@Composable
fun PeopleScreen(
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
        LazyColumn() {
            itemsIndexed(people) { index, person ->
                PersonItem(person = person)

                if (index < people.size - 1) {
                    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    Canvas(modifier = Modifier.fillMaxWidth().height(1.dp)) {
                        drawLine(
                            color = Color.Black,
                            start = Offset(0f, 0f),
                            end = Offset(size.width, 0f),
                            pathEffect = pathEffect
                        )
                    }
                }
            }
            item {
                Button(onClick = { viewModel.load(billId) }) {
                    Text(text = "Load")
                }
            }
        }
    }
}