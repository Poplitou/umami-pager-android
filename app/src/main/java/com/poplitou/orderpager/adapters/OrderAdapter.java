package com.poplitou.orderpager.adapters;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.poplitou.orderpager.R;
import com.poplitou.orderpager.models.Location;
import com.poplitou.orderpager.models.Order;
import com.poplitou.orderpager.utils.ServerTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Shows order. Allows to be swiped.
 */
public class OrderAdapter extends FirebaseRecyclerAdapter<OrderAdapter.ViewHolder, Order> implements View.OnClickListener{

    DatabaseReference mDatabase;

    List<ViewHolder> mHolderList = new ArrayList<>();

    Handler mHandler = new Handler();

    //Update order timer for all active items
    Runnable mTimerRunnable = new Runnable() {

        @Override
        public void run() {
            for (ViewHolder holder : mHolderList) {
                int index = holder.getAdapterPosition();
                 
                if(index >=0) {
                    Order order = getItem(holder.getAdapterPosition());

                    long remainingTime = ServerTime.getServerTime() - order.timestamp;
                    int min = (int) (remainingTime / (60*1000));
                    int sec = ((int)remainingTime /1000) % (60);

                    holder.timerTextView.setText(new StringBuilder()
                            .append(min)
                            .append(":")
                            .append(String.format("%02d", sec)));
                }
            }
            
            mHandler.postDelayed(this, 1000);
        }

    };

    public static class ViewHolder extends RecyclerView.ViewHolder {

        FrameLayout background;
        RelativeLayout container;
        TextView titleTextView;
        TextView timerTextView;
        ImageView doneView;
        AppCompatImageButton kitchenButton;
        AppCompatImageButton cafeButton;
        AppCompatImageButton deliButton;

        public ViewHolder(View itemView) {
            super(itemView);

            background = (FrameLayout) itemView.findViewById(R.id.item_order_background);
            container = (RelativeLayout) itemView.findViewById(R.id.item_order_container);
            titleTextView = (TextView) itemView.findViewById(R.id.item_order_title);
            timerTextView = (TextView) itemView.findViewById(R.id.item_order_timer);
            doneView = (ImageView) itemView.findViewById(R.id.item_order_done);
            kitchenButton = (AppCompatImageButton) itemView.findViewById(R.id.item_order_btn_kitchen);
            cafeButton = (AppCompatImageButton) itemView.findViewById(R.id.item_order_btn_cafe);
            deliButton = (AppCompatImageButton) itemView.findViewById(R.id.item_order_btn_deli);

        }

        public View getContainer() {return container;}
    }

    public OrderAdapter(Query query) {
        this(query, null, null);
    }

