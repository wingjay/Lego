package com.wingjay.lego.inner;

import android.util.Log;

/**
 * LegoLogger
 *
 * @author wingjay
 */
public class LegoLogger {

    private static final String TAG = "Lego";

    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void w(String msg) {
        Log.w(TAG, msg);
    }

    public static void d(String msg) {
        Log.d(TAG, msg);
    }
}
