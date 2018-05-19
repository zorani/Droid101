package net.clientuser.droid101;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;



/**
 * Created for a ClientUser.net Tutorial on 05/05/15.
 */
public class AboutActivity extends Activity {
    //A ClientUser.net tutorial
    protected Droid101ApplicationClass app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
    //A ClientUser.net tutorial
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);




        TextView tv = (TextView)findViewById(R.id.textView_activity_about);


        String receivedAction = getIntent().getAction();

        if (receivedAction != null) {
            //Action detected.
            //Must be Incoming IMPLICIT intent from another app that wants to use our activity.

            String receivedActionType = getIntent().getType();

            if (receivedAction.equals(Intent.ACTION_SEND)) {

                if (receivedActionType.startsWith("text/")) {

                    String data = getIntent().getStringExtra(Intent.EXTRA_TEXT);

                    tv.setText(data);

                }

            }



            if (receivedAction.equals(this.getString(R.string.CUSTOM_ACTION_DRINKBEER))){

            if (receivedActionType.startsWith("text/")) {
                String data = getIntent().getStringExtra(Intent.EXTRA_TEXT);
                tv.setText(data);
            }


            }


        }else{
            //No Action detected.
            //Explicit internal request from our own app, ahhh.... Billy no mates.

            Bundle extras = getIntent().getExtras();
            tv.setText(extras.getString("Message"));

        }


        app=(Droid101ApplicationClass)getApplication();
        app.playGSoundCoin();


        //A ClientUser.net tutorial
    }
}
