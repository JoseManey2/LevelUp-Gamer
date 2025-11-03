package com.example.levelup_gamer.ui.settings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SettingsUiState(
    val notificationsEnabled: Boolean = true,
    val darkModeEnabled: Boolean = true,
    val selectedLanguage: String = "Español",
    val showLogoutConfirmDialog: Boolean = false
)

class SettingsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    fun onNotificationsToggled(isEnabled: Boolean) {
        _uiState.update { it.copy(notificationsEnabled = isEnabled) }
    }

    fun onDarkModeToggled(isEnabled: Boolean) {
        // TODO: Implement dark mode logic
        _uiState.update { it.copy(darkModeEnabled = isEnabled) }
    }

    fun onLanguageSelected(language: String) {
        _uiState.update { it.copy(selectedLanguage = language) }
    }

    fun onLogoutClicked() {
        _uiState.update { it.copy(showLogoutConfirmDialog = true) }
    }

    fun onDismissLogoutDialog() {
        _uiState.update { it.copy(showLogoutConfirmDialog = false) }
    }
}