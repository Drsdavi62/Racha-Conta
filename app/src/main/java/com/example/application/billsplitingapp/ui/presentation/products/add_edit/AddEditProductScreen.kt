package com.example.application.billsplitingapp.ui.presentation.products.add_edit

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.application.billsplitingapp.ui.presentation.products.components.HorizontalAmountSelector
import com.example.application.billsplitingapp.ui.presentation.products.components.TotalValueAnimated
import com.example.application.billsplitingapp.ui.theme.DisabledGray
import com.example.application.billsplitingapp.utils.Formatter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditProductScreen(
    navController: NavController,
    viewModel: AddEditProductViewModel = hiltViewModel()
) {

    val name = viewModel.name.value

    val amount = viewModel.amount.value

    val value = viewModel.value.value

    val isEditing = viewModel.isEditing.value

    val isTotalBig = remember {
        mutableStateOf(false)
    }

    val people = viewModel.people.value

    var selectedPeople = viewModel.selectedPeople

    val composableScope = rememberCoroutineScope()

    val listState = rememberLazyListState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                AddEditProductViewModel.UIEvents.SuccessSavingProduct -> {
                    navController.navigateUp()
                }
                AddEditProductViewModel.UIEvents.ErrorSavingProduct -> {

                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(Modifier.fillMaxHeight(.9f)) {

            Text(text = "Produto", style = MaterialTheme.typography.h6)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { viewModel.onEvent(AddEditProductEvents.EnteredName(it)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = MaterialTheme.colors.primary),
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth(.48f)) {
                    Text(text = "Valor Unitário", style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = value,
                        onValueChange = { viewModel.onEvent(AddEditProductEvents.EnteredValue(it.text)) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = MaterialTheme.colors.primary),
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    TotalValueAnimated(value = viewModel.fullValue, triggered = isTotalBig.value)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Quantidade", style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalAmountSelector(
                        amount = amount,
                        onAmountChange = {
                            viewModel.onEvent(AddEditProductEvents.ChangedAmount(it))
                            composableScope.launch {
                                isTotalBig.value = true
                                delay(250)
                                isTotalBig.value = false
                            }
                        })
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(Modifier.fillMaxWidth()) {

            }

            Text(text = "Quem irá dividir esse produto?", style = MaterialTheme.typography.h6)

            BoxWithConstraints() {
                LazyColumn(state = listState, modifier = Modifier.simpleVerticalScrollbar(listState, listTotalHeight = maxHeight)) {
                    items(people) { person ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedPeople.contains(person),
                                onCheckedChange = {
                                    viewModel.onEvent(AddEditProductEvents.ToggledPersonSelection(person))
                                },
                                colors = CheckboxDefaults.colors()
                            )
                            Text(
                                text = person.name,
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            OutlinedButton(
                onClick = { navController.navigateUp() },
                border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = MaterialTheme.colors.background),
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.5f)
            ) {
                Text(
                    text = "Cancelar",
                    color = MaterialTheme.colors.secondary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { viewModel.onEvent(AddEditProductEvents.SaveProduct) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    disabledBackgroundColor = DisabledGray
                ),
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                enabled = name.isNotEmpty() && Formatter.currencyFormatFromString(value.text) > 0f
            ) {
                Text(
                    text = if (isEditing) "Salvar" else "Adicionar",
                    color = MaterialTheme.colors.onSecondary,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6
                )
            }
        }
    }


}

@Composable
fun Modifier.simpleVerticalScrollbar(
    state: LazyListState,
    width: Dp = 4.dp,
    color: Color = MaterialTheme.colors.primary,
    listTotalHeight: Dp
): Modifier {

    var lol: Int = 0
    if (state.layoutInfo.visibleItemsInfo.isNotEmpty()) {
        lol = remember {
            state.layoutInfo.visibleItemsInfo.size
        }
    }

    val targetAlpha = if (state.isScrollInProgress) 1f else 0f
    val duration = if (state.isScrollInProgress) 150 else 500

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration)
    )

    val elementHeight = listTotalHeight / state.layoutInfo.totalItemsCount
    val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index

    var heightInPx: Float

    with(LocalDensity.current) {
        heightInPx = elementHeight.toPx()
    }
    val scrollbarOffsetY = firstVisibleElementIndex?.times(heightInPx) ?: 0f

    val offset by animateFloatAsState(
        targetValue = scrollbarOffsetY,
        animationSpec = tween(duration)
    )
    val scrollbarHeight = lol * heightInPx

    return drawWithContent {
        drawContent()
        val needDrawScrollbar = state.isScrollInProgress || alpha > 0.0f

        // Draw scrollbar if scrolling or if the animation is still running and lazy column has content
        if (needDrawScrollbar && firstVisibleElementIndex != null) {

            drawRect(
                color = color,
                topLeft = Offset(this.size.width - width.toPx(), offset),
                size = Size(width.toPx(), scrollbarHeight),
                alpha = alpha
            )
        }
    }
}