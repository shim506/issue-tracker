package com.example.issu_tracker.data.repository

import android.util.Log
import com.example.issu_tracker.data.Issue
import com.example.issu_tracker.data.IssueDto
import com.example.issu_tracker.data.toIssue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val fireStore: FirebaseFirestore) :
    HomeRepository {
    override suspend fun loadIssues(): List<Issue> {
        val list = mutableListOf<Issue>()
        val collectionData = fireStore.collection(FIREBASE_COLLECTION_PATH).get().await()

        collectionData.documents.forEach {
            val issueObj = it.toObject(IssueDto::class.java)
            issueObj?.id = it.id
            issueObj?.let { it1 ->
                it1.toIssue()?.let { it2 -> list.add(it2) }
                // 데이터를 추가하는 코드
                // fireStore.collection(FIREBASE_COLLECTION_PATH).document().set(it1)
            }
        }
        return list
    }

    override suspend fun updateIssueState(itemId: String, boolean: Boolean) {
//delete 문
//        fireStore.collection(FIREBASE_COLLECTION_PATH).document(itemId)
//            .delete().await()

        fireStore.collection(FIREBASE_COLLECTION_PATH).document(itemId)
            .update("state", false).await()

    }

    override suspend fun updateIssueState(list: List<Issue>, boolean: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteIssueList(itemId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateIssueList(list: List<Issue>, boolean: Boolean) {
        TODO("Not yet implemented")
    }

    companion object {
        const val FIREBASE_COLLECTION_PATH = "Issue"
    }
}
