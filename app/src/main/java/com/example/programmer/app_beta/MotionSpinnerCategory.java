package com.example.programmer.app_beta;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class MotionSpinnerCategory extends BaseAdapter {

    LayoutInflater inflater;
    Context mContext;
    private MotionCategory category;

    public MotionSpinnerCategory(Context context, MotionCategory category) {
        mContext = context;
        this.category = category;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return MotionCategory.values().length;
    }

    @Override
    public MotionCategory getItem(int position) {
        return MotionCategory.values()[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.spinner_item, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.spItem);
        textView.setText(getItem(position).getName());
        textView.setTextSize(24);


        return convertView;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView text = new TextView(mContext);
        text.setTextColor(Color.WHITE);
        text.setTextSize(24);
        text.setFadingEdgeLength(12);
        text.setText(getItem(position).getName());
        text.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.backgroundspinner));

        return text;

    }
}
