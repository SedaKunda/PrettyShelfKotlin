package com.example.prettyshelf.data

import com.example.prettyshelf.ISBNResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenLibraryApi {
    @GET("isbn/{isbn}")
    suspend fun fetchISBN(@Path("isbn") isbn: String): ISBNResponse
}