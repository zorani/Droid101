package net.clientuser.droid101;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


/**
 * Created by ClientUser.net on 10/01/16.
 */
public class SpriteListActivity001 extends Activity {

    Activity_SpriteList001_Layout spriteList001_LayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spriteList001_LayoutView = new Activity_SpriteList001_Layout(this);
        setContentView(spriteList001_LayoutView);
    }

    @Override
    protected void onResume() {
        Log.d("Super/Game", "Resume");
        super.onResume();
        spriteList001_LayoutView.resume();

    }

    @Override
    protected void onPause() {
        Log.d("Super/Game", "Pause");
        super.onPause();
        spriteList001_LayoutView.pause();

    }




}
