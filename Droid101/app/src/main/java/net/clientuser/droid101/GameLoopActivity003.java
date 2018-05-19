package net.clientuser.droid101;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


/**
 * Created by ClientUser.net on 10/01/16.
 */
public class GameLoopActivity003 extends Activity {

    Activity_GameLoop003_Layout gameLoop003_LayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameLoop003_LayoutView = new Activity_GameLoop003_Layout(this);
        setContentView(gameLoop003_LayoutView);
    }

    @Override
    protected void onResume() {
        Log.d("Super/Game", "Resume");
        super.onResume();
        gameLoop003_LayoutView.resume();

    }

    @Override
    protected void onPause() {
        Log.d("Super/Game", "Pause");
        super.onPause();
        gameLoop003_LayoutView.pause();

    }




}
