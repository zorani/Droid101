package net.clientuser.droid101;

import android.app.Application;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;

import java.io.IOException;

/**
 * Created by ClientUser.net on 13/06/15.
 */
public class Droid101ApplicationClass extends Application {

    SoundPool globalSoundPool;
    SoundPool.Builder globalSoundPoolBuilder;

    AudioAttributes globalAttributes;
    AudioAttributes.Builder globalAttributesBuilder;
    //A ClientUser.net tutorial
    AudioManager audioManager;
    float gCurVolume, gMaxVolume, gVolume;

    int globalSoundID_coin;

    public Boolean getSoundFX_state() {
        return soundFX_state;
    }

    public void setSoundFX_state(Boolean soundFX_state) {
        this.soundFX_state = soundFX_state;
    }

    Boolean soundFX_state;

    public Boolean getMedia_electronicloop_state() {
        return media_electronicloop_state;
    }

    public void setMedia_electronicloop_state(Boolean media_electronicloop_state) {
        this.media_electronicloop_state = media_electronicloop_state;
    }
    //A ClientUser.net tutorial
    Boolean media_electronicloop_state;
    MediaPlayer media_electronicloop;



    @Override
    public void onCreate() {
        super.onCreate();

        gCreateSoundPool();
        gLoadSounds();
        gVolumeSounds();
        //A ClientUser.net tutorial
        setSoundFX_state(true);
        setMedia_electronicloop_state(false);
        media_electronicloop = new MediaPlayer();


    }

    protected void musicPlay(){

        media_electronicloop.reset();
        media_electronicloop.setLooping(true);

        try{
            media_electronicloop.setDataSource(getApplicationContext(), Uri.parse("android.resource://net.clientuser.droid101/" + R.raw.electronicloop));
            media_electronicloop.prepare();
            media_electronicloop.start();
        }catch (IOException e){

            e.printStackTrace();

        }



    }


    protected void musicStop(){

        media_electronicloop.stop();

    }



    protected void gVolumeSounds(){
        //A ClientUser.net tutorial
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        gCurVolume = (float)audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        gMaxVolume = (float)audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        gVolume = gCurVolume / gMaxVolume;


    }

    protected void playGSoundCoin(){
        //A ClientUser.net Tutorial. :o)

        if(soundFX_state==true){
            globalSoundPool.play(globalSoundID_coin,gVolume,gVolume,0,0,1);

        }




    }

    protected void gLoadSounds(){

        //Visit ClientUser.net for more tutorials.
        globalSoundID_coin = globalSoundPool.load(this,R.raw.gamecoin,1);


    }

    protected void gCreateSoundPool(){

        //A www.ClientUser.net tutorial
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            globalAttributesBuilder = new AudioAttributes.Builder();
            globalAttributesBuilder.setUsage(AudioAttributes.USAGE_GAME);
            globalAttributesBuilder.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION);
            globalAttributes=globalAttributesBuilder.build();

            globalSoundPoolBuilder = new SoundPool.Builder();
            globalSoundPoolBuilder.setAudioAttributes(globalAttributes);
            globalSoundPool = globalSoundPoolBuilder.build();

        } else
        {
        //A ClientUser.net tutorial
            globalSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }

    }
}
