package net.clientuser.droid101;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

/**
 * Created by ClientUser.net on 19/05/15.
 */
public class SplashScreenActivity extends Activity {

    public LinearLayout linearLayout;
    public static int splashimage;

    //A ClientUser.net tutorial
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        linearLayout =(LinearLayout) findViewById(R.id.layout_splashscreen);

        if(splashimage==0)
        {
            linearLayout.setBackgroundResource(R.drawable.splashscreen);
        }
        else
        {

            linearLayout.setBackgroundResource(splashimage);

        }


        Thread displaySplash = new Thread(){


            public void run(){

                try{
                    sleep(5 * 1000);
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally{
                    //kill the app
                    finish();
                }

            }

        };

        displaySplash.start();








    }
    //Video Copyright 2015, ClientUser.net, Zoran Ilievski
}
