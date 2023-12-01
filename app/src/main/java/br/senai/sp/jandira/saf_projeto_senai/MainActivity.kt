package br.senai.sp.jandira.saf_projeto_senai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import br.senai.sp.jandira.saf_projeto_senai.signupcomponent.screen.SignUp
import br.senai.sp.jandira.saf_projeto_senai.ui.theme.SAFProjetoSenaiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SAFProjetoSenaiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignUp()
                }
            }
        }
    }
}