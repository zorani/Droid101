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
public class Activity_GameLoop004_Layout extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    //A ClientUser.net tutorial
    Thread thread = null;
    double frames_per_second, frame_time_seconds, frame_time_ms, frame_time_ns;
    double tLF, tEOR, delta_t,physics_rate,dt,dt_pool;

    Paint red_paintbrush_fill, blue_paintbrush_fill, green_paintbrush_fill;
    Paint red_paintbrush_stroke,blue_paintbrush_stroke,green_paintbrush_stroke;

    //A ClientUser.net tutorial
    //--- Density Pixel Cannon Ball Trajectory Variables ---
    double d_cannon_ball_xo, d_cannon_ball_yo;
    double d_cannon_ball_xtn, d_cannon_ball_ytn,d_cannon_ball_xtn_dt, d_cannon_ball_ytn_dt;
    double acceleration_x, acceleration_y  ;
    double velocity_o, velocity_xo, velocity_yo;
    double velocity_xtn, velocity_ytn,velocity_xtn_dt, velocity_ytn_dt;
    //Launch angle, radians from Y-AXIS, remember we are watching in landscape.
    double angle_theta;
    double dist_to_wall,dist_to_floor;

    //A ClientUser.net tutorial

    Boolean CanDraw;
    SurfaceHolder surfaceHolder;
    Bitmap backGroundCheck;
    Canvas canvas;

    //screen info
    float cheight, cwidth;


    public Activity_GameLoop004_Layout(Context context) {
        super(context);
        //A ClientUser.net tutorial
        backGroundCheck = BitmapFactory.decodeResource(getResources(), R.drawable.check);
        CanDraw= false;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        frames_per_second=5;
        frame_time_seconds=1/frames_per_second;
        frame_time_ms=frame_time_seconds*1000;
        frame_time_ns=frame_time_ms*1000000;
        //1s=1,000 ms
        //1s=1,000,000,000 ns
        //1ms=1,000,000 ns

        // 50 physics updates per second, = 1/50 sec = (1/50)*1,000,000,000 nanosecs
        // 1 physics update every 20,000,000 nanoseconds
        physics_rate=50;
        dt=(1/physics_rate)*1000000000;
        dt_pool=0;


        //cannon ball initial conditions
        ////////////////////////////////
        d_cannon_ball_xo=toPxs(0);
        d_cannon_ball_yo=toPxs(0);
        //15deg=0.261799rad
        //30deg=0.523599rad
        //45deg=0.785398rad
        //60deg=1.047200rad

        angle_theta = 0.523599;
        velocity_o = toPxs(100);

        velocity_xo = velocity_o*Math.sin(angle_theta);
        velocity_yo = velocity_o*Math.cos(angle_theta);

        acceleration_x = toPxs(-5);
        acceleration_y = 0;

        //Initial iteration settings.
        /////////////////////////////////////
        velocity_ytn=velocity_yo;
        velocity_xtn=velocity_xo;
        d_cannon_ball_xtn=d_cannon_ball_xo;
        d_cannon_ball_ytn=d_cannon_ball_yo;



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
            testFloatingPoints();
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

        delta_t=delta_t+dt_pool;
        dt_pool=0;

        while ((delta_t>=dt)){
            update(dt);
            delta_t=delta_t-dt;

            hitfloor();
            hitwall();

        }

        dt_pool=delta_t;






    }



    private void update(double delta_t){

        //delta_t is in nanoseconds, trajectory values are in seconds...
        //need to convert

        velocity_xtn_dt = velocity_xtn + (acceleration_x*delta_t/1000000000);
        velocity_ytn_dt = velocity_ytn + (acceleration_y*delta_t/1000000000);

        d_cannon_ball_xtn_dt = d_cannon_ball_xtn + ( velocity_xtn * delta_t/1000000000 ) + (0.5 * acceleration_x * (delta_t/1000000000) * (delta_t/1000000000));
        d_cannon_ball_ytn_dt = d_cannon_ball_ytn + ( velocity_ytn * delta_t/1000000000 ) + (0.5 * acceleration_y * (delta_t/1000000000) * (delta_t/1000000000));

        velocity_xtn=velocity_xtn_dt;
        velocity_ytn=velocity_ytn_dt;
        d_cannon_ball_xtn=d_cannon_ball_xtn_dt;
        d_cannon_ball_ytn=d_cannon_ball_ytn_dt;

    }


    private void hitwall(){
        //Check if cannon hits the wall set at, canvas.getHeight()

        dist_to_wall = cheight - d_cannon_ball_ytn_dt;

        Log.d("**********cheight", Double.toString(cheight));
        Log.d("***d_cannon_ball_ytn_dt", Double.toString(d_cannon_ball_ytn_dt));
        Log.d("******dist_to_wall", Double.toString(dist_to_wall));

        if(  ( dist_to_wall < toPxs(3)) & (dist_to_wall > 0) ){

            Log.d("/////","///////hit the walll");

            velocity_ytn=0;
            velocity_ytn_dt=0;
            acceleration_y=0;
        }
    }

    private void hitfloor(){
        //Check if cannon hits the wall set at, canvas.getHeight()

        dist_to_floor = d_cannon_ball_xtn_dt;

        if(  ( dist_to_floor < toPxs(3)) & ( dist_to_floor > 0) & (velocity_xtn_dt < 0) ){

            Log.d("/////","///////hit the floor");

            velocity_xtn=0;
            velocity_xtn_dt=0;
            acceleration_x=0;

            //once we hit the floor, we can stop the update draw loop.
            CanDraw=false;
        }
    }


    private void draw(){
        //A ClientUser.net tutorial
        canvas = surfaceHolder.lockCanvas();
        canvas.drawBitmap(backGroundCheck, 0, 0, null);
        canvas.drawCircle(cwidth, cheight, toPxs(10), red_paintbrush_fill);

        //cannon ball
        canvas.drawCircle((float) d_cannon_ball_xtn_dt, (float) d_cannon_ball_ytn_dt, toPxs(3), red_paintbrush_fill);


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

    // private int toPxs(int dps){
    //   return (int) (dps * getResources().getDisplayMetrics().density);
    // }

    private int toPxs(float dps){
        //A ClientUser.net tutorial
        return (int) (dps * getResources().getDisplayMetrics().density + 0.5f);
    }

    private void testFloatingPoints() {
        //User floating point 0.5f to round density conversions to nearest integer.
        //Also show operationally 0.5f=0.5F=(float)0.5
        int af, bf, cf;
        int aF, bF, cF;
        int afloat, bfloat, cfloat;

        af = (int) (0f + 0.5f);
        bf = (int) (0.5f + 0.5f);
        cf = (int) (1f + 0.5f);
        Log.d("FLOAT_f_TEST_A", Integer.toString(af));
        Log.d("FLOAT_f_TEST_B", Integer.toString(bf));
        Log.d("FLOAT_f_TEST_C", Integer.toString(cf));
        /////
        aF = (int) (0F + 0.5F);
        bF = (int) (0.5F + 0.5F);
        cF = (int) (1F + 0.5F);
        Log.d("FLOAT_F_TEST_A", Integer.toString(aF));
        Log.d("FLOAT_F_TEST_B", Integer.toString(bF));
        Log.d("FLOAT_F_TEST_C", Integer.toString(cF));
        /////
        afloat = (int) ((float) 0 + (float) 0.5);
        bfloat = (int) ((float) 0.5 + (float) 0.5);
        cfloat = (int) ((float) 1 + (float) 0.5);
        Log.d("FLOAT_float_TEST_A", Integer.toString(afloat));
        Log.d("FLOAT_float_TEST_B", Integer.toString(bfloat));
        Log.d("FLOAT_float_TEST_C", Integer.toString(cfloat));
    }

        // (int)(0f + 0.5f)=0
        // (int)(0.5f + 0.5f)=1
        // (int)(1f + 0.5f)=1

        //OUTPUT YOU SHOULD SEE
        //A ClientUser.net tutorial

        //net.clientuser.droid101 D/FLOAT_f_TEST_A: 0
        //net.clientuser.droid101 D/FLOAT_f_TEST_B: 1
        //net.clientuser.droid101 D/FLOAT_f_TEST_C: 1
        //A ClientUser.net tutorial
        //net.clientuser.droid101 D/FLOAT_F_TEST_A: 0
        //net.clientuser.droid101 D/FLOAT_F_TEST_B: 1
        //net.clientuser.droid101 D/FLOAT_F_TEST_C: 1
        //A ClientUser.net tutorial
        //net.clientuser.droid101 D/FLOAT_float_TEST_A: 0
        //net.clientuser.droid101 D/FLOAT_float_TEST_B: 1
        //net.clientuser.droid101 D/FLOAT_float_TEST_C: 1





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
        Log.d("d_cannon_ball_xtn_dt", Double.toString(d_cannon_ball_xtn_dt));
        Log.d("d_cannon_ball_ytn_dt", Double.toString(d_cannon_ball_ytn_dt));
        Log.d("velocity_xo",Double.toString(velocity_xo));
        Log.d("velocity_yo",Double.toString(velocity_yo));
        Log.d("velocity_xtn_dt", Double.toString(velocity_xtn_dt));
        Log.d("velocity_ytn_dt", Double.toString(velocity_ytn_dt));
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