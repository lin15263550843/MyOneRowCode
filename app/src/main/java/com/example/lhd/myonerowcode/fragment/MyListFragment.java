package com.example.lhd.myonerowcode.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lhd.myonerowcode.R;

public class MyListFragment extends Fragment {
    private static final String TAG = "MyListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.my_list_fragment, container, false);
        Log.v(TAG, "onCreateView: 我是新的my_list_fragment，O(∩_∩)O哈哈哈~");
        return view;
    }
}
