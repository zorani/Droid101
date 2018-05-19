package net.clientuser.droid101;

import android.app.Activity;
import android.os.Bundle;


/**
 * Created by ClientUser.net on 19/06/15.
 */
public class AnimationActivity001 extends Activity {

    Activity_Animation001_Layout animation001_LayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        animation001_LayoutView = new Activity_Animation001_Layout(this);
        setContentView(animation001_LayoutView);


    }







}
