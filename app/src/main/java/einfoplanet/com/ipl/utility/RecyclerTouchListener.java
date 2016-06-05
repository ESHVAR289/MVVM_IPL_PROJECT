package einfoplanet.com.ipl.utility;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import einfoplanet.com.ipl.recyclerinterface.RecyclerClickListener;

/**
 * Created by bridgeit007 on 4/6/16.
 */

public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
    private GestureDetector gestureDetector;
    private RecyclerClickListener clickListener;

    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final RecyclerClickListener clickListener){
        this.clickListener=clickListener;
        gestureDetector=new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(), e.getY());
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
