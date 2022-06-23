package com.example.issu_tracker.ui.issue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issu_tracker.data.Comment
import com.example.issu_tracker.data.Label
import com.example.issu_tracker.data.NewIssue
import com.example.issu_tracker.data.User
import com.example.issu_tracker.data.repository.FilterRepository
import com.example.issu_tracker.ui.common.Constants
import com.example.issu_tracker.ui.filter.FilterViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueEditorViewModel @Inject constructor(val repository: FilterRepository) : ViewModel() {

    private val _issueTitleStateFlow = MutableStateFlow<String>("")
    val issueTitleStateFlow = _issueTitleStateFlow.asStateFlow()

    private val _issueBodyStateFlow = MutableStateFlow<String>("")
    val issueBodyStateFlow = _issueBodyStateFlow.asStateFlow()

    private val _assigneeStateFlow = MutableStateFlow<List<String>>(mutableListOf(""))
    val assigneeStateFlow = _assigneeStateFlow.asStateFlow()

    private val _labelStateFlow = MutableStateFlow<List<String>>(mutableListOf(""))
    val labelStateFlow = _labelStateFlow.asStateFlow()

    private val _mileStoneStateFlow = MutableStateFlow<List<String>>(mutableListOf(""))
    val mileStoneStateFlow = _mileStoneStateFlow.asStateFlow()

    private var assigneeConditionValue = ""
    private var labelConditionValue = ""
    private var mileStoneConditionValue = ""

    init {
        viewModelScope.launch {
            setLabelList()
            setAssigneeList()
        }
    }

    fun inputIssueTitleText(text: String) {
        _issueTitleStateFlow.value = text
    }

    fun inputIssueBodyText(text: String) {
        _issueBodyStateFlow.value = text
    }

    /*fun createIssue() {
        val newIssue = NewIssue(
            id = "",
        comments = listOf(),
        description = _issueBodyStateFlow.value,
        label = listOf(labelConditionValue),
        mileStone = ,
        state = true,
        title = _issueTitleStateFlow.value,
        user =
        )
    }*/

    private suspend fun setLabelList() {
        val labelList = repository.loadLabel().toMutableList()
        labelList.add("")
        _labelStateFlow.emit(labelList)
    }

    private suspend fun setAssigneeList() {
        val assigneeList = repository.loadUsers().toMutableList()
        assigneeList.add("")
        _assigneeStateFlow.emit(assigneeList)
    }

    fun inputSpinnerValue(text: String, conditionType: Int) {
        when (conditionType) {
            Constants.CONDITION_TYPE_ASSIGNEE -> assigneeConditionValue = text
            Constants.CONDITION_TYPE_LABEL -> labelConditionValue = text
//            Constants.CONDITION_TYPE_ASSIGNEE -> mileStoneConditionValue = text
        }
    }
}
