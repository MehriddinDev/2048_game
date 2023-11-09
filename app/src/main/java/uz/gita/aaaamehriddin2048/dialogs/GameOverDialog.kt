package uz.gita.aaaamehriddin2048.dialogs

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import uz.gita.aaaamehriddin2048.R

class GameOverDialog(context: Context):AlertDialog(context) {
    private lateinit var listenerClickRestart:(()->Unit)

    fun setListenerClickRestart(k:()->Unit){
        listenerClickRestart = k
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_over_dialog)

        val restart = findViewById<ImageButton>(R.id.buttonReload)!!

        restart.setOnClickListener {
            listenerClickRestart.invoke()
        }

        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )

        setCancelable(false)
        window?.setBackgroundDrawable(null)

    }


}