package com.example.prettyshelf.ui.screens.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingIndicator(
    isLoading: MutableState<Boolean>,
    content: @Composable () -> Unit
) = if (isLoading.value) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Searching...")
            CircularProgressIndicator()
        }
    }
} else {
    content()
}

@Composable
fun ErrorIndicator(
    hasError: MutableState<Boolean>,
    errorType: MutableState<ErrorState>
) {
    if (hasError.value) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "There's been a problem")
                when (errorType.value) {
                    ErrorState.NOT_FOUND -> Text(text = "It was not found")
                    ErrorState.BAD_REQUEST -> Text(text = "something")
                    ErrorState.INTERNAL_SERVER_ERROR -> Text(text = "something else")
                    ErrorState.FORBIDDEN -> Text(text = "oh no")
                    ErrorState.UNKNOWN -> Text(text = "oh no no no no no")
                }
            }
        }
    }
}
