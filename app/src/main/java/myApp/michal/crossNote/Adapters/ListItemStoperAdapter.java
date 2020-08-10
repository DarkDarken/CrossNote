package myApp.michal.crossNote.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Code.Classes.TrainingBuilder;

public class ListItemStoperAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    private List<TrainingBuilder> trainingslist = null;
    private ArrayList<TrainingBuilder> arraylist;

    public ListItemStoperAdapter(Context context, List<TrainingBuilder> trainingslist) {
        mContext = context;
        this.trainingslist = trainingslist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(trainingslist);
    }

    public class ViewHolder {
        TextView date;
        TextView time;
        TextView category;
    }

    @Override
    public int getCount() {
        return trainingslist.size();
    }

    @Override
    public TrainingBuilder getItem(int position) {
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
            view = inflater.inflate(R.layout.list_item_stoper_training, null);

            holder.date = view.findViewById(R.id.date);
            holder.time = view.findViewById(R.id.timeCap);
            holder.category = view.findViewById(R.id.category);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.date.setText(trainingslist.get(position).getDate());
        holder.category.setText(trainingslist.get(position).getEWorkoutNames().name());
        String time = trainingslist.get(position).getTime();
        holder.time.setText(new StringBuilder().append(time).append(trainingslist.get(position).getEWorkoutNames().getNameTime(time)));
        holder.category.setText(trainingslist.get(position).getEWorkoutNames().name());

        return view;
    }
}
