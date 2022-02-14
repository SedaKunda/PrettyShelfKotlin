package com.example.prettyshelf

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val openLibraryApi: OpenLibraryApi) : ViewModel() {

    val isbnSearchResult: MutableLiveData<Response> by lazy {
        MutableLiveData<Response>()
    }

    fun getBookTitleAndCategory(isbn: String) {
        viewModelScope.launch {
            val response = openLibraryApi.fetchISBN("$isbn.json")
            isbnSearchResult.postValue(Response(
                isbnResponse = response,
                showResponse = true
            ))
        }
    }

    data class Response(
        val isbnResponse: ISBNResponse,
        val showResponse: Boolean
    )
}