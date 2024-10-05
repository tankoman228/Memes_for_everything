package com.example.memes_for_everything;

import android.annotation.SuppressLint;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static MainActivity THIS;

    private RecyclerView recyclerView;
    private MemeSoundAdapter adapter;
    private EditText editText;
    private Resources resourcesLoader;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    List<MemeSound> soundListAfterSearch = new ArrayList<>();
    private int SortMode = 3;
    private int Columns = 2;

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
                search();
            }
        });

        THIS = this;

        recyclerView.setLayoutManager(new GridLayoutManager(this, Columns));

        findViewById(R.id.btnStop).setOnClickListener(l -> {
            try {
                mediaPlayer.stop();
            }
            catch (Exception e) { e.printStackTrace();}
        });
        findViewById(R.id.btnNoSort).setOnClickListener(l -> Sort(0));
        findViewById(R.id.btnByName).setOnClickListener(l -> Sort(1));
        findViewById(R.id.btnByCategory).setOnClickListener(l -> Sort(2));
        findViewById(R.id.btnInitial).setOnClickListener(l -> Sort(3));
        findViewById(R.id.btnLesserColumns).setOnClickListener(l -> {
            if (Columns > 1) {
                Columns--;
                recyclerView.setLayoutManager(new GridLayoutManager(this, Columns));
            }
        });
        findViewById(R.id.btnMoreColumns).setOnClickListener(l -> {
            Columns++;
            recyclerView.setLayoutManager(new GridLayoutManager(this, Columns));
        });

        search();

    }

    private void search() {
        soundListAfterSearch = new ArrayList<>();
        for (MemeSound meme: AllMemesList.All) {
            if (meme.filterChecked(editText.getText().toString())) {
                soundListAfterSearch.add(meme);
            }
        }
        Sort(SortMode);
    }

    private boolean flagDoubleClick = false;
    private void Sort(int mode) {

        switch (mode) {
            case 0:
                Collections.shuffle(soundListAfterSearch); break;
            case 1:
                soundListAfterSearch.sort(Comparator.comparing(meme -> meme.Name)); break;
            case 2:
                soundListAfterSearch.sort(Comparator.comparing(meme -> meme.Source)); break;
            default:
                break;
        }
        if (flagDoubleClick) {
            Collections.reverse(soundListAfterSearch);
        }
        adapter = new MemeSoundAdapter(MainActivity.this, soundListAfterSearch);
        recyclerView.setAdapter(adapter);

        if (SortMode != mode) {
            SortMode = mode;
            flagDoubleClick = false;
        }
        else if (!flagDoubleClick) {
            flagDoubleClick = true;
        }
    }

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