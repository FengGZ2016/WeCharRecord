package com.example.administrator.wecharrecord.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.wecharrecord.MainActivity;
import com.example.administrator.wecharrecord.R;
import com.example.administrator.wecharrecord.manager.MediaManager;
import com.example.administrator.wecharrecord.model.Record;
import com.example.administrator.wecharrecord.util.CommonsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/4.
 */

public class AudioAdapter extends BaseAdapter{
    List<Record> mRecordList;
    Context mContext;
    List<AnimationDrawable> mAnimationDrawableList;
    int pos=-1;//记录当前索引，默认没有播放任何一个


    public AudioAdapter(Context mContext,List<Record> mRecordList){
        this.mContext=mContext;
        this.mRecordList=mRecordList;
        mAnimationDrawableList=new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mRecordList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRecordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view;
        if (convertView==null){
            view= LayoutInflater.from(mContext).inflate(R.layout.item_example_activity,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.ieaHeadImg = (ImageView) view.findViewById(R.id.iea_headImg);
            viewHolder.ieaIvVoiceLine = (ImageView) view.findViewById(R.id.iea_iv_voiceLine);
            viewHolder.ieaLlSinger = (LinearLayout) view.findViewById(R.id.iea_ll_singer);
            viewHolder.ieaTvVoicetime1 = (TextView) view.findViewById(R.id.iea_tv_voicetime1);
            viewHolder.ieaIvRed = (ImageView) view.findViewById(R.id.iea_iv_red);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }
        final Record record=mRecordList.get(position);
        //设置显示时长
        viewHolder.ieaTvVoicetime1.setText(record.getSecond() <= 0 ? 1 + "''" : record.getSecond() + "''");
        if (!record.isPlayed()) {
            //如果没有播放过，就显示红点
            viewHolder.ieaIvRed.setVisibility(View.VISIBLE);
        } else {
            //如果已经播放过，就隐藏红点
            viewHolder.ieaIvRed.setVisibility(View.GONE);
        }

        //更改并显示录音条长度
        RelativeLayout.LayoutParams ps = (RelativeLayout.LayoutParams) viewHolder.ieaIvVoiceLine.getLayoutParams();
        ps.width = CommonsUtils.getVoiceLineWight(mContext, record.getSecond());
        viewHolder.ieaIvVoiceLine.setLayoutParams(ps); //更改语音长条长度

        //信号栏
        final LinearLayout  layoutSinger=viewHolder.ieaLlSinger;
        //对语音条设置监听
        viewHolder.ieaIvVoiceLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //只要点击就设置为已播放状态（隐藏小红点）
                record.setPlayed(true);
                notifyDataSetChanged();
                // 这里更新数据库小红点。可以获取DBDao的实例来调用
                ((MainActivity) mContext).getDBDao().updateRecord(record);

                final AnimationDrawable animationDrawable = (AnimationDrawable) layoutSinger.getBackground();
                //重置动画
                resetAnim(animationDrawable);
                animationDrawable.start();
                //处理点击正在播放的语音时，可以停止；再次点击时重新播放。
                if (pos==position) {
                    if (record.isPlaying()) {
                        record.setPlaying(false);
                        MediaManager.release();
                        animationDrawable.stop();
                        animationDrawable.selectDrawable(0);//reset
                        return;
                    } else {
                        record.setPlaying(true);
                    }
                }
                //记录当前位置正在播放。
                pos = position;
                record.setPlaying(true);

                //播放前重置。
                MediaManager.release();
                //开始实质播放
                MediaManager.playSound(record.getPath(),
                        new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                //显示动画第一帧
                                animationDrawable.selectDrawable(0);
                                animationDrawable.stop();
                                //播放完毕，当前播放索引置为-1。
                                pos = -1;
                            }
                        });
            }
        });
        return view;
    }

    private void resetData() {
        for (Record record : mRecordList) {
            //保证在第二次点击该语音栏时当作没有“不是在播放状态”。
            record.setPlaying(false);
        }
    }

    /**
     * 重置动画
     * */
    private void resetAnim(AnimationDrawable animationDrawable) {
        if (!mAnimationDrawableList.contains(animationDrawable)) {
            mAnimationDrawableList.add(animationDrawable);
        }
        for (AnimationDrawable ad : mAnimationDrawableList) {
            ad.selectDrawable(0);
            ad.stop();
        }
    }

    class ViewHolder {
        ImageView ieaHeadImg;
        ImageView ieaIvVoiceLine;
        LinearLayout ieaLlSinger;
        TextView ieaTvVoicetime1;
        ImageView ieaIvRed;
    }

}
