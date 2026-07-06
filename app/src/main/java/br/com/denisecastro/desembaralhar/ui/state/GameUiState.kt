package br.com.denisecastro.desembaralhar.ui.state

data class GameUiState(
    val currentScrambledWord: String = "",
    val currentWordCount: Int = 1,
    val isGuessedWordWrong: Boolean = false,
    val isGameOver: Boolean = false,
    val score: Int = 0
)
