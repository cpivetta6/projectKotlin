package com.example.buylistapp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.buylistapp.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //val layout = findViewById<ConstraintLayout>(R.id.rootLayout)
        //layout.setBackgroundColor(ContextCompat.getColor(this, R.color.background_color))

        val title = findViewById<TextView>(R.id.mainTitle)
        val btnGroceryList = findViewById<Button>(R.id.btnGroceryList)
        val btnStoric = findViewById<Button>(R.id.btnStoric)
        val btnSettings = findViewById<Button>(R.id.btnSettings)

        btnGroceryList.setOnClickListener{
            val intent = Intent(this, GroceryList::class.java)
            startActivity(intent)
        }



    }
}
