package myApp.michal.crossNote.RecycleAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import myApp.michal.app_02.R;
import myApp.michal.crossNote.Code.Classes.MotionBuilder;
import myApp.michal.crossNote.Code.Enums.EMotionNames;

public class RecyclerViewListAdapter extends RecyclerView.Adapter<RecyclerViewListAdapter.MyViewHolder> {

    private Context context;
    private List<MotionBuilder> motionBuilders;
    private TrainingAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView repetition, category, weight;
        public RelativeLayout viewBackground;
        public RelativeLayout viewForeground;
        public ImageView del_R, del_L;

        public MyViewHolder(View view) {
                super(view);
                repetition = view.findViewById(R.id.repetition_view);
                category = view.findViewById(R.id.category_view);
                weight = view.findViewById(R.id.weight_view);
                del_L = view.findViewById(R.id.delete_icon_left_view);
                del_R = view.findViewById(R.id.delete_icon_right_view);
                viewBackground = view.findViewById(R.id.view_background_view);
                viewForeground = view.findViewById(R.id.view_foreground_view);

                view.setOnClickListener(v -> listener.onContactSelected(getAdapterPosition()));
            }
        }

        public RecyclerViewListAdapter(Context context, List<MotionBuilder> motionBuilders, TrainingAdapterListener listener) {
            this.context = context;
            this.listener = listener;
            this.motionBuilders = motionBuilders;
        }

        public RecyclerViewListAdapter(Context context, List<MotionBuilder> motionBuilders){
        this.context = context;
        this.motionBuilders = motionBuilders;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_list_item, parent, false);

            return new MyViewHolder(itemView);
        }


    @Override
        public void onBindViewHolder(RecyclerViewListAdapter.MyViewHolder holder, int position) {
        final MotionBuilder motionBuilder = motionBuilders.get(position);
        EMotionNames EMotionNames = motionBuilder.getEMotionNames();

        switch (EMotionNames) {
            case Row: case Airbike: case Run: {
                if (motionBuilder.isStatus()) {
                    holder.weight.setText(motionBuilder.getWeight() + " cals");
                } else {
                    holder.weight.setText(motionBuilder.getWeight() + " m");
                }
                holder.category.setText(motionBuilder.getEMotionNames().name().replace("_", " "));
                break;
            }
            case Handstand_walk: {
                holder.weight.setText(motionBuilder.getWeight() + " m");
                holder.category.setText(motionBuilder.getEMotionNames().name().replace("_", " "));
                break;
            }
            case Pull_up: case Chest_to_bar: case Box_jump: case Burpee: case Bar_muscle_up: case Air_squat: case HSPU:
            case Pistol: case Push_up: case Ring_dip: case Ring_muscle_up: case Rope_climb: case DU: case Sit_up: case Toe_to_bar: {
                holder.weight.setText("");
                holder.category.setText(motionBuilder.getEMotionNames().name().replace("_", " "));
                break;
            }
            case EMPTY: {
                holder.weight.setText("");
                holder.category.setText("");
                break;
            }
            default: {
                holder.weight.setText(motionBuilder.getWeight() + " kg");
                holder.category.setText(motionBuilder.getEMotionNames().name().replace("_", " "));
            }

        }
        holder.repetition.setText(motionBuilder.getRepetition());

    }

        @Override
        public int getItemCount() {
            return motionBuilders.size();
        }

        public boolean onItemMove(int fromPosition, int toPosition) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(motionBuilders, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(motionBuilders, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        public List<MotionBuilder> getBuilderList(){
            return motionBuilders;
        }

        public void removeItem(int position) {
            motionBuilders.remove(position);
            notifyItemRemoved(position);
        }

        public void restoreItem(MotionBuilder item, int position) {
            motionBuilders.add(position, item);
            notifyItemInserted(position);
        }

        public void restoreList(List<MotionBuilder> list, boolean shouldClear){
            if(shouldClear) {
                motionBuilders.clear();
                motionBuilders.addAll(list);
                notifyDataSetChanged();
            }
        }

        public interface TrainingAdapterListener {
            void onContactSelected(int position);
        }
}
