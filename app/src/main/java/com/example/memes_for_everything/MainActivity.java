package com.example.memes_for_everything;

import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SoundPool mSoundPool;
    private Resources resourcesLoader;
    private RecyclerView recyclerView;
    private MemeSoundAdapter adapter;
    private EditText editText;

    public static MainActivity THIS;

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

        THIS = this;

        adapter = new MemeSoundAdapter(this, Arrays.asList(AllMemesList.All));
        recyclerView.setAdapter(adapter);

        AudioAttributes attributes = new AudioAttributes.Builder().
                setUsage(AudioAttributes.USAGE_GAME).
                setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build();

        mSoundPool = new SoundPool.Builder().
                setAudioAttributes(attributes).build();
    }

    public void playSound(int sound_id) {
        Log.d("play","play");
        mSoundPool.play(sound_id,1,1,1,0,1);
    }
}