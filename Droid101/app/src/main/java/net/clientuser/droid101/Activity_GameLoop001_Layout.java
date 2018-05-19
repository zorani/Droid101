package net.clientuser.droid101;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by ClientUser.net on 11/09/15.
 */
public class Activity_GameLoop001_Layout extends SurfaceView implements Runnable {

    //A ClientUser.net tutorial
    Thread thread = null;
    double frames_per_second, frame_time_seconds, frame_time_ms, frame_time_ns;
    double tLF, tEOR, delta_t;
    double theta, theta_per_sec;

    Paint red_paintbrush_fill, blue_paintbrush_fill, green_paintbrush_fill;
    Paint red_paintbrush_stroke,blue_paintbrush_stroke,green_paintbrush_stroke;

    //A ClientUser.net tutorial
    //--- Density Pixel Coordinates ---
    int d_circle_x, d_circle_y;


    Boolean CanDraw;
    SurfaceHolder surfaceHolder;
    Bitmap backGroundCheck;
    Canvas canvas;


    public Activity_GameLoop001_Layout(Context context) {
        super(context);
        //A ClientUser.net tutorial
        backGroundCheck = BitmapFactory.decodeResource(getResources(), R.drawable.check);
        CanDraw=false;
        surfaceHolder = getHolder();

        //Angle in Radians.
        theta=0;
        theta_per_sec=(Math.PI/2000000000);



        frames_per_second=60;
        frame_time_seconds=1/frames_per_second;
        frame_time_ms=frame_time_seconds*1000;
        frame_time_ns=frame_time_ms*1000000;
        //1s=1,000 ms
        //1s=1,000,000,000 ns
        //1ms=1,000,000 ns

    }

    @Override
    public void run() {
        //A ClientUser.net tutorial

        prepPaintBrushes();

        tLF= System.nanoTime();
        delta_t=0;

        while (CanDraw) {
            // DO SOME X,Y STATE UPDATES
            update(delta_t);
            //A ClientUser.net tutorial
            //END X,Y STATE UPDATES

            // DO SOME DRAWING
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            draw();
            //A ClientUser.net tutorial
            //END DRAWING

            //GET END OF RENDER TIMESTAMP, Returns nanoseconds
            tEOR=System.nanoTime();

            //CALCULATE DELTAT
            delta_t= frame_time_ns - (tEOR - tLF);
            //A ClientUser.net tutorial

            stats();


            //SLEEP THE FRAME FOR DELTA T
            try {
                //sleep takes milliseconds as long argument.  ms = ns / 1000000;
                //must cast double vars to long. Double are needed for decimal calculations.

                if(delta_t > 0 ) {
                    thread.sleep((long) (delta_t / 1000000));
                    // ms=ns/1000000;
                };



            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //END SLEEP

            //GET END OF LAST FRAME TIME
            tLF=System.nanoTime();


        }
    }


    private void update(double delta_t){
        //A ClientUser.net tutorial

        if (delta_t <0){
            delta_t = frame_time_ns - delta_t;

            Log.d("NEGATIVE","NEGATIVE");
        }

        Log.d("UPDATE Delta_t", Double.toString(delta_t));
        theta = theta + (theta_per_sec * delta_t);

        if(theta > (2*Math.PI) ){

            theta = theta - (2*Math.PI);
            //A ClientUser.net tutorial
}

        d_circle_x = (int)(toPxs(195) + (toPxs(130)*Math.sin(theta)));
        d_circle_y = (int) ( toPxs(65) +  ( toPxs(130) - ( toPxs(130) * Math.cos(theta)  )    )  );



    }

    private void draw(){
        //A ClientUser.net tutorial

          canvas = surfaceHolder.lockCanvas();
          canvas.drawBitmap(backGroundCheck, 0, 0, null);
          canvas.drawCircle(toPxs(195), toPxs(195), toPxs(130), blue_paintbrush_stroke);
          canvas.drawCircle(toPxs(195), toPxs(65), toPxs(15), blue_paintbrush_fill);
          //A ClientUser.net tutorial
          canvas.drawCircle(toPxs(195), toPxs(325), toPxs(15), green_paintbrush_fill);
          canvas.drawCircle(d_circle_x, d_circle_y, toPxs(10), red_paintbrush_fill);

          surfaceHolder.unlockCanvasAndPost(canvas);


    }


     private void prepPaintBrushes(){

        red_paintbrush_fill = new Paint();
        red_paintbrush_fill.setColor(Color.RED);
        red_paintbrush_fill.setStyle(Paint.Style.FILL);

        //A ClientUser.net tutorial

        blue_paintbrush_fill = new Paint();
        blue_paintbrush_fill.setColor(Color.BLUE);
        blue_paintbrush_fill.setStyle(Paint.Style.FILL);

        green_paintbrush_fill = new Paint();
        green_paintbrush_fill.setColor(Color.GREEN);
        green_paintbrush_fill.setStyle(Paint.Style.FILL);

        //A ClientUser.net tutorial

        red_paintbrush_stroke = new Paint();
        red_paintbrush_stroke.setColor(Color.RED);
        red_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        red_paintbrush_stroke.setStrokeWidth(10);

        blue_paintbrush_stroke = new Paint();
        blue_paintbrush_stroke.setColor(Color.BLUE);
        blue_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        blue_paintbrush_stroke.setStrokeWidth(10);

        //A ClientUser.net tutorial

        green_paintbrush_stroke = new Paint();
        green_paintbrush_stroke.setColor(Color.GREEN);
        green_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        green_paintbrush_stroke.setStrokeWidth(10);

    }



    private int toPxs(int dps){
      //A ClientUser.net tutorial
        return (int) (dps * getResources().getDisplayMetrics().density);

    }


    public void pause(){
        //A ClientUser.net tutorial
        CanDraw=false;
        Log.d("Thread", "Pausing thread..." + Thread.currentThread().getId());


        while(true){



            try {
                Log.d("Thread", "Joining" );
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        Log.d("Thread", "THREAD IS PAUSED " + Thread.currentThread().getId() );
        thread=null;
        Log.d("Thread", "NULLED ******");

    }

    public void resume(){
        //A ClientUser.net tutorial
        CanDraw = true;
        Log.d("CanDraw", "true");

        if(thread==null){
            Log.d("Thread", "MAKING NEW");
            thread = new Thread(this);
            Log.d("Thread", "STARTING NEW");
            thread.start();
            Log.d("Thread", "STARTED");
        };
    }

    private void stats(){

        Log.d("Frames_per_second", Double.toString(frames_per_second));
        Log.d("Frame_time_seconds", Double.toString(frame_time_seconds));
        Log.d("Frame_time_ms", Double.toString(frame_time_ms));
        Log.d("Frame_Time_NS", Double.toString(frame_time_ns));
        Log.d("TLF", Double.toString(tLF));
        Log.d("TEOR", Double.toString(tEOR));
        Log.d("F_delta_t", Double.toString(delta_t));
        Log.d("delta_t sec", Double.toString(delta_t / 1000000000));
        Log.d("-----","---------");


    }






}
