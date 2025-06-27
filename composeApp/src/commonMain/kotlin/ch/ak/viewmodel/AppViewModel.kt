package ch.ak.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppViewModel {
    // Or directly use MutableStateFlow<Boolean>(false)
    // val isShowingContent = MutableStateFlow(false)

    private val _isShowingContent = MutableStateFlow(false)
    val isShowingContent: StateFlow<Boolean> = _isShowingContent

    fun toggleContentVisibility() {
        _isShowingContent.value = !_isShowingContent.value
    }
}
