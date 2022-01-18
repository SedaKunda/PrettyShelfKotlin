package com.example.prettyshelf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    fun getBook(isbn: String) {
        val apiService = NetworkModule().makeRetrofitService()

        viewModelScope.launch {
            val response = apiService.fetchISBN("$isbn.json")
            println(response.title)
        }
    }
}