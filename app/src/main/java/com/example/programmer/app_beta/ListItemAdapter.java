package com.example.programmer.app_beta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ListItemAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Training> trainingslist = null;
    private ArrayList<Training> arraylist;

    public ListItemAdapter(Context context, List<Training> trainingslist) {
        mContext = context;
        this.trainingslist = trainingslist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Training>();
        this.arraylist.addAll(trainingslist);
    }

    public class ViewHolder {
        TextView date;
        TextView time;
        TextView category;
        CheckBox box;
    }

    @Override
    public int getCount() {
        return trainingslist.size();
    }

    @Override
    public Training getItem(int position) {
        return trainingslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_item_training, null);

            holder.date = (TextView) view.findViewById(R.id.date);
            holder.time = (TextView) view.findViewById(R.id.timeCap);
            holder.category = (TextView) view.findViewById(R.id.category);
            holder.box = (CheckBox) view.findViewById(R.id.boxItem);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.date.setText(trainingslist.get(position).getDate());
        holder.category.setText(trainingslist.get(position).getCategory().getName());
        String time = trainingslist.get(position).getTime();
        if(trainingslist.get(position).getCategory().getName() == "RFT"){
            if(time == "1") {
                holder.time.setText(trainingslist.get(position).getTime() + " " + "round");
            } else {
                holder.time.setText(trainingslist.get(position).getTime() + " " + "rounds");
            }
        } else if(trainingslist.get(position).getCategory().getName() == "Benchmark"){
            holder.time.setText(trainingslist.get(position).getTime() + "");
        } else{

            holder.time.setText(trainingslist.get(position).getTime() + " " + "min");
        }
        holder.category.setText(trainingslist.get(position).getCategory().getName());
        holder.box.setOnCheckedChangeListener(myBoxListener);
        holder.box.setTag(position);
        holder.box.setChecked(trainingslist.get(position).isBox());

        return view;
    }

    CompoundButton.OnCheckedChangeListener myBoxListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            int id = (Integer) compoundButton.getTag();
            TrainingDatabase.getTrainings().get(id).setBox(isChecked);

        }
    };

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        trainingslist.clear();
        if (charText.length() == 0) {
            trainingslist.addAll(arraylist);
        }
        else
        {
            for (Training wp : arraylist)
            {
                if (wp.getDate().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    trainingslist.add(wp);
                }
                if(wp.getCategory().getName().toLowerCase(Locale.getDefault()).contains(charText)){
                    trainingslist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
