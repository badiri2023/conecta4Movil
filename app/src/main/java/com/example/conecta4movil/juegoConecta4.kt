package com.example.conecta4movil

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class juegoConecta4 : AppCompatActivity() {

    private lateinit var tableLayout: TableLayout
    private val FILAS = 6
    private val COLUMNAS = 7
    private val matrizCeldas = Array(FILAS) { arrayOfNulls<ImageView>(COLUMNAS) }
    private val estadoTablero = Array(FILAS) { Array(COLUMNAS) { "" } } // "R", "A" o ""
    private var turnoRojo = true
    private var ultimoMovimiento: Pair<Int, Int>? = null
    private var jugadaPendiente = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego_conecta4)

        tableLayout = findViewById(R.id.tablero)
        crearTablero()
        for (col in 0 until COLUMNAS) {
            val botonId = resources.getIdentifier("btnCol$col", "id", packageName)
            val boton = findViewById<Button>(botonId)
            boton.setOnClickListener {
                colocarFicha(col)
            }
        }
        // Botón Aceptar
        findViewById<Button>(R.id.btnConfirm).setOnClickListener {
            confirmarJugada()
        }

        // Botón Deshacer
        findViewById<Button>(R.id.btnUndo).setOnClickListener {
            deshacerJugada()
        }

        desactivarBotonesConfirmacion()

    }

    private fun crearTablero() {
        tableLayout.removeAllViews()

        for (fila in 0 until FILAS) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
            )

            for (col in 0 until COLUMNAS) {
                val celda = ImageView(this)
                celda.setImageResource(R.drawable.celda_vacia)

                val params = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
                params.setMargins(4, 4, 4, 4)
                celda.layoutParams = params

                tableRow.addView(celda)
                matrizCeldas[fila][col] = celda
            }

            tableLayout.addView(tableRow)
        }
    }
    private fun activarBotonesConfirmacion() {
        findViewById<Button>(R.id.btnConfirm).isEnabled = true
        findViewById<Button>(R.id.btnUndo).isEnabled = true
    }
    private fun desactivarBotonesConfirmacion() {
        findViewById<Button>(R.id.btnConfirm).isEnabled = false
        findViewById<Button>(R.id.btnUndo).isEnabled = false
    }
    private fun confirmarJugada() {
        ultimoMovimiento?.let { (fila, col) ->
            if (comprobarVictoria(fila, col)) {
                Toast.makeText(this, "¡Ganó ${if (turnoRojo) "Rojo" else "Amarillo"}!", Toast.LENGTH_LONG).show()
                desactivarBotonesConfirmacion()
                return
            }
        }
        jugadaPendiente = false
        ultimoMovimiento = null
        desactivarBotonesConfirmacion()
        turnoRojo = !turnoRojo
    }
    private fun deshacerJugada() {
        ultimoMovimiento?.let { (fila, col) ->
            estadoTablero[fila][col] = ""
            matrizCeldas[fila][col]?.setImageResource(R.drawable.celda_vacia)
            ultimoMovimiento = null
            jugadaPendiente = false
            desactivarBotonesConfirmacion()
        }
    }


    private fun colocarFicha(col: Int) {
        // evita colocar mas de una ficha a la vez
        if (jugadaPendiente) return

        for (fila in FILAS - 1 downTo 0) {
            if (estadoTablero[fila][col].isEmpty()) {
                estadoTablero[fila][col] = if (turnoRojo) "R" else "A"
                matrizCeldas[fila][col]?.setImageResource(
                    if (turnoRojo) R.drawable.ficha_roja else R.drawable.ficha_amarilla
                )
                ultimoMovimiento = Pair(fila, col)
                jugadaPendiente = true
                activarBotonesConfirmacion()
                break
            }
        }
    }

    private fun comprobarVictoria(fila: Int, col: Int): Boolean {
        val jugador =estadoTablero[fila][col]
        if (jugador.isEmpty()) return false

        fun contar(dx: Int,dy:Int): Int{
            var count=0
            var x=col+dx
            var y=fila+dy
            while (x in 0 until COLUMNAS && y in 0 until FILAS && estadoTablero[y][x] == jugador){
                count++
                x += dx
                y += dy
            }
            return count
        }
        val direcciones = listOf(
            Pair(1, 0),  // Horizontal →
            Pair(0, 1),  // Vertical ↓
            Pair(1, 1),  // Diagonal ↘
            Pair(1, -1)  // Diagonal ↗
        )
        for ((dx, dy) in direcciones) {
            val total = 1 + contar(dx, dy) + contar(-dx, -dy)
            if (total >= 4) return true
        }

        return false

    }

}