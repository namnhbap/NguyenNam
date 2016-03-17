package com.example.viethoang.firstgameproject.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.viethoang.firstgameproject.GameThread;
import com.example.viethoang.firstgameproject.MainActivity;
import com.example.viethoang.firstgameproject.object.Ball;
import com.example.viethoang.firstgameproject.object.Brick;
import com.example.viethoang.firstgameproject.object.MoveBox;

import java.util.ArrayList;
import java.util.Random;
import java.util.jar.Attributes;

/**
 * Created by VietHoang on 14/01/2016.
 */

public class MainGameView extends SurfaceView {

    private SurfaceHolder holder;
    private GameThread gameThread;
    Display display = MainActivity.display;
    Ball ball;
    MoveBox moveBox;
    MoveBox moveBoxTop;
    ArrayList<Brick> brickList;
    ArrayList<Brick> collistionBrick;
    static int[] color = {Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.YELLOW, Color.MAGENTA};

    public MainGameView(Context context, AttributeSet attributes) {
        super(context, attributes);
        ball = new Ball(50, 100, 15);
        ball.setColor(Color.WHITE);
        moveBox = new MoveBox(display.getWidth() / 2, display.getHeight() - 100, 100, 20);
        moveBox.setColor(Color.BLACK);
        moveBoxTop = new MoveBox(display.getWidth() / 2, 100, 100, 20);
        moveBoxTop.setColor(Color.BLACK);
        brickList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Brick b = new Brick(i * 540 / 10, display.getHeight() / 2, 540 / 10, 50);
            Random r = new Random();
            b.setColor(Color.parseColor("#424242"));
//            b.setColor(color[r.nextInt(6)]);
            brickList.add(b);
        }
        collistionBrick = new ArrayList<>();

        gameThread = new GameThread(this);
        holder = this.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameThread.setRunning(true);
                gameThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                gameThread.setRunning(false);
                try {
                    gameThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#4CAF50"));
        canvas.drawPaint(paint);

        brickList.removeAll(collistionBrick);
        collistionBrick.clear();
        if(ball.checkCollisionBackground(this)){
            MainActivity.soundManager.playHit();
        }
        moveBox.draw(canvas);
        if (ball.checkCollision(moveBox, false)) {
            MainActivity.soundManager.playHit();
            if (Math.abs(moveBox.getDeltaX()) > Math.abs(ball.getVelocityX())) {
                ball.setX(ball.getX() + moveBox.getDeltaX());
            }
        }
        moveBoxTop.draw(canvas);
        if (ball.checkCollision(moveBoxTop, true)) {
            MainActivity.soundManager.playHit();
            if (Math.abs(moveBoxTop.getDeltaX()) > Math.abs(ball.getVelocityX())) {
                ball.setX(ball.getX() + moveBoxTop.getDeltaX());
            }
        }
        ball.move();
        ball.drawBitmap(canvas);
        for (Brick b : brickList) {
            b.draw(canvas);
            if (ball.checkCollision(b)) {
                collistionBrick.add(b);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        moveBox.onTouchEvent(event);
        moveBoxTop.onTouchEvent(event);
        return true;
    }

}
