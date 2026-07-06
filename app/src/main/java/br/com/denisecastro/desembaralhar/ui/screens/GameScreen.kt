package br.com.denisecastro.desembaralhar.ui.screens

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.denisecastro.desembaralhar.R
import br.com.denisecastro.desembaralhar.ui.state.GameUiState
import br.com.denisecastro.desembaralhar.ui.theme.DesembaralharTheme
import br.com.denisecastro.desembaralhar.ui.viewmodel.GameViewModel

@Composable
fun GameScreen() {
    val gameViewModel: GameViewModel = viewModel()
    val gameUiState by gameViewModel.uiState.collectAsState()

    GameScreenContent(
        uiState = gameUiState,
        userGuess = gameViewModel.userGuess,
        onUserGuessChanged = { gameViewModel.updateUserGuess(it) },
        onSubmitWord = { gameViewModel.checkUserGuess() },
        onSkipWord = { gameViewModel.skipWord() },
        onPlayAgain = { gameViewModel.resetGame() } // AJUSTADO: Passando a ação do ViewModel
    )
}

@Composable
fun GameScreenContent(
    uiState: GameUiState,
    userGuess: String = "",
    onUserGuessChanged: (String) -> Unit = {},
    onSubmitWord: () -> Unit = {},
    onSkipWord: () -> Unit = {},
    onPlayAgain: () -> Unit = {}, // AJUSTADO: Adicionado parâmetro para repassar o clique
    modifier: Modifier = Modifier
) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(mediumPadding),
        verticalArrangement = Arrangement.spacedBy(mediumPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.size(240.dp)
        )
        GameLayout(
            currentScrambledWord = uiState.currentScrambledWord,
            userGuess = userGuess,
            onUserGuessChanged = onUserGuessChanged,
            onKeyboardDone = { onSubmitWord() },
            wordCount = uiState.currentWordCount,
            isGuessWrong = uiState.isGuessedWordWrong
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mediumPadding),
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.isGameOver) {
                FinalScoreDialog(
                    score = uiState.score, // AJUSTADO: Agora a assinatura aceita o score
                    onPlayAgain = onPlayAgain // AJUSTADO: Usando o lambda correto
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onSubmitWord() }
            ) {
                Text(
                    text = stringResource(R.string.submit),
                    fontSize = 16.sp,
                )
            }
            OutlinedButton(
                onClick = onSkipWord,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.skip),
                    fontSize = 16.sp
                )
            }
        }
        GameStatus(score = uiState.score, modifier = Modifier.padding(20.dp))
    }
}

@Composable
fun GameStatus(score: Int, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Text(
            text = stringResource(R.string.score, score),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun GameLayout(
    currentScrambledWord: String,
    userGuess: String,
    onUserGuessChanged: (String) -> Unit,
    onKeyboardDone: () -> Unit,
    wordCount: Int,
    isGuessWrong: Boolean,
    modifier: Modifier = Modifier
) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(mediumPadding)
        ) {
            Text(
                modifier = Modifier
                    .clip(shapes.medium)
                    .background(colorScheme.surfaceTint)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(alignment = Alignment.End),
                text = stringResource(R.string.word_count, wordCount),
                style = typography.titleMedium,
                color = colorScheme.onPrimary
            )
            Text(
                text = currentScrambledWord,
                fontSize = 45.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = stringResource(R.string.instructions),
                textAlign = TextAlign.Center,
                style = typography.titleLarge
            )
            OutlinedTextField(
                value = userGuess,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                onValueChange = onUserGuessChanged,
                label = {
                    if (isGuessWrong) {
                        Text(stringResource(R.string.wrong_guess))
                    } else {
                        Text(stringResource(R.string.enter_your_word))
                    }
                },
                isError = isGuessWrong,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                )
            )
        }
    }
}

@Composable
private fun FinalScoreDialog(
    score: Int, // AJUSTADO: Readicionado parâmetro score
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = LocalActivity.current

    AlertDialog(
        onDismissRequest = {},
        modifier = modifier,
        icon = {
            Image(
                painter = painterResource(R.drawable.trophy),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
        },
        title = {
            Text(
                text = stringResource(R.string.congratulations),
                style = typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.you_scored, score), // AJUSTADO: Passando score para a string formatada
                    style = typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = { activity?.finish() }
            ) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {
            Button(
                onClick = onPlayAgain
            ) {
                Text(text = stringResource(R.string.play_again))
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun GameScreenPreview() {
    DesembaralharTheme {
        GameScreenContent(
            uiState = GameUiState(currentScrambledWord = "RVALAPA")
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun FinalScoreDialogPreview() {
    DesembaralharTheme {
        FinalScoreDialog(
            score = 10,
            onPlayAgain = {}
        )
    }
}