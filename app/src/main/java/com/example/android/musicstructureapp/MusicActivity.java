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

public class MusicActivity extends AppCompatActivity {
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

        final List<Entity> songList = new ArrayList<>();
        songList.add(new Music(R.string.song_abc, R.raw.music_abc));
        songList.add(new Music(R.string.song_animals, R.raw.music_animals));
        songList.add(new Music(R.string.song_numbers, R.raw.music_numbers));
        songList.add(new Music(R.string.song_old_mc, R.raw.music_old_mcdolnad));
        songList.add(new Music(R.string.song_baby, R.raw.music_baby_shark_do));
        songList.add(new Music(R.string.song_tigger, R.raw.music_tigger));
        songList.add(new Music(R.string.song_scooby, R.raw.music_shobby));
        songList.add(new Music(R.string.song_addams, R.raw.music_addams));
        songList.add(new Music(R.string.song_frozen, R.raw.music_frozen));
        songList.add(new Music(R.string.song_hakuna, R.raw.music_hakuna));
        songList.add(new Music(R.string.song_moana, R.raw.music_moana));
        songList.add(new Music(R.string.song_like_move, R.raw.music_move));

        EntityAdapter adaptor = new EntityAdapter(this, songList, R.color.category_song);
        ListView listView = findViewById(R.id.list_entity);

        listView.setAdapter(adaptor);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();
                Entity song = songList.get(i);

                int result = mAudioManager.requestAudioFocus(mAudioFocusChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(MusicActivity.this, song.getmSound());
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
