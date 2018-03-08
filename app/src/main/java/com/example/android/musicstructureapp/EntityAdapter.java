package com.example.android.musicstructureapp;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class EntityAdapter extends ArrayAdapter<Entity> {
    private int colorPage;

    public EntityAdapter(Context context, List<Entity> entityList, int colorPage) {
        super(context, 0, entityList);
        this.colorPage = colorPage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_entity, parent, false);
        }
        Entity currentEntity = getItem(position);

        ImageView songCoverImageView = listItemView.findViewById(R.id.song_cover_image_view);
        if (currentEntity.hasImage()) {
            songCoverImageView.setImageResource(currentEntity.getmImageResourceId());
            songCoverImageView.setVisibility(View.VISIBLE);
        } else {
            songCoverImageView.setVisibility(View.GONE);
        }

        TextView nameSongTextView = listItemView.findViewById(R.id.song_name_text_view);
        nameSongTextView.setText(currentEntity.getNameInstrument());

        View textContainer = listItemView.findViewById(R.id.song_name_text_view);
        int color = ContextCompat.getColor(getContext(), colorPage);
        textContainer.setBackgroundColor(color);

        return listItemView;

    }
}
