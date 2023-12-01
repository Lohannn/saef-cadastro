package br.senai.sp.jandira.saf_projeto_senai.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.saf_projeto_senai.R

@Composable
fun TextBox(
    label : String,
    valor : String,
    icon : Painter,
    aoMudar: (it: String) -> Unit
) {
    OutlinedTextField(
        value = valor,
        onValueChange = {
            aoMudar(it)
        },
        label = {
            Text(text = label,
                style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold))
        },
        colors = TextFieldDefaults
            .colors(unfocusedContainerColor = colorResource(id = R.color.dark_blue),
                unfocusedTextColor = colorResource(id = R.color.emerald_green),
                focusedTextColor = colorResource(id = R.color.dark_blue),
                focusedContainerColor = colorResource(id = R.color.emerald_green),
                unfocusedIndicatorColor = colorResource(id = R.color.emerald_green),
                focusedIndicatorColor = colorResource(id = R.color.dark_blue),
                focusedLeadingIconColor = colorResource(id = R.color.dark_blue),
                unfocusedLeadingIconColor = colorResource(id = R.color.emerald_green)),
        textStyle = TextStyle(fontSize = 20.sp),
        leadingIcon = {
            Icon(painter = icon, contentDescription = "")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TextBoxPreview() {
    TextBox(
        label = "Email",
        valor = "",
        icon = painterResource(id = R.drawable.email_icon),
        aoMudar = {})
}