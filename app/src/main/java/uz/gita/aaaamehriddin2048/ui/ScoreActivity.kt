package uz.gita.aaaamehriddin2048.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import uz.gita.aaaamehriddin2048.R

import uz.gita.aaaamehriddin2048.repastory.MyPref

class ScoreActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        findViewById<ImageView>(R.id.buttonBack).setOnClickListener {
            val i = Intent(this,MenuActivity::class.java)
            startActivity(i)
            finish()
        }

        val text = findViewById<TextView>(R.id.txtHighResult)

        text.text = MyPref.getInstance().getHighScore().toString()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this,MenuActivity::class.java)
        startActivity(i)
        finish()
    }
}