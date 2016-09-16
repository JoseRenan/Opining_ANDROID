package br.com.opining.helpers;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.opining.R;

public class AndroidHelper {

    private static final String TOKEN = "token";

    public static void showSnackbar(Activity activity, String msg) {

        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG);
        ViewGroup group = (ViewGroup) snackbar.getView();
        group.setBackgroundColor(ContextCompat.getColor(activity.getApplicationContext(), R.color.deepOrange));
        snackbar.show();
    }


    public static void showSnackbar(Activity activity, int msg) {
        showSnackbar(activity, activity.getApplicationContext().getString(msg));
    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context context, int msg) {
        showToast(context, context.getString(msg));
    }
}
