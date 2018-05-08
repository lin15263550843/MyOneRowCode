package com.example.lhd.myonerowcode;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lhd.myonerowcode.common.TestOneItems;

import java.util.List;

/**
 * Created by lhd on 2018/5/7.
 */

public class TestOneAdapter extends ArrayAdapter<TestOneItems> {

    private int resourceId;

    public TestOneAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<TestOneItems> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

//    public TestOneAdapter(Context context, int textViewResourceId, List<TestOneItems> objects) {
//        super(context, textViewResourceId, objects);
//        resourceId = textViewResourceId;
//    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        TestOneItems testOneItems = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.testOneImageView = (ImageView) view.findViewById(R.id.test_one_imageView);
            viewHolder.testOneTextViewName = (TextView) view.findViewById(R.id.test_one_textView_1);
            viewHolder.testOneTextViewContent = (TextView) view.findViewById(R.id.test_one_textView_2);
            view.setTag(viewHolder);    //将viewHolder缓存到view中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();    //取出存在viewHolder中的
        }
//        ImageView testOneImageView = (ImageView) view.findViewById(R.id.test_one_imageView);
//        TextView testOneTextViewName = (TextView) view.findViewById(R.id.test_one_textView_1);
//        TextView testOneTextViewContent = (TextView) view.findViewById(R.id.test_one_textView_2);
//        testOneImageView.setImageResource(testOneItems.getImgId());
//        testOneTextViewName.setText(testOneItems.getName());
//        testOneTextViewContent.setText(testOneItems.getContent());
        viewHolder.testOneImageView.setImageResource(testOneItems.getImgId());
        viewHolder.testOneTextViewName.setText(testOneItems.getName());
        viewHolder.testOneTextViewContent.setText(testOneItems.getContent());

        return view;
    }

    private class ViewHolder {
        ImageView testOneImageView;
        TextView testOneTextViewName;
        TextView testOneTextViewContent;
    }
}
