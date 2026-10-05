package com.example.jogoadivinhacao

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
// Android Studio 3.0/3.1 (sem AndroidX): troque a linha acima por
// import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtNome = findViewById<EditText>(R.id.edtNome)
        val rgLimite = findViewById<RadioGroup>(R.id.rgLimite)
        val btnJogar = findViewById<Button>(R.id.btnJogar)

        btnJogar.setOnClickListener {
            val nome = edtNome.text.toString().trim()
            if (nome.isEmpty()) {
                edtNome.error = "Digite o nome do jogador"
                return@setOnClickListener
            }

            val maximo = when (rgLimite.checkedRadioButtonId) {
                R.id.rb10 -> 10
                R.id.rb100 -> 100
                else -> 50
            }

            val intent = Intent(this, JogoActivity::class.java)
            intent.putExtra(JogoActivity.EXTRA_NOME, nome)
            intent.putExtra(JogoActivity.EXTRA_MAXIMO, maximo)
            startActivity(intent)
        }
    }
}
