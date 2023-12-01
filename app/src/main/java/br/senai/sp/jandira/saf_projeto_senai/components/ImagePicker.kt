package br.senai.sp.jandira.saf_projeto_senai.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.senai.sp.jandira.saf_projeto_senai.R
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberImagePainter
import coil.size.Scale

@Composable
fun ImagePicker(
    onImageSelected: (Uri?) -> Unit
) {
    var selectedImage: Uri? by remember { mutableStateOf(null) }

    // Contrato de resultado para obter a imagem selecionada
    val getContent = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        // Verifique se a URI não é nula antes de atualizar a imagem
        uri?.let {
            selectedImage = it
            onImageSelected(it)
        }
    }

    Row (
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy((-35).dp),
        modifier = Modifier.clickable { getContent.launch("image/*") }
        ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(shape = CircleShape, color = Color.Transparent)
                .border(
                    shape = CircleShape,
                    color = colorResource(id = R.color.emerald_green),
                    width = 2.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = if (selectedImage != null) {
                    rememberImagePainter(
                        data = selectedImage,
                        builder = {
                            scale(Scale.FIT)
                            // Outras configurações do carregamento da imagem, se necessário
                        }
                    )
                } else {
                    painterResource(id = R.drawable.default_icon)
                },
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(colorResource(id = R.color.emerald_green))
                    .border(
                        shape = CircleShape,
                        color = Color.Transparent,
                        width = 2.dp
                    )
                    .background(colorResource(id = R.color.emerald_green))
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.add_image),
            contentDescription = "",
            modifier = Modifier
                .size(35.dp)
                .background(
                    color = colorResource(R.color.gray),
                    shape = CircleShape,
                ),
            tint = colorResource(R.color.dark_blue)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ImagePickerPreview() {
    ImagePicker({})
}