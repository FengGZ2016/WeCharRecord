package com.example.administrator.wecharrecord;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.wecharrecord.adapter.AudioAdapter;
import com.example.administrator.wecharrecord.db.DBDao;
import com.example.administrator.wecharrecord.manager.AudioRecondButton;
import com.example.administrator.wecharrecord.manager.MediaManager;
import com.example.administrator.wecharrecord.model.Record;
import com.example.administrator.wecharrecord.util.PermissionHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private AudioRecondButton mAudioRecondButton;
    private List<Record> mRecordList;
    private DBDao mDBDao;
    private AudioAdapter mAudioAdapter;
    private PermissionHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initAdapter();
        initPermission();
    }

    private void initPermission() {
        mAudioRecondButton.setHasRecordPromission(false);
        mHelper=new PermissionHelper(this);
        //申请权限
        mHelper.requestPermissions("请授于录音和读写权限，否则无法录音", new PermissionHelper.PermissionListener() {
            @Override
            public void doAfterGrand(String... permission) {
                mAudioRecondButton.setHasRecordPromission(true);
                mAudioRecondButton.setAudioFinishRecorderListener(new AudioRecondButton.AudioFinishRecorderListener() {
                    @Override
                    public void onFinished(float seconds, String filePath) {
                        //录音完成后
                        Record recordModel = new Record();
                        recordModel.setSecond((int) seconds <= 0 ? 1 : (int) seconds);
                        recordModel.setPath(filePath);
                        recordModel.setPlayed(false);
                        //添加到数据库
                        mDBDao.add(recordModel);
                       // mRecordList.add(recordModel);
                        mRecordList=mDBDao.queryAll();
                        initAdapter();
                       //mAudioAdapter.notifyDataSetChanged();


                    }
                });
            }

            @Override
            public void doAfterDenied(String... permission) {
                mAudioRecondButton.setHasRecordPromission(false);
                Toast.makeText(MainActivity.this, "请授权,否则无法录音", Toast.LENGTH_SHORT).show();
            }
        }, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 直接把参数交给mHelper就行了
     * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initAdapter() {
        mAudioAdapter=new AudioAdapter(this,mRecordList);
        mListView.setAdapter(mAudioAdapter);

       // mAudioAdapter.notifyDataSetChanged();
    }

    private void initData() {
//        mRecordList=new ArrayList<>();
        mDBDao=new DBDao(this);
        mRecordList=mDBDao.queryAll();
//        List<Record> records=mDBDao.queryAll();
//        if (records==null||records.isEmpty()){
//            return;
//        }
//        for (Record record:records){
//            Log.e("wgy", "initAdapter: "+record.toString() );
//        }
//        mRecordList.addAll(records);
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

    @Override
    protected void onPause() {
        //保证在退出该页面时，终止语音播放
        MediaManager.release();
        super.onPause();
    }

}
