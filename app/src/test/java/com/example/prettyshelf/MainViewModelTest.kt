package com.example.prettyshelf

import io.mockk.coEvery
import io.mockk.verify
import org.junit.Test

class MainViewModelTest {

    private val testSubject = MainViewModel()

    @Test
    fun `can get book`() {

        testSubject.getBook("isbn")

    }

}