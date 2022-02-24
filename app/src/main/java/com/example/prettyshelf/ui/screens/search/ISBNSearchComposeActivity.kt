package com.example.prettyshelf.ui.screens.search

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prettyshelf.appComponent
import com.example.prettyshelf.domain.ISBNResponse
import com.example.prettyshelf.ui.screens.shared.LoadingIndicator
import com.example.prettyshelf.ui.screens.shared.assistedViewModel
import com.example.prettyshelf.ui.theme.PrettyShelfTheme
import javax.inject.Inject

class ISBNSearchComposeActivity : ComponentActivity() {

    @Inject
    lateinit var isbnSearchViewModel: ISBNSearchViewModel

    private lateinit var response: ISBNResponse
    private lateinit var shouldShowResponse: MutableState<Boolean>
    private lateinit var isLoading: MutableState<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContent {
            InitViewModel()
            SetUpObservers(isbnSearchViewModel)
            PrettyShelfTheme {
                ISBNSearchScreen()
            }
        }
    }

    @Composable
    private fun InitViewModel() {
        isbnSearchViewModel = assistedViewModel { appComponent.getISBNViewModel() }
    }

    @Composable
    fun SetUpObservers(
        viewModel: ISBNSearchViewModel = viewModel()
    ) {
        val responseAsState = viewModel.isbnResultLiveData.observeAsState()
        responseAsState.value?.let {
            shouldShowResponse.value = it.showResponse
            response = it.isbnResponse
            isLoading.value = false
        }
    }

    @Composable
    private fun ISBNSearchScreen() {
        shouldShowResponse = remember { mutableStateOf(false) }
        isLoading = remember { mutableStateOf(false) }
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
                AddForm(onSearchPressed = { isbn ->
                    isbnSearchViewModel.getBookTitleAndCategory(isbn)
                    isLoading.value = true
                })
                Spacer(modifier = Modifier.size(4.dp))
                AddFormResponse(shouldShowResponse)
                LoadingIndicator(isLoading = isLoading) {}
            }
        }
    }

    @Composable
    fun AddForm(
        onSearchPressed: (String) -> Unit
    ) {
        Column {
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PrettyShelfTheme {
        Column {
            ISBNSearchComposeActivity().AddForm(onSearchPressed = { })
            Spacer(modifier = Modifier.size(4.dp))
            LoadingIndicator(isLoading = mutableStateOf(true)) {

            }
        }
    }
}