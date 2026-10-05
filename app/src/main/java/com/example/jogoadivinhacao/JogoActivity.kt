package com.example.jogoadivinhacao

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
// Android Studio 3.0/3.1 (sem AndroidX): troque a linha acima por
// import android.support.v7.app.AppCompatActivity
import java.util.Random

class JogoActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOME = "nome"
        const val EXTRA_MAXIMO = "maximo"
        private const val STATE_SECRETO = "secreto"
        private const val STATE_TENTATIVAS = "tentativas"
    }

    private var nome = "Jogador"
    private var maximo = 50
    private var secreto = 0
    private var tentativas = 0

    private lateinit var txtBoasVindas: TextView
    private lateinit var edtPalpite: EditText
    private lateinit var btnChutar: Button
    private lateinit var txtDica: TextView
    private lateinit var txtTentativas: TextView
    private lateinit var btnJogarNovamente: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo)

        txtBoasVindas = findViewById(R.id.txtBoasVindas)
        edtPalpite = findViewById(R.id.edtPalpite)
        btnChutar = findViewById(R.id.btnChutar)
        txtDica = findViewById(R.id.txtDica)
        txtTentativas = findViewById(R.id.txtTentativas)
        btnJogarNovamente = findViewById(R.id.btnJogarNovamente)

        // Recebe os dados enviados pela MainActivity
        nome = intent.getStringExtra(EXTRA_NOME) ?: "Jogador"
        maximo = intent.getIntExtra(EXTRA_MAXIMO, 50)

        // Sorteia entre 1 e maximo (inclusive). Preserva o número ao girar a tela.
        secreto = savedInstanceState?.getInt(STATE_SECRETO)
            ?: (Random().nextInt(maximo) + 1)
        tentativas = savedInstanceState?.getInt(STATE_TENTATIVAS) ?: 0

        txtBoasVindas.text = "$nome, pensei num número de 1 a $maximo!"
        txtTentativas.text = "Tentativas: $tentativas"

        btnChutar.setOnClickListener { chutar() }
        btnJogarNovamente.setOnClickListener { finish() } // volta à primeira tela
    }

    private fun chutar() {
        val palpite = edtPalpite.text.toString().toIntOrNull()
        if (palpite == null || palpite < 1 || palpite > maximo) {
            edtPalpite.error = "Digite um número de 1 a $maximo"
            return
        }

        tentativas++
        txtTentativas.text = "Tentativas: $tentativas"
        edtPalpite.text.clear()

        if (palpite == secreto) {
            venceu()
        } else {
            mostrarDica(palpite)
        }
    }

    private fun mostrarDica(palpite: Int) {
        val diferenca = Math.abs(palpite - secreto)
        val clima = if (diferenca <= maximo * 0.10) "Quente" else "Frio"
        val direcao = if (secreto > palpite) "MAIOR" else "MENOR"
        txtDica.text = "$clima! O número é $direcao."
    }

    // Desafio: mensagem final + botão "Jogar de novo"
    private fun venceu() {
        val palavra = if (tentativas == 1) "tentativa" else "tentativas"
        txtDica.text = "$nome acertou em $tentativas $palavra!"
        edtPalpite.isEnabled = false
        btnChutar.isEnabled = false
        btnJogarNovamente.visibility = View.VISIBLE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(STATE_SECRETO, secreto)
        outState.putInt(STATE_TENTATIVAS, tentativas)
    }
}
