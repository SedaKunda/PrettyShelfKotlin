package com.example.prettyshelf.ui.screens.shared

import retrofit2.HttpException

fun Throwable.getErrorState(): ErrorState {
    val errorState = when (this) {
        is HttpException -> {
            when (this.code()) {
                400 -> ErrorState.BAD_REQUEST
                403 -> ErrorState.FORBIDDEN
                404 -> ErrorState.NOT_FOUND
                500 -> ErrorState.INTERNAL_SERVER_ERROR
                else -> ErrorState.UNKNOWN
            }
        }
        else -> ErrorState.UNKNOWN
    }
    return errorState
}

enum class ErrorState {
    NOT_FOUND, BAD_REQUEST, INTERNAL_SERVER_ERROR, FORBIDDEN, UNKNOWN
}