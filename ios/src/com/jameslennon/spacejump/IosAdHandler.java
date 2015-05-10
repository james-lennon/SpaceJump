package com.jameslennon.spacejump;

import com.jameslennon.spacejump.util.handlers.AdHandler;
import org.robovm.bindings.chartboost.*;

/**
 * Created by jameslennon on 5/10/15.
 */
public class IosAdHandler extends AdHandler {

    @Override
    public void init() {
        Chartboost.start("554fc70f04b01648b4d247df", "547428f67014890a6a5d7133321a765d0e763e6a", new ChartboostDelegateAdapter(){

        });
    }

    @Override
    public void show() {
        super.show();
    }
}
