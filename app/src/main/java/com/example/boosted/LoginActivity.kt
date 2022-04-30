package com.example.boosted

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.boosted.databinding.ActivityLoginBinding


//data class LoginViewModel(val name : String, val password : String) : ViewModel()

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val mTAG : String = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginButton = binding.login
        val usernameField = binding.username
        val passwordField = binding.password

        loginButton.setOnClickListener{
            val password = passwordField.text.toString()
            val username = usernameField.text.toString()
            Log.d(mTAG, "Pass is " + password + "Username is " + username)

            //ForFasterLogin
//            intent= Intent(this, GreetingsActivity::class.java)
//            startActivity(intent)

            //If the password is correct and the username is non empty navigate to greetings Activity
            if(password == "sar" && username != ""){
                intent= Intent(this, GreetingsActivity::class.java)
                startActivity(intent)
            }
        }
    }
}