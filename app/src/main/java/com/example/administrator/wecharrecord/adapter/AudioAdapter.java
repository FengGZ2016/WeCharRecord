package com.example.administrator.wecharrecord.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.wecharrecord.R;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view;
        if (convertView==null){
            view= LayoutInflater.from(mContext).inflate(R.layout.item_example_activity,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.ieaHeadImg = (ImageView) convertView.findViewById(R.id.iea_headImg);
            viewHolder.ieaIvVoiceLine = (ImageView) convertView.findViewById(R.id.iea_iv_voiceLine);
            viewHolder.ieaLlSinger = (LinearLayout) convertView.findViewById(R.id.iea_ll_singer);
            viewHolder.ieaTvVoicetime1 = (TextView) convertView.findViewById(R.id.iea_tv_voicetime1);
            viewHolder.ieaIvRed = (ImageView) convertView.findViewById(R.id.iea_iv_red);
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

            }
        });
        return null;
    }

    class ViewHolder {
        ImageView ieaHeadImg;
        ImageView ieaIvVoiceLine;
        LinearLayout ieaLlSinger;
        TextView ieaTvVoicetime1;
        ImageView ieaIvRed;
    }

}
