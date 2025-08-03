package com.example.chatapp.presentation.ui.login



import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.chatapp.R
import com.example.chatapp.presentation.ui.salas.SalasActivity
import com.example.chatapp.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 * Actividad para el inicio de sesión y registro de usuarios.
 */
@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        // Asegúrate de que el layout 'activity_login.xml' tenga un EditText con id 'usernameEditText'
        // Si no lo tiene, quita esta línea o añádelo a tu XML.
        val usernameEditText: EditText = findViewById(R.id.usernameEditText)
        val loginButton: Button = findViewById(R.id.loginButton)
        val registerButton: Button = findViewById(R.id.registerButton)

        // Observa el estado de autenticación del ViewModel.
        lifecycleScope.launch {
            loginViewModel.authState.collect { authState ->
                when (authState) {
                    is LoginViewModel.AuthState.Authenticated -> {
                        Toast.makeText(this@LoginActivity, "Sesión iniciada como ${authState.user.username}", Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.GONE
                        val intent = Intent(this@LoginActivity, SalasActivity::class.java)
                        startActivity(intent)
                        finish() // Finaliza esta actividad para que el usuario no pueda volver atrás.
                    }
                    is LoginViewModel.AuthState.Error -> {
                        Toast.makeText(this@LoginActivity, "Error: ${authState.message}", Toast.LENGTH_LONG).show()
                        Log.e("LoginActivity", "Authentication Error: ${authState.message}")
                        progressBar.visibility = View.GONE
                    }
                    LoginViewModel.AuthState.Loading -> {
                        // Mostrar un indicador de carga.
                        progressBar.visibility = View.VISIBLE
                        Toast.makeText(this@LoginActivity, "Cargando...", Toast.LENGTH_SHORT).show()
                    }
                    LoginViewModel.AuthState.Unauthenticated -> {
                        // No hacer nada, el usuario necesita iniciar sesión/registrarse o ya se ha desconectado.
                        Log.d("LoginActivity", "User is unauthenticated.")
                        Toast.makeText(this@LoginActivity, "No estás autenticado.", Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.GONE

                    }
                }
            }
        }

        // Intenta auto-autenticar al inicio si ya hay un usuario.
        lifecycleScope.launch {
            loginViewModel.checkCurrentUser()
        }

        loginButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (password.length >= 6) {
                    loginViewModel.login(email, password)
                } else{
                    Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                }
            } else {
                Toast.makeText(this, "Por favor, ingresa email y contraseña.", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
        }

        registerButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val username = usernameEditText.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()) {
                if (password.length >= 6){
                    loginViewModel.register(email, password, username)
                } else{
                    Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                }

            } else {
                Toast.makeText(this, "Por favor, completa todos los campos para registrarte.", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
        }
    }
}