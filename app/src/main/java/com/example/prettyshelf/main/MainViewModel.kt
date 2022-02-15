package com.example.prettyshelf.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prettyshelf.ISBNResponse
import com.example.prettyshelf.data.OpenLibraryApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val openLibraryApi: OpenLibraryApi) : ViewModel() {

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