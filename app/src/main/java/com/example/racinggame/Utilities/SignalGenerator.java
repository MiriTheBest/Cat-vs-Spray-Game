package com.example.racinggame.Utilities;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import com.example.racinggame.R;

public class SignalGenerator {

    private static SignalGenerator instance;
    private Context context;
    private static Vibrator vibrator;
    private static SoundPool soundPool;
    private static int angrySoundID;
    private static int purrSoundID;
    private SignalGenerator(Context context) {
        this.context = context;
    }

    public static void init(Context context) {
        if (instance == null) {
            instance = new SignalGenerator(context);
            vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
                soundPool = new SoundPool.Builder().setMaxStreams(3).setAudioAttributes(audioAttributes).build();
            }
            else {
                soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
            }
            angrySoundID = soundPool.load(context, R.raw.angry, 1);
            purrSoundID = soundPool.load(context, R.raw.purring, 1);
        }
    }

    public static SignalGenerator getInstance() {
        return instance;
    }

    public void toast(String text,int length){
        Toast
                .makeText(context,text,length)
                .show();
    }

    public void vibrate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(500);
        }
    }

    public void playAngrySound() {
        soundPool.play(angrySoundID, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playPurrSound() {
        soundPool.play(purrSoundID, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}
