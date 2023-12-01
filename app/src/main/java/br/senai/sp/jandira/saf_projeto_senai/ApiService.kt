package br.senai.sp.jandira.saf_projeto_senai

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/")
    suspend fun signUp(@Body request: CadastroUsuario): Response<JsonObject>
}