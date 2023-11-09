package uz.gita.aaaamehriddin2048.repastory

import android.annotation.SuppressLint
import android.util.Log
import kotlin.random.Random

class AppRepastory private constructor() {
    private lateinit var emptyList: ArrayList<Pair<Int, Int>>
    private val pref = MyPref.getInstance()
    private var sum = 0

    companion object {
        private var repastory: AppRepastory? = null
        fun getInctance(): AppRepastory? {
            if (repastory == null)
                repastory = AppRepastory()
            return repastory
        }
    }

    private var NEW_ELEMENT = 2

    val matrix = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )

    init {
        addNewElement()
        addNewElement()
    }

    fun addNewElement(): Boolean {
        emptyList = ArrayList<Pair<Int, Int>>()

        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) emptyList.add(Pair(i, j))
            }
        }
        if (emptyList.isEmpty()) return true
        val randomPos = Random.nextInt(0, emptyList.size)
        matrix[emptyList[randomPos].first][emptyList[randomPos].second] = NEW_ELEMENT
        return false
    }


    fun moveToLeft() {


        val matrix2 = Array(matrix.size) { row -> matrix[row].copyOf() }

        for (i in matrix.indices) {
            val amounts = ArrayList<Int>(4)
            var bool = true
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) continue
                if (amounts.isEmpty()) amounts.add(matrix[i][j])
                else {
                    if (amounts.last() == matrix[i][j] && bool) {
                        sum += amounts.last() * 2
                        amounts[amounts.size - 1] = amounts.last() * 2
                        bool = false
                    } else {
                        amounts.add(matrix[i][j])
                        bool = true
                    }
                }
                matrix[i][j] = 0
            }

            for (k in amounts.indices) {
                matrix[i][k] = amounts[k]
            }
        }
        var isSame = true
        for (i in 0..3) {
            for (j in 0..3) {
                Log.d("PPP", "matrix2 = ${matrix2[i][j]}  matrix = ${matrix[i][j]}")
                if (matrix2[i][j] != matrix[i][j])
                    isSame = false
            }
        }

        if (!isSame) addNewElement()
        // bosh joy yoq bolsa oyin tugashi kk
    }

    fun moveToRight() {


        val matrix2 = Array(matrix.size) { row -> matrix[row].copyOf() }
        for (i in matrix.indices) {
            val amounts = ArrayList<Int>(4)
            var bool = true
            for (j in matrix[i].size - 1 downTo 0) {
                if (matrix[i][j] == 0) continue

                if (amounts.isEmpty()) amounts.add(matrix[i][j])
                else {
                    if (amounts.last() == matrix[i][j] && bool) {
                        sum += amounts.last() * 2
                        amounts[amounts.size - 1] = amounts.last() * 2
                        bool = false
                    } else {
                        amounts.add(matrix[i][j])
                        bool = true
                    }
                }
                matrix[i][j] = 0
            }
            for (k in amounts.indices) {
                matrix[i][matrix[i].size - k - 1] = amounts[k]
            }
        }

        var isSame = true
        for (i in 0..3) {
            for (j in 0..3) {
                Log.d("PPP", "matrix2 = ${matrix2[i][j]}  matrix = ${matrix[i][j]}")
                if (matrix2[i][j] != matrix[i][j])
                    isSame = false
            }
        }

        if (!isSame) addNewElement()

    }

    fun moveToUp() {

        val matrix2 = Array(matrix.size) { row -> matrix[row].copyOf() }
        for (col in matrix.indices) {
            val amounts = ArrayList<Int>(4)
            var bool = true
            for (row in matrix.indices) {
                if (matrix[row][col] == 0) continue

                if (amounts.isEmpty()) {
                    amounts.add(matrix[row][col])
                } else {
                    if (matrix[row][col] == amounts.last() && bool) {
                        sum += amounts.last() * 2
                        amounts[amounts.size - 1] = amounts.last() * 2
                        bool = false
                    } else {
                        amounts.add(matrix[row][col])
                        bool = true
                    }
                }
                matrix[row][col] = 0
            }
            for (k in amounts.indices) {
                matrix[k][col] = amounts[k]
            }
        }
        var isSame = true
        for (i in 0..3) {
            for (j in 0..3) {
                Log.d("PPP", "matrix2 = ${matrix2[i][j]}  matrix = ${matrix[i][j]}")
                if (matrix2[i][j] != matrix[i][j])
                    isSame = false
            }
        }

        if (!isSame) addNewElement()


    }

    fun moveToDown() {

        val matrix2 = Array(matrix.size) { row -> matrix[row].copyOf() }
        for (col in matrix.indices) {
            val amounts = ArrayList<Int>(4)
            var bool = true
            for (row in matrix.indices.reversed()) {
                if (matrix[row][col] == 0) continue

                if (amounts.isEmpty()) {
                    amounts.add(matrix[row][col])
                } else {
                    if (matrix[row][col] == amounts.last() && bool) {
                        sum += amounts.last() * 2
                        amounts[amounts.size - 1] = amounts.last() * 2
                        bool = false
                    } else {
                        amounts.add(matrix[row][col])
                        bool = true
                    }
                }
                matrix[row][col] = 0
            }
            for (k in amounts.indices.reversed()) {
                matrix[matrix.lastIndex - k][col] = amounts[k]
            }
        }
        var isSame = true
        for (i in 0..3) {
            for (j in 0..3) {

                if (matrix2[i][j] != matrix[i][j])
                    isSame = false
            }
        }

        if (!isSame) addNewElement()
    }

    fun isGameOver(): Boolean {
        // Barcha joylarni tekshirish
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (matrix[i][j] == 0) {
                    return false
                }
            }
        }

        // Qatorlarni va ustunlarni tekshirish
        for (i in matrix.indices) {
            for (j in matrix[i].indices) {
                if (i < matrix.size - 1 && matrix[i][j] == matrix[i + 1][j]) {
                    // Xali ustunga joy qo'shish mumkin
                    return false
                }
                if (j < matrix[i].size - 1 && matrix[i][j] == matrix[i][j + 1]) {
                    // Xali qatorga joy qo'shish mumkin
                    return false
                }
            }
        }
        return true
    }

    @SuppressLint("SuspiciousIndentation")
    fun fixMatrix() {
        var bool = false
        val s = pref.getNumbers()
        val arr = s?.split("/")

        if (arr != null) {
            for (i in arr.indices) {
                if (!arr[i].equals("0")) bool = true
            }
            Log.d("LLL", "fixMatrix s = $s")
            if (bool)
                for (i in matrix.indices) {
                    for (j in matrix.indices) {
                        matrix[i][j] = arr[i * 4 + j].toInt()
                    }
                }
        }
    }


    fun restart() {
        for (i in matrix.indices) {
            for (j in matrix.indices) {
                matrix[i][j] = 0
            }
        }

        addNewElement()
        addNewElement()
        if (pref.getHighScore() < sum){
            pref.saveHighScore(sum)
        }
        pref.saveStateFixMatrix(false)
        pref.saveCurrentScore(0)
        pref.saveNumbers("0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/0/")
        sum = 0
    }

    fun getCurrScore():String = sum.toString()

    fun setSum(s:Int){
        sum = s
    }
}