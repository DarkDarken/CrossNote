package myApp.michal.crossNote.RecycleAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import myApp.michal.crossNote.Code.Classes.PrRecordBuilder;
import myApp.michal.crossNote.Code.Enums.EBenchmarkNames;
import myApp.michal.crossNote.Databases.DbPrHelper;
import myApp.michal.app_02.R;

import java.util.Collections;
import java.util.List;

public class RecyclerPrViewListAdapter extends RecyclerView.Adapter<RecyclerPrViewListAdapter.MyViewHolder> {

    private Context context;
    private int mainPosition;
    private DbPrHelper dbPrHelper;
    private List<PrRecordBuilder> records;
    private EBenchmarkNames names;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, result;
        public RelativeLayout viewBackground, viewForeground;
        public ImageView del_R, del_L;

        public MyViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.date);
            result = view.findViewById(R.id.category);
            del_L = view.findViewById(R.id.delete_icon_left);
            del_R = view.findViewById(R.id.delete_icon_right);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }

    public RecyclerPrViewListAdapter(Context context, List<PrRecordBuilder> records, int mainPosition) {
        this.context = context;
        this.records = records;
        this.mainPosition = mainPosition;
        dbPrHelper = new DbPrHelper(context);
    }

    @Override
    public RecyclerPrViewListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pr_list_view_item, parent, false);

        return new RecyclerPrViewListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerPrViewListAdapter.MyViewHolder holder, int position) {
        final PrRecordBuilder record = records.get(position);
        Enum<?> achievementCategory = dbPrHelper.getAllPr().get(mainPosition).getMotionCategory();

        holder.date.setText(record.getDate());

        holder.result.setText(record.getResult() + achievementCategory.toString());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(records, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(records, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public List<PrRecordBuilder> getRecords(){
        return records;
    }

    public void removeItem(int position) {
        records.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(PrRecordBuilder item, int position) {
        records.add(position, item);
        notifyItemInserted(position);
    }
}
