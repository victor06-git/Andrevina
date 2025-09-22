package com.victorasensio.andrevina

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var randomNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Generar número aleatorio entre 1 y 100
        randomNumber = Random.nextInt(1, 101)

        val button = findViewById<Button>(R.id.button)
        val inputText = findViewById<TextInputEditText>(R.id.inputText)

        button.setOnClickListener {
            val userInput = inputText.text.toString()

            // Validar que el input no esté vacío y sea un número
            if (userInput.isEmpty()) {
                Toast.makeText(this, "Introdueix un número!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userNumber = try {
                userInput.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "El valor introduït no és un número vàlid.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar que esté entre 1 y 100
            if (userNumber !in 1..100) {
                Toast.makeText(this, "Número fora de l'interval 1-100.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Comparar con el número aleatorio y mostrar toast
            when {
                userNumber == randomNumber -> {
                    Toast.makeText(this, "Has encertat el número! 🎉", Toast.LENGTH_LONG).show()
                    // Generar otro número para un nuevo juego
                    randomNumber = Random.nextInt(1, 101)
                }
                userNumber < randomNumber -> {
                    Toast.makeText(this, "El número és massa baix.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "El número és massa alt.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
