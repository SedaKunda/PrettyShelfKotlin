package com.example.prettyshelf.ui.screens.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.prettyshelf.PrettyShelfApplication
import com.example.prettyshelf.databinding.ActivityMainBinding
import javax.inject.Inject

class ISBNSearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var isbnSearchViewModel: ISBNSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as PrettyShelfApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setUpClickListeners()
        setupViewModelListeners()
    }

    private fun setUpClickListeners() {
        with(binding) {
            searchButton.setOnClickListener {
                val input = searchBar.text.toString()
                isbnSearchViewModel.getBookTitleAndCategory(input)
            }
        }
    }

    private fun setupViewModelListeners() {
        isbnSearchViewModel.isbnResultLiveData.observe(this) { result ->
            if (result.isbnResponse != null) {
                binding.bookTitle.text = result.isbnResponse.title
                binding.category.text = result.isbnResponse.subjects?.toList().toString()

                if (result.showResponse) {
                    showBookView()
                }
            } //todo else error screen
        }
    }

    private fun showBookView() {
        with(binding) {
            bookTitle.isVisible = true
            bookTitleHeader.isVisible = true
            category.isVisible = true
            categoryTitleHeader.isVisible = true
        }
    }
}