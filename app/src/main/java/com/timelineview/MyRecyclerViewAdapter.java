package com.timelineview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Map;

/**
 * MyRecyclerViewAdapter is simple recycle adapter created for Recycle list view
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private final Animation animationUp;
    private final Animation animationDown;
    private Map<String, Integer> mData = null;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private View prevView = null; //previous selected view for undo expansion.

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, Map<String, Integer> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        animationUp = AnimationUtils.loadAnimation(context, R.anim.slide_up);
        animationDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);

    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);

    }

    // binds the data to the textview in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String row_name = (String) mData.keySet().toArray()[position];
        holder.myTextView.setText(row_name);
        holder.circleView.setCircleColor((int) mData.values().toArray()[position]);

        if(position == 0 )
            holder.circleView.setFirstElement();

        if(position == mData.size()-1)
            holder.circleView.setLastElement();

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return (String) mData.keySet().toArray()[id];
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {

        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(int position);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView myTextView;
        public final TimeLiner circleView;

        public ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvAnimalName);
            circleView = itemView.findViewById(R.id.circleDot);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(prevView!=null && prevView.equals(view)) {
                prevView = null;
                view.findViewById(R.id.tvAnimalNameExpanded).setVisibility(View.GONE);
                view.findViewById(R.id.tvAnimalNameExpanded).startAnimation(animationUp);

            }else {

                if (prevView != null) {
                    prevView.findViewById(R.id.tvAnimalNameExpanded).setVisibility(View.GONE);
                    prevView.findViewById(R.id.tvAnimalNameExpanded).findViewById(R.id.tvAnimalNameExpanded);
                    prevView.findViewById(R.id.tvAnimalNameExpanded).startAnimation(animationUp);
                }
                    view.findViewById(R.id.tvAnimalNameExpanded).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.tvAnimalNameExpanded).startAnimation(animationDown);



                if (mClickListener != null) mClickListener.onItemClick(getAdapterPosition());
                prevView = view;
            }
        }
    }
}