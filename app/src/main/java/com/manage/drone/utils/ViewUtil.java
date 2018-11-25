package com.manage.drone.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.manage.drone.MainActivity;
import com.manage.drone.fragment.ZoningFragment;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class ViewUtil {
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
    public static void showAlertDialog(final MainActivity context){
        if (context!=null){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Chọn khu vực khoanh vùng");
            builder.setMessage("Bạn chưa chọn khu vực khoanh vùng để thực hiện quan sát");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (context!=null){
                        context.switchFragment(ZoningFragment.newInstance());
                    }

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            alertDialog.setCancelable(false);
        }


    }

}
