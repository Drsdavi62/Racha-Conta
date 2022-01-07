package com.example.application.billsplitingapp.ui.presentation.homeFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application.billsplitingapp.domain.use_case.CreateNewBill
import com.example.application.billsplitingapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val createNewBill: CreateNewBill
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun createBill(name: String) {
        viewModelScope.launch {
            val id = createNewBill(name)
            _eventFlow.emit(UIEvent.SuccessfullyCreatedBill(id))
        }
    }

    sealed class UIEvent {
        data class SuccessfullyCreatedBill(val id: Int?) : UIEvent()
        object ErrorCreatingBill: UIEvent()
    }
}