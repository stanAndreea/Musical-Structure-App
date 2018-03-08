package com.example.android.musicstructureapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AnimalsActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    private AudioManager.OnAudioFocusChangeListener mAudioFocusChange = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entity_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final List<Entity> animalsList = new ArrayList<>();
        animalsList.add(new Animals(R.string.animals_dog, R.drawable.animals_dog, R.raw.animals_dog));
        animalsList.add(new Animals(R.string.animals_cat, R.drawable.animals_cat, R.raw.animals_cat));
        animalsList.add(new Animals(R.string.animals_frog, R.drawable.animals_frog, R.raw.animals_frog));
        animalsList.add(new Animals(R.string.animals_bear, R.drawable.animals_bear, R.raw.animals_bear));
        animalsList.add(new Animals(R.string.animals_wolf, R.drawable.animals_walf, R.raw.animal_walf));
        animalsList.add(new Animals(R.string.animals_bird, R.drawable.animals_bird, R.raw.animals_bird));
        animalsList.add(new Animals(R.string.animals_chicken, R.drawable.animals_chicken, R.raw.animals_chicken));
        animalsList.add(new Animals(R.string.animals_cow, R.drawable.animals_cow, R.raw.animals_cow));
        animalsList.add(new Animals(R.string.animals_cricket, R.drawable.animals_cricket, R.raw.animals_cricket));
        animalsList.add(new Animals(R.string.animals_eagle, R.drawable.animals_eagle, R.raw.animals_eagle));
        animalsList.add(new Animals(R.string.animals_horses, R.drawable.animals_horses, R.raw.animals_horses));
        animalsList.add(new Animals(R.string.animals_lion, R.drawable.animals_lion, R.raw.animals_lion));
        animalsList.add(new Animals(R.string.animals_monkey, R.drawable.animals_monkey, R.raw.animals_mankey));
        animalsList.add(new Animals(R.string.animals_owl, R.drawable.animals_owl, R.raw.animals_owl));
        animalsList.add(new Animals(R.string.animals_pig, R.drawable.animals_pig, R.raw.animals_pig));
        animalsList.add(new Animals(R.string.animals_whales, R.drawable.animals_whales, R.raw.animals_whales));
        EntityAdapter adaptor = new EntityAdapter(this, animalsList, R.color.category_animals);
        ListView listView = findViewById(R.id.list_entity);

        listView.setAdapter(adaptor);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();
                Entity animals = animalsList.get(i);

                int result = mAudioManager.requestAudioFocus(mAudioFocusChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(AnimalsActivity.this, animals.getmSound());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;

            mAudioManager.abandonAudioFocus(mAudioFocusChange);
        }
    }
}
