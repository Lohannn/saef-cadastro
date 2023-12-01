package br.senai.sp.jandira.saf_projeto_senai.signupcomponent.components

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.senai.sp.jandira.saf_projeto_senai.ApiService
import br.senai.sp.jandira.saf_projeto_senai.CadastroUsuario
import br.senai.sp.jandira.saf_projeto_senai.R
import br.senai.sp.jandira.saf_projeto_senai.components.ImagePicker
import br.senai.sp.jandira.saf_projeto_senai.components.TextBox
import coil.compose.rememberImagePainter
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.UUID
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

@Composable
fun Form(){
    var context = LocalContext.current
    var emailState by remember { mutableStateOf("") }
    var senhaState by remember { mutableStateOf("") }
    var selectedImage: Uri? by remember { mutableStateOf(null) }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .height(350.dp)
            .fillMaxWidth()){
        ImagePicker(
            onImageSelected = {
                selectedImage = it
            }
        )

        Column {
            TextBox(
                label = "Login",
                valor = emailState,
                icon = painterResource(id = R.drawable.email_icon),
                aoMudar = {
                    emailState = it
                })

            TextBox(
                label = "Password",
                valor = senhaState,
                icon = painterResource(id = R.drawable.password_icon),
                aoMudar = {
                    senhaState = it
                })
        }

        Button(
            onClick = {
                val storageRef = FirebaseStorage.getInstance().reference.child("images")
                val firebaseFirestore = FirebaseFirestore.getInstance()

                selectedImage?.let { it ->
                    val riversRef = storageRef.child("${it.lastPathSegment}-${System.currentTimeMillis()}.jpg")
                    val uploadTask = riversRef.putFile(it)
                    uploadTask.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            riversRef.downloadUrl.addOnSuccessListener { uri ->
                                val imageUrl = uri.toString()

                                // Criar instância do Retrofit
                                val retrofit = Retrofit.Builder()
                                    .baseUrl("http://localhost:3000")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()

                                // Criar instância do serviço da API
                                val apiService = retrofit.create(ApiService::class.java)

                                // Criar objeto SignUpRequest com os dados necessários
                                val signUpRequest = CadastroUsuario(emailState, senhaState, imageUrl)

                                // Fazer a chamada à API
                                val scope = CoroutineScope(Dispatchers.Main) // Pode ser substituído por lifecycleScope se estiver dentro de um componente do ciclo de vida

                                scope.launch {
                                    try {
                                        val response = apiService.signUp(signUpRequest)

                                        if (response.isSuccessful) {
                                            // Lidar com a resposta da API
                                            Toast.makeText(context, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                                        } else {
                                            // Lidar com falha na resposta da API
                                            Toast.makeText(context, "Erro na resposta da API", Toast.LENGTH_SHORT).show()
                                        }
                                    } catch (e: IOException) {
                                        // Lidar com exceções de IO (por exemplo, falha de conexão)
                                        Log.e("TAG", "Erro de IO: ${e.message}")
                                        Toast.makeText(context, "Erro de IO", Toast.LENGTH_SHORT).show()
                                    } catch (e: Exception) {
                                        // Lidar com outras exceções
                                        Log.e("TAG", "Erro desconhecido: ${e.message}")
                                        Toast.makeText(context, "Erro desconhecido", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        } else {
                            // Lidar com falha no upload da imagem
                            Toast.makeText(context, "Erro no upload da imagem", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.emerald_green),
                contentColor = colorResource(id = R.color.dark_blue)
            ),
            modifier = Modifier.width(280.dp)
        ) {
            Text(text = "Cadastrar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormPreview() {
    Form()
}