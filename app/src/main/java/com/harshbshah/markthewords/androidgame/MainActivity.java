package com.harshbshah.markthewords.androidgame;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends Activity implements OnClickListener {

    private Handler frame = new Handler();
    //Divide the frame by 1000 to calculate how many times per second the screen will update.
    private static final int FRAME_RATE = 20; //50 frames per second
    private Point sprite1Velocity;
    private Point sprite2Velocity;
    private Point sprite3Velocity;
    private Point sprite4Velocity;

    private int sprite3MaxX;
    private int sprite3MaxY;
    private int sprite4MaxX;
    private int sprite4MaxY;
    private int sprite1MaxX;
    private int sprite1MaxY;
    private int sprite2MaxX;
    private int sprite2MaxY;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler h = new Handler();

        ((Button) findViewById(R.id.the_button)).setOnClickListener(this);
        //We can't initialize the graphics immediately because the layout manager
        //needs to run first, thus we call back in a sec.
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                initGfx();
            }
        }, 1000);
    }
    private Point getSunVelocity(){
       // int min = 1;
       // int max = 5;
        Point sp3 = new Point
                (((GameBoard) findViewById(R.id.the_canvas)).getSprite3X(),
                        ((GameBoard) findViewById(R.id.the_canvas)).getSprite3Y());
        int x= sp3.x+2;
        int y = 3;
        return new Point(x, y);
    }

    private Point getMoonVelocity(){
        // int min = 1;
        // int max = 5;
        Point sp4 = new Point
                (((GameBoard) findViewById(R.id.the_canvas)).getSprite4X(),
                        ((GameBoard) findViewById(R.id.the_canvas)).getSprite4Y());
        int x= sp4.x+2;
        int y = 3;
        return new Point(x, y);
    }
    private Point getRandomVelocity() {
        Random r = new Random();
        int min = 1;
        int max = 5;
        int x = r.nextInt(max - min + 1) + min;
        int y = r.nextInt(max - min + 1) + min;
        return new Point(x, y);
    }

    private Point getRandomPoint() {
        Random r = new Random();
        int minX = 0;
        int maxX = findViewById(R.id.the_canvas).getWidth() -
                ((GameBoard) findViewById(R.id.the_canvas)).getSprite1Width();
        int x = 0;
        int minY = 0;
        int maxY = findViewById(R.id.the_canvas).getHeight() -
                ((GameBoard) findViewById(R.id.the_canvas)).getSprite1Height();
        int y = 0;
        x = r.nextInt(maxX - minX + 1) + minX;
        y = r.nextInt(maxY - minY + 1) + minY;
        return new Point(x, y);
    }


    synchronized public void initGfx() {
        ((GameBoard) findViewById(R.id.the_canvas)).resetStarField();
        //Select two random points for our initial sprite placement.
        //The loop is just to make sure we don't accidentally pick
        //two points that overlap.
        Point p1, p2,p3,p4;
        p3 = new Point(15,3);
        p4 = new Point(0,3);
        do {
            p1 = getRandomPoint();
            p2 = getRandomPoint();
        }

        while (Math.abs(p1.x - p2.x) <
                ((GameBoard) findViewById(R.id.the_canvas)).getSprite1Width());
        ((GameBoard) findViewById(R.id.the_canvas)).setSprite3(p3.x, p3.y);
        ((GameBoard) findViewById(R.id.the_canvas)).setSprite4(p4.x, p4.y);
        ((GameBoard) findViewById(R.id.the_canvas)).setSprite1(p1.x, p1.y);
        ((GameBoard) findViewById(R.id.the_canvas)).setSprite2(p2.x, p2.y);
        sprite3Velocity = getMoonVelocity();
        sprite4Velocity = getMoonVelocity();
        //Give the asteroid a random velocity
        sprite1Velocity = getRandomVelocity();
        //Fix the ship velocity at a constant speed for now
        sprite2Velocity = new Point(1, 1);
        //Set our boundaries for the sprites
        sprite1MaxX = findViewById(R.id.the_canvas).getWidth() -
                ((GameBoard) findViewById(R.id.the_canvas)).getSprite1Width();
        sprite1MaxY = findViewById(R.id.the_canvas).getHeight() -
                ((GameBoard) findViewById(R.id.the_canvas)).getSprite1Height();
        sprite2MaxX = findViewById(R.id.the_canvas).getWidth() -
                ((GameBoard) findViewById(R.id.the_canvas)).getSprite2Width();
        sprite2MaxY = findViewById(R.id.the_canvas).getHeight() -
                ((GameBoard) findViewById(R.id.the_canvas)).getSprite2Height();
        sprite3MaxX = findViewById(R.id.the_canvas).getWidth() -
                ((GameBoard) findViewById(R.id.the_canvas)).getSprite3Width();
        sprite3MaxY = findViewById(R.id.the_canvas).getHeight() -
                ((GameBoard) findViewById(R.id.the_canvas)).getSprite3Height();
        sprite4MaxX = findViewById(R.id.the_canvas).getWidth() -
                ((GameBoard) findViewById(R.id.the_canvas)).getSprite4Width();
        sprite4MaxY = findViewById(R.id.the_canvas).getHeight() -
                ((GameBoard) findViewById(R.id.the_canvas)).getSprite4Height();
        ((Button) findViewById(R.id.the_button)).setEnabled(true);
        frame.removeCallbacks(frameUpdate);
        frame.postDelayed(frameUpdate, FRAME_RATE);

    }

    @Override
    synchronized public void onClick(View v) {
        initGfx();
    }

    private Runnable frameUpdate = new Runnable() {
        @Override
        synchronized public void run() {
            if (((GameBoard)findViewById(R.id.the_canvas)).wasCollisionDetected()) {
                Point collisionPoint =
                        ((GameBoard)findViewById(R.id.the_canvas)).getLastCollision();
                if (collisionPoint.x>=0) {
                    ((TextView)findViewById(R.id.the_other_label)).setText("Last Collision XY ("+Integer.toString(collisionPoint.x)+","+Integer.toString(collisionPoint.y)+")");
                }
                //turn off the animation until reset gets pressed
                return;
            }
                frame.removeCallbacks(frameUpdate);
            //First get the current positions of both sprites
            Point sprite1 = new Point
                    (((GameBoard) findViewById(R.id.the_canvas)).getSprite1X(),
                            ((GameBoard) findViewById(R.id.the_canvas)).getSprite1Y());
            Point sprite2 = new Point
                    (((GameBoard) findViewById(R.id.the_canvas)).getSprite2X(),
                            ((GameBoard) findViewById(R.id.the_canvas)).getSprite2Y());
            Point sprite3 = new Point
                    (((GameBoard) findViewById(R.id.the_canvas)).getSprite3X(),
                            ((GameBoard) findViewById(R.id.the_canvas)).getSprite3Y());
            Point sprite4 = null;


            //Now calc the new positions.
            //Note if we exceed a boundary the direction of the velocity gets reversed.
            sprite1.x = sprite1.x + sprite1Velocity.x;
            if (sprite1.x > sprite1MaxX || sprite1.x < 5) {
                sprite1Velocity.x *= -1;
            }
            sprite1.y = sprite1.y + sprite1Velocity.y;
            if (sprite1.y > sprite1MaxY || sprite1.y < 5) {
                sprite1Velocity.y *= -1;
            }
            sprite2.x = sprite2.x + sprite2Velocity.x;
            if (sprite2.x > sprite2MaxX || sprite2.x < 5) {
                sprite2Velocity.x *= -1;
            }
            sprite2.y = sprite2.y + sprite2Velocity.y;
            if (sprite2.y > sprite2MaxY || sprite2.y < 5) {
                sprite2Velocity.y *= -1;
            }
            sprite3.x = sprite3.x + sprite3Velocity.x;
          if (sprite3.x > sprite3MaxX + 1 || sprite3.x < 6) {
             //sprite3Velocity.x = -3;
              //Toast.makeText(MainActivity.this,"here",Toast.LENGTH_LONG).show();
              if(sprite4 == null) {
                  sprite4 = new Point
                          (((GameBoard) findViewById(R.id.the_canvas)).getSprite4X(),
                                  ((GameBoard) findViewById(R.id.the_canvas)).getSprite4Y());
                  //sprite3=null;

              }

                  sprite4.x = sprite4.x + sprite4Velocity.x;
                  sprite4.y = sprite4Velocity.y;

                  //reappeare here
              //sprite3 = ;
          }

            sprite3.y =  sprite3Velocity.y;


            ((GameBoard) findViewById(R.id.the_canvas)).setSprite1(sprite1.x,
                    sprite1.y);
            ((GameBoard) findViewById(R.id.the_canvas)).setSprite2(sprite2.x, sprite2.y);


           ((GameBoard) findViewById(R.id.the_canvas)).setSprite3(sprite3.x,sprite3.y);
            if(sprite4!=null) {
                ((GameBoard) findViewById(R.id.the_canvas)).setSprite4(sprite4.x, sprite4.y);
            }
                ((GameBoard) findViewById(R.id.the_canvas)).invalidate();
            frame.postDelayed(frameUpdate, FRAME_RATE);
        }
    };

}