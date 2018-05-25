package com.example.lhd.myonerowcode;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lhd.myonerowcode.entity.TestTwoItemsActivity;

import java.util.List;

/**
 * Created by lhd on 2018/5/10.
 */

public class TestTwoAdapter extends RecyclerView.Adapter<TestTwoAdapter.ViewHolder> {

    private List<TestTwoItemsActivity> myTestTwoList;

    static class ViewHolder extends RecyclerView.ViewHolder {

        View testTwoView;

        ImageView testTwoImageView;
        TextView testTwoTextViewName;
        TextView testTwoTextViewContent;

        public ViewHolder(View itemView) {
            super(itemView);

            testTwoView = itemView;

            testTwoImageView = (ImageView) itemView.findViewById(R.id.test_two_imageView);
            testTwoTextViewName = (TextView) itemView.findViewById(R.id.test_two_textView_1);
            testTwoTextViewContent = (TextView) itemView.findViewById(R.id.test_two_textView_2);
        }
    }

    public TestTwoAdapter(List<TestTwoItemsActivity> testOneList) {
        myTestTwoList = testOneList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_two_items_layout, parent, false);
//        ViewHolder viewHolder = new ViewHolder(view);

        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.testTwoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                TestTwoItemsActivity testTwoItemsActivity = myTestTwoList.get(position);
                Toast.makeText(view.getContext(), "点我干嘛，我是：" + testTwoItemsActivity.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.testTwoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                TestTwoItemsActivity testTwoItemsActivity = myTestTwoList.get(position);
                Toast.makeText(view.getContext(), "我是图片：" + testTwoItemsActivity.getContent(), Toast.LENGTH_SHORT).show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TestTwoItemsActivity testTwoItemsActivity = myTestTwoList.get(position);
        holder.testTwoImageView.setImageResource(testTwoItemsActivity.getImgId());
        holder.testTwoTextViewName.setText(testTwoItemsActivity.getName());
        holder.testTwoTextViewContent.setText(testTwoItemsActivity.getContent());
    }

    @Override
    public int getItemCount() {
        return myTestTwoList.size();
    }
}

