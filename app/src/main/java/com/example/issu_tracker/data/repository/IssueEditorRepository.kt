package com.example.issu_tracker.data.repository

import com.example.issu_tracker.data.Issue
import com.example.issu_tracker.data.Label
import com.example.issu_tracker.data.User

interface IssueEditorRepository {

    suspend fun loadLabel(): List<Label>
    suspend fun loadAssignee(): List<User>
    fun createNewIssue(newIssue: Issue)
    suspend fun uploadImageAndGetImageUriFromFireBase(uri: String): String

}