package com.exemplo.curiosidadegatos

import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // --- Configuração do Vídeo ---
        val videoView = findViewById<VideoView>(R.id.videoView)
        // URL de um vídeo MP4 público (exemplo direto)
        val videoUri = Uri.parse("https://www.w3schools.com/html/mov_bbb.mp4")
        videoView.setVideoURI(videoUri)

        // Adiciona controles de Play/Pause conforme requisito 3.1 [4]
        val mc = MediaController(this)
        mc.setAnchorView(videoView)
        videoView.setMediaController(mc)
        videoView.start()

        // --- Configuração do Retrofit ---
        val retrofit = Retrofit.Builder()
            .baseUrl("https://viacep.com.br/ws/") // URL base
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(MinhaApi::class.java)

        // --- Botão Curiosidade ---
        findViewById<Button>(R.id.btnCarregarDados).setOnClickListener {
            lifecycleScope.launch {
                try {
                    val response = api.obterCuriosidade()
                    findViewById<TextView>(R.id.txtCuriosidade).text = response.fact
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Erro ao carregar curiosidade", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // --- Botão Pesquisar CEP [2, 6] ---
        findViewById<Button>(R.id.btnPesquisarCep).setOnClickListener {
            val cep = findViewById<EditText>(R.id.editCep).text.toString().trim()

            if (cep.length != 8) {
                findViewById<TextView>(R.id.txtResultadoCep).text = "CEP inválido! Digite 8 números."
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val endereco = api.pesquisarCep(cep)
                    if (endereco.cep == null) {
                        findViewById<TextView>(R.id.txtResultadoCep).text = "CEP não encontrado!"
                    } else {
                        findViewById<TextView>(R.id.txtResultadoCep).text =
                            "Endereço: ${endereco.logradouro}\nBairro: ${endereco.bairro}\nCidade: ${endereco.localidade} - ${endereco.uf}"
                    }
                } catch (e: Exception) {
                    findViewById<TextView>(R.id.txtResultadoCep).text = "Erro de conexão!"
                }
            }
        }
    }
}