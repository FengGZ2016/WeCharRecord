package com.example.administrator.wecharrecord;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.administrator.wecharrecord.manager.AudioRecondButton;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private AudioRecondButton mAudioRecondButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mListView= (ListView) findViewById(R.id.list_view);
        mAudioRecondButton= (AudioRecondButton) findViewById(R.id.ad_btn);


    }
}
