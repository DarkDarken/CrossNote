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
import myApp.michal.crossNote.Code.Classes.AchievementBuilder;
import myApp.michal.crossNote.Code.Classes.PrRecordBuilder;

public class RecyclerPrMainListAdapter extends RecyclerView.Adapter<RecyclerPrMainListAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<AchievementBuilder> trainingList;
    private List<AchievementBuilder> trainingListFiltered;
    private RecyclerPrMainListAdapter.PrAdapterListener listener;
    List<Integer> ids = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, category;
        public RelativeLayout viewBackground, viewForeground;
        public ImageView del_R, del_L, image;

        public MyViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.date);
            category = view.findViewById(R.id.category);
            image = view.findViewById(R.id.imagePR);
            del_L = view.findViewById(R.id.delete_icon_left);
            del_R = view.findViewById(R.id.delete_icon_right);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);

            view.setOnClickListener(v -> listener.onContactSelected(getAdapterPosition()));
        }
    }

    public RecyclerPrMainListAdapter(Context context,
                                     List<AchievementBuilder> trainingList,
                                     RecyclerPrMainListAdapter.PrAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.trainingList = trainingList;
        this.trainingListFiltered = trainingList;
    }

    @Override
    public RecyclerPrMainListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pr_list_item, parent, false);

        return new RecyclerPrMainListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerPrMainListAdapter.MyViewHolder holder, int position) {
        final AchievementBuilder achievementBuilder = trainingListFiltered.get(position);
        int size = achievementBuilder.getPrRecordBuilderList().size();
        final PrRecordBuilder prRecordBuilder;
        if(size == 0){
            holder.date.setText("Empty");
        } else {
            prRecordBuilder = achievementBuilder.getPrRecordBuilderList().get(size-1);
            holder.date.setText(prRecordBuilder.getDate());
        }
        holder.category.setText(achievementBuilder.getMotionCategory().name().replace("_", " "));
        holder.image.setBackgroundResource(achievementBuilder.getImage());
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

    public List<AchievementBuilder> getBuilderList(){
        return trainingList;
    }

    public void removeItem(int position) {
        trainingList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(AchievementBuilder item, int position) {
        trainingList.add(position, item);
        notifyItemInserted(position);
    }

    public List<Integer> getIds() {
        return ids;
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
                    List<AchievementBuilder> filteredList = new ArrayList<>();
                    int size = trainingList.size();
                    for(int i = 0; i < size; i++){
                        AchievementBuilder obj = trainingList.get(i);
                        if (trainingList.get(i).getMotionCategory().name().toLowerCase().contains(charString.toLowerCase())) {
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
                trainingListFiltered = (ArrayList<AchievementBuilder>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface PrAdapterListener {
        void onContactSelected(int position);
    }

}
