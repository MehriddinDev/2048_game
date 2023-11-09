package uz.gita.aaaamehriddin2048.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import uz.gita.aaaamehriddin2048.R
import uz.gita.aaaamehriddin2048.ui.MainActivity


class MenuActivity : AppCompatActivity() {
    private lateinit var btnStart: ImageView
    private lateinit var btnShare:ImageView
    private lateinit var btnInfo:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_activity)
        findView()
        clickEvent()
    }

    private fun findView(){
        btnStart = findViewById(R.id.btnStartGame)
        btnShare = findViewById(R.id.btnShare)
        btnInfo = findViewById(R.id.btnInfo)
    }

    private fun clickEvent(){
        btnStart.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name)
            var shareMessage = "2048 game for Android. Develop your brain".trim() + "\n"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "Share This App"))
        }

        btnInfo.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("                     2048 game")
                .setMessage("Created by Mehriddin Sobirov\nGita academy of programmers")
                .create()
                .show()
        }
    }
}