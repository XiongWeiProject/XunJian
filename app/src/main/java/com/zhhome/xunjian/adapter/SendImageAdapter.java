package com.zhhome.xunjian.adapter;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.zhhome.xunjian.R;
import com.zhhome.xunjian.utils.DensityUtil;
import com.zhhome.xunjian.utils.LogUtil;
import com.zhhome.xunjian.utils.ScreenUtil;

import org.devio.takephoto.model.TImage;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by zlc on 2017/5/25.
 */
public class SendImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter{

    private Activity mContext;
    private List<TImage> mDatas;
    private LayoutInflater mLayoutInflater;

    private static final int BODY_TYPE = 00002;
    private static final int FOOT_TYPE = 00003;
    private int footCount = 1;//尾部个数，后续可以自己拓展
    public SendImageAdapter(Activity context,List<TImage> datas){
        this.mContext = context;
        this.mDatas = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case BODY_TYPE:
                return new ViewHolder(mLayoutInflater.inflate(R.layout.send_grid_item, parent, false));
            case FOOT_TYPE:
                return new FootViewHolder(mLayoutInflater.inflate(R.layout.item_foot, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ((ViewHolder) holder).id_rl_img.getLayoutParams();
            int w = params.width = (int) ((ScreenUtil.getScreenWidth(mContext) - DensityUtil.dp2px(mContext,38)) / 3.0);
            params.height = w;
            params.bottomMargin = DensityUtil.dp2px(mContext,7);
            params.rightMargin = DensityUtil.dp2px(mContext,7);
            ((ViewHolder) holder).id_rl_img.setLayoutParams(params);
            LogUtil.e("mDatas.size()"+mDatas.size());
            if ((mDatas.size()<=8)) {
                ((ViewHolder) holder).im_delete.setVisibility(View.VISIBLE);
                //这里最好使用glide Picasso加载本地图片 bitmap容易造成内存溢出
                if ("1".equals(mDatas.get(position).getCompressPath())){
                    Picasso.with(mContext).load(R.drawable.video_bg).into(((ViewHolder) holder).im_img);
                }else {
                    Picasso.with(mContext).load(new File(mDatas.get(position).getCompressPath())).into(((ViewHolder) holder).im_img);
                }


            } else {
                ((ViewHolder) holder).im_delete.setVisibility(View.INVISIBLE);
                ((ViewHolder) holder).im_img.setVisibility(View.GONE);
            }
            ((ViewHolder) holder).im_img.setOnClickListener(new MyClickListener(((ViewHolder) holder)));
            ((ViewHolder) holder).im_delete.setOnClickListener(new MyClickListener(((ViewHolder) holder)));

        } else if (holder instanceof FootViewHolder) {
            ((FootViewHolder) holder).add_account.setOnClickListener(new MyClickListener1(((FootViewHolder) holder)));
//            ((FootViewHolder) holder).add_account.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }
    }


    @Override
    public int getItemCount() {
        return getBodySize() + footCount;
    }

    public List<TImage> getDatas() {
        return mDatas;
    }

    private int getBodySize() {
        return mDatas.size();
    }

    private boolean isFoot(int position) {
        return footCount != 0 && (position >= (getBodySize()));
    }
    @Override
    public int getItemViewType(int position) {
        if (isFoot(position)) {
            return FOOT_TYPE;
        } else {
            return BODY_TYPE;
        }
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView im_img;
        private final ImageView im_delete;
        private final FrameLayout id_rl_img;

        public ViewHolder(View itemView) {
            super(itemView);
            im_img = (ImageView) itemView.findViewById(R.id.iv_img);
            im_delete = (ImageView) itemView.findViewById(R.id.im_delete);
            id_rl_img = (FrameLayout) itemView.findViewById(R.id.id_rl_img);
        }
    }
    private static class FootViewHolder extends RecyclerView.ViewHolder {
        ImageView add_account;

        public FootViewHolder(View itemView) {
            super(itemView);
            add_account = (ImageView) itemView.findViewById(R.id.iv_add);
        }
    }
    //拖拽排序相关
    @Override
    public void onItemMove(int fromPos, int toPos) {
        if(fromPos == mDatas.size()-1 || toPos == mDatas.size()-1 )
            return;
        if (fromPos < toPos)
            //向下拖动
            for (int i = fromPos; i < toPos; i++) {
                Collections.swap(mDatas, i, i + 1);
            }
        else {
            //向上拖动
            for (int i = fromPos; i > toPos; i--) {
                Collections.swap(mDatas, i, i - 1);
            }
        }
        notifyItemMoved(fromPos,toPos);
    }

    //滑动删除相关
    @Override
    public void onItemDel(int pos) {
        if(pos == mDatas.size() - 1)
            return;
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }


    public interface OnClickListener{
        void onClick(View v, int position);
    }

    private static OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public static class MyClickListener implements View.OnClickListener{

        private ViewHolder mHolder;
        public MyClickListener(ViewHolder holder ){
            this.mHolder = holder;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.im_delete:
                case R.id.iv_img:
                    if(onClickListener!=null) {
                        int pos = mHolder.getAdapterPosition();
                        onClickListener.onClick(view, pos);
                    }
                    break;
            }
        }
    }

    public static class MyClickListener1 implements View.OnClickListener{

        private FootViewHolder mHolder;
        public MyClickListener1(FootViewHolder holder ){
            this.mHolder = holder;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.iv_add:
                    if(onClickListener!=null) {
                        int pos = mHolder.getAdapterPosition();
                        onClickListener.onClick(view, pos);
                    }
                    break;
            }
        }
    }
}