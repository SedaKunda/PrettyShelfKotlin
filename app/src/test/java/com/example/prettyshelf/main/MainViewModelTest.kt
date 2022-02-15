package com.example.prettyshelf.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.prettyshelf.ISBNResponse
import com.example.prettyshelf.main.MainViewModel.Response
import com.example.prettyshelf.Identifiers
import com.example.prettyshelf.Type
import com.example.prettyshelf.data.OpenLibraryApi
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val openLibraryApi = mockk<OpenLibraryApi>()
    private val testSubject = MainViewModel(openLibraryApi)
    private val isbnSearchResult = mockk<Observer<Response>>()
    private val emptyIsbnResponse = ISBNResponse(
        title = "",
        subjects = listOf(),
        authors = listOf(),
        covers = listOf(),
        identifiers = Identifiers(listOf(), listOf()),
        isbn10 = listOf(),
        isbn13 = listOf(),
        key = "",
        languages = listOf(),
        latestRevision = 0,
        lcClassifications = listOf(),
        lccn = listOf(),
        numberOfPages = 0,
        ocaid = "",
        oclcNumbers = listOf(),
        physicalDimensions = "",
        physicalFormat = "",
        publishDate = "",
        publishers = listOf(),
        revision = 0,
        sourceRecords = listOf(),
        type = Type(""),
        weight = "",
        works = listOf()
    )

    @Test
    fun `can get book`() {
        testSubject.isbnResultLiveData.observeForever(isbnSearchResult)

        coEvery { openLibraryApi.fetchISBN(any()) } returns emptyIsbnResponse.copy(title = "some title")

        assertNull(testSubject.isbnResultLiveData.value)

        testSubject.getBookTitleAndCategory("9781942788973")

        verify { isbnSearchResult.onChanged(Response(emptyIsbnResponse.copy(title = "some title"), true)) }
    }

}