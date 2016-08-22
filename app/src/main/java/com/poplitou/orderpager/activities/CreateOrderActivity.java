package com.poplitou.orderpager.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.poplitou.orderpager.R;
import com.poplitou.orderpager.models.Location;
import com.poplitou.orderpager.models.Order;
import com.poplitou.orderpager.utils.ServerTime;
import com.poplitou.orderpager.views.NumpadView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateOrderActivity extends AppCompatActivity {

    DatabaseReference mDatabase;

    int mTicket = 0;

    TextView mTicketNum;
    NumpadView mNumpad;
    Button mSubmit;

    CheckBox mCafeCheckbox;
    CheckBox mDeliCheckbox;
    CheckBox mKitchenCheckbox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //noinspection ConstantConditions
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mTicketNum = (TextView) findViewById(R.id.create_ticket_num);
        mNumpad = (NumpadView) findViewById(R.id.create_numpad);
        mSubmit = (Button) findViewById(R.id.create_submit);
        mCafeCheckbox = (CheckBox) findViewById(R.id.create_cafe_checkbox);
        mDeliCheckbox = (CheckBox) findViewById(R.id.create_deli_checkbox);
        mKitchenCheckbox = (CheckBox) findViewById(R.id.create_kitchen_checkbox);

        mSubmit.setEnabled(isComplete());

        mNumpad.setOnNumpadClickListener(new NumpadView.OnNumpadClickListener() {
            @Override
            public void onNumpadClickListener(int num) {
                mTicket = num;
                mTicketNum.setText(String.valueOf(num));
                mSubmit.setEnabled(isComplete());
            }
        });

        $(R.id.create_cafe_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCafeCheckbox.setChecked(!mCafeCheckbox.isChecked());
                mSubmit.setEnabled(isComplete());
            }
        });

        $(R.id.create_deli_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDeliCheckbox.setChecked(!mDeliCheckbox.isChecked());
                mSubmit.setEnabled(isComplete());
            }
        });

        $(R.id.create_kitchen_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mKitchenCheckbox.setChecked(!mKitchenCheckbox.isChecked());
                mSubmit.setEnabled(isComplete());
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    private boolean isComplete() {
        return mTicket != 0 &&
                (mCafeCheckbox.isChecked() || mDeliCheckbox.isChecked() || mKitchenCheckbox.isChecked());
    }

    private void submit() {

        String name = String.valueOf(mTicket);

        List<Location> locationList = new ArrayList<>();

        if(mKitchenCheckbox.isChecked()){
            locationList.add(new Location("kitchen", false));
        }
        if(mCafeCheckbox.isChecked()){
            locationList.add(new Location("cafe", false));
        }
        if(mDeliCheckbox.isChecked()){
            locationList.add(new Location("deli", false));
        }

        Order order = new Order(ServerTime.getServerTime(), name.toString(), locationList, 0);

        mDatabase.child(Order.ORDER_PATH)
                .child(String.valueOf(order.timestamp)).setValue(order);

        finish();
    }

    protected <T extends View> T $(@IdRes int id) {
        return (T) super.findViewById(id);
    }
}
