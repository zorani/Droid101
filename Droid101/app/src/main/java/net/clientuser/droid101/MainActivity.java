package net.clientuser.droid101;

import android.app.Application;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    //Public class MainActivity, the default activity for the Droid101 App.
    //Video Copyright 2015, ClientUser.net, Zoran Ilievski
    //Extends ActionBarActivity and implements OnClickListener

    SoundPool soundPool;
    SoundPool.Builder soundPoolBuilder;

    AudioAttributes attributes;
    AudioAttributes.Builder attributesBuilder;

    int soundID_coin;

    AudioManager audioManager;
    float curVolume, maxVolume, volume;

    protected Droid101ApplicationClass app;

    GridView gridView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        //A ClientUser.net tutorial

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        //A ClientUser.net tutorial
        Button myfirstbutton_m2 = (Button)findViewById(R.id.button_myfirstbutton_m2);
        myfirstbutton_m2.setOnClickListener(this);

        Button button_coinsound = (Button)findViewById(R.id.button_coinsound);
        button_coinsound.setOnClickListener(this);
        //A ClientUser.net tutorial
        Button button_lowsound = (Button)findViewById(R.id.button_lowsound);
        button_lowsound.setOnClickListener(this);
        //A ClientUser.net tutorial
        Button button_appsound = (Button)findViewById(R.id.button_appsound);
        button_appsound.setOnClickListener(this);


        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        createSoundPool();
        loadSounds();

        app=(Droid101ApplicationClass)getApplication();


        gridView = (GridView)findViewById(R.id.activity_main_gridView);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setOnItemClickListener(this);




    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId()==R.id.activity_main_gridView){

            switch (position){

                case 0:{

                    startActivity(new Intent(this, AnimationActivity001.class));
                    break;
                }

                case 1:{

                    startActivity(new Intent(this, MyFirstButtonActivity.class));
                    break;

                }

                case 2:{

                    startActivity(new Intent(this, AnimationActivity002.class));
                    break;
                }

                case 3:{

                    startActivity(new Intent(this, GameLoopActivity001.class));
                    break;
                }


                case 4:{

                    startActivity(new Intent(this, GameLoopActivity002.class));
                    break;
                }

                case 5:{

                    startActivity(new Intent(this, GameLoopActivity003.class));
                    break;
                }

                case 6:{

                    startActivity(new Intent(this, GameLoopActivity004.class));
                    break;
                }

                case 7:{

                    startActivity(new Intent(this, SpriteSheetActivity001.class));
                    break;
                }

                case 8:{

                    startActivity(new Intent(this, SpriteArrayActivity001.class));
                    break;
                }

                case 9:{

                    startActivity(new Intent(this, SpriteListActivity001.class));
                    break;
                }

                default:{

                    Toast.makeText(getApplicationContext(), "No action associated with this item.", Toast.LENGTH_LONG).show();
                    break;

                }
            }



        }

    }




    protected void volumeSounds(){
        //A ClientUser.net tutorial
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        curVolume = (float)audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = (float)audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        volume = curVolume / maxVolume;


    }

    protected void loadSounds(){

        //Visit ClientUser.net for more tutorials.
        soundID_coin = soundPool.load(this,R.raw.gamecoin,1);


    }

    protected void createSoundPool(){

    //A www.ClientUser.net tutorial
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

        attributesBuilder = new AudioAttributes.Builder();
        attributesBuilder.setUsage(AudioAttributes.USAGE_GAME);
        attributesBuilder.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION);
        attributes=attributesBuilder.build();

        soundPoolBuilder = new SoundPool.Builder();
        soundPoolBuilder.setAudioAttributes(attributes);
        soundPool = soundPoolBuilder.build();

    } else
    {

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
    }

}


    @Override
    protected void onPause() {
        //A ClientUser.net tutorial
        super.onPause();
        soundPool.release();


    }

    @Override
    protected void onResume() {
        //A ClientUser.net tutorial
        super.onResume();
        createSoundPool();
        loadSounds();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Video Copyright 2015, ClientUser.net, Zoran Ilievski
        // Make sure you list menu items in the menu XML file stated below.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // Video Copyright 2015, ClientUser.net, Zoran Ilievski
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);


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
        //Button click event handler. XML.
        //sendMessage used when defining the click handler in
        //Video Copyright 2015, ClientUser.net, Zoran Ilievski
        //the xml layout file for the activity class

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

    @Override
    public void onClick(View view){
        //Button click event handler. Programmatic.
        //Video Copyright 2015, ClientUser.net, Zoran Ilievski
        //Declare buttons in OnCreate() member method.
        switch (view.getId()){
            case R.id.button_myfirstbutton_m2: {
                //A ClientUser.net tutorial

                Intent intent = new Intent(this, MyFirstButtonActivity.class);
                startActivity(intent);
                break;


            }
            case R.id.button_coinsound: {
                //A ClientUser.net tutorial
                soundPool.play(soundID_coin, 1, 1, 0, 0, 1);
                break;

            }
            case R.id.button_lowsound: {
                //A ClientUser.net tutorial
                volumeSounds();
                soundPool.play(soundID_coin, volume/8,volume/8 , 0, 0, 1);
                break;


            }
            case R.id.button_appsound: {
                //A ClientUser.net tutorial
                Toast.makeText(getApplicationContext(),"App Button Pressed", Toast.LENGTH_SHORT).show();

                app.playGSoundCoin();

                break;


            }
        }
    }



}
