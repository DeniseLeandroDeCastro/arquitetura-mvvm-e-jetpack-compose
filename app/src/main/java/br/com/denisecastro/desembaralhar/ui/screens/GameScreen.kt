package br.com.denisecastro.desembaralhar.ui.screens

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.denisecastro.desembaralhar.R
import br.com.denisecastro.desembaralhar.ui.theme.DesembaralharTheme

@Composable
fun GameScreen() {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(mediumPadding)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(300.dp)
            )
            GameLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(mediumPadding)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mediumPadding),
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { }
            ) {
                Text(
                    text = stringResource(R.string.submit),
                    fontSize = 16.sp,
                )
            }
            OutlinedButton(
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.skip),
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            GameStatus(score = 0, modifier = Modifier.padding(20.dp))
        }
    }
}

@Composable
fun GameStatus(score: Int, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Text(
            text = stringResource(R.string.score, score),
            style = typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun GameLayout(modifier: Modifier = Modifier) {
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
                text = stringResource(R.string.word_count, 0),
                style = typography.titleMedium,
                color = colorScheme.onPrimary
            )
            Text(
                text = stringResource(R.string.instructions),
                textAlign = TextAlign.Center,
                style = typography.titleLarge
            )
            OutlinedTextField(
                value = "",
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                onValueChange = { },
                label = { Text(stringResource(R.string.enter_your_word)) },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { }
                )
            )
        }
    }
}

@Composable
private fun FinalScoreDialog(
    score: Int,
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
                    text = stringResource(R.string.you_scored),
                    style = typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "$score",
                    style = typography.displayMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.primary
                    ),
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
        GameScreen()
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
