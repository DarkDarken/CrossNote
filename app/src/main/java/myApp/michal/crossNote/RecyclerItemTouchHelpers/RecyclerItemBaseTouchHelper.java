package myApp.michal.crossNote.RecyclerItemTouchHelpers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerItemBaseTouchHelper extends ItemTouchHelper.SimpleCallback {

    public RecyclerItemBaseTouchHelper(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return super.getMovementFlags(recyclerView, viewHolder);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    public void setSth(Canvas c, View foregroundView, View backgroundView, int actionState,
                       float dX, float dY, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                       Boolean isCurrentlyActive, View delL, View delR){
        foregroundView.setBackgroundColor(Color.parseColor("#939393"));
        backgroundView.setBackgroundColor(Color.parseColor("#ee8934"));
        getDefaultUIUtil().onSelected(foregroundView);
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            if(dX > 0){
                delL.setVisibility(View.VISIBLE);
                delR.setVisibility(View.INVISIBLE);
            } else {
                delL.setVisibility(View.INVISIBLE);
                delR.setVisibility(View.VISIBLE);
            }
            if(dX == 0){
                clearView(recyclerView, viewHolder);
            }
        }
        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
    }
}
