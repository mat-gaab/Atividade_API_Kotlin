package com.exemplo.curiosidadegatos

import retrofit2.http.GET
import retrofit2.http.Path

interface MinhaApi {
    // Endpoint para curiosidades
    @GET("https://catfact.ninja/fact")
    suspend fun obterCuriosidade(): CuriosidadeResponse

    // Endpoint para ViaCEP
    @GET("https://viacep.com.br/ws/{cep}/json/")
    suspend fun pesquisarCep(@Path("cep") cep: String): CepResponse
}