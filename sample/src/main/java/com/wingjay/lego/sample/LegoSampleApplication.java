package com.wingjay.lego.sample;

import android.app.Application;
import com.wingjay.lego.LegoAppViewHolderMapper;
import com.wingjay.lego.LegoManager;

/**
 * LegoSampleApplication
 *
 * @author wingjay
 * @date 2018/05/25
 */
public class LegoSampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LegoManager.init(new LegoAppViewHolderMapper());
    }
}
