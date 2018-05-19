package net.clientuser.droid101;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by ClientUser.net on 21/07/15.
 */
public class Activity_Animation002_Layout extends SurfaceView implements Runnable {

    Thread thread = null;
    boolean CanDraw = false;

    Paint red_paintbrush_fill, blue_paintbrush_fill, green_paintbrush_fill;
    Paint red_paintbrush_stroke,blue_paintbrush_stroke,green_paintbrush_stroke;
    Path square = new Path();
    Path d_square = new Path();

    Bitmap queen;

    //A ClientUser.net tutorial
    //--- Pixel Coordinates ---
    int queen_x, queen_y;
    int circle_x, circle_y;

    //A ClientUser.net tutorial
    //--- Density Pixel Coordinates ---
    int d_circle_x, d_circle_y;
    int d_queen_x, d_queen_y;



    Bitmap backGroundCheck;
    Canvas canvas;
    SurfaceHolder surfaceHolder;

    public Activity_Animation002_Layout(Context context) {
        super(context);
        surfaceHolder = getHolder();
        backGroundCheck = BitmapFactory.decodeResource(getResources(), R.drawable.check);
        queen = BitmapFactory.decodeResource(getResources(), R.drawable.queen);

        //A ClientUser.net tutorial
        //--- Pixel Coordinates ---
        queen_x=650 ;
        queen_y=130 ;
        circle_x=130;
        circle_y=130;

        //A ClientUser.net tutorial
        //--- Density Pixel Coordinates ---
        d_queen_x=toPxs(65)  ;
        d_queen_y=toPxs(325) ;
        d_circle_x=toPxs(65);
        d_circle_y=toPxs(65);


    }

