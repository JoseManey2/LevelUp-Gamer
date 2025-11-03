package com.example.levelup_gamer.ui.UserProfile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class UserProfileUiState(
    val username: String = "",
    val email: String = "",
    val name: String = "",
    val lastName: String = ""
)

class UserProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        // TODO: Load real user data from a repository
        _uiState.update {
            it.copy(
                username = "GamerX123",
                email = "usuario@example.com",
                name = "Juan",
                lastName = "Pérez"
            )
        }
    }
}