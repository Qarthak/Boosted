package com.example.boosted

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.boosted.databinding.ActivityLoginBinding

class GreetingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_greetings)
    }

    fun proceedButton(view: View) {
        //Navigate to feed activity on click
        intent= Intent(this, FeedActivity::class.java)
        startActivity(intent)
    }
}
