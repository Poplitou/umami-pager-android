package com.poplitou.orderpager;

import android.app.Application;

import com.poplitou.orderpager.utils.ServerTime;

/**
 * Created by quent on 2016-08-21.
 */
public class OrderPagerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ServerTime.fetchOffset();

    }
}
