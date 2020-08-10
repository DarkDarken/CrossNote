package myApp.michal.crossNote.RecyclerItemTouchHelpers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import myApp.michal.crossNote.RecycleAdapters.RecyclerViewListAdapter;

public class RecyclerItemViewTouchHelper extends RecyclerItemBaseTouchHelper {

    private RecyclerItemMainTouchHelper.RecyclerItemTouchHelperListenerOnMove listenerOnMove;
    private RecyclerItemMainTouchHelper.RecyclerItemTouchHelperListenerOnSwipe listenerOnSwipe;

    public RecyclerItemViewTouchHelper(int dragDirs, int swipeDirs,
                                       RecyclerItemMainTouchHelper.RecyclerItemTouchHelperListenerOnSwipe listenerOnSwipe,
                                       RecyclerItemMainTouchHelper.RecyclerItemTouchHelperListenerOnMove listenerOnMove) {
        super(dragDirs, swipeDirs);
        this.listenerOnSwipe = listenerOnSwipe;
        this.listenerOnMove = listenerOnMove;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        final View backgroundView = ((RecyclerViewListAdapter.MyViewHolder) viewHolder).viewBackground;
        final View delL = ((RecyclerViewListAdapter.MyViewHolder) viewHolder).del_L;
        final View delR = ((RecyclerViewListAdapter.MyViewHolder) viewHolder).del_R;
        backgroundView.setBackgroundColor(0);
        delL.setVisibility(View.INVISIBLE);
        delR.setVisibility(View.INVISIBLE);
        listenerOnMove.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            final View foregroundView = ((RecyclerViewListAdapter.MyViewHolder) viewHolder).viewForeground;
            final View backgroundView = ((RecyclerViewListAdapter.MyViewHolder) viewHolder).viewBackground;
            final View delL = ((RecyclerViewListAdapter.MyViewHolder) viewHolder).del_L;
            final View delR = ((RecyclerViewListAdapter.MyViewHolder) viewHolder).del_R;
            getDefaultUIUtil().onSelected(foregroundView);
            foregroundView.setBackgroundColor(Color.parseColor("#939393"));
            backgroundView.setBackgroundColor(Color.parseColor("#ee8934"));
            delL.setVisibility(View.VISIBLE);
            delR.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((RecyclerViewListAdapter.MyViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((RecyclerViewListAdapter.MyViewHolder) viewHolder).viewForeground;
        final View backgroundView = ((RecyclerViewListAdapter.MyViewHolder) viewHolder).viewBackground;
        final View delL = ((RecyclerViewListAdapter.MyViewHolder) viewHolder).del_L;
        final View delR = ((RecyclerViewListAdapter.MyViewHolder) viewHolder).del_R;
        getDefaultUIUtil().clearView(foregroundView);
        foregroundView.setBackgroundColor(0);
        backgroundView.setBackgroundColor(0);
        delL.setVisibility(View.INVISIBLE);
        delR.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((RecyclerViewListAdapter.MyViewHolder) viewHolder).viewForeground;
        final View backgroundView = ((RecyclerViewListAdapter.MyViewHolder) viewHolder).viewBackground;
        final View delL = ((RecyclerViewListAdapter.MyViewHolder) viewHolder).del_L;
        final View delR = ((RecyclerViewListAdapter.MyViewHolder) viewHolder).del_R;
        setSth(c, foregroundView, backgroundView, actionState, dX, dY, recyclerView, viewHolder, isCurrentlyActive, delL, delR);
        if(actionState == ItemTouchHelper.ACTION_STATE_DRAG){
            clearView(recyclerView, viewHolder);
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listenerOnSwipe.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }
}
