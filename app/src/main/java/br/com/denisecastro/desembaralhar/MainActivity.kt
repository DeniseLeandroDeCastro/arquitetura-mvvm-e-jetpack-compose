package br.com.denisecastro.desembaralhar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.denisecastro.desembaralhar.ui.screens.GameScreen
import br.com.denisecastro.desembaralhar.ui.screens.GameScreenContent
import br.com.denisecastro.desembaralhar.ui.state.GameUiState
import br.com.denisecastro.desembaralhar.ui.theme.DesembaralharTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DesembaralharTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    GameScreen()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainActivityPreview() {
    DesembaralharTheme {
        GameScreenContent(
            uiState = GameUiState(currentScrambledWord = "RVALAPA")
        )
    }
}
