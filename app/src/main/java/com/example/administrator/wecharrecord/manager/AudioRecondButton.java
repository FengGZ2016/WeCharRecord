package com.example.administrator.wecharrecord.manager;

import android.content.Context;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by Administrator on 2017/6/4.
 * 自定义Button
 */

public class AudioRecondButton extends Button{
    //三个对话框的状态常量
    private static final int STATE_NORMAL = 1;
    private static final int STATE_RECORDING = 2;
    private static final int STATE_WANT_TO_CANCEL = 3;

    //垂直方向滑动取消的临界距离
    private static final int DISTANCE_Y_CANCEL = 50;
    //取消录音的状态值
    private static final int MSG_VOICE_STOP = 4;
    //当前状态
    private int mCurrentState = STATE_NORMAL;
    // 正在录音标记
    private boolean isRecording = false;
    //录音对话框
    private DialogManager mDialogManager;
    //核心录音类
    private AudioManager mAudioManager;
    //当前录音时长
    private float mTime = 0;
    // 是否触发了onlongclick，准备好了
    private boolean mReady;
    //标记是否强制终止
    private boolean isOverTime = false;
    //最大录音时长（单位:s）。def:60s
    private int mMaxRecordTime = 60;

    public AudioRecondButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AudioRecondButton(Context context) {
        super(context);
    }
}
