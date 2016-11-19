package com.hackplan.androidarcmenu.demo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hackplan.androidarcmenu.ArcButton;
import com.hackplan.androidarcmenu.ArcMenu;
import com.hackplan.androidarcmenu.SimpleCirView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] mDataset;
    private ArcMenu.Builder builder;
    public static final int ARC_MENU_ID_3 = 3;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    public MyAdapter(String[] myDataset, Activity activity, ArcMenu.OnClickMenuListener listener) {
        mDataset = myDataset;
        builder = new ArcMenu.Builder(activity)
                .setId(ARC_MENU_ID_3)
                .addBtn(R.drawable.a, 0)
                .addBtn(R.drawable.r, 1)
                .addBtns(new ArcButton.Builder(new SimpleCirView(activity)
                                .setText("66")
                                .setCirColor(Color.parseColor("#2196F3")),
                                3))
                .setListener(listener)
                .hideOnTouchUp(false);
        builder.build();
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        TextView tv = (TextView)LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        return new ViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset[position]);
        builder.showOnLongClick(holder.mTextView);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
