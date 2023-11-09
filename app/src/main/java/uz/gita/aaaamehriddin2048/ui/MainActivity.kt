package uz.gita.aaaamehriddin2048.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import uz.gita.aaaamehriddin2048.R
import uz.gita.aaaamehriddin2048.dialogs.GameOverDialog
import uz.gita.aaaamehriddin2048.model.SideEnum
import uz.gita.aaaamehriddin2048.repastory.AppRepastory
import uz.gita.aaaamehriddin2048.repastory.MyPref
import uz.gita.aaaamehriddin2048.utils.BackgroundUtil
import uz.gita.aaaamehriddin2048.utils.MyTouchListener


class MainActivity : AppCompatActivity() {
    private val items: MutableList<TextView> = ArrayList(16)
    private lateinit var mainView: LinearLayoutCompat
    private lateinit var myTouchListener: MyTouchListener
    private lateinit var repastory: AppRepastory
    private val util = BackgroundUtil()
    private val pref = MyPref.getInstance()
    private lateinit var btnRestart: ImageView
    private lateinit var btnHome: ImageView
    private lateinit var txtCurrentScore: TextView
    private lateinit var txtHighScore: TextView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findView()
        repastory = AppRepastory.getInctance()!!
        loadViews()
        clickEvents()
        describeData()
        Log.d(
            "TTT",
            "matrix = ${pref.getNumbers()}   state = ${pref.getStateFixMatrix()}  sum  = ${pref.getCurrentScore()}"
        )
        //pref.saveStateFixMatrix(false)

        myTouchListener = MyTouchListener(this)
        myTouchListener.setMyMovementSideListener {
            when (it) {
                SideEnum.UP -> {
                    repastory.moveToUp()
                    describeData()
                }

                SideEnum.DOWN -> {
                    repastory.moveToDown()
                    describeData()
                }

                SideEnum.RIGHT -> {
                    repastory.moveToRight()
                    describeData()
                }

                SideEnum.LEFT -> {
                    repastory.moveToLeft()
                    describeData()
                }
            }
        }
        mainView.setOnTouchListener(myTouchListener)
    }

    private fun loadViews() {
        for (i in 0 until mainView.childCount) {
            val linear = mainView.getChildAt(i) as LinearLayoutCompat
            for (j in 0 until linear.childCount) {
                items.add(linear.getChildAt(j) as TextView)
            }
        }


    }

    private fun clickEvents() {
        btnRestart.setOnClickListener {
           showResetDialog()
        }

        btnHome.setOnClickListener {
             val i = Intent(this,MenuActivity::class.java)
             startActivity(i)
             finish()
        }
    }

    private fun describeData() {
///
        if (repastory.isGameOver()) {
            val dialog = GameOverDialog(this)
            dialog.setListenerClickRestart {
                repastory.restart()
                dialog.dismiss()
                describeData()
            }
            dialog.show()
        }
        if (pref.getStateFixMatrix()) {
            repastory.fixMatrix()
            txtCurrentScore.text = pref.getCurrentScore().toString()
            repastory.setSum(pref.getCurrentScore())
            pref.saveStateFixMatrix(false)
        } else {
            txtCurrentScore.text = repastory.getCurrScore()
        }

        if(pref.getHighScore() < repastory.getCurrScore().toInt()){
            txtHighScore.text = repastory.getCurrScore()
        }else{
            txtHighScore.text = pref.getHighScore().toString()
        }


        val mmatrix = repastory.matrix
        for (i in mmatrix.indices) {
            for (j in mmatrix.indices) {
                items[i * 4 + j].apply {
                    text = if (mmatrix[i][j] == 0) ""
                    else mmatrix[i][j].toString()

                    setBackgroundResource(util.colorByAmount(mmatrix[i][j]))
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
        saveState()

    }

    private fun saveState() {
        var s = ""
        val matrix_ = repastory.matrix
        for (i in matrix_.indices) {
            for (j in matrix_.indices) {
                s += matrix_[i][j].toString() + "/"
            }
        }
        pref.saveNumbers(s)
        pref.saveStateFixMatrix(true)
        pref.saveCurrentScore(repastory.getCurrScore().toInt())

        if (pref.getHighScore() < repastory.getCurrScore().toInt()){
            pref.saveHighScore(repastory.getCurrScore().toInt())
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        // pref.saveStateFixMatrix(true)
        Log.d(
            "TTT",
            "matrix = ${pref.getNumbers()}   state = ${pref.getStateFixMatrix()}  sum  = ${pref.getCurrentScore()}"
        )
    }

    private fun findView() {
        btnRestart = findViewById(R.id.buttonReload)
        mainView = findViewById(R.id.mainView)
        txtCurrentScore = findViewById(R.id.txtCurrentResult)
        txtHighScore = findViewById(R.id.txtHighResult)
        btnHome = findViewById(R.id.buttonHome)
    }

    private fun showResetDialog(){
       AlertDialog.Builder(this)
            .setTitle("Restart game ?")
            .setMessage("Do you want to restart the game ?")
            .setPositiveButton("YES"){i,d->
                repastory.restart()
                describeData()
                i.dismiss()
            }
           .setNegativeButton("NO"){i,d->
                i.dismiss()
           }
           .create()
           .show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this,MenuActivity::class.java)
        startActivity(i)
        finish()
    }
}