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

public class InstrumentActivity extends AppCompatActivity {
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

        final List<Entity> instrumentsList = new ArrayList<>();
        instrumentsList.add(new Instrument(R.string.instruments_guitar, R.drawable.instruments_guitar, R.raw.instruments_guitar));
        instrumentsList.add(new Instrument(R.string.instruments_piano, R.drawable.instruments_piano, R.raw.instruments_piano));
        instrumentsList.add(new Instrument(R.string.instruments_saxophone, R.drawable.instruments_saxophone, R.raw.instruments_saxophon));
        instrumentsList.add(new Instrument(R.string.instruments_tambourine, R.drawable.instruments_tamburine, R.raw.instruments_tambourine));
        instrumentsList.add(new Instrument(R.string.instruments_violin, R.drawable.instruments_violin, R.raw.instruments_violin));
        instrumentsList.add(new Instrument(R.string.instruments_drums, R.drawable.instruments_dums, R.raw.instruments_dums));
        instrumentsList.add(new Instrument(R.string.instruments_trumpet, R.drawable.instruments_trumpet, R.raw.instruments_trumpets));
        instrumentsList.add(new Instrument(R.string.instruments_harp, R.drawable.instruments_harp, R.raw.instruments_harp));

        EntityAdapter adaptor = new EntityAdapter(this, instrumentsList, R.color.category_instrument);
        ListView listView = findViewById(R.id.list_entity);

        listView.setAdapter(adaptor);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                releaseMediaPlayer();
                Entity instrument = instrumentsList.get(i);

                int result = mAudioManager.requestAudioFocus(mAudioFocusChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(InstrumentActivity.this, instrument.getmSound());
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
