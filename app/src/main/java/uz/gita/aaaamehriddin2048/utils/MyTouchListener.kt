package uz.gita.aaaamehriddin2048.utils

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import uz.gita.aaaamehriddin2048.model.SideEnum

class MyTouchListener(context: Context) : View.OnTouchListener {
    private val myGestureDetector = GestureDetector(context, MyGestureListener())
    private var myMovementSideListener: ((SideEnum) -> Unit)? = null

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        myGestureDetector.onTouchEvent(event!!)
        return true
    }

    inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (kotlin.math.abs(e2.x - e1.x) > 100 || kotlin.math.abs(e2.y - e1.y) > 100) {
                if (kotlin.math.abs(e2.x - e1.x) < kotlin.math.abs(e2.y - e1.y)) {  // vertical
                    if (e2.y > e1.y) {// down
                        Log.d("TTT", "down ")
                        myMovementSideListener?.invoke(SideEnum.DOWN)
                    } else {  // up
                        Log.d("TTT", "up")
                        myMovementSideListener?.invoke(SideEnum.UP)
                    }
                } else { // horizontal
                    if (e2.x > e1.x) {// right
                        Log.d("TTT", "right")
                        myMovementSideListener?.invoke(SideEnum.RIGHT)
                    } else {  // left
                        Log.d("TTT", "left")
                        myMovementSideListener?.invoke(SideEnum.LEFT)
                    }
                }
            }

            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }

    fun setMyMovementSideListener(block: (SideEnum) -> Unit) {
        myMovementSideListener = block
    }

}