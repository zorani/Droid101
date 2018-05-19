package net.clientuser.droid101;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;


/**
 * Created by ClientUser.net on 20/03/16.
 */
public class Sprite_Arrow {
    //A ClientUser.net tutorial
    //Context holder, to hold context passed in to object.
    Context context;


    //***OBJECT IMPORTANT VARIABLES***
    // A ClientUser.net tutorial
    // local clock & counting.  Work in Seconds.
    double local_clock;
    int  sprite_frame_count;
    int  sprite_frame_speed_up;



    //***SOURCE VARIABLES***
    //SPRITE SHEET INFO
    // A ClientUser.net tutorial
    Bitmap spriteSheet;
    int ss_height,ss_width;
    //Source sprite frame copy info
    Rect src;
    int sf_src_width, sf_src_height;
    int sf_src_x_pos, sf_src_y_pos;
    int num_sprite_frame;

    //***DESTINATION VARIABLES***
    // A ClientUser.net tutorial
    //Canvas info

    //int canvas_width, canvas_height;

    int max_screen_width, max_screen_height;
    //Status bar measurements, from, Android Platform > res > values > dimens
    int status_bar_ID, status_bar_height;

    //Destination sprite frame paste info
    Rect dst;
    int sf_dst_width, sf_dst_height;
    double sf_dst_x_pos, sf_dst_y_pos;
    //Destination speed info
    int sf_dst_speed;
    //Destination direction info
    int sf_dst_x_dir, sf_dst_y_dir;

    public Sprite_Arrow(Context ctext, int motion , int spritespeed,int spriteframespeedup, int size) {
        this.context=ctext;

        // A ClientUser.net tutorial
        //motion 1=diag, 0=border
        //spritespeed dpx/sec  speed of object
        //spriteframespeedup multiplier frame rate
        //size < 0
        //     -1= 1 * source size
        //     -2= 2 * source size
        //     -3= 3 * source size
        //size > 0
        //      density pxl values


        //WHO AM I?
        // A ClientUser.net tutorial
        sprite_frame_speed_up=spriteframespeedup;
        sprite_frame_count=0;
        local_clock=0;

        //WHAT AM I?
        // A ClientUser.net tutorial
        spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrowsheet);
        ss_height=spriteSheet.getHeight();
        ss_width=spriteSheet.getWidth();
        num_sprite_frame=4;
        //This sprite has 8 rows, and 4 columns.
        //The dimensions of a source sprite frame is found by,
        sf_src_height=ss_height/8;
        sf_src_width=ss_width/4;


        //WHERE AM I?
        // A ClientUser.net tutorial


        max_screen_width = context.getResources().getDisplayMetrics().widthPixels;
        max_screen_height = context.getResources().getDisplayMetrics().heightPixels;

        status_bar_ID = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        //Android Platform > res > values > dimens
        status_bar_height = context.getResources().getDimensionPixelSize(status_bar_ID);

        //Sprite frame - initial destination position on phone.
        sf_dst_x_pos=0;
        sf_dst_y_pos=0;
        sf_dst_speed=toPxs(spritespeed);
        if(motion==1){sf_dst_x_dir=1;sf_dst_y_dir=1;}
        if(motion==0){sf_dst_x_dir=1; sf_dst_y_dir=0;}
        //You can set the destination sprite frame width and height as,
        if(size<0){

            sf_dst_width = -1*size*sf_src_width;
            sf_dst_height = -1*size*sf_src_height;

        }

