package com.tomclaw.minion.demo.utils;

import android.app.Activity;
import android.os.Build;
import android.view.View;

/**
 * Created by solkin on 29.08.17.
 */
public class StatusBarHelper {

    public static void tintStatusBarIcons(Activity activity, boolean tintToDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = activity.getWindow().getDecorView();
            if (tintToDark) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                // We want to change tint color to white again.
                // You can also record the flags in advance so that you can turn UI back completely if
                // you have set other flags before, such as translucent or full screen.
                decor.setSystemUiVisibility(0);
            }
        }
    }
}
