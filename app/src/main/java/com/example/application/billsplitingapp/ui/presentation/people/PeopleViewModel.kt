package com.example.application.billsplitingapp.ui.presentation.people

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.billsplitingapp.domain.model.Person
import com.example.application.billsplitingapp.domain.model.Product
import com.example.application.billsplitingapp.domain.repository.BillRepository
import com.example.application.billsplitingapp.domain.use_case.people.FetchPeopleFromBill
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: BillRepository,
    private val fetchPeopleFromBill: FetchPeopleFromBill
) : ViewModel() {

    private val _people = mutableStateOf<List<Person>>(emptyList())
    val people: State<List<Person>> = _people

    var job: Job? = null

    fun load(billId: Int) {
        viewModelScope.launch {
            repository.insertPerson(Person(
                billId = billId,
                name = "Davi",
                products = emptyList()
            ))
            repository.insertPerson(Person(
                billId = billId,
                name = "Julia",
                products = listOf(
                    Product(
                        id = 0,
                        billId = 0,
                        name = "Hamburguer",
                        value = 0.0f,
                        amount = 0,
                        people = listOf()
                    ),
                    Product(
                        id = 0,
                        billId = 0,
                        name = "Hamburguer",
                        value = 0.0f,
                        amount = 0,
                        people = listOf()
                    ),
                )
            ))
            repository.insertPerson(Person(
                billId = billId,
                name = "Isa",
                products = emptyList()
            ))
            repository.insertPerson(Person(
                billId = billId,
                name = "Joao",
                products = emptyList()
            ))
        }
    }

    fun fetchPeople(billId: Int) {
        job?.cancel()
        job = fetchPeopleFromBill(billId).onEach { people ->
            _people.value = people
        }.launchIn(viewModelScope)
    }
}