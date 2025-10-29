package com.example.conecta4movil

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
// indicar contraseña y usuario para iniciar
class MainActivity : AppCompatActivity() {
    private val credenciales = mapOf("1234" to "1234")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // referencia a los elementos del layout
        val loginButton = findViewById<Button>(R.id.login_button)
        val usernameField = findViewById<EditText>(R.id.username)
        val passwordField = findViewById<EditText>(R.id.password)
        val errorText = findViewById<TextView>(R.id.error_text)

        loginButton.setOnClickListener {
            val username = usernameField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (credenciales.containsKey(username) && credenciales[username] == password) {
                val intent = Intent(this, juegoConecta4::class.java)
                intent.putExtra("username", username)
                startActivity(intent)
            } else {
                errorText.text = "Usuario o contraseña incorrectos"
                errorText.visibility = View.VISIBLE
            }
        }
    }
}
