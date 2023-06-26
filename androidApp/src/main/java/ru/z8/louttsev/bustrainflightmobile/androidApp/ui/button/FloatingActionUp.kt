package ru.z8.louttsev.bustrainflightmobile.androidApp.ui.button

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import ru.z8.louttsev.bustrainflightmobile.androidApp.R


class FloatingActionUp : Service() {
    //менеджер к которому цепляем кнопку что бы все время быть на верху
    private var windowManager: WindowManager? = null
    private var chatHead: ImageView? = null
    private var params: WindowManager.LayoutParams? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate() {
        super.onCreate()

        //инициализируем его
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        //создаем нашу кнопку что бы отобразить
        chatHead = ImageView(this)
        chatHead!!.setImageResource(R.drawable.arrow_up)

        //задаем параметры для картинки, что бы была
        //своего размера, что бы можно было перемещать по экрану
        //что бы была прозрачной, и устанавливается ее стартовое полодение
        //на экране при создании
        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        params!!.gravity = Gravity.TOP or Gravity.LEFT
        params!!.x = 0
        params!!.y = 100

        //кол перемещения тоста по экрану при помощи touch
        chatHead!!.setOnTouchListener(object : OnTouchListener {
            private var initialX = 0
            private var initialY = 0
            private var initialTouchX = 0f
            private var initialTouchY = 0f
            private var shouldClick = false
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        shouldClick = true
                        initialX = params!!.x
                        initialY = params!!.y
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                        return true
                    }

                    MotionEvent.ACTION_UP -> {
                        if (shouldClick) Toast.makeText(
                            applicationContext,
                            "Клик по тосту случился!",
                            Toast.LENGTH_LONG
                        ).show()
                        return true
                    }

                    MotionEvent.ACTION_MOVE -> {
                        shouldClick = false
                        params!!.x = (initialX
                                + (event.rawX - initialTouchX).toInt())
                        params!!.y = (initialY
                                + (event.rawY - initialTouchY).toInt())
                        windowManager!!.updateViewLayout(chatHead, params)
                        return true
                    }
                }
                return false
            }
        })
        windowManager!!.addView(chatHead, params)
    }

    //удалем тост если была команда выключить сервис
    override fun onDestroy() {
        super.onDestroy()
        if (chatHead != null) windowManager!!.removeView(chatHead)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}