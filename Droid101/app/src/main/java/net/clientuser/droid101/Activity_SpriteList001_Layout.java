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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ClientUser.net on 11/09/15.
 */
public class Activity_SpriteList001_Layout extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    //A ClientUser.net tutorial
    Thread thread = null;
    double frames_per_second, frame_time_seconds, frame_time_ms, frame_time_ns;
    double tLF, tEOR, delta_t,physics_rate,dt;

    Paint red_paintbrush_fill, blue_paintbrush_fill, green_paintbrush_fill;
    Paint red_paintbrush_stroke,blue_paintbrush_stroke,green_paintbrush_stroke;

    //A ClientUser.net tutorial

    //A ClientUser.net tutorial

    Boolean CanDraw;
    SurfaceHolder surfaceHolder;
    Bitmap backGroundCheck;
    Canvas canvas;

    //screen info
    int cheight, cwidth;



    //Declare List of type Sprite_Arrow, note casting List to type Sprite_Arrow
    List<Sprite_Arrow> mySpriteList = new ArrayList<Sprite_Arrow>();

    public Activity_SpriteList001_Layout(Context context) {
        super(context);
        //A ClientUser.net tutorial
        backGroundCheck = BitmapFactory.decodeResource(getResources(), R.drawable.check);
        CanDraw= false;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        frames_per_second=15;
        frame_time_seconds=1/frames_per_second;
        frame_time_ms=frame_time_seconds*1000;
        frame_time_ns=frame_time_ms*1000000;
        //1s=1,000 ms
        //1s=1,000,000,000 ns
        //1ms=1,000,000 ns

        // 50 physics updates per second, = 1/50 sec = (1/50)*1,000,000,000 nanosecs
        // 1 physics update every 20,000,000 nanoseconds
        physics_rate=60;
        dt=(1/physics_rate)*1000000000;

        //Add objects to our list.
        //A ClientUser.net tutorial
        mySpriteList.add( new Sprite_Arrow(this.getContext(),1,40,1,-1) );
        mySpriteList.add( new Sprite_Arrow(this.getContext(),0,60,5,-2) );
        mySpriteList.add( new Sprite_Arrow(this.getContext(),0,15,10,200) );
        mySpriteList.add( new Sprite_Arrow(this.getContext(),1,120,1,20) );
        mySpriteList.add( new Sprite_Arrow(this.getContext(),1,240,1,10) );
        mySpriteList.add( new Sprite_Arrow(this.getContext(),0,300,1,10) );
        //1 diag , 0 rectangular wall follow
        //spritespeed dpx/sec  speed of object
        //spriteframespeedup multiplier frame rate
        //size < 0
        //     -1= 1 * source size
        //     -2= 2 * source size
        //     -3= 3 * source size
        //size > 0
        //      density pxl values
        mySpriteList.set(1,new Sprite_Arrow(this.getContext(),1,42,1,-1));
        mySpriteList.set(2,new Sprite_Arrow(this.getContext(),1,44,1,-1));
        mySpriteList.set(3,new Sprite_Arrow(this.getContext(),1,46,1,-1));
        mySpriteList.set(4,new Sprite_Arrow(this.getContext(),1,48,1,-1));
        mySpriteList.set(5,new Sprite_Arrow(this.getContext(),1,50,1,-1));
        //Lets add a new object.
        mySpriteList.add(new Sprite_Arrow(this.getContext(),1,52,3,-3));
        //Lets remove the first object.
        mySpriteList.remove(0);


    }

    @Override
    public void run() {
        //A ClientUser.net tutorial

        prepPaintBrushes();

        tLF= System.nanoTime();
        delta_t=0;

        //GAME LOOP START
        while (CanDraw) {
            // DO SOME X,Y STATE UPDATES
            update_BIG_DELTA(delta_t);
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
        //GAME LOOP END
    }


    private void update_BIG_DELTA(double delta_t){
        //A ClientUser.net tutorial
        if (delta_t <0){
            delta_t = frame_time_ns - delta_t;
            Log.d("NEGATIVE","NEGATIVE");
        }
        Log.d("UPDATE Delta_t", Double.toString(delta_t));
        //A ClientUser.net tutorial


        while ((delta_t>=dt)){
            update(dt);
            delta_t=delta_t-dt;


            if ( (delta_t>0)&(delta_t<dt)){
                //A ClientUser.net tutorial
                //At this point if 0 < delta_t < dt
                //you have a fraction of a dt left.
                //Were going to go ahead and do the full dt step anyway


                update(dt);


            }




        }






    }

    private void update(double delta_t){
        //A ClientUser.net tutorial
        //delta_t is in nanoseconds, physics calculations  are in seconds...
        //need to convert, delta_t/1000000000

        for (int i=0; i<(mySpriteList.size()); i++){

           mySpriteList.get(i).update(delta_t / 1000000000);
        }




    }



    private void draw(){
        //A ClientUser.net tutorial
        canvas = surfaceHolder.lockCanvas();
        canvas.drawBitmap(backGroundCheck, 0, 0, null);
        canvas.drawCircle(cwidth, cheight, toPxs(10), red_paintbrush_fill);

        for (int i=0; i<(mySpriteList.size()); i++){

            mySpriteList.get(i).draw(canvas);
        }


        surfaceHolder.unlockCanvasAndPost(canvas);
        //A ClientUser.net tutorial







    }


    private void prepPaintBrushes(){
        red_paintbrush_fill = new Paint();
        red_paintbrush_fill.setColor(Color.RED);
        red_paintbrush_fill.setStyle(Paint.Style.FILL);
        //A ClientUser.net tutorial
        blue_paintbrush_fill = new Paint();
        blue_paintbrush_fill.setColor(Color.BLUE);
        blue_paintbrush_fill.setStyle(Paint.Style.FILL);
        //A ClientUser.net tutorial
        green_paintbrush_fill = new Paint();
        green_paintbrush_fill.setColor(Color.GREEN);
        green_paintbrush_fill.setStyle(Paint.Style.FILL);
        //A ClientUser.net tutorial
        red_paintbrush_stroke = new Paint();
        red_paintbrush_stroke.setColor(Color.RED);
        red_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        red_paintbrush_stroke.setStrokeWidth(10);
        //A ClientUser.net tutorial
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
            //A ClientUser.net tutorial
            try {
                Log.d("Thread", "Joining" );
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //A ClientUser.net tutorial
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
        //A ClientUser.net tutorial
        Log.d("Frames_per_second", Double.toString(frames_per_second));
        Log.d("Frame_time_seconds", Double.toString(frame_time_seconds));
        Log.d("Frame_time_ms", Double.toString(frame_time_ms));
        Log.d("Frame_Time_NS", Double.toString(frame_time_ns));
        Log.d("TLF", Double.toString(tLF));
        Log.d("TEOR", Double.toString(tEOR));
        Log.d("F_delta_t", Double.toString(delta_t));
        Log.d("delta_t sec", Double.toString(delta_t / 1000000000));
        Log.d("-----","---------");
        //A ClientUser.net tutorial
        Log.d("canvas height", Float.toString(cheight));
        Log.d("canvas width", Float.toString(cwidth));
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        cheight=height;
        cwidth=width;

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}