package net.clientuser.droid101;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

/**
 * Created by ClientUser.net on 19/07/15.
 */
public class AnimationActivity002 extends Activity {
//Draw directly to the Canvas, using surface view.
//A ClientUser.net tutorial

    Activity_Animation002_Layout animation002_LayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Visit ClientUser.net for more tutorials
        animation002_LayoutView = new Activity_Animation002_Layout(this);
        setContentView(animation002_LayoutView);
        Log.d("AnimationActivity002", "A ClientUser.net Tutorial - On Create");

    }

    @Override
    protected void onPause() {
        super.onPause();
        //Visit ClientUser.net for more tutorials
        Log.d("AnimationActivity002", "A ClientUser.net Tutorial - On Pause");
        animation002_LayoutView.pause();
      }

    @Override
    protected void onResume() {
        super.onResume();
        //Visit ClientUser.net for more tutorials
        Log.d("AnimationActivity002", "A ClientUser.net Tutorial - On Resume");
        animation002_LayoutView.resume();
    }


    @Override
    public void onBackPressed() {
        //Visit ClientUser.net for more tutorials
        //onBackPressed() works for versions including and above Android (ECLAIR) 2.0, API 5.
        Log.d("AnimationActivity002", "A ClientUser.net Tutorial - Back Button Pressed");
        super.onBackPressed(); //Actually calls the finish() method in superclass,
        // could replace with finish();
    }






}
