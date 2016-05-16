package yinlei.customize.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by wuyin on 2016/5/16.
 * 一个单例的toast
 */
public class SingleToast {

    public static void showToast(final Activity context, final String msg) {
        if ("main".equals(Thread.currentThread().getName())) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } else {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
