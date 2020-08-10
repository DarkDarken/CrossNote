package myApp.michal.crossNote.RecyclerItemTouchHelpers;

import android.graphics.Canvas;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import myApp.michal.crossNote.RecycleAdapters.RecyclerMainListAdapter;

public class RecyclerItemMainTouchHelper extends RecyclerItemBaseTouchHelper {

    private RecyclerItemTouchHelperListenerOnSwipe listenerOnSwipe;

    public RecyclerItemMainTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListenerOnSwipe listenerOnSwipe) {
        super(dragDirs, swipeDirs);
        this.listenerOnSwipe = listenerOnSwipe;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            final View foregroundView = ((RecyclerMainListAdapter.MyViewHolder) viewHolder).viewForeground;
            getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                    actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((RecyclerMainListAdapter.MyViewHolder) viewHolder).viewForeground;
        final View backgroundView = ((RecyclerMainListAdapter.MyViewHolder) viewHolder).viewBackground;
        final View delL = ((RecyclerMainListAdapter.MyViewHolder) viewHolder).del_L;
        final View delR = ((RecyclerMainListAdapter.MyViewHolder) viewHolder).del_R;
        getDefaultUIUtil().clearView(foregroundView);
        foregroundView.setBackgroundColor(0);
        backgroundView.setBackgroundColor(0);
        delL.setVisibility(View.INVISIBLE);
        delR.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((RecyclerMainListAdapter.MyViewHolder) viewHolder).viewForeground;
        final View backgroundView = ((RecyclerMainListAdapter.MyViewHolder) viewHolder).viewBackground;
        final View delL = ((RecyclerMainListAdapter.MyViewHolder) viewHolder).del_L;
        final View delR = ((RecyclerMainListAdapter.MyViewHolder) viewHolder).del_R;
        setSth(c, foregroundView, backgroundView, actionState, dX, dY, recyclerView, viewHolder, isCurrentlyActive, delL, delR);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listenerOnSwipe.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    public interface RecyclerItemTouchHelperListenerOnMove {
        boolean onMove(int firstPosition, int secondPosition);
    }
    public interface RecyclerItemTouchHelperListenerOnSwipe {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }

}