    @Override
    public void run() {
        //A ClientUser.net tutorial

        prepPaintBrushes();

        while (CanDraw) {
            //Carry out some drawing...

            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }


            canvas = surfaceHolder.lockCanvas();
            motionQueen(10);
            motionCircle(10);
            motion_d_Circle(5);
            motion_d_Queen(5);
            canvas.drawBitmap(backGroundCheck, 0, 0, null);
            drawSquare(130, 130, 650, 650);
            drawDensitySquare(65, 65, 325, 325);
            canvas.drawBitmap(queen, queen_x - (queen.getWidth() / 2), queen_y - (queen.getHeight() / 2), null);
            canvas.drawCircle(circle_x, circle_y, 50, green_paintbrush_fill);
            //Density Pixel Drawing
            canvas.drawCircle(d_circle_x, d_circle_y, toPxs(25), red_paintbrush_stroke);
            canvas.drawBitmap(queen, d_queen_x - (queen.getWidth() / 2), d_queen_y - (queen.getHeight()/2) ,null);
            surfaceHolder.unlockCanvasAndPost(canvas);


        }

    }


    public void pause(){
        //A ClientUser.net tutorial
        CanDraw=false;

        while(true){

            try {
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        thread=null;


    }

    public void resume(){
        //A ClientUser.net tutorial
        CanDraw = true;
        thread = new Thread(this);
        thread.start();


    }

    private void prepPaintBrushes(){

        red_paintbrush_fill = new Paint();
        red_paintbrush_fill.setColor(Color.RED);
        red_paintbrush_fill.setStyle(Paint.Style.FILL);

        blue_paintbrush_fill = new Paint();
        blue_paintbrush_fill.setColor(Color.BLUE);
        blue_paintbrush_fill.setStyle(Paint.Style.FILL);

        green_paintbrush_fill = new Paint();
        green_paintbrush_fill.setColor(Color.GREEN);
        green_paintbrush_fill.setStyle(Paint.Style.FILL);

        red_paintbrush_stroke = new Paint();
        red_paintbrush_stroke.setColor(Color.RED);
        red_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        red_paintbrush_stroke.setStrokeWidth(10);

        blue_paintbrush_stroke = new Paint();
        blue_paintbrush_stroke.setColor(Color.BLUE);
        blue_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        blue_paintbrush_stroke.setStrokeWidth(10);

        green_paintbrush_stroke = new Paint();
        green_paintbrush_stroke.setColor(Color.GREEN);
        green_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        green_paintbrush_stroke.setStrokeWidth(10);

    }

    private void drawSquare(int x1, int y1, int x2, int y2){

        square.moveTo(x1, y1);
        square.lineTo(x2, y1);
        square.moveTo(x2, y1);
        square.lineTo(x2, y2);
        square.moveTo(x2, y2);
        square.lineTo(x1, y2);
        square.moveTo(x1, y2);
        square.lineTo(x1, y1);

        this.canvas.drawPath(square, green_paintbrush_stroke);

    }

    private void motionQueen(int speed){

        if( (queen_y == 130) && (queen_x < 650) ){
            queen_x = queen_x+speed;
        }

        if( (queen_y < 650) && (queen_x == 650) ){
            queen_y = queen_y+speed;
        }

        if( (queen_y == 650) && (queen_x > 130) ){
            queen_x = queen_x - speed;
        }

        if( (queen_y > 130) && (queen_x == 130) ){
            queen_y = queen_y - speed;
        }

    }

    private void motionCircle(int speed){

        if( (circle_y == 130) && (circle_x < 650) ){
            circle_x = circle_x+speed;
        }

        if( (circle_y < 650) && (circle_x == 650) ){
            circle_y = circle_y+speed;
        }

        if( (circle_y == 650) && (circle_x > 130) ){
            circle_x = circle_x - speed;
        }

        if( (circle_y > 130) && (circle_x == 130) ){
            circle_y = circle_y - speed;
        }

    }

    private int toPxs(int dps){
        return (int) (dps * getResources().getDisplayMetrics().density);

    }

    private void motion_d_Queen(int speed){

        if( (d_queen_y == toPxs(65) ) && (d_queen_x < toPxs(325) )){
            d_queen_x = d_queen_x+toPxs(speed);
        }

        if( (d_queen_y < toPxs(325)) && (d_queen_x == toPxs(325)) ){
            d_queen_y = d_queen_y+toPxs(speed);
        }

        if( (d_queen_y == toPxs(325)) && (d_queen_x > toPxs(65)) ){
            d_queen_x = d_queen_x - toPxs(speed);
        }

        if( (d_queen_y > toPxs(65)) && (d_queen_x == toPxs(65)) ){
            d_queen_y = d_queen_y - toPxs(speed);
        }

    }


    private void motion_d_Circle(int speed){



        if( (d_circle_y == toPxs(65) ) && (d_circle_x < toPxs(325) )){
            d_circle_x = d_circle_x+toPxs(speed);
        }

        if( (d_circle_y < toPxs(325)) && (d_circle_x == toPxs(325)) ){
            d_circle_y = d_circle_y+toPxs(speed);
        }

        if( (d_circle_y == toPxs(325)) && (d_circle_x > toPxs(65)) ){
            d_circle_x = d_circle_x - toPxs(speed);
        }

        if( (d_circle_y > toPxs(65)) && (d_circle_x == toPxs(65)) ){
            d_circle_y = d_circle_y - toPxs(speed);
        }


    }

    private void drawDensitySquare(int x1, int y1, int x2, int y2){

        int xdp1, ydp1, xdp2, ydp2;

        xdp1 = toPxs(x1);
        ydp1 = toPxs(y1);
        xdp2 = toPxs(x2);
        ydp2 = toPxs(y2);


        d_square.moveTo(xdp1, ydp1);
        d_square.lineTo(xdp2, ydp1);

        d_square.moveTo(xdp2, ydp1);
        d_square.lineTo(xdp2, ydp2);

        d_square.moveTo(xdp2, ydp2);
        d_square.lineTo(xdp1, ydp2);

        d_square.moveTo(xdp1, ydp2);
        d_square.lineTo(xdp1, ydp1);


        this.canvas.drawPath(d_square, red_paintbrush_stroke);

    }











}
