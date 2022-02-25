package com.example.prettyshelf.ui.screens.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prettyshelf.domain.ISBNResponse
import com.example.prettyshelf.networking.OpenLibraryApi
import com.example.prettyshelf.ui.screens.shared.ErrorState
import com.example.prettyshelf.ui.screens.shared.getErrorState
import kotlinx.coroutines.launch
import javax.inject.Inject

class ISBNSearchViewModel @Inject constructor(private val openLibraryApi: OpenLibraryApi) :
    ViewModel() {

    val isbnResultLiveData: MutableLiveData<Response> by lazy {
        MutableLiveData<Response>()
    }

    fun getBookTitleAndCategory(isbn: String) {
        viewModelScope.launch {
            runCatching { openLibraryApi.fetchISBN("$isbn.json") }
                .onSuccess { response ->
                    isbnResultLiveData.postValue(
                        Response(
                            isbnResponse = response,
                            showResponse = true
                        )
                    )
                }
                .onFailure { error ->
                    val errorResponse = Response(errorState = error.getErrorState())
                    isbnResultLiveData.postValue(errorResponse)
                }
        }
    }

    data class Response(
        val isbnResponse: ISBNResponse? = null,
        val showResponse: Boolean = false,
        val errorState: ErrorState? = null
    )
}