package net.clientuser.droid101;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Created by ClientUser.net on 13/06/15.
 */
public class SettingsActivity extends Activity {

    private RadioGroup radioGroup_soundFX;
    private RadioButton radioButton_sfxOn,radioButton_sfxOff;

    protected Droid101ApplicationClass app;

    private Switch musicSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        //A ClientUser.net tutorial

        app=(Droid101ApplicationClass)getApplication();

        radioGroup_soundFX=(RadioGroup)findViewById(R.id.setting_rg_sfx);
        radioButton_sfxOn=(RadioButton)findViewById(R.id.setting_rb_sfx_on);
        radioButton_sfxOff=(RadioButton)findViewById(R.id.setting_rb_sfx_off);

        musicSwitch=(Switch)findViewById(R.id.settings_music_switch);

        if(app.getMedia_electronicloop_state()){
            musicSwitch.setChecked(true);
        }else{
            musicSwitch.setChecked(false);
        }

        if(app.getSoundFX_state()){
            radioButton_sfxOn.setChecked(true);

        }else{
            radioButton_sfxOff.setChecked(true);
        }

        radioGroup_soundFX.setOnCheckedChangeListener(

                new RadioGroup.OnCheckedChangeListener()
                {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        RadioButton selectedButton=(RadioButton)findViewById(checkedId);

                        if(selectedButton.getId()==R.id.setting_rb_sfx_off){

                            Toast.makeText(getApplicationContext(), "SFX Off, Prog Handler", Toast.LENGTH_SHORT).show();
                            app.setSoundFX_state(false);

                        }



                    }

                }

        );


        // A ClientUser.net tutorial

        musicSwitch.setOnCheckedChangeListener(

                new CompoundButton.OnCheckedChangeListener()
                {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                        if(isChecked){
                            Toast.makeText(getApplicationContext(), "Music On", Toast.LENGTH_SHORT).show();
                            app.setMedia_electronicloop_state(true);
                            app.musicPlay();


                        }else{
                            Toast.makeText(getApplicationContext(), "Music Off", Toast.LENGTH_SHORT).show();
                            app.setMedia_electronicloop_state(false);
                            app.musicStop();


                        }


                    }


                }

        );








    }

    public void onSFXRadioButtonClick(View view){

        Toast.makeText(getApplicationContext(), "SFX On, XML Handler", Toast.LENGTH_SHORT).show();
        app.setSoundFX_state(true);


    }
}
