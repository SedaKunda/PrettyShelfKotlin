package com.example.prettyshelf.networking

import com.example.prettyshelf.domain.ISBNResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface OpenLibraryApi {
    @GET("isbn/{isbn}")
    suspend fun fetchISBN(@Path("isbn") isbn: String): ISBNResponse
}