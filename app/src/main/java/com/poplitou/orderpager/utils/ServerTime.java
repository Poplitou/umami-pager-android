package com.poplitou.orderpager.utils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by quent on 2016-08-21.
 */
public class ServerTime {

    public static double offset = 0;

    private ServerTime(){}

    public static void fetchOffset() {
        DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset");
        offsetRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                offset = snapshot.getValue(Double.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });
    }

    public static long getServerTime() {
        return (long) (System.currentTimeMillis() + offset);
    }
}
