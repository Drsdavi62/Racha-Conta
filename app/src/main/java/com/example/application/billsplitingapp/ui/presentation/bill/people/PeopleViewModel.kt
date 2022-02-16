package com.example.application.billsplitingapp.ui.presentation.bill.people

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.billsplitingapp.domain.model.Person
import com.example.application.billsplitingapp.domain.repository.BillRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val repository: BillRepository
) : ViewModel() {

    private val _people = mutableStateOf<List<Person>>(emptyList())
    val people: State<List<Person>> = _people

    var job: Job? = null

    fun load(billId: Int) {
        viewModelScope.launch {
            repository.insertPerson(Person(
                billId = billId,
                name = "Davi"
            ))
            repository.insertPerson(Person(
                billId = billId,
                name = "Julia"
            ))
            repository.insertPerson(Person(
                billId = billId,
                name = "Isa"
            ))
            repository.insertPerson(Person(
                billId = billId,
                name = "Joao"
            ))
        }
    }

    fun fetchPeople(billId: Int) {
        job?.cancel()
        job = repository.fetchPeopleFromBill(billId).onEach { people ->
            _people.value = people
        }.launchIn(viewModelScope)
    }
}