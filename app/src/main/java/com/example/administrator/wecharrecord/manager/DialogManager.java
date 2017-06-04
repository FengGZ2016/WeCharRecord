package com.example.administrator.wecharrecord.manager;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.wecharrecord.R;

/**
 * Created by Administrator on 2017/6/4.
 * 录音的对话框
 */

public class DialogManager {
    private Dialog mDialog;
    private Context mContext;
    private View mView;
    private RelativeLayout bg_layout_one;
    private TextView tipsTxt_one;
    private RelativeLayout bg_layout_two;
    private TextView tipsTxt_two;
  //  private RelativeLayout
    public DialogManager(Context context){
        mContext=context;
    }

    public void showRecordingDialog(){
        mDialog=new Dialog(mContext, R.style.Theme_audioDialog);
        mView=LayoutInflater.from(mContext).inflate(R.layout.dialog_manager,null);
        mDialog.setContentView(mView);
        initView();
        mDialog.show();
    }

    private void initView() {
        bg_layout_one= (RelativeLayout) mView.findViewById(R.id.bg_layout_one);
        bg_layout_two= (RelativeLayout) mView.findViewById(R.id.bg_layout_two);
        tipsTxt_one= (TextView) mView.findViewById(R.id.dm_tv_txt_one);
        tipsTxt_two= (TextView) mView.findViewById(R.id.dm_tv_txt_two);
    }

    /**
     * 设置正在录音的dialog界面
     * */
    public void recording() {
        if (mDialog != null && mDialog.isShowing()) {
            bg_layout_one.setVisibility(View.VISIBLE);
            tipsTxt_one.setVisibility(View.VISIBLE);
            bg_layout_two.setVisibility(View.GONE);
            tipsTxt_two.setVisibility(View.GONE);
            bg_layout_one.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.yuyin_voice_1));
            tipsTxt_one.setText("手指上滑，取消发送");
        }
    }


   /**
    * 取消的界面
    * */
    public void wantToCancel() {
        if (mDialog != null && mDialog.isShowing()) {
            bg_layout_one.setVisibility(View.GONE);
            tipsTxt_one.setVisibility(View.GONE);
            bg_layout_two.setVisibility(View.VISIBLE);
            tipsTxt_two.setVisibility(View.VISIBLE);
            bg_layout_two.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.yuyin_cancel));
            tipsTxt_two.setText("松开手指，取消发送");
            tipsTxt_two.setBackgroundColor(mContext.getResources().getColor(R.color.colorRedBg));
        }
    }


    /**
     * 时间过短
     * */
    public void tooShort() {
        if (mDialog != null && mDialog.isShowing()) {
            bg_layout_two.setVisibility(View.VISIBLE);
            tipsTxt_two.setVisibility(View.VISIBLE);
            bg_layout_one.setVisibility(View.GONE);
            tipsTxt_one.setVisibility(View.GONE);
            bg_layout_two.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.yuyin_gantanhao));
            tipsTxt_two.setText("录音时间过短");
        }

    }


    /**
     * 隐藏dialog
     * */
    public void dimissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    public void updateVoiceLevel(int level) {
        if (level > 0 && level < 6) {

        } else {
            level = 5;
        }
        if (mDialog != null && mDialog.isShowing()) {
            //通过level来找到图片的id，也可以用switch来寻址，但是代码可能会比较长
            int resId = mContext.getResources().getIdentifier("yuyin_voice_" + level,
                    "drawable", mContext.getPackageName());
            bg_layout_one.setBackgroundResource(resId);
        }

    }

    public TextView getTipsTxt() {
        return tipsTxt_one;
    }

    public void setTipsTxt(TextView tipsTxt) {
      tipsTxt_one=tipsTxt;
    }

}
