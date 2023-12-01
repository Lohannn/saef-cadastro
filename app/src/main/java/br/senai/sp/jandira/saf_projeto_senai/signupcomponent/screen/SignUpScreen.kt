package br.senai.sp.jandira.saf_projeto_senai.signupcomponent.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.senai.sp.jandira.saf_projeto_senai.signupcomponent.components.Form

@Composable
fun SignUp() {
        Form()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpScreenPreview() {
    SignUp()
}