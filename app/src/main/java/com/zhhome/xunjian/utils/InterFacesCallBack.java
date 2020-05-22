package com.zhhome.xunjian.utils;

import android.view.View;


import java.util.ArrayList;

/**
 * Created by cheng on 2018/3/23.
 */

public class InterFacesCallBack {
    //recycleview  回掉
    public interface OnItemClickListener {
        void onItemClick(View view);
    }

    public interface guzhang{
        void guzhang(int type, int position);
    }


}