    public OrderAdapter(Query query,  @Nullable ArrayList<Order> items, @Nullable ArrayList<String> keys) {
        super(query, Order.class, items, keys);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        //Start timer update function
        mHandler.post(mTimerRunnable);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Order order = getItem(position);

        holder.titleTextView.setText("Ticket " + order.name);

        long remainingTime = ServerTime.getServerTime() - order.timestamp;
        int min = (int) (remainingTime / (60*1000));
        int sec = ((int)remainingTime /1000) % (60);

        holder.timerTextView.setText(new StringBuilder()
                .append(min)
                .append(":")
                .append(String.format("%02d", sec)));

        //Add to list for timer update
        mHolderList.add(holder);

        Location kitchen = null;
        Location deli = null;
        Location cafe = null;

        for(Location location : order.locations) {
            switch (location.name){
                case "kitchen":
                    kitchen = location;
                    break;
                case "cafe":
                    cafe = location;
                    break;
                case "deli":
                    deli = location;
                    break;
            }
        }

        if(kitchen != null) {
            holder.kitchenButton.setVisibility(View.VISIBLE);
            if(kitchen.done) {
                holder.kitchenButton.setImageDrawable(
                        holder.kitchenButton.getResources().getDrawable(R.drawable.ic_check));
                holder.kitchenButton.setSupportBackgroundTintList(ColorStateList.valueOf( holder.kitchenButton.getResources().getColor(R.color.colorItemSwipedGreen)));
            } else {
                holder.kitchenButton.setImageDrawable(
                        holder.kitchenButton.getResources().getDrawable(R.drawable.ic_restaurant));
                holder.kitchenButton.setSupportBackgroundTintList(ColorStateList.valueOf( holder.kitchenButton.getResources().getColor(R.color.colorPrimary)));
            }
            holder.kitchenButton.setTag(R.id.tag_order, order);
            holder.kitchenButton.setTag(R.id.tag_location, kitchen);
            holder.kitchenButton.setOnClickListener(this);
        } else {
            holder.kitchenButton.setVisibility(View.GONE);
        }

        if(cafe != null) {
            holder.cafeButton.setVisibility(View.VISIBLE);
            if(cafe.done) {
                holder.cafeButton.setImageDrawable(
                        holder.cafeButton.getResources().getDrawable(R.drawable.ic_check));
                holder.cafeButton.setSupportBackgroundTintList(ColorStateList.valueOf( holder.cafeButton.getResources().getColor(R.color.colorItemSwipedGreen)));
            } else {
                holder.cafeButton.setImageDrawable(
                        holder.cafeButton.getResources().getDrawable(R.drawable.ic_cafe));
                holder.cafeButton.setSupportBackgroundTintList(ColorStateList.valueOf( holder.cafeButton.getResources().getColor(R.color.colorPrimary)));
            }
            holder.cafeButton.setTag(R.id.tag_order, order);
            holder.cafeButton.setTag(R.id.tag_location, cafe);
            holder.cafeButton.setOnClickListener(this);
        } else {
            holder.cafeButton.setVisibility(View.GONE);
        }

        if(deli != null) {
            holder.deliButton.setVisibility(View.VISIBLE);
            if(deli.done) {
                holder.deliButton.setImageDrawable(
                        holder.deliButton.getResources().getDrawable(R.drawable.ic_check));
                holder.deliButton.setSupportBackgroundTintList(ColorStateList.valueOf( holder.deliButton.getResources().getColor(R.color.colorItemSwipedGreen)));
            } else {
                holder.deliButton.setImageDrawable(
                        holder.deliButton.getResources().getDrawable(R.drawable.ic_deli));
                holder.deliButton.setSupportBackgroundTintList(ColorStateList.valueOf( holder.deliButton.getResources().getColor(R.color.colorPrimary)));
            }
            holder.deliButton.setTag(R.id.tag_order, order);
            holder.deliButton.setTag(R.id.tag_location, deli);
            holder.deliButton.setOnClickListener(this);
        } else {
            holder.deliButton.setVisibility(View.GONE);
        }

        if(order.done == order.locations.size()) {
            holder.background.setBackground(holder.background.getResources().getDrawable(R.drawable.background_item_swiped_green));
        } else {
            holder.background.setBackground(holder.background.getResources().getDrawable(R.drawable.background_item_swiped_red));
        }

        //Ensure that the container is visible
        holder.container.setTranslationX(0);
        holder.container.setTranslationY(0);

    }

    @Override
    public void onClick(View view) {
        int position = getPositionForItem((Order) view.getTag(R.id.tag_order));
        if(position != -1){
            Order order = getItem(position);
            Location location = (Location) view.getTag(R.id.tag_location);
            order.locations.remove(location);
            location.done = !location.done;
            order.done = location.done ? order.done + 1 : order.done - 1;
            order.locations.add(location);
            mDatabase.child(Order.ORDER_PATH)
                    .child(String.valueOf(order.timestamp)).setValue(order);
        }
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);

        //No need to update this view holder anymore
        mHolderList.remove(holder);
    }

    @Override
    public void destroy() {
        super.destroy();

        mHandler.removeCallbacks(mTimerRunnable);
        mHolderList.clear();

    }
}
