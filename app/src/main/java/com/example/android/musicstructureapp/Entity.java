package com.example.android.musicstructureapp;


public abstract class Entity {
    public int name;
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    public static final int NO_IMAGE_PROVIDED = -1;

    public int mSoundId;

    public Entity(int name, int imageResourceId, int soundResource) {
        this.name = name;
        this.mImageResourceId = imageResourceId;
        this.mSoundId = soundResource;
    }

    public Entity(int name, int mSoundId) {
        this.name = name;
        this.mSoundId = mSoundId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    public int getNameInstrument() {
        return name;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public int getmSound() {
        return mSoundId;
    }

}
