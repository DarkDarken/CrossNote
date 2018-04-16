package com.example.programmer.app_beta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
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

        String name = arraylist.get(position).getMotionCategory().getName();

        holder.motion.setText(arraylist.get(position).getMotionCategory().getName());
        if("Airbike" == name || "Row" == name) {
            holder.weight.setText(arraylist.get(position).getRepetition() + " cal");
            holder.repetition.setText("");
        } else if("Run" == name || "Handstand walk" == name){
            holder.weight.setText("");
            holder.repetition.setText(arraylist.get(position).getWeight() + " m");
        } else if("Pull-up" == name || "Chest to bar" == name || "Box jump" == name || "Burpee" == name || "Bar muscle up" == name || "Air squat" == name ||
        "HSPU" == name || "Pistol" == name || "Push-up" == name || "Ring dip" == name || "Ring muscle up" == name || "Rope climb" == name || "Sit-up" == name ||
                "Toe to bar" == name ){
            holder.weight.setText("");
            holder.repetition.setText(arraylist.get(position).getRepetition());
        } else {
            holder.weight.setText(arraylist.get(position).getWeight() + " kg");
            holder.repetition.setText(arraylist.get(position).getRepetition());
        }

        return view;
    }


}
