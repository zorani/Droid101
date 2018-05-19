package net.clientuser.droid101;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by ClientUser.net on 28/06/15.
 */
public class Activity_Animation001_Layout extends View {

    Paint red_paintbrush_fill, blue_paintbrush_fill, green_paintbrush_fill;
    Paint red_paintbrush_stroke,blue_paintbrush_stroke,green_paintbrush_stroke;
    Path triangle;
    Bitmap cherry_bm;
    int cherry_x,cherry_y;
    int x_dir, y_dir;
    int cherryHeight, cherryWidth;

    public Activity_Animation001_Layout(Context context) {
        super(context);
        setBackgroundResource(R.drawable.check);
        cherry_x=320;
        cherry_y=470;
        x_dir=1;
        y_dir=1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

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

        Rect rectangle001 = new Rect();
        rectangle001.set(210, 125, 250, 175);
        canvas.drawRect(rectangle001, green_paintbrush_stroke);

        Rect rectangle002 = new Rect();
        rectangle002.set(420, 125, 460, 175);
        canvas.drawRect(rectangle002, red_paintbrush_fill);

        canvas.drawCircle(400, 400, 70, blue_paintbrush_stroke);
        canvas.drawCircle(400, 400, 20, green_paintbrush_fill);
        canvas.drawCircle(400, 400, 10, red_paintbrush_fill);

        triangle = new Path();
        triangle.moveTo(400, 400);
        triangle.lineTo(600, 600);
        triangle.moveTo(600, 600);
        triangle.lineTo(200, 600);
        triangle.moveTo(200, 600);
        triangle.lineTo(400, 400);

        canvas.drawPath(triangle, red_paintbrush_stroke);

        canvas.drawCircle(600, 600, 70, blue_paintbrush_stroke);
        canvas.drawCircle(600, 600, 20, green_paintbrush_fill);
        canvas.drawCircle(600, 600, 10, red_paintbrush_fill);

        canvas.drawCircle(200, 600, 70, blue_paintbrush_stroke);
        canvas.drawCircle(200, 600, 20, green_paintbrush_fill);
        canvas.drawCircle(200, 600, 10, red_paintbrush_fill);

        cherry_bm = BitmapFactory.decodeResource(getResources(),R.drawable.cherry);

        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(),R.drawable.cherry,option);
        cherryHeight = option.outHeight;
        cherryWidth = option.outWidth;

        if (cherry_x >= canvas.getWidth()-cherryWidth){
            x_dir= -1;
        }

        if (cherry_x <= 0){
            x_dir= 1;
        }

        if (cherry_y >= canvas.getHeight()-cherryHeight){
            y_dir= -1;
        }

        if (cherry_y <= 0){
            y_dir= 1;
        }



        cherry_x = cherry_x + x_dir;
        cherry_y = cherry_y + y_dir;

        canvas.drawBitmap(cherry_bm,cherry_x,cherry_y,null);

        invalidate();



    }
}
