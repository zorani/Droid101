package net.clientuser.droid101;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        Button myfirstbutton_m2 = (Button)findViewById(R.id.button_myfirstbutton_m2);

        myfirstbutton_m2.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, MyFirstButtonActivity.class);
                        startActivity(intent);
                    }
                }
        );




    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;

        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {

            Intent intent = new Intent(this, AboutActivity.class);
            intent.putExtra("Message","Hey, wassssup? Did my app make $1000000s yet?");
            startActivity(intent);

            return true;

        }

        if (id == R.id.implicit_share) {

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String stringToShare="Hey guys, have you seen the latest tutorial on clientuser.net";
            sharingIntent.putExtra(Intent.EXTRA_TEXT, stringToShare);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));



            return true;

        }

        if (id == R.id.drink_beer){


            Intent intent = new Intent(getString(R.string.CUSTOM_ACTION_DRINKBEER));
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "WoW!! HOme brew intent!");
            startActivity(intent);

        }





        return super.onOptionsItemSelected(item);
    }


    public void sendMessage(View view) {

        switch (view.getId()){

            case R.id.button_myfirstbutton_m1: {
                //do something for first button.
                Intent intent = new Intent(this.getString(R.string.CUSTOM_ACTION_MYFIRSTBUTTON));
                startActivity(intent);
                break;
            }

            case R.id.imageButton_myfirstbutton: {

                Intent intent = new Intent(this.getString(R.string.CUSTOM_ACTION_MYFIRSTBUTTON));
                startActivity(intent);
                break;

            }

        }


    }



}
