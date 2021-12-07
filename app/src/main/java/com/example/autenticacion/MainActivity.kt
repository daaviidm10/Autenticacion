package com.example.autenticacion

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inicializamos FireBase
        auth = Firebase.auth

        //Botón para registrarse
        val Registro: Button = findViewById(R.id.registro)
        Registro.setOnClickListener{
            val correo: EditText = findViewById(R.id.usuario) //Recojo el texto
            val contraseña: EditText = findViewById(R.id.contraseña)  //Recojo la contraseña
            crearCuenta(correo.text.toString(),contraseña.text.toString())//Llamo al metodo crear cuenta y le paso  el correo y la contraseña
        }

        val iniciarsesion: Button = findViewById(R.id.inicioSesion)//Botón para iniciar sesión
        iniciarsesion.setOnClickListener{
            val correo: EditText = findViewById(R.id.usuario)//Recojo el texto
            val contraseña: EditText = findViewById(R.id.contraseña) //Recojo la contraseña
            accederCuenta(correo.text.toString(),contraseña.text.toString())//Llamo al metodo acceder a la  cuenta y le paso  el correo y la contraseña
        }
    }
    public override fun onStart() {
        super.onStart()
        //Comprobacion de si la sesión está iniciada
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }
    //Sentencia para crear un nuevo usuario
    private fun crearCuenta(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.i("Correcto", "Usuario creado correctamente") // Crea usuario y actualiza la informacion
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.i("Fallo", "No creaste bien el usuario", task.exception)// Si el inicio de sesión falla muestra el mensaje
                    Toast.makeText(baseContext, "Fallo al crear.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun accederCuenta (email: String, password: String) {
        // accede a tu cuenta con email y contraseña
        auth.signInWithEmailAndPassword(email, password)

            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Inicia sesión y actualiza la informacion
                    Log.d("Correcto", "Acceso permitido")
                    val user = auth.currentUser
                    updateUI(user)
                    Toast.makeText(baseContext, "Correcto.",
                        Toast.LENGTH_SHORT).show()
                } else {
                    // Si falla muestra este mensaje
                    Log.w("Error", "Acceso denegado", task.exception)
                    Toast.makeText(baseContext, "Fallo al iniciar sesión",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {

    }

}