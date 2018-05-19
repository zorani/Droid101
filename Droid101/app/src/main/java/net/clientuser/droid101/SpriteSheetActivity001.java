package net.clientuser.droid101;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


/**
 * Created by ClientUser.net on 10/01/16.
 */
public class SpriteSheetActivity001 extends Activity {

    Activity_SpriteSheet001_Layout spriteSheet001_LayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spriteSheet001_LayoutView = new Activity_SpriteSheet001_Layout(this);
        setContentView(spriteSheet001_LayoutView);
    }

    @Override
    protected void onResume() {
        Log.d("Super/Game", "Resume");
        super.onResume();
        spriteSheet001_LayoutView.resume();

    }

    @Override
    protected void onPause() {
        Log.d("Super/Game", "Pause");
        super.onPause();
        spriteSheet001_LayoutView.pause();

    }




}
