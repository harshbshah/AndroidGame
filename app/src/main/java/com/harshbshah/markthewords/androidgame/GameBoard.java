package com.harshbshah.markthewords.androidgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by harshshah on 2017-10-11.
 */

public class GameBoard  extends View {
        private Paint p;
        private List<Point> starField = null;
    private Rect sprite1Bounds = new Rect(0,0,0,0);
    private Rect sprite2Bounds = new Rect(0,0,0,0);
    private Rect sprite3Bounds = new Rect(0,0,0,0);
    private Rect sprite4Bounds = new Rect(0,0,0,0);

    private Point sprite1;
    private Point sprite2;
    private Point sprite3;
    private Point sprite4;
    private int startAlpha = 80;
    private int startFade = 2;
    private int sprite1Rotation = 0;
    private static final int NUM_OF_STARS = 25;
    private boolean collisionDetected = false;
    private Point lastCollision = new Point(-1,-1);

//Bitmaps that hold the actual sprite images


    private Bitmap bm1 = null;
    private Bitmap bm2 = null;
    private Bitmap bm3 = null;
    private Bitmap bm4 = null;


    private Matrix m = null;

    //sprite 1 setter
    synchronized public void setSprite1(int x, int y) {
        sprite1=new Point(x,y);
    }
    //sprite 1 getter
    synchronized public int getSprite1X() {
        return sprite1.x;
    }

    synchronized public int getSprite1Y() {
        return sprite1.y;
    }
    //sprite 2 setter
    synchronized public void setSprite2(int x, int y) {
        sprite2=new Point(x,y);
    }
    //sprite 2 getter
    synchronized public int getSprite2X() {
        return sprite2.x;
    }

    synchronized public int getSprite2Y() {
        return sprite2.y;
    }

    synchronized public void resetStarField() {
        starField = null;
    }
    //expose sprite bounds to controller
    synchronized public int getSprite1Width() {
        return sprite1Bounds.width();
    }

    synchronized public int getSprite1Height() {
        return sprite1Bounds.height();
    }

    synchronized public int getSprite2Width() {
        return sprite2Bounds.width();
    }

    synchronized public int getSprite2Height() {
        return sprite2Bounds.height();
    }
    synchronized public void setSprite3(int x, int y) {
        sprite3=new Point(x,y);
    }
    //sprite 3 getter
    synchronized public int getSprite3X() {
        return sprite3.x;
    }

    synchronized public int getSprite3Y() {
        return sprite3.y;
    }
    //sprite 4 setter
    synchronized public void setSprite4(int x, int y) {
        sprite4=new Point(x,y);
    }
    //sprite 4 getter
    synchronized public int getSprite4X() {
        return sprite4.x;
    }

    synchronized public int getSprite4Y() {
        return sprite4.y;
    }


    //expose sprite bounds to controller
    synchronized public int getSprite3Width() {
        return sprite3Bounds.width();
    }

    synchronized public int getSprite3Height() {
        return sprite3Bounds.height();
    }

    synchronized public int getSprite4Width() {
        return sprite4Bounds.width();
    }

    synchronized public int getSprite4Height() {
        return sprite4Bounds.height();
    }
    synchronized public Point getLastCollision() {
        return lastCollision;
    }
    //return the collision flag
    synchronized public boolean wasCollisionDetected() {
        return collisionDetected;
    }

