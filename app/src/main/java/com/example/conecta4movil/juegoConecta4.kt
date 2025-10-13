package com.example.conecta4movil

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class juegoConecta4 : AppCompatActivity() {

    private val FILAS = 6
    private val COLUMNAS = 7
    private lateinit var gridLayout: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego_conecta4)

        // Referencia al GridLayout
        gridLayout = findViewById(R.id.game_board)

        // Crear visualmente el tablero
        crearTablero()
    }
    private fun crearTablero() {
        gridLayout.removeAllViews()
        val tamañoCelda = resources.displayMetrics.widthPixels / COLUMNAS

        for (fila in 0 until FILAS) {
            for (col in 0 until COLUMNAS) {
                val celda = TextView(this)
                celda.text = ""
                celda.background = ContextCompat.getDrawable(this, R.drawable.celda_vacia)

                val params = GridLayout.LayoutParams()
                params.width = tamañoCelda
                params.height = tamañoCelda
                params.rowSpec = GridLayout.spec(fila)
                params.columnSpec = GridLayout.spec(col)
                params.setMargins(4, 4, 4, 4)

                celda.layoutParams = params
                gridLayout.addView(celda)
            }
        }
    }



}
