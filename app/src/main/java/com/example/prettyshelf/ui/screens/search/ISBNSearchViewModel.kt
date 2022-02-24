package com.example.prettyshelf.ui.screens.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prettyshelf.domain.ISBNResponse
import com.example.prettyshelf.networking.OpenLibraryApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class ISBNSearchViewModel @Inject constructor(private val openLibraryApi: OpenLibraryApi) :
    ViewModel() {

    val isbnResultLiveData: MutableLiveData<Response> by lazy {
        MutableLiveData<Response>()
    }

    fun getBookTitleAndCategory(isbn: String) {
        viewModelScope.launch {
            val response = openLibraryApi.fetchISBN("$isbn.json")
            isbnResultLiveData.postValue(
                Response(
                isbnResponse = response,
                showResponse = true
            )
            )
        }
    }

    data class Response(
        val isbnResponse: ISBNResponse,
        val showResponse: Boolean
    )
}