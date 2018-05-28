package com.example.lhd.myonerowcode.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lhd.myonerowcode.R;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragmentTransaction`
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        Log.v(TAG, "onCreateView: 我是新的home_fragment，O(∩_∩)O哈哈哈~");
        return view;
    }

}
