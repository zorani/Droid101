package net.clientuser.droid101;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


/**
 * Created by ClientUser.net on 10/01/16.
 */
public class GameLoopActivity004 extends Activity {

    Activity_GameLoop004_Layout gameLoop004_LayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameLoop004_LayoutView = new Activity_GameLoop004_Layout(this);
        setContentView(gameLoop004_LayoutView);
    }

    @Override
    protected void onResume() {
        Log.d("Super/Game", "Resume");
        super.onResume();
        gameLoop004_LayoutView.resume();

    }

    @Override
    protected void onPause() {
        Log.d("Super/Game", "Pause");
        super.onPause();
        gameLoop004_LayoutView.pause();

    }




}
