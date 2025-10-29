package com.victorasensio.andrevina

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import com.google.android.material.textfield.TextInputEditText
import kotlin.random.Random
import android.content.Context


class MainActivity : AppCompatActivity(), RecordsDialog.RecordsDialogListener {

    private var randomNumber = 0
    companion object {
        val recordsArray = ArrayList<String>()
    }

    override fun onRecordAccepted(name: String, attempts: Int) {
        val record = "Jugador: $name, Intentos: $attempts"
        recordsArray.add(record)
        Toast.makeText(this, "Record guardado: $record", Toast.LENGTH_SHORT).show()
    }

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
        val textHistorial = findViewById<TextView>(R.id.textViewHistorial)
        val button2 = findViewById<Button>(R.id.button2)
        var numIntents = 0
        var numJoc = 1

        button.setOnClickListener {
            val userInput = inputText.text.toString()

            // Validar que el input no esté vacío
            if (userInput.isEmpty()) {
                Toast.makeText(this, "Introdueix un número!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Validar que el valor sea un número
            val userNumber = try {
                userInput.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "El valor introduït no és un número vàlid.", Toast.LENGTH_SHORT).show()
                if (numIntents == 1) {textHistorial.append("\nJoc número $numJoc")}
                textHistorial.append("\nIntent no vàlid, el jugador s'ha tornat boig!!!")
                inputText.setText("")
                return@setOnClickListener
            }

            // Validar que esté entre 1 y 100
            if (userNumber !in 1..100) {
                Toast.makeText(this, "Número fora de l'interval 1-100.", Toast.LENGTH_SHORT).show()
                if (numIntents == 1) {textHistorial.append("\nJoc número $numJoc")}
                textHistorial.append("\nIntent no vàlid, el jugador s'ha tornat boig!!!")
                inputText.setText("")
                return@setOnClickListener
            }

            // Comparar con el número aleatorio y mostrar toast
            when {
                userNumber == randomNumber -> {
                    Toast.makeText(this, "Has encertat el número! 🎉", Toast.LENGTH_LONG).show()
                    numIntents += 1
                    // Generar otro número para un nuevo juego
                    randomNumber = Random.nextInt(1, 101)

                    if (numIntents == 1) {textHistorial.append("\nJoc número $numJoc")}
                    textHistorial.append("\nIntent número $numIntents: el número $userNumber és el correcte")

                    val dialog = RecordsDialog.newInstance(numIntents)
                    dialog.show(supportFragmentManager, "RecordsDialog")

                    numIntents = 0 //número intents reiniciat
                    numJoc += 1 //S'afegeix un joc

                }
                userNumber < randomNumber -> {
                    Toast.makeText(this, "El número és massa baix.", Toast.LENGTH_SHORT).show()
                    numIntents += 1
                    if (numIntents == 1) {textHistorial.append("\nJoc número $numJoc")}
                    textHistorial.append("\nIntent número $numIntents: número més alt que $userNumber")
                }
                else -> {
                    Toast.makeText(this, "El número és massa alt.", Toast.LENGTH_SHORT).show()
                    numIntents += 1
                    if (numIntents == 1) {textHistorial.append("\nJoc número $numJoc")}
                    textHistorial.append("\nIntent número $numIntents: número més baix que $userNumber")
                }
            }
            inputText.setText("");
        }

        button2.setOnClickListener {

            val intent = Intent(this, HallOfFame::class.java)
            intent.putStringArrayListExtra("lista-records", recordsArray)
            startActivity(intent)
        }
    }
}


