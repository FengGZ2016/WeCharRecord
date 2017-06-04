package com.example.administrator.wecharrecord.model;

/**
 * Created by Administrator on 2017/6/4.
 */

public class Record {
    private String id;
    private String path;
    private int second;//语音时长
    private boolean isPlayed;//是否已经播放过
    private boolean isPlaying;//是否正在播放

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                ", second=" + second +
                ", isPlayed=" + isPlayed +
                ", isPlaying=" + isPlaying +
                '}';
    }
}
