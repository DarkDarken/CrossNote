package com.example.programmer.app_beta;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoryAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context mContext;
    private TrainingCategory category;

    public CategoryAdapter(Context context, TrainingCategory category) {
        mContext = context;
        this.category = category;
        inflater = LayoutInflater.from(mContext);
    }

        @Override
        public int getCount() {
            return TrainingCategory.values().length;
        }

        @Override
        public TrainingCategory getItem(int position) {
            return TrainingCategory.values()[position];
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


            return convertView;
        }
@Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
    TextView text = new TextView(mContext);
    text.setTextColor(Color.WHITE);
    text.setFadingEdgeLength(12);
    text.setTextSize(24);
    text.setText(getItem(position).getName());
    text.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.backgroundspinner));

    return text;

    }
    }
