package com.example.memes_for_everything;

import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

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

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                List<MemeSound> nigger = new ArrayList<>();

                for (MemeSound meme: AllMemesList.All) {
                    if (meme.filterChecked(editText.getText().toString())) {
                        nigger.add(meme);
                    }
                }

                adapter = new MemeSoundAdapter(MainActivity.this, nigger);

                recyclerView.setAdapter(adapter);
            }
        });

        THIS = this;

        adapter = new MemeSoundAdapter(this, Arrays.asList(AllMemesList.All));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    MediaPlayer mediaPlayer = new MediaPlayer();

    public void playSound(int soundId) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }

        try {
            // Пауза, если уже воспроизводим
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset(); // Сбрасываем
            }

            AssetFileDescriptor afd = resourcesLoader.openRawResourceFd(soundId);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepare(); // Подготовка
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace(); // Обработка исключения
            mediaPlayer = new MediaPlayer();
            playSound(soundId);
        }
    }

    // Паша-Паша лысый гей, он насилует детей
}