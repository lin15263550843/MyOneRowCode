package com.example.lhd.myonerowcode.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.example.lhd.myonerowcode.R;
import com.example.lhd.myonerowcode.entity.TestTwoItemsActivity;

import java.util.List;

/**
 * Created by dhc on 2018/8/24.
 */

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "CardRecyclerViewAdapter";
    private Context mContext;
    private List<TestTwoItemsActivity> mCardList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView cardItemImage;
        TextView cardItemText;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            cardItemImage = (ImageView) itemView.findViewById(R.id.card_item_image);
            cardItemText = (TextView) itemView.findViewById(R.id.card_item_text);
        }
    }

//    public CardRecyclerViewAdapter(Context mContext) {
//        this.mContext = mContext;
//    }

    public CardRecyclerViewAdapter(List<TestTwoItemsActivity> mCardList) {
        this.mCardList = mCardList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + position);
        TestTwoItemsActivity ttia = mCardList.get(position);
        holder.cardItemText.setText(ttia.getName());
        // 首先调用 Glide.with() 方法并传入一个 Context、Activity 或 Fragment 参数，
        // 然后调用 load() 方法去加载图片，可以是一个URL地址，也可以是一个本地路径，或者是一个资源id，
        // 最后调用 into() 方法将图片设置到具体某一个 ImageView 中就可以了。
        // Glide 在内部做了许多非常复杂的逻辑操作，其中就包括了图片压缩，我们只需要安心按照 Glide 的标准=用法去加载图片就可以了。
        Glide.with(mContext).load(ttia.getImgId()).into(holder.cardItemImage);
//        holder.cardItemImage.setImageResource(ttia.getImgId());
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }

}
