package com.example.prettyshelf.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prettyshelf.ISBNResponse
import com.example.prettyshelf.di.DaggerApplicationComponent
import com.example.prettyshelf.main.ui.theme.PrettyShelfTheme
import javax.inject.Inject

class MainActivityCompose : ComponentActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    private lateinit var response: ISBNResponse
    private var showResponse = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mainViewModel = daggerViewModel {
                DaggerApplicationComponent.builder().build().getViewModel() //todo refactor
            }
            SetUpObservers()
            PrettyShelfTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AddForm(onSearchPressed = { isbn -> mainViewModel.getBookTitleAndCategory(isbn) })
                    if (showResponse) {
                        AddFormResponse(response = response)
                    }
                }
            }
        }
    }

    @Composable
    private fun SetUpObservers() {
        mainViewModel.isbnResultLiveData.observe(this) {
            response = it.isbnResponse
            showResponse = it.showResponse
        }
    }

    //todo refactor
    @Composable
    inline fun daggerViewModel(
        key: String? = null,
        crossinline viewModelInstanceCreator: () -> MainViewModel
    ): MainViewModel =
        androidx.lifecycle.viewmodel.compose.viewModel(
            modelClass = MainViewModel::class.java,
            key = key,
            factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return viewModelInstanceCreator() as T
                }
            }
        )
}

@Composable
fun AddForm(
    onSearchPressed: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Row {
            val isbnText = remember { mutableStateOf("") }
            OutlinedTextField(
                value = isbnText.value,
                label = { Text("ISBN") },
                onValueChange = { isbnText.value = it }
            )
            Spacer(modifier = Modifier.size(4.dp))
            Button(
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = { onSearchPressed(isbnText.value) }) { //todo (not searching)
                Text(text = "Search")
            }
        }
        Spacer(modifier = Modifier.size(4.dp))
    }
}

@Composable
fun AddFormResponse(response: ISBNResponse) {
    Column {
        Text("Title: ${response.title}")
        Text("Classification: ${response.subjects.toString()}")
        Text("Publisher: ${response.publishers}")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PrettyShelfTheme {
        AddForm(onSearchPressed = {  })
    }
}