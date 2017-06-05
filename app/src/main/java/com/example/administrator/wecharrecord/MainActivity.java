package com.example.administrator.wecharrecord;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.administrator.wecharrecord.adapter.AudioAdapter;
import com.example.administrator.wecharrecord.db.DBDao;
import com.example.administrator.wecharrecord.manager.AudioRecondButton;
import com.example.administrator.wecharrecord.model.Record;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private AudioRecondButton mAudioRecondButton;
    private List<Record> mRecordList;
    private DBDao mDBDao;
    private AudioAdapter mAudioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initAdapter();
    }

    private void initAdapter() {
        mAudioAdapter=new AudioAdapter(this,mRecordList);
        mListView.setAdapter(mAudioAdapter);
        List<Record> records=mDBDao.queryAll();
        if (records==null||records.isEmpty()){
            return;
        }
        for (Record record:records){
            Log.e("wgy", "initAdapter: "+record.toString() );
        }
        mRecordList.addAll(records);
        mAudioAdapter.notifyDataSetChanged();
    }

    private void initData() {
        mRecordList=new ArrayList<>();
        mDBDao=new DBDao(this);
    }

    private void initView() {
        mListView= (ListView) findViewById(R.id.list_view);
        mAudioRecondButton= (AudioRecondButton) findViewById(R.id.ad_btn);


    }

    public DBDao getDBDao() {
        return mDBDao;
    }

    public void setDBDao(DBDao mDBDao) {
       this.mDBDao=mDBDao;
    }
}
