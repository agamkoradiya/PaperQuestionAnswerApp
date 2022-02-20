package com.example.paperquestionanswerapp.util

/**
 * Created by Agam on 16-01-2022.
 */

inline fun <T> safeCall(action: () -> Resource<T>): Resource<T> {
    return try {
        action()
    } catch (e: Exception) {
        Resource.Error(e.message ?: "An unknown error found.")
    }
}