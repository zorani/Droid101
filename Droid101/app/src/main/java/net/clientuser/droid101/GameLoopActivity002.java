package net.clientuser.droid101;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by ClientUser.net on 10/01/16.
 */
public class GameLoopActivity002 extends Activity {

    Activity_GameLoop002_Layout gameLoop002_LayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameLoop002_LayoutView = new Activity_GameLoop002_Layout(this);
        setContentView(gameLoop002_LayoutView);
    }

    @Override
    protected void onResume() {
        Log.d("Super/Game", "Resume");
        super.onResume();
        gameLoop002_LayoutView.resume();

    }

    @Override
    protected void onPause() {
        Log.d("Super/Game", "Pause");
        super.onPause();
        gameLoop002_LayoutView.pause();

    }




}
