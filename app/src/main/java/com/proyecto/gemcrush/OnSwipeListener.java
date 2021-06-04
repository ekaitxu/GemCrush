package com.proyecto.gemcrush;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/*
* Esta clase sirve para controlar el la dirección del gesto que se realize con el dedo
* sobre cualquiera de las "gemas" del juego
*/

public class OnSwipeListener implements View.OnTouchListener {

    public GestureDetector gestureDetector;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public OnSwipeListener(Context context) {
        gestureDetector = new GestureDetector(context, new GestureListener());
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        public static final int SWIPE_THRESOLD = 100;
        public static final int SWIPE_VELOCITY_THRESOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean resultado = false;
            float yDiff = e2.getY() - e1.getY();
            float xDiff = e2.getX() - e1.getX();

            if (Math.abs(xDiff) > Math.abs(yDiff)) {
                if (Math.abs(xDiff) > SWIPE_THRESOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESOLD) {
                    if (xDiff > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                    resultado = true;
                }
            }
            else if(Math.abs(yDiff) > SWIPE_THRESOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESOLD) {
                if (yDiff > 0) {
                    onSwipeBottom();
                } else {
                    onSwipeTop();
                }
                resultado = true;
            }
            return resultado;

        }

    }

    void onSwipeLeft() {}
    void onSwipeRight() {}
    void onSwipeTop() {}
    void onSwipeBottom() {}
}
