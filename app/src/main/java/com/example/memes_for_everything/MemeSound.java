package com.example.memes_for_everything;

import java.util.Locale;

public class MemeSound {

    public String Name;
    public String Source;
    public String Theme;

    public int sound_id, image_id;

    public MemeSound(String name, String source, String theme, int sound, int image) {
        Name = name;
        Source = source;
        Theme = theme;
        sound_id = sound;
        image_id = image;
    }

    public boolean filterChecked(String search) {
        search = search.toLowerCase(Locale.ROOT);
        return
                Name.toLowerCase(Locale.ROOT).contains(search) ||
                Source.toLowerCase(Locale.ROOT).contains(search) ||
                Theme.toLowerCase(Locale.ROOT).contains(search);
    }
}
