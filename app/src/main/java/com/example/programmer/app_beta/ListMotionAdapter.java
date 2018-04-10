package com.example.programmer.app_beta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListMotionAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private ArrayList<Motion> arraylist;

    public ListMotionAdapter(Context context, ArrayList<Motion> arraylist) {
        mContext = context;
        this.arraylist = arraylist;
        inflater = LayoutInflater.from(mContext);
    }

        public class ViewHolder {
            TextView repetition;
            TextView motion;
            TextView weight;
        }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Motion getItem(int position) {
        return arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ListMotionAdapter.ViewHolder holder;
        if (view == null) {
            holder = new ListMotionAdapter.ViewHolder();
            view = inflater.inflate(R.layout.list_item_motion, null);

            holder.repetition = (TextView) view.findViewById(R.id.repetition);
            holder.motion = (TextView) view.findViewById(R.id.motion);
            holder.weight = (TextView) view.findViewById(R.id.weight);
            view.setTag(holder);
        } else {
            holder = (ListMotionAdapter.ViewHolder) view.getTag();
        }

        holder.motion.setText(arraylist.get(position).getMotionCategory().getName());
        if(arraylist.get(position).getMotionCategory().getName() == "Row") {
            holder.weight.setText(arraylist.get(position).getWeight() + " cal");
            holder.repetition.setText(arraylist.get(position).getRepetition() + "");
        }
        else {
            holder.weight.setText(arraylist.get(position).getWeight() + " kg");
            holder.repetition.setText(arraylist.get(position).getRepetition() + "");
        }

        return view;
    }


}
