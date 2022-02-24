package com.example.prettyshelf.ui.screens.shared

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import javax.inject.Inject

class ViewModelFactory<VM : ViewModel> @Inject constructor(private val viewModel: dagger.Lazy<VM>) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        viewModel.get() as T
}

@Composable
inline fun <reified T : ViewModel> assistedViewModel(
    key: String? = null,
    crossinline viewModelInstanceCreator: () -> T
): T =
    viewModel(
        modelClass = T::class.java,
        key = key,
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return viewModelInstanceCreator() as T
            }
        }
    )
