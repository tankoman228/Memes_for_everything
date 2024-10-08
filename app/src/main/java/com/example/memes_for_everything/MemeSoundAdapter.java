package com.example.memes_for_everything;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MemeSoundAdapter extends RecyclerView.Adapter<MemeSoundAdapter.ViewHolder> {

    private List<MemeSound> memeSounds;
    private Context context;

    public MemeSoundAdapter(Context context, List<MemeSound> memeSounds) {
        this.context = context;
        this.memeSounds = memeSounds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sound, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MemeSound memeSound = memeSounds.get(position);
        holder.soundName.setText(memeSound.Name);
        holder.soundSrc.setText(memeSound.Source);
        holder.imageButton.setImageResource(memeSound.image_id);



        holder.imageButton.setOnClickListener(v -> {
            MainActivity.THIS.playSound(memeSound.sound_id);
        });
    }

    @Override
    public int getItemCount() {
        return memeSounds.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton imageButton;
        TextView soundName;
        TextView soundSrc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.image_button);
            soundName = itemView.findViewById(R.id.sound_name);
            soundSrc = itemView.findViewById(R.id.sound_source);
        }
    }
}
