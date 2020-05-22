package com.zhhome.xunjian.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by h on 2017/8/28.
 */

public class RecyclerViewSpaceItem extends RecyclerView.ItemDecoration{
    int mSpace;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = mSpace;
        }

    }

    public RecyclerViewSpaceItem(int space) {
        this.mSpace = space;
    }
}
