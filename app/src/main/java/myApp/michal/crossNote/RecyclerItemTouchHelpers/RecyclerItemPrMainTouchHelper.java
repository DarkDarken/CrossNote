package myApp.michal.crossNote.RecyclerItemTouchHelpers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import myApp.michal.crossNote.RecycleAdapters.RecyclerPrMainListAdapter;

public class RecyclerItemPrMainTouchHelper extends RecyclerItemBaseTouchHelper {

    private RecyclerItemMainTouchHelper.RecyclerItemTouchHelperListenerOnSwipe listenerOnSwipe;

    public RecyclerItemPrMainTouchHelper(int dragDirs, int swipeDirs, RecyclerItemMainTouchHelper.RecyclerItemTouchHelperListenerOnSwipe listenerOnSwipe) {
        super(dragDirs, swipeDirs);
        this.listenerOnSwipe = listenerOnSwipe;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            final View foregroundView = ((RecyclerPrMainListAdapter.MyViewHolder) viewHolder).viewForeground;
            final View backgroundView = ((RecyclerPrMainListAdapter.MyViewHolder) viewHolder).viewBackground;
            final View delL = ((RecyclerPrMainListAdapter.MyViewHolder) viewHolder).del_L;
            final View delR = ((RecyclerPrMainListAdapter.MyViewHolder) viewHolder).del_R;
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
        final View foregroundView = ((RecyclerPrMainListAdapter.MyViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((RecyclerPrMainListAdapter.MyViewHolder) viewHolder).viewForeground;
        final View backgroundView = ((RecyclerPrMainListAdapter.MyViewHolder) viewHolder).viewBackground;
        final View delL = ((RecyclerPrMainListAdapter.MyViewHolder) viewHolder).del_L;
        final View delR = ((RecyclerPrMainListAdapter.MyViewHolder) viewHolder).del_R;
        getDefaultUIUtil().clearView(foregroundView);
        foregroundView.setBackgroundColor(0);
        backgroundView.setBackgroundColor(0);
        delL.setVisibility(View.INVISIBLE);
        delR.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((RecyclerPrMainListAdapter.MyViewHolder) viewHolder).viewForeground;
        final View backgroundView = ((RecyclerPrMainListAdapter.MyViewHolder) viewHolder).viewBackground;
        final View delL = ((RecyclerPrMainListAdapter.MyViewHolder) viewHolder).del_L;
        final View delR = ((RecyclerPrMainListAdapter.MyViewHolder) viewHolder).del_R;
        setSth(c, foregroundView, backgroundView, actionState, dX, dY, recyclerView, viewHolder, isCurrentlyActive, delL, delR);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listenerOnSwipe.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }
}
