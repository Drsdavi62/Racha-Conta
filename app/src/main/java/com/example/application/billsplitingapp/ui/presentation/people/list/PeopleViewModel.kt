package com.example.application.billsplitingapp.ui.presentation.people.list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.billsplitingapp.domain.model.Person
import com.example.application.billsplitingapp.domain.model.Product
import com.example.application.billsplitingapp.domain.repository.BillRepository
import com.example.application.billsplitingapp.domain.use_case.people.DeletePerson
import com.example.application.billsplitingapp.domain.use_case.people.FetchPeopleFromBill
import com.example.application.billsplitingapp.domain.use_case.people.InsertPerson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchPeopleFromBill: FetchPeopleFromBill,
    private val deletePersonUseCase: DeletePerson,
    private val insertPerson: InsertPerson
) : ViewModel() {

    private val _people = mutableStateOf<List<Person>>(emptyList())
    val people: State<List<Person>> = _people

    var lastDeletedPerson: Person? = null

    var job: Job? = null

    fun deletePerson(person: Person) {
        viewModelScope.launch {
            deletePersonUseCase(person)
            lastDeletedPerson = person
        }
    }

    fun undoDeletion() {
        lastDeletedPerson?.let {
            viewModelScope.launch {
                insertPerson(it)
                lastDeletedPerson = null
            }
        }
    }

    fun fetchPeople(billId: Int) {
        job?.cancel()
        job = fetchPeopleFromBill(billId).onEach { people ->
            _people.value = people
        }.launchIn(viewModelScope)
    }
}