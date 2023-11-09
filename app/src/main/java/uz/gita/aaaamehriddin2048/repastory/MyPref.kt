package uz.gita.aaaamehriddin2048.repastory

import android.content.Context
import android.content.SharedPreferences
import uz.gita.aaaamehriddin2048.app.App

class MyPref private constructor(){
    val pref: SharedPreferences = App.context.getSharedPreferences("2048", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = pref.edit()

    companion object {
        private lateinit var myPref: MyPref

        fun getInstance(): MyPref {
            if (!::myPref.isInitialized) {
                myPref = MyPref()
            }
            return myPref
        }
    }
    fun saveStateFixMatrix(bool:Boolean){
        editor.putBoolean("hhh",bool).apply()
    }

    fun getStateFixMatrix():Boolean = pref.getBoolean("hhh",false)

    fun saveNumbers(s:String){
        editor.putString("NUM",s).apply()
    }
    fun getNumbers():String = pref.getString("NUM","0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/")!!

    fun saveHighScore(s:Int){
        editor.putInt("highscore",s).apply()
    }

    fun getHighScore():Int = pref.getInt("highscore",0)

    fun saveCurrentScore(s:Int){
        editor.putInt("currscore",s).apply()
    }

    fun getCurrentScore():Int = pref.getInt("currscore",0)


}