package com.example.prettyshelf.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prettyshelf.ISBNResponse
import com.example.prettyshelf.di.DaggerApplicationComponent
import com.example.prettyshelf.main.ui.theme.PrettyShelfTheme
import javax.inject.Inject

class MainActivityCompose : ComponentActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    private lateinit var response: ISBNResponse
    private lateinit var shouldShowResponse: MutableState<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InitViewModel()
            SetUpObservers(mainViewModel)
            PrettyShelfTheme {
                ISBNSearchScreen()
            }
        }
    }

    @Composable
    private fun InitViewModel() {
        mainViewModel = assistedViewModel {
            DaggerApplicationComponent.builder().build().getViewModel() //todo refactor
        }
    }

    @Composable
    private fun ISBNSearchScreen() {
        shouldShowResponse = remember { mutableStateOf(false) }
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                AddForm(onSearchPressed = { isbn -> mainViewModel.getBookTitleAndCategory(isbn) })
                AddFormResponse(shouldShowResponse)
            }
        }
    }

    @Composable
    fun SetUpObservers(
        viewModel: MainViewModel = viewModel()
    ) {
        val responseAsState = viewModel.isbnResultLiveData.observeAsState()
        responseAsState.value?.let {
            shouldShowResponse.value = it.showResponse
            response = it.isbnResponse
        }
    }

    @Composable
    fun LoadingScreen(
        isLoading: Boolean,
        content: @Composable () -> Unit
    ) = if (isLoading
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Loading")
                CircularProgressIndicator()
            }
        }
    } else {
        content()
    }

    //todo refactor
    @Composable
    inline fun assistedViewModel(
        key: String? = null,
        crossinline viewModelInstanceCreator: () -> MainViewModel
    ): MainViewModel =
        viewModel(
            modelClass = MainViewModel::class.java,
            key = key,
            factory = object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return viewModelInstanceCreator() as T
                }
            }
        )

    @Composable
    fun AddFormResponse(shouldShowResponse: MutableState<Boolean>) {
        if (shouldShowResponse.value) {
            Column {
                Text("Title: ${response.title}")
                Text("Classification: ${response.subjects.toString()}")
                Text("Publisher: ${response.publishers}")
            }
        }
    }
}

@Composable
fun AddForm(
    onSearchPressed: (String) -> Unit
) {
    Column() {
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
                onClick = { onSearchPressed(isbnText.value) }) {
                Text(text = "Search")
            }
        }
        Spacer(modifier = Modifier.size(4.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PrettyShelfTheme {
        Column {
            AddForm(onSearchPressed = { })
            Spacer(modifier = Modifier.size(4.dp))
            Column {
                Text("test")
            }
        }
    }
}