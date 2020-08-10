package myApp.michal.crossNote.RecycleAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Code.Classes.MotionBuilder;
import myApp.michal.crossNote.Code.Classes.TrainingBuilder;
import myApp.michal.crossNote.Databases.DbHelper;

public class RecyclerMainListAdapter extends RecyclerView.Adapter<RecyclerMainListAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<TrainingBuilder> trainingList;
    private List<TrainingBuilder> trainingListFiltered;
    private TrainingAdapterListener listener;
    List<Integer> ids = new ArrayList<>();
    private DbHelper dbHelper;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView time, date, category;
        public RelativeLayout viewBackground, viewForeground;
        public ImageView del_R, del_L;

        public MyViewHolder(View view) {
            super(view);
            time = view.findViewById(R.id.timeCap);
            date = view.findViewById(R.id.date);
            category = view.findViewById(R.id.category);
            del_L = view.findViewById(R.id.delete_icon_left);
            del_R = view.findViewById(R.id.delete_icon_right);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);

            view.setOnClickListener(v -> listener.onContactSelected(getAdapterPosition()));
        }
    }

    public RecyclerMainListAdapter(Context context, List<TrainingBuilder> trainingList, TrainingAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.trainingList = trainingList;
        this.trainingListFiltered = trainingList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final TrainingBuilder trainingBuilder = trainingListFiltered.get(position);
        String time = trainingBuilder.getTime();
        switch (trainingBuilder.getEWorkoutNames()){
            case RFT: {
                holder.time.setText((Integer.parseInt(time) == 1) ? time +" round" : time + " rounds");
                break;
            }
            case Benchmark:{
                holder.time.setText(time);
                break;
            }
            default: {
                if(!time.isEmpty()) {
                    if(Character.isDigit(time.charAt(0))) {
                        holder.time.setText((Integer.parseInt(time) == 1) ? time + " minute" : time + " minutes");
                    }
                }
            }
        }
        holder.date.setText(trainingBuilder.getDate());
        holder.category.setText(trainingBuilder.getEWorkoutNames().name());
    }

    @Override
    public int getItemCount() {
        return trainingListFiltered.size();
    }

    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(trainingList, i, i + 1);

            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(trainingList, i, i - 1);

            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public List<TrainingBuilder> getBuilderList(){
        return trainingList;
    }

    public void removeItem(int position) {
        trainingList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(TrainingBuilder item, int position) {
        trainingList.add(position, item);
        notifyItemInserted(position);
    }

    public List<Integer> getIds() {
        return ids;
    }

    private String convertElemmentsToString(TrainingBuilder trainingBuilder){
        String temp = "";
        for(MotionBuilder item : trainingBuilder.getMotionList()){
            temp += item.getEMotionNames().name().replace("_", " ") + " ";
        }
        return temp;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                ids.clear();
                if (charString.isEmpty()) {
                    trainingListFiltered = trainingList;
                } else {
                    List<TrainingBuilder> filteredList = new ArrayList<>();
                    int size = trainingList.size();
                    for (int i = 0; i < size; i++) {
                        TrainingBuilder obj = trainingList.get(i);
                        if (trainingList.get(i).getDate().toLowerCase().contains(charString.toLowerCase())
                                || trainingList.get(i).getEWorkoutNames().name().toLowerCase().contains(charString.toLowerCase())
                                || trainingList.get(i).getTime().toLowerCase().contains(charString.toLowerCase())
                                || convertElemmentsToString(trainingList.get(i)).toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(obj);
                            ids.add(i);
                        }
                    }

                    trainingListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = trainingListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                trainingListFiltered = (ArrayList<TrainingBuilder>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface TrainingAdapterListener {
        void onContactSelected(int position);
    }
}

