package com.example.issu_tracker.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.issu_tracker.data.FilterCondition
import com.example.issu_tracker.data.Issue
import com.example.issu_tracker.data.repository.FilterRepository
import com.example.issu_tracker.ui.common.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(private val repository: FilterRepository) : ViewModel() {

    private val _stateStateFlow = MutableStateFlow<List<String>>(mutableListOf(""))
    val stateStateFlow = _stateStateFlow.asStateFlow()

    private val _writerStateFlow = MutableStateFlow<List<String>>(mutableListOf(""))
    val writerStateFlow = _writerStateFlow.asStateFlow()

    private val _labelStateFlow = MutableStateFlow<List<String>>(mutableListOf(""))
    val labelStateFlow = _labelStateFlow.asStateFlow()

    private val _mileStoneStateFlow = MutableStateFlow<List<String>>(mutableListOf(""))
    val mileStoneStateFlow = _mileStoneStateFlow.asStateFlow()

    private val _conditionValueStateFlow = MutableStateFlow<Boolean>(false)
    val conditionValueStateFlow = _conditionValueStateFlow.asStateFlow()

    private var stateConditionValue = ""
    private var writerConditionValue = ""
    private var labelConditionValue = ""
    private var mileStoneConditionValue = ""

    init {
        createDummyData()
    }

    private fun createDummyData() {
        val test = mutableListOf<String>()
        (0..10).forEach {
            test.add("안녕 $it")
        }
        test.add(DEFAULT_VALUE)
        viewModelScope.launch {
            setStateList()
            setWriterList()
            setLabelList()
            _mileStoneStateFlow.emit(test)
        }
    }

    private suspend fun setStateList() {
        val stateList = mutableListOf<String>()
        stateList.add("열린 이슈")
        stateList.add("닫힌 이슈")
        stateList.add(DEFAULT_VALUE)
        _stateStateFlow.emit(stateList)
    }

    private suspend fun setLabelList() {
        val labelList = repository.loadLabel().toMutableList()
        labelList.add(DEFAULT_VALUE)
        _labelStateFlow.emit(labelList)
    }

    private suspend fun setWriterList() {
        val writerList = repository.loadUsers().toMutableList()
        writerList.add(DEFAULT_VALUE)
        _writerStateFlow.emit(writerList)
    }

    fun inputSpinnerValue(text: String, conditionType: Int) {
        if (text != DEFAULT_VALUE) {
            viewModelScope.launch {
                _conditionValueStateFlow.emit(true)
            }
            when (conditionType) {
                Constants.FILTER_TYPE_STATE_CONDITION -> stateConditionValue = text
                Constants.FILTER_TYPE_WRITER_CONDITION -> writerConditionValue = text
                Constants.FILTER_TYPE_LABEL_CONDITION -> labelConditionValue = text
                Constants.FILTER_TYPE_MILESTONE_CONDITION -> mileStoneConditionValue = text
            }
        }
    }

    fun saveConditionValues() = FilterCondition(
        stateConditionValue,
        writerConditionValue,
        labelConditionValue,
        mileStoneConditionValue
    )

    fun resetConditionValues() {
        viewModelScope.launch {
            _conditionValueStateFlow.emit(false)
        }
    }

    companion object {
        const val DEFAULT_VALUE = "선택"
    }

}