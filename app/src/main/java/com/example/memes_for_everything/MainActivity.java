package com.example.memes_for_everything;

import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SoundPool mSoundPool;
    private RecyclerView recyclerView;
    private MemeSoundAdapter adapter;
    private EditText editText;

    public static MainActivity THIS;
    private Resources resourcesLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.rv);
        editText = findViewById(R.id.et);
        resourcesLoader = getResources();

        THIS = this;

        adapter = new MemeSoundAdapter(this, Arrays.asList(AllMemesList.All));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        AudioAttributes attributes = new AudioAttributes.Builder().
                setUsage(AudioAttributes.USAGE_GAME).
                setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();

        mSoundPool = new SoundPool.Builder().
                setAudioAttributes(attributes).build();
    }


    public void playSound(int sound_id) {
        Log.d("play", "play");

        AssetFileDescriptor afd;
        try {
            afd = resourcesLoader.openRawResourceFd(sound_id);
            int soundPoolId = mSoundPool.load(afd, 1);

            mSoundPool.setOnLoadCompleteListener((soundPool, loadedSoundId, status) -> {
                if (status == 0) { // Успешно
                    mSoundPool.play(loadedSoundId, 1, 1, 1, 0, 1);
                } else {
                    Log.e("SoundPool", "Error loading sound");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}