        public GameBoard(Context context, AttributeSet aSet)
        {
            super(context,aSet);
            //p =new Paint();
            p = new Paint();
            //load our bitmaps and set the bounds for the controller
            sprite1 = new Point(-1,-1);
            sprite2 = new Point(-1,-1);
            sprite3 = new Point(-1,-1);
            sprite4 = new Point(-1,-1);
            //Define a matrix so we can rotate the asteroid
            m = new Matrix();
            p = new Paint();
            bm1 = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid);
            bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.ufo);
            bm3 = BitmapFactory.decodeResource(getResources(),R.drawable.sun);
            bm4 = BitmapFactory.decodeResource(getResources(),R.drawable.moon);
            sprite1Bounds = new Rect(0,0, bm1.getWidth(), bm1.getHeight());
            sprite2Bounds = new Rect(0,0, bm2.getWidth(), bm2.getHeight());
            sprite3Bounds = new Rect(0,0, bm3.getWidth(), bm3.getHeight());
            sprite4Bounds = new Rect(0,0, bm4.getWidth(), bm4.getHeight());


        }
        private void initializeStars(int maxX,int maxY)
        {
            starField = new ArrayList<Point>();
            for(int i=0;i<NUM_OF_STARS;i++){
                Random r =new Random();
                int x= r.nextInt(maxX-5+1)+5;
                int y= r.nextInt(maxY-5+1)+5;
                starField.add(new Point(x,y));

            }
            collisionDetected = false;
        }
    private boolean checkForCollision() {
//        if (sprite1.x<0 && sprite2.x<0 && sprite1.y<0 && sprite2.y<0) return false;
//        Rect r1 = new Rect(sprite1.x, sprite1.y, sprite1.x
//                + sprite1Bounds.width(),  sprite1.y + sprite1Bounds.height());
//        Rect r2 = new Rect(sprite2.x, sprite2.y, sprite2.x +
//                sprite2Bounds.width(), sprite2.y + sprite2Bounds.height());
//        Rect r3 = new Rect(r1);
//        if(r1.intersect(r2)) {
//            for (int i = r1.left; i<r1.right; i++) {
//                for (int j = r1.top; j<r1.bottom; j++) {
//                    if (bm1.getPixel(i-r3.left, j-r3.top)!=
//                            Color.TRANSPARENT) {
//                        if (bm2.getPixel(i-r2.left, j-r2.top) !=
//                                Color.TRANSPARENT) {
//                            lastCollision = new Point(sprite2.x +
//                                    i-r2.left, sprite2.y + j-r2.top);
//                            return true;
//                        }
//                    }
//                }
//            }
//        }
//        lastCollision = new Point(-1,-1);
        return false;
    }

    @Override
        synchronized public void onDraw(Canvas canvas) {


            p.setColor(Color.BLACK);
            p.setAlpha(255);
            p.setStrokeWidth(1);
            canvas.drawRect(0, 0, getWidth(), getHeight(), p);

            if (starField==null) {
                initializeStars(canvas.getWidth(), canvas.getHeight());
            }
            p.setColor(Color.CYAN);
            p.setAlpha(startAlpha+=startFade);
            if (startAlpha>=252 || startAlpha <=80) startFade=startFade*-1;
            p.setStrokeWidth(5);
            for (int i=0; i<NUM_OF_STARS; i++) {
                canvas.drawPoint(starField.get(i).x, starField.get(i).y, p);
            }
            if(sprite3.x>=0){
                canvas.drawBitmap(bm3, sprite3.x, sprite3.y, null);
                int sprite3MaxX = findViewById(R.id.the_canvas).getWidth() -
                        ((GameBoard) findViewById(R.id.the_canvas)).getSprite3Width();

                if (sprite3.x > sprite3MaxX + 1 || sprite3.x < 6) {
                        canvas.drawBitmap(bm4, sprite4.x, sprite4.y, null);
                    Log.d("le","le");
                }
                //Log.d("sprite3.x "+sprite3.x,"HI");

            }



            //Now we draw our sprites.  Items drawn in this function are stacked.
            //The items drawn at the top of the loop are on the bottom of the z-order.
            //Therefore we draw our set, then our actors, and finally any fx.
            if (sprite1.x>=0) {
                m.reset();
                m.postTranslate((float)(sprite1.x), (float)(sprite1.y));
                m.postRotate(sprite1Rotation,
                        (float)(sprite1.x+sprite1Bounds.width()/2.0),
                        (float)(sprite1.y+sprite1Bounds.width()/2.0));
                canvas.drawBitmap(bm1, m, null);
                sprite1Rotation+=5;
                if (sprite1Rotation >= 360) sprite1Rotation=0;
            }
            if (sprite2.x>=0) {
                canvas.drawBitmap(bm2, sprite2.x, sprite2.y, null);
            }
        collisionDetected = checkForCollision();
        if (collisionDetected ) {
            //if there is one lets draw a red X
            p.setColor(Color.RED);
            p.setAlpha(255);
            p.setStrokeWidth(5);
            canvas.drawLine(lastCollision.x - 5, lastCollision.y - 5,
                    lastCollision.x + 5, lastCollision.y + 5, p);
            canvas.drawLine(lastCollision.x + 5, lastCollision.y - 5,
                    lastCollision.x - 5, lastCollision.y + 5, p);
        }
    }


        }