        if(size>0){

            sf_dst_width = toPxs(size);
            sf_dst_height = toPxs(size);

        }

    }


    public void draw(Canvas canvas){
    //A ClientUser.net tutorial

        //source rectangle to copy from
        src = new Rect(sf_src_x_pos,sf_src_y_pos, sf_src_width+sf_src_x_pos, sf_src_height+sf_src_y_pos);
        //destination rectangle to paste to
        dst = new Rect((int)sf_dst_x_pos,(int)sf_dst_y_pos,(int)sf_dst_x_pos+sf_dst_width,(int)sf_dst_y_pos+sf_dst_height);

        //Carry out the "copy paste".

        canvas.drawBitmap(spriteSheet, src, dst, null);
    }


    public void update(double delta_t){
    //A ClientUser.net tutorial
        //Expects delta_t in Seconds

        src_calculations(delta_t);

        dst_calculations(delta_t);

    }

    private void src_calculations(double delta_t){
        //Expects delta_t in Seconds
        //A ClientUser.net tutorial
        //SOURCE CALCULATIONS

        //Given row number that matches sprite direction, what is the pixel coordinate.
        sf_src_y_pos=detect_sprite_dir() * sf_src_height;

        //clock, start at 0, keep addint dt slices of time.
        local_clock=local_clock+(delta_t*sprite_frame_speed_up);

        //if clock ticks to 1 , increase the frame count.
        //use this to manipulate sprite animation speed.
        if(local_clock > 1 ) {

            //sprite frame time will help us move
            //1 sprite frame/column to the right.
            sprite_frame_count++;
            //We have detected out clock has hit
            //the requred time period.
            //Time to reset it so it can
            //start to count again.
            local_clock=0;
        }

        //Use % to make sure we always get the correct row.
        //We know there are num_sprite_frames ( 4 in our case)
        //Multiplying by the src width give us our source x coordinates.
        sf_src_x_pos=(sprite_frame_count%num_sprite_frame)*sf_src_width;


        //I don't like uncontrolled counting.
        //Unchecked, variable sprite_frame_count could get very large.
        //Here, I just reset it to 0 when % operation is zero,
        //which gives the equivalent answer.
        if(sprite_frame_count%num_sprite_frame == 0){
            sprite_frame_count=0;
        }

    }

    private void dst_calculations(double delta_t){
        //Expects delta_t in Seconds
        //A ClientUser.net tutorial
        //DESTINATION CALCULATIONS

        if ( (sf_dst_y_dir == 0) || (sf_dst_x_dir == 0)  ) {
            //Motion is following the border.
            //Please watch ClientUser.net tutorial #45 for rectangular motion

            if((sf_dst_y_pos <= 0)&&(sf_dst_x_pos < (max_screen_width - sf_dst_width)) ){
                sf_dst_x_pos = (sf_dst_x_pos + (1 * sf_dst_speed * delta_t));
                Log.d("SPRITE_X_POS_SQUARE---", Double.toString(sf_dst_x_pos));
                sf_dst_x_dir=1;
                sf_dst_y_dir=0;
            }

            if( (sf_dst_y_pos < (max_screen_height-status_bar_height-sf_dst_height) ) && (sf_dst_x_pos >= (max_screen_width - sf_dst_width) ) ){
                sf_dst_y_pos = (sf_dst_y_pos + (1 * sf_dst_speed * delta_t));
                sf_dst_x_dir=0;
                sf_dst_y_dir=1;
            }

            if( (sf_dst_y_pos >= (max_screen_height-status_bar_height-sf_dst_height) ) && (sf_dst_x_pos >0)  ){
                sf_dst_x_pos = (sf_dst_x_pos + (-1 * sf_dst_speed * delta_t));
                sf_dst_x_dir=-1;
                sf_dst_y_dir=0;
            }

            if( (sf_dst_y_pos > 0 ) && (sf_dst_x_pos <= 0 )){
                sf_dst_y_pos = (sf_dst_y_pos + (-1 * sf_dst_speed * delta_t));
                sf_dst_x_dir=0;
                sf_dst_y_dir=-1;
            };

        }
        else{
            //motion is moving diagonally
            //Please watch ClientUser.net tutorial #40 for diagonal motion

            //////////////////////
            if (sf_dst_x_pos >= max_screen_width - sf_dst_width) {
                sf_dst_x_dir = -1;
                Log.d("SPRITE_X", "-1");
            }

            if (sf_dst_x_pos <= 0) {
                sf_dst_x_dir = 1;
                Log.d("SPRITE_X", "1");
            }

            if (sf_dst_y_pos >= max_screen_height - status_bar_height - sf_dst_height) {
                sf_dst_y_dir = -1;
                Log.d("SPRITE_Y", "-1");
            }

            if (sf_dst_y_pos <= 0) {
                sf_dst_y_dir = 1;
                Log.d("SPRITE_Y", "1");
            }


            sf_dst_x_pos = (sf_dst_x_pos + (sf_dst_x_dir * sf_dst_speed * delta_t));
            sf_dst_y_pos = (sf_dst_y_pos + (sf_dst_y_dir * sf_dst_speed * delta_t));

        }


    }


    private int detect_sprite_dir(){
        //A ClientUser.net tutorial
        //Return Row number on sprite sheet.

        int row=0;

        // East
        if((sf_dst_x_dir==1)&&(sf_dst_y_dir==0) ) row = 0;
        // South-East
        if((sf_dst_x_dir==1)&&(sf_dst_y_dir==1) ) row = 1;
        // South
        if((sf_dst_x_dir==0)&&(sf_dst_y_dir==1) ) row = 2;
        // South-West
        if((sf_dst_x_dir==-1)&&(sf_dst_y_dir==1) ) row = 3;
        // West
        // A ClientUser.net tutorial
        if((sf_dst_x_dir==-1)&&(sf_dst_y_dir==0) ) row = 4;
        // North-West
        if((sf_dst_x_dir==-1)&&(sf_dst_y_dir==-1) ) row = 5;
        // North
        if((sf_dst_x_dir==0)&&(sf_dst_y_dir==-1) ) row = 6;
        // North-East
        if((sf_dst_x_dir==1)&&(sf_dst_y_dir==-1) ) row = 7;

        //default
        //A ClientUser.net tutorial
        return row;

    }


    //A ClientUser.net tutorial
    private int toPxs(float dps){
        //A ClientUser.net tutorial
        return (int) (dps * this.context.getResources().getDisplayMetrics().density + 0.5f);
    }

}