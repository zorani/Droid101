package net.clientuser.droid101;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by ClientUser.net on 11/09/15.
 */
public class GameLoopActivity001 extends Activity {

    Activity_GameLoop001_Layout gameLoop001_LayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameLoop001_LayoutView = new Activity_GameLoop001_Layout(this);
        setContentView(gameLoop001_LayoutView);
    }

    @Override
    protected void onResume() {
        Log.d("Super/Game", "Resume");
        super.onResume();
        gameLoop001_LayoutView.resume();

    }

    @Override
    protected void onPause() {
        Log.d("Super/Game", "Pause");
        super.onPause();
        gameLoop001_LayoutView.pause();

    }
}