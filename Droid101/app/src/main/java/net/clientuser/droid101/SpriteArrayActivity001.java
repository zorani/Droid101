package net.clientuser.droid101;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


/**
 * Created by ClientUser.net on 10/01/16.
 */
public class SpriteArrayActivity001 extends Activity {

    Activity_SpriteArray001_Layout spriteArray001_LayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spriteArray001_LayoutView = new Activity_SpriteArray001_Layout(this);
        setContentView(spriteArray001_LayoutView);
    }

    @Override
    protected void onResume() {
        Log.d("Super/Game", "Resume");
        super.onResume();
        spriteArray001_LayoutView.resume();

    }

    @Override
    protected void onPause() {
        Log.d("Super/Game", "Pause");
        super.onPause();
        spriteArray001_LayoutView.pause();

    }




}
