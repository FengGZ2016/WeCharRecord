package com.example.administrator.wecharrecord.manager;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/6/4.
 * 录音核心类
 */

public class MyAudioManager{
    private MediaRecorder mRecorder;
    //文件夹位置
    private String mDirString;
    //录音文件保存路径
    private String mCurrentFilePathString;
    //是否真备好开始录音
    private boolean isPrepared;

    /**
     * 监听接口，监听是否准备好录音
     * */
    public interface AudioStageListener{
        void wellPrepared();
    }

    public AudioStageListener mAudioStageListener;

    /**
     * 设置监听器
     * */
    public void setOnAudioStageListener(AudioStageListener mAudioStageListener){
        this.mAudioStageListener=mAudioStageListener;
    }

    private MyAudioManager(String dir) {
        mDirString = dir;
    }

    /**
     * 单例化这个类
     */
    private static MyAudioManager mInstance;

    /**
     * 单例模式
     * */
    public static MyAudioManager getmInstance(String dir){
        if (mInstance==null){
            synchronized (MyAudioManager.class){
                if (mInstance==null){
                    mInstance=new MyAudioManager(dir);
                }
            }
        }
        return mInstance;
    }

   /**
    * 录音准备方法
    * */
    public void prepareAudio(){
        //默认为未准备好
        isPrepared=false;
        //创建所属文件夹
        File dir=new File(mDirString);
        if (!dir.exists()){
            dir.mkdirs();
        }
        //文件名字
        String fileName=generalFileName();
        //创建文件
        File file=new File(dir,fileName);
        //获取文件的路径
        mCurrentFilePathString=file.getAbsolutePath();
        //初始化MediaRecorder
        initMediaRecorder();
        //完成准备，开始录制
        if(mAudioStageListener!=null){
            mAudioStageListener.wellPrepared();
        }


    }


    /**
     * 初始化MediaRecorder
     * */
    private void initMediaRecorder() {
        //实例化MediaRecorder
        mRecorder=new MediaRecorder();
        //设置输出文件
        mRecorder.setOutputFile(mCurrentFilePathString);
        //设置MediaRecorder的音频源是麦克风
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //设置MediaRecorder的输出格式为amr
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        //设置MediaRecorder的编码格式为amr，这里采用AAC主要为了适配IOS，保证在IOS上可以正常播放
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        try {
            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //准备工作完成
        isPrepared=true;

    }

    /**
     * 获取声音的Level
     * */
    public int getVoiceLevel(int maxLevel){
        // mRecorder.getMaxAmplitude()这个是音频的振幅范围，值域是1-32767
        if (isPrepared){
            // 取证+1，否则去不到7
            return maxLevel*mRecorder.getMaxAmplitude()/32768+1;
        }
        return 1;
    }


    /**
     * 释放资源
     * */
    public void release(){
        if (mRecorder==null){
            return;
        }
        try {
            //下面三个参数必须加，不加的话会奔溃，再mediarecorder.stop();
            //报错为：RuntimeException:stop failed
            mRecorder.setOnErrorListener(null);
            mRecorder.setOnInfoListener(null);
            mRecorder.setPreviewDisplay(null);
            mRecorder.stop();
        } catch (IllegalStateException e) {
            Log.i("Exception", Log.getStackTraceString(e) + "123");
        } catch (RuntimeException e) {
            Log.i("Exception", Log.getStackTraceString(e) + "123");
        } catch (Exception e) {
            Log.i("Exception", Log.getStackTraceString(e) + "123");
        }
        mRecorder.release();
        mRecorder = null;
    }


    /**
     * cancel时删除这个文件
     * */
    public void cancel(){
        release();
        if (mCurrentFilePathString!=null){
            File file=new File(mCurrentFilePathString);
            file.delete();
            mCurrentFilePathString=null;
        }
    }

    /**
     * 返回录音文件的路径
     * */
    public String getCurrentFilePath(){
        return mCurrentFilePathString;
    }


    /**
     * 系统当前时间作为文件名
     * */
    private String generalFileName() {
        SimpleDateFormat mat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate=new Date(System.currentTimeMillis());
        String dateStr=mat.format(curDate);
        return dateStr+".amr";
    }


}
