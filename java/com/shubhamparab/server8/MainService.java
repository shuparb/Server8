package com.shubhamparab.server8;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Shubham on 20-06-2017.
 */
/**
    MainService: this is the main service running servers
 */
public class MainService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